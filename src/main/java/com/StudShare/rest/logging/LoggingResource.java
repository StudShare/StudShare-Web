package com.StudShare.rest.logging;

import com.StudShare.rest.HTTPHeaderNames;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;


@Path("/user")
@ComponentScan(basePackageClasses = LoginAuthenticator.class)
public class LoggingResource
{

    @Autowired
    LoginAuthenticator loginAuthenticator;

    @POST
    @Path("/login")
    public Response login(@Context HttpHeaders httpHeaders)
            throws NoSuchProviderException, NoSuchAlgorithmException, JsonProcessingException
    {
        String login = httpHeaders.getHeaderString(HTTPHeaderNames.LOGIN);
        String password = httpHeaders.getHeaderString(HTTPHeaderNames.PASSWORD);
        String ssid = httpHeaders.getHeaderString(HTTPHeaderNames.SSID);

        return loginAuthenticator.login(login, password, ssid).build();


    }


    @POST
    @Path("/logout")
    public Response logout(@Context HttpHeaders httpHeaders) throws GeneralSecurityException
    {

        String login = httpHeaders.getHeaderString(HTTPHeaderNames.LOGIN);
        String authToken = httpHeaders.getHeaderString(HTTPHeaderNames.AUTH_TOKEN);

        return loginAuthenticator.logout(login, authToken).build();


    }


}
