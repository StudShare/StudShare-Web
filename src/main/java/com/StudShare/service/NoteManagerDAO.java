package com.StudShare.service;


import com.StudShare.domain.Note;
import com.StudShare.domain.SiteUser;
import org.hibernate.SessionFactory;

import java.util.List;

public interface NoteManagerDAO {

    SessionFactory getSessionFactory();

    void setSessionFactory(SessionFactory sessionFactory);


    Note addNote (Note note);

    void deleteNote(Note note);

    Note findNoteByID(Note note);

    Note findNoteByTitle(String title);

    List<Note> getBasicInfoNoteByTag(String value);

    Note findNoteByIDSiteUser(long idSiteUser);

    Note updateNote(Note note);

    List<Note> getAllUserNotesByID(long idSiteUser);

    List<Note> getAllUserBasicNotesByLogin(String login);

    Object getAllUserPictureNotesByID(long idNote);
}
