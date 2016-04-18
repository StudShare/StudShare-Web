package com.StudShare.rest.account;

import com.StudShare.domain.SiteUser;
import com.StudShare.rest.logging.LoginAuthenticator;
import com.StudShare.rest.registration.RegistrationHelper;
import com.StudShare.service.SiteUserManagerDao;
import com.StudShare.utils.PasswordService;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.regex.Pattern;

@Path("/user")
@ComponentScan(basePackageClasses = { LoginAuthenticator.class, SiteUserManagerDao.class })
public class AccountResource
{
    @Autowired
    SiteUserManagerDao siteUserManager;

    @Autowired
    LoginAuthenticator loginAuthenticator;


    SiteUser siteUser;
    PasswordService passwordService = new PasswordService();


    @POST
    @Path("/changePassword")
    public Response changePassword(@FormDataParam("login") String login,
                                   @FormDataParam("authToken") String authToken,
                                   @FormDataParam("password") String password,
                                   @FormDataParam("newPassword") String newPassword,
                                   @FormDataParam("repeatNewPassword") String repeatNewPassword)
            throws NoSuchProviderException, NoSuchAlgorithmException
    {
        if(!loginAuthenticator.isAuthTokenValid(login, authToken))
        {
            NewCookie loginC = new NewCookie(new Cookie("login", "", "/", ""),"", 0, false);
            NewCookie auth_tokenC = new NewCookie(new Cookie("auth_token", "", "/", ""),"", 0, false);
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Musisz byc zalogowanym, aby zmienic haslo")
                    .cookie(loginC, auth_tokenC)
                    .build();
        }

        if(password == null || newPassword == null || repeatNewPassword == null)
            return Response.status(Response.Status.NO_CONTENT).build();

        siteUser = siteUserManager.findSiteUserByLogin(login);
        String passwordToCheck = passwordService.getSecurePassword(password, siteUser.getSalt());

        if (!siteUser.getHash().equals(passwordToCheck))
            return Response.status(Response.Status.UNAUTHORIZED).entity("Haslo nie prawidlowe!").build();
        if(!newPassword.equals(repeatNewPassword))
            return Response.status(Response.Status.BAD_REQUEST).build();
        if(newPassword.length() < 6)
            return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Nowe haslo musi mieć conajmniej 6 liter.").build();


        String newSalt = passwordService.generateSalt();
        String newHash = passwordService.getSecurePassword(newPassword, newSalt);

        siteUser.setHash(newHash);
        siteUser.setSalt(newSalt);
        siteUserManager.updateUser(siteUser);

        return Response.status(Response.Status.OK).build();

    }

    @POST
    @Path("/changeEmail")
    public Response changeEmail(@FormDataParam("login") String login,
                                @FormDataParam("authToken") String authToken,
                                @FormDataParam("email") String email,
                                @FormDataParam("newEmail") String newEmail,
                                @FormDataParam("repeatNewEmail") String repeatNewEmail)
            throws NoSuchProviderException, NoSuchAlgorithmException
    {

        if(!loginAuthenticator.isAuthTokenValid(login, authToken))
        {
            NewCookie loginC = new NewCookie(new Cookie("login", "", "/", ""),"", 0, false);
            NewCookie auth_tokenC = new NewCookie(new Cookie("auth_token", "", "/", ""),"", 0, false);
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Musisz byc zalogowanym, aby zmienic email")
                    .cookie(loginC, auth_tokenC)
                    .build();
        }

        if(email == null || newEmail == null || repeatNewEmail == null)
            return Response.status(Response.Status.NO_CONTENT).build();

        siteUser = siteUserManager.findSiteUserByLogin(login);

        if (!siteUser.getEmail().equals(email))
            return Response.status(Response.Status.UNAUTHORIZED).entity("Email nie prawidlowy!").build();
        if(!newEmail.equals(repeatNewEmail))
            return Response.status(Response.Status.BAD_REQUEST).build();
        if(!checkEmail(newEmail))
            return  Response.status(Response.Status.BAD_REQUEST).entity("Wpisny email posiada nieprawidlowe znaki").build();
        if(siteUserManager.findSiteUserByEmail(newEmail) != null)
            return  Response.status(Response.Status.BAD_REQUEST).entity("Email jest zajety!").build();

        siteUser.setEmail(newEmail);
        siteUserManager.updateUser(siteUser);

        return Response.status(Response.Status.OK).build();

    }

    private boolean checkEmail(String email)
    {
        return Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(email).matches();
    }

}
