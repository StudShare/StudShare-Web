package com.StudShare.rest.logging;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
    public Response login(@FormDataParam("login")String login,
                          @FormDataParam("password") String password,
                          @FormDataParam("authToken") String authToken)
            throws NoSuchProviderException, NoSuchAlgorithmException, JsonProcessingException
    {

        return loginAuthenticator.login(login, password, authToken).build();
    }


    @POST
    @Path("/logout")
    public Response logout(@FormDataParam("login")String login,
                           @FormDataParam("authToken") String authToken) throws GeneralSecurityException
    {

        return loginAuthenticator.logout(login, authToken).build();
    }


}
