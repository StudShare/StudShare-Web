package com.StudShare.service;

import com.StudShare.config.HibernateConfig;
import com.StudShare.domain.Note;
import com.StudShare.domain.SiteUser;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("noteManager")
@Rollback(value = false)
@Transactional
@ComponentScan(basePackageClasses = HibernateConfig.class)
public class NoteManagerImpl implements NoteManagerDAO {


    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }

    @Override
    public void setSessionFactory(SessionFactory sessionFactory)
    {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public Note addNote (Note note) {
        long idNote = ((Long) sessionFactory.getCurrentSession().save(note)).longValue();
        note.setIdNote(idNote);
        return note;
    }

    public void deleteNote(Note note) {
        sessionFactory.getCurrentSession().delete(note);
    }


    public Note findNoteByID(Note note) {
        return sessionFactory.getCurrentSession().get(Note.class, note.getIdNote());
    }

    public Note findNoteByIDSiteUser(long idSiteUser){
        return (Note) sessionFactory.getCurrentSession().getNamedQuery("note.getNoteByIDSiteUser").setLong("idSiteUser", idSiteUser).uniqueResult();
    }

    public Note findNoteByTitle(String title) {
        return (Note) sessionFactory.getCurrentSession().getNamedQuery("note.getNoteByTitle").setString("title", title).uniqueResult();
    }

    public List<Note> getBasicInfoNoteByTag(String value)
    {
        return sessionFactory.getCurrentSession().getNamedQuery("note.getBasicInfoNotesByTag").setString("value", value).list();
    }

    public Note updateNote(Note note){

        return (Note) sessionFactory.getCurrentSession().merge(note);
    }


    public List<Note> getAllUserNotesByID(long idSiteUser){
        return sessionFactory.getCurrentSession().getNamedQuery("note.getNoteByIDSiteUser").setLong("idSiteUser", idSiteUser).list();
    }


    public List<Note> getAllUserBasicNotesByLogin(String login)
    {
        return sessionFactory.getCurrentSession().getNamedQuery("note.getBacisInfo").setString("login", login).list();
    }

    public Object getAllUserPictureNotesByID(long idNote)
    {
        return  sessionFactory.getCurrentSession().getNamedQuery("note.getPicturecontent").setLong("idNote", idNote).uniqueResult();
    }

}


