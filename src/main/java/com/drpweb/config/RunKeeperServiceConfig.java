package com.drpweb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

@Configuration
public class RunKeeperServiceConfig {
    @Autowired
    @Qualifier("runkeeperResourceDetails")
    AuthorizationCodeResourceDetails oAuth2ProtectedResourceDetails;
    @Autowired
    OAuth2ClientContext oAuth2ClientContext;

    @Bean(name = "runkeeperRestTemplate")
    public OAuth2RestTemplate runkeeperRestTemplate(){
        return new OAuth2RestTemplate(oAuth2ProtectedResourceDetails, oAuth2ClientContext);
    }


}
