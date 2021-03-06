package com.StudShare.rest.registration;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URISyntaxException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@Path("/user")
@ComponentScan(basePackageClasses = {RegistrationHelper.class, ConfirmationRegistrationHelper.class})
public class RegistrationResource
{
    @Autowired
    RegistrationHelper registrationHelper;

    @Autowired
    ConfirmationRegistrationHelper confirmRegistrationHelper;

    @POST
    @Path("/register")
    public Response registration(@FormDataParam("login") String login,
                                 @FormDataParam("email") String email,
                                 @FormDataParam("repeatEmail") String repeatEmail,
                                 @FormDataParam("password") String password,
                                 @FormDataParam("repeatPassword") String repeatPassword,
                                 @Context UriInfo uriInfo) throws NoSuchProviderException, NoSuchAlgorithmException, URISyntaxException
    {
        System.out.println("login = [" + login + "], email = [" + email + "], repeatEmail = [" + repeatEmail + "], password = [" + password + "], repeatPassword = [" + repeatPassword + "], uriInfo = [" + uriInfo + "]");
        return registrationHelper.registerUser(login, email, repeatEmail, password, repeatPassword, uriInfo).build();
    }

    @GET
    @Path("/register")
    public Response confirmationRegistration(@QueryParam("u") long idSiteUser,
                                             @QueryParam("activation_key") String activationKey,
                                             @Context HttpServletRequest httpServletRequest) throws URISyntaxException
    {

        StringBuilder url = new StringBuilder();
        url.append(httpServletRequest.getScheme())
                .append("://").append(httpServletRequest.getServerName());

        if (httpServletRequest.getServerPort() != 80 && httpServletRequest.getServerPort() != 443)
        {
            url.append(":").append(httpServletRequest.getServerPort());
        }


        return confirmRegistrationHelper.confirmRegisterUser(idSiteUser, activationKey, url.toString()).build();
    }


}
