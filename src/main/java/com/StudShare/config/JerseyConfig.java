package com.StudShare.config;

import org.apache.catalina.SessionListener;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.ApplicationPath;


@Configuration
@ApplicationPath("/*")
public class JerseyConfig extends ResourceConfig
{
    private final String REST_PACKAGE = "com.StudShare.rest";
    private final String SERVLET_MAPPING = "/rest/*";

    public JerseyConfig()
    {
       // register(JAXBContextResolver.class);
        //register(JacksonFeature.class);
        register(MultiPartFeature.class);
        register(SessionListener.class);
        packages(REST_PACKAGE);
    }



    @Bean
    public ServletRegistrationBean jerseyServlet()
    {
        ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), SERVLET_MAPPING);
        registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, JerseyConfig.class.getName());
        return registration;
    }
}