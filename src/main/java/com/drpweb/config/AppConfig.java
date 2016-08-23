//package com.drpweb.config;
//
//import org.springframework.context.annotation.*;
//import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import org.springframework.web.servlet.view.JstlView;
//import org.springframework.web.servlet.view.UrlBasedViewResolver;
//
///**
// * Created by Asus on 7/8/2559.
// */
//@EnableWebMvc
//@Configuration
//@ComponentScan(basePackages = {"com.drpweb"})
//@EnableAspectJAutoProxy
//public class AppConfig extends WebMvcConfigurerAdapter {
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/**").addResourceLocations("/views/").setCachePeriod(31556926);
//        registry.addResourceHandler("/js/**").addResourceLocations("/views/js/").setCachePeriod(31556926);
//        registry.addResourceHandler("/bower_components/**").addResourceLocations("/views/bower_components/");
//    }
//
//    @Override
//    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//        configurer.enable();
//    }
//    @Bean
//    public UrlBasedViewResolver setupViewResolver() {
//        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
//        resolver.setPrefix("/views/");
//        resolver.setSuffix(".jsp");
//        resolver.setViewClass(JstlView.class);
//        return resolver;
//    }
//}
