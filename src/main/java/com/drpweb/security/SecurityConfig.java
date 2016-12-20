package com.drpweb.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Asus on 7/8/2559.
 */
@Configuration
@EnableWebSecurity
@EnableOAuth2Client
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    OAuth2ClientContext oauth2ClientContext;
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws
            Exception {
        auth.userDetailsService(customUserDetailsService);
    }

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AuthenticationTokenProcessingFilter authenticationTokenProcessingFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()

                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                //open h2
                .and().headers().frameOptions().disable()
                .and()
                .addFilterBefore(ssoFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(authenticationTokenProcessingFilter,UsernamePasswordAuthenticationFilter.class);



    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/css/**", "/img/**", "/js/**");
    }

    @Bean(name="myAuthenticationManager")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }
    private Filter ssoFilter() {
        OAuth2ClientAuthenticationProcessingFilter runkeeperFilter = new OAuth2ClientAuthenticationProcessingFilter("/login/runkeeper");
        OAuth2RestTemplate runkeeperTemplate = new OAuth2RestTemplate(runkeeper(), oauth2ClientContext);
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type","application/vnd.com.runkeeper.ErrorMessage+json");
//        runkeeperTemplate.headForHeaders(runkeeperResource().getUserInfoUri(),headers);
        runkeeperFilter.setRestTemplate(runkeeperTemplate);
        runkeeperFilter.setTokenServices(new RunkeeperUserInfoTokenService(runkeeperResource().getUserInfoUri(), runkeeper().getClientId()));
        return runkeeperFilter;
    }
    @Bean(name = "runkeeperResourceDetails")
    @ConfigurationProperties("runkeeper.client")
    public AuthorizationCodeResourceDetails runkeeper() {
        return new AuthorizationCodeResourceDetails();
    }
    @Bean
    @ConfigurationProperties("runkeeper.resource")
    public ResourceServerProperties runkeeperResource() {
        return new ResourceServerProperties();
    }


    @Bean
    public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }
}
