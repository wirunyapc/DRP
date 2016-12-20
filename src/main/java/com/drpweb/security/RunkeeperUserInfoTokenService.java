package com.drpweb.security;

import com.google.gson.Gson;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedAuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedPrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.util.Assert;

import java.util.*;

/**
 * Created by ADMIN on 12/15/2016.
 */


public class RunkeeperUserInfoTokenService implements ResourceServerTokenServices {
    protected final Log logger = LogFactory.getLog(this.getClass());
    private final String userInfoEndpointUrl;
    private final String clientId;
    private OAuth2RestOperations restTemplate;
    private String tokenType = "Bearer";
    private AuthoritiesExtractor authoritiesExtractor = new FixedAuthoritiesExtractor();
    private PrincipalExtractor principalExtractor = new FixedPrincipalExtractor();
    private String accessToken = "";
    public static String activitiesInfo;
    public RunkeeperUserInfoTokenService(String userInfoEndpointUrl, String clientId) {
        this.userInfoEndpointUrl = userInfoEndpointUrl;
        this.clientId = clientId;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setRestTemplate(OAuth2RestOperations restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void setAuthoritiesExtractor(AuthoritiesExtractor authoritiesExtractor) {
        Assert.notNull(authoritiesExtractor, "AuthoritiesExtractor must not be null");
        this.authoritiesExtractor = authoritiesExtractor;
    }

    public void setPrincipalExtractor(PrincipalExtractor principalExtractor) {
        Assert.notNull(principalExtractor, "PrincipalExtractor must not be null");
        this.principalExtractor = principalExtractor;
    }

    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        Map map = this.getMap(this.userInfoEndpointUrl, accessToken);
        if(map.containsKey("error")) {
            this.logger.debug("userinfo returned error: " + map.get("error"));
            throw new InvalidTokenException(accessToken);
        } else {
            return this.extractAuthentication(map);
        }
    }

    private OAuth2Authentication extractAuthentication(Map<String, Object> map) {
        Object principal = this.getPrincipal(map);
        List authorities = this.authoritiesExtractor.extractAuthorities(map);
        OAuth2Request request = new OAuth2Request((Map)null, this.clientId, (Collection)null, true, (Set)null, (Set)null, (String)null, (Set)null, (Map)null);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
        token.setDetails(map);
        return new OAuth2Authentication(request, token);
    }

    protected Object getPrincipal(Map<String, Object> map) {
        Object principal = this.principalExtractor.extractPrincipal(map);
        return principal == null?"unknown":principal;
    }

    public OAuth2AccessToken readAccessToken(String accessToken) {
        throw new UnsupportedOperationException("Not supported: read access token");
    }

    private Map<String, Object> getMap(String path, String accessToken) {
        this.logger.info("Getting user info from: " + path);

        try {
            Object ex = this.restTemplate;
            if(ex == null) {
                BaseOAuth2ProtectedResourceDetails existingToken = new BaseOAuth2ProtectedResourceDetails();
                existingToken.setClientId(this.clientId);
                ex = new OAuth2RestTemplate(existingToken);
            }
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer "+accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Accept", "application/vnd.com.runkeeper.User+json");

            HttpEntity requestEntity = new HttpEntity<>(headers);

// Create a new RestTemplate instance
            OAuth2AccessToken existingToken1 = ((OAuth2RestOperations)ex).getOAuth2ClientContext().getAccessToken();
            if(existingToken1 == null || !accessToken.equals(existingToken1.getValue())) {
                DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(accessToken);
                token.setTokenType(this.tokenType);
                ((OAuth2RestOperations)ex).getOAuth2ClientContext().setAccessToken(token);
            }
            Map user = (Map)((OAuth2RestOperations)ex).exchange(path, HttpMethod.GET,requestEntity, Object.class).getBody();
            user.forEach((k,v) -> System.out.println(k.toString() + ":" + v.toString()));
            HttpHeaders headers2 = new HttpHeaders();
            headers2.set("Authorization", "Bearer "+accessToken);
            headers2.setContentType(MediaType.APPLICATION_JSON);
            headers2.set("Accept", "application/vnd.com.runkeeper.FitnessActivityFeed+json");
            HttpEntity requestEntity2 = new HttpEntity<>(headers2);
           try {
               Map activities = (Map)((OAuth2RestOperations)ex).exchange("https://api.runkeeper.com/fitnessActivities", HttpMethod.GET,requestEntity2, Object.class).getBody();
               activities.forEach((k,v) -> System.out.println(k.toString() + ":" + v.toString()));
                activitiesInfo = "";

               Gson gson = new Gson();
                String actJson = gson.toJson(activities);
               activitiesInfo = actJson;
           }
           catch (Exception e){
               System.out.println(e.getMessage());
               e.printStackTrace();
           }
            return (Map)((OAuth2RestOperations)ex).exchange(path, HttpMethod.GET,requestEntity, Object.class).getBody();
        } catch (Exception var6) {
            this.logger.info("Could not fetch user details: " + var6.getClass() + ", " + var6.getMessage());
            return Collections.singletonMap("error", "Could not fetch user details");
        }
    }

    public static String getActivities(){
        return activitiesInfo;
    }
    /*
    @CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/connectRunKeeper", method = RequestMethod.GET)
    public ArrayList<String> connectRunKeeper() throws IOException {

        /*
        //content type must be set to text/event-stream
        response.setContentType("text/event-stream");
        //cache must be set to no-cache
        response.setHeader("Cache-Control", "no-cache");
        //encoding is set to UTF-8
        response.setCharacterEncoding("UTF-8");

        PrintWriter writer = response.getWriter();

        AsyncContext actx = request.startAsync();
        actx.setTimeout(30*1000);

            String result = "";
            actx.getResponse().getWriter().write( result);
            actx.getResponse().getWriter().flush();
           try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        actx.complete();
        writer.close();

        ArrayList<String> actInfo = new ArrayList<String>();
        if(activitiesInfo != null){

            return activitiesInfo;
        }
        for (String s : actInfo ) {
            System.out.println("Activity info"+s);
        }
        return actInfo;
    }*/

}
