package com.StudShare;

import com.StudShare.service.NoteManagerDAO;
import com.StudShare.service.SiteUserManagerDao;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(StudShareApplication.class)
@Rollback(value = true)
@Transactional
public class NoteManagerTest {
/*
    @Autowired
    SiteUserManagerDao siteUserManager;

    @Autowired
    NoteManagerDAO noteManager;
/*
//dopisac notatke zdjeciowa
    @Test
    public void checkAddNote()
    {
        SiteUser user = siteUserManager.addSiteUser(new SiteUser("kamil", "password", "salt", "example1@com.pl"));
        SiteUser userToTests = siteUserManager.findSiteUserById(user);

        Note note = noteManager.addNote(new Note(user, "notka", "tytuł"));
        Note noteToTests = noteManager.findNoteByID(note);

        assertNotNull(userToTests);
        assertEquals(noteToTests.getIdNote(), note.getIdNote());
        assertEquals(noteToTests.getTextcontent(), note.getTextcontent());
        assertEquals(noteToTests.getTitle(), note.getTitle());

    }

    @Test
    public void CheckUpdateNote(){
        SiteUser user = siteUserManager.addSiteUser(new SiteUser("kamil", "password", "salt", "example1@com.pl"));
        Note note = noteManager.addNote(new Note(user, "notka", "tytuł"));

        note.setSiteUser(user);
        note.setTextcontent("notatka_tekstowa");
        note.setTitle("brak");

        Note noteToTest = noteManager.updateNote(note);

        assertNotNull(noteToTest);
        assertEquals(noteToTest.getSiteUser(), note.getSiteUser());
        assertEquals(noteToTest.getTextcontent(), "notatka_tekstowa");
        assertEquals(noteToTest.getTitle(), "brak");

    }

    @Test
    public void CheckFindNoteByID() {
        SiteUser user = siteUserManager.addSiteUser(new SiteUser("kamil", "password", "salt", "example1@com.pl"));
        SiteUser userToTests = siteUserManager.findSiteUserById(user);

        Note note = noteManager.addNote(new Note(user, "notka", "tytuł"));
        Note noteToTests = noteManager.findNoteByID(note);

        assertEquals(noteToTests.getIdNote(), note.getIdNote());
        assertEquals(noteToTests.getSiteUser(), note.getSiteUser());
        assertEquals(noteToTests.getTextcontent(), note.getTextcontent());
        assertEquals(noteToTests.getTitle(), note.getTitle());
    }

    @Test
    public void CheckFindNoteByTitle(){
        SiteUser user = siteUserManager.addSiteUser(new SiteUser("kamil", "password", "salt", "example1@com.pl"));
        SiteUser userToTests = siteUserManager.findSiteUserById(user);

        Note note = noteManager.addNote(new Note(user, "notka", "tytuł"));
        Note noteToTests = noteManager.findNoteByTitle("tytuł");

        assertEquals(noteToTests.getIdNote(), note.getIdNote());
        assertEquals(noteToTests.getSiteUser(), note.getSiteUser());
        assertEquals(noteToTests.getTextcontent(), note.getTextcontent());
        assertEquals(noteToTests.getTitle(), note.getTitle());
    }


    /*@Test
    public void CheckFindNoteByIDSiteUser(){
        SiteUser user = siteUserManager.addSiteUser(new SiteUser("kamil", "password", "salt", "example1@com.pl"));
        SiteUser userToTests = siteUserManager.findSiteUserById(user);

        Note note = noteManager.addNote(new Note(user, "notka", "tytuł"));
        Note noteToTests = noteManager.findNoteByTitle("notka");

        assertEquals(noteToTests.getIdNote(), note.getIdNote());
        assertEquals(noteToTests.getSiteUser(), note.getSiteUser());
        assertEquals(noteToTests.getTextcontent(), note.getTextcontent());
        assertEquals(noteToTests.getTitle(), note.getTitle());

    }*/



    }



