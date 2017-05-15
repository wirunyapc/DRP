package com.drpweb;


import com.drpweb.security.RunkeeperUserInfoTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

/**
 * Created by ADMIN on 12/15/2016.
 */
@RestController
@CrossOrigin(origins = "*")
public class RunkeeperController  {


    @Autowired
    @Qualifier("runkeeperRestTemplate")
    private RestTemplate restTemplate;


    //@CrossOrigin(origins = "http://localhost:8080")
    @RequestMapping(value = "/callback", method = RequestMethod.POST)
    public void runKeeperCallback(@RequestBody String response)
            throws IOException {
        System.out.println("Response" + response);
    }

    //@CrossOrigin(origins = "http://localhost:3000")
    @RequestMapping(value = "/connectRunKeeper", method = RequestMethod.GET)
    public String connectRunKeeper()
            throws IOException {
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
*/
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String actInfo  = RunkeeperUserInfoTokenService.getActivities();
        String activity ="";
        if(actInfo == ""){
            return null;
        }
        return actInfo;
    }


    public String index(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Accept", "  Accept: application/vnd.com.runkeeper.FitnessActivityFeed+json");

        HttpEntity requestEntity = new HttpEntity<>(headers);

        return restTemplate.exchange("https://api.runkeeper.com/fitnessActivities", HttpMethod.GET,requestEntity,Object.class).getBody().toString();
    }
}
