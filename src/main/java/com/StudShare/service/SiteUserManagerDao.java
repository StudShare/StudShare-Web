package com.StudShare.service;

import com.StudShare.domain.Note;
import com.StudShare.domain.SiteUser;
import org.hibernate.SessionFactory;

import java.util.List;

public interface SiteUserManagerDao
{
    SessionFactory getSessionFactory();

    void setSessionFactory(SessionFactory sessionFactory);

    SiteUser addSiteUser(SiteUser siteSiteUser);

    void deleteSiteUser(SiteUser siteSiteUser);

    SiteUser updateUser(SiteUser siteUser);

    SiteUser findSiteUserByLogin(String login);

    List<Note> findSiteUserByLoginWithNotes(String login);

    SiteUser findSiteUserByEmail(String email);

    SiteUser findSiteUserById(SiteUser siteSiteUser);

}
