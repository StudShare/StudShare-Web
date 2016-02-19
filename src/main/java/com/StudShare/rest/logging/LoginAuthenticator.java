package com.StudShare.rest.logging;


import com.StudShare.domain.LogToken;
import com.StudShare.domain.SiteUser;
import com.StudShare.service.LogTokenManagerDao;
import com.StudShare.service.SiteUserManagerDao;
import com.StudShare.utils.NoCacheResponse;
import com.StudShare.utils.PasswordService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.security.GeneralSecurityException;
import java.util.*;

@Component(value = "loginAuthenticator")
@ComponentScan(basePackageClasses = {LogTokenManagerDao.class, SiteUserManagerDao.class, NoCacheResponse.class})
public final class LoginAuthenticator
{

    @Autowired
    private LogTokenManagerDao logTokenManager;

    @Autowired
    private SiteUserManagerDao siteUserManager;

    @Autowired
    private NoCacheResponse noCacheResponse;

    public LogTokenManagerDao getLogTokenManager()
    {
        return logTokenManager;
    }

    public SiteUserManagerDao getSiteUserManager()
    {
        return siteUserManager;
    }

    public Response.ResponseBuilder login(String login, String password, String ssid) throws JsonProcessingException
    {
        logTokenManager.deleteExpiredLogTokens();

        SiteUser siteUser;
        LogToken logTokenExist;
        PasswordService passwordMatcher;
        String passwordToCheck, authToken;


        if (login == null || login.length() == 0 || password == null || password.length() == 0 )
            return noCacheResponse.getNoCacheResponseBuilder(Response.Status.UNAUTHORIZED).entity("Nie uzupelniles wszystkich pol!");

        siteUser = siteUserManager.findSiteUserByLogin(login);
        if(siteUser == null)
            return noCacheResponse.getNoCacheResponseBuilder(Response.Status.UNAUTHORIZED).entity("Nie ma konta o podanej nazwie uzytkownika");


        /*if(logTokenManager.findLogTokensByIdSiteUser(siteUser.getIdSiteUser()).size() >= 3)
            return noCacheResponse.getNoCacheResponseBuilder(Response.Status.BAD_REQUEST).entity("Osiągnąłeś limit zalogowań");*/

        if(logTokenManager.findLogTokenBySSID(ssid) != null)
            return noCacheResponse.getNoCacheResponseBuilder(Response.Status.BAD_REQUEST).entity("Jesteś już zalogowny");


        passwordMatcher = new PasswordService();
        passwordToCheck = passwordMatcher.getSecurePassword(password, siteUser.getSalt());

        if (!siteUser.getHash().equals(passwordToCheck))
            return noCacheResponse.getNoCacheResponseBuilder(Response.Status.UNAUTHORIZED).entity("Haslo nie prawidlowe!");



        do
        {
            authToken = UUID.randomUUID().toString();
            logTokenExist = logTokenManager.findLogTokenBySSID(authToken);
        }
        while (logTokenExist != null);

        LogToken logToken = new LogToken(authToken, siteUser);
        logTokenManager.addLogToken(logToken);

        NewCookie loginC = new NewCookie(new Cookie("login", login, "/", ""),"", 60*60*2, false);
        NewCookie auth_tokenC = new NewCookie(new Cookie("auth_token", authToken, "/", ""),"", 60*60*2, false);

        return noCacheResponse.getNoCacheResponseBuilder(Response.Status.OK).cookie(loginC, auth_tokenC);
    }


    public Response.ResponseBuilder logout(String login, String ssid) throws GeneralSecurityException
    {

        LogToken logToken = logTokenManager.findLogTokenBySSID(ssid);

        logTokenManager.deleteLogToken(logToken);
        NewCookie loginC = new NewCookie(new Cookie("login", null, "/", ""),"", 0, false);
        NewCookie auth_tokenC = new NewCookie(new Cookie("auth_token", null, "/", ""),"", 0, false);

        return noCacheResponse.getNoCacheResponseBuilder(Response.Status.NO_CONTENT).cookie(loginC, auth_tokenC);

    }


    public boolean isAuthTokenValid(String login, String ssid)
    {
        SiteUser siteUser = siteUserManager.findSiteUserByLogin(login);
        LogToken logToken = logTokenManager.findLogTokenBySSID(ssid);

        if (logToken == null || siteUser == null)
            return false;
        if (!(logToken.getSiteUser().getIdSiteUser() == siteUser.getIdSiteUser()))
            return false;

        return true;

    }
}