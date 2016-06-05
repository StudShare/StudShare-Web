package com.StudShare.rest.note;

import com.StudShare.domain.*;


import com.StudShare.rest.logging.LoginAuthenticator;
import com.StudShare.service.*;
import org.apache.commons.io.IOUtils;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.query.Param;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Path("/note")
@ComponentScan(basePackageClasses = { NoteHelper.class, SiteUserManagerDao.class, NoteManagerDAO.class, LoginAuthenticator.class})
public class NoteResource {

    @Autowired
    NoteHelper notehelper;

    @Autowired
    private SiteUserManagerDao siteUserManager;

    @Autowired
    private NoteManagerDAO noteManager;

    @Autowired
    RateManagerDAO rateManager;

    @Autowired
    private LoginAuthenticator loginAuthenticator;

    Note noteByID = new Note();
    Note note = new Note();
    SiteUser siteUser = new SiteUser();

    @POST
    @Path("/addNote")
    @Consumes(value = {MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA})
    public Response addNote(@FormDataParam("login") String login,
                            @FormDataParam("authToken") String authToken,
                            @FormDataParam("picturecontent") InputStream picturecontent,
                            @FormDataParam("textcontent") String textcontent,
                            @FormDataParam("filecontent")InputStream filecontent,
                            @FormDataParam("title") String title,
                            @FormDataParam("tagsValues") String tagsValues) throws IOException
    {

        if(!loginAuthenticator.isAuthTokenValid(login, authToken))
        {
            NewCookie loginC = new NewCookie(new Cookie("login", "", "/", ""),"", 0, false);
            NewCookie auth_tokenC = new NewCookie(new Cookie("auth_token", "", "/", ""),"", 0, false);
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Musisz byc zalogowanym, aby dodac notatke")
                    .cookie(loginC, auth_tokenC)
                    .build();
        }


        System.out.println("\n\n\n\n\n");
        System.out.println(filecontent);
        System.out.println(picturecontent);

        System.out.println("\n\n\n\n\n");

        siteUser = siteUserManager.findSiteUserByLogin(login);

        if(siteUser.isEnabled() == false)
            return Response.status(Response.Status.BAD_REQUEST).build();

/*

        if(!notehelper.checkNoteContent(title, picturecontent, textcontent, filecontent, tagsValues))
            return Response.status(Response.Status.NO_CONTENT).build();
*/


/*        if (picturecontent != null && textcontent.length() > 0)
            note = new Note(siteUser, textcontent, IOUtils.toByteArray(picturecontent), title, "both");
        else */
        if(picturecontent != null && textcontent.length() == 0)
        {
            note = new Note(siteUser, null, IOUtils.toByteArray(picturecontent), null, title, "photo");
        }
        else if (filecontent!= null && textcontent.length() == 0){
            note = new Note(siteUser, null, null, IOUtils.toByteArray(filecontent), title, "pdf");
        }
        else {
            note = new Note(siteUser, textcontent, null, null, title, "text");

        }

        List<String> values = Arrays.asList(tagsValues.split("\\s*,\\s*"));
        if(values.size() < 1)
            return Response.status(Response.Status.NOT_FOUND).entity("Dodaj Minimum 1 tag.").build();
        if(values.size() > 10)
            return Response.status(Response.Status.NOT_FOUND).entity("Mozesz dodac maksimum 10 tagow").build();

        note.setTags(notehelper.addTagsToNote(values));

        noteManager.addNote(note);
        return Response.status(Response.Status.CREATED).build();
    }


    @POST
    @Path("/deleteNote")
    @Consumes(value = {MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA})
    public Response deleteNote(@FormDataParam("idNote") long idNote,
                               @FormDataParam("login") String login,
                               @FormDataParam("authToken") String authToken) throws IOException
    {
        //pobiera id notatki, login zalogowanego usera, jezeli login zalogowanego = login w notce, wtedy usun, else blad

        if(!loginAuthenticator.isAuthTokenValid(login, authToken))
        {
            NewCookie loginC = new NewCookie(new Cookie("login", "", "/", ""),"", 0, false);
            NewCookie auth_tokenC = new NewCookie(new Cookie("auth_token", "", "/", ""),"", 0, false);
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Musisz byc zalogowanym, aby usunac notatke")
                    .cookie(loginC, auth_tokenC)
                    .build();
        }

        siteUser = siteUserManager.findSiteUserByLogin(login);
        noteByID.setIdNote(idNote);

        if(!notehelper.checkExistingAndOwnerNote(noteManager.findNoteByID(noteByID), siteUser))
            return Response.status(Response.Status.BAD_REQUEST).build();
        System.out.println("\n\n\n\n");


        List<Rate> rates = rateManager.findRatesByIdNote(idNote);

        for (int i = 0; i < rates.size(); i++) {

            rateManager.deleteAll(idNote);

        }

        noteManager.deleteNote(noteByID);
        return Response.status(Response.Status.OK).build();

    }

    @POST
    @Path("/updateNote")
    public Response updateNote(@FormDataParam("idNote") long idNote,
                               @FormDataParam("login") String login,
                               @FormDataParam("authToken") String authToken,
                               @FormDataParam("picturecontent")InputStream picturecontent,
                               @FormDataParam("textcontent") String textcontent,
                               @FormDataParam("filecontent")InputStream filecontent,
                               @FormDataParam("title") String title,
                               @FormDataParam("tagsValues") String tagsValues)  throws IOException
    {

        if(!loginAuthenticator.isAuthTokenValid(login, authToken))
        {
            NewCookie loginC = new NewCookie(new Cookie("login", "", "/", ""),"", 0, false);
            NewCookie auth_tokenC = new NewCookie(new Cookie("auth_token", "", "/", ""),"", 0, false);
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("Musisz byc zalogowanym, aby zmienic notatke")
                    .cookie(loginC, auth_tokenC)
                    .build();
        }


/*        if(!notehelper.checkNoteContent(title, picturecontent, textcontent, filecontent, tagsValues))
            return Response.status(Response.Status.NO_CONTENT).build();*/

        siteUser = siteUserManager.findSiteUserByLogin(login);
        note.setIdNote(idNote);
        noteByID = noteManager.findNoteByID(note);

        if(!notehelper.checkExistingAndOwnerNote(noteByID, siteUser))
            return Response.status(Response.Status.BAD_REQUEST).build();

        noteByID.setSiteUser(siteUser);
        noteByID.setTitle(title);
        noteByID.setTextcontent(null);

        if (picturecontent != null)
            noteByID.setPicturecontent(IOUtils.toByteArray(picturecontent));
        if(textcontent.length() > 0)
            noteByID.setTextcontent(textcontent);

/*
//edit TODO
        if(noteByID.getPicturecontent().length > 0 && noteByID.getTextcontent() != null)
            noteByID.setType("both");
        else
        if(noteByID.getPicturecontent().length > 0 && noteByID.getTextcontent() == null)
            noteByID.setType("photo");
        else
            noteByID.setType("text");
*/

        List<String> values = Arrays.asList(tagsValues.split("\\s*,\\s*"));

        if(values.size() < 1)
            return Response.status(Response.Status.NOT_FOUND).entity("Dodaj Minimum 1 tag.").build();
        else if(values.size() > 10)
            return Response.status(Response.Status.NOT_FOUND).entity("Mozesz dodac maksimum 10 tagow").build();


        noteByID.setTags(notehelper.addTagsToNote(values));

        noteManager.updateNote(noteByID);

        return Response.status(Response.Status.OK).entity("Zaktualizowano!").build();
    }

    @GET
    @Path("/getAllUserNotes/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Note> getAllUserNotes(@PathParam ("login") String login)
    {
        return siteUserManager.findSiteUserByLoginWithNotes(login);
    }

    @GET
    @Path("/getAllUserBasicNotes/{login}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Note> getAllUserBasicNotes(@PathParam ("login") String login)
    {
        return noteManager.getAllUserBasicNotesByLogin(login);
    }

    @GET
    @Path("/getAllUserPictureNotes/{idNote}")
    @Produces(MediaType.APPLICATION_JSON)
    public Object getAllUserPictureNotes(@PathParam ("idNote") long idNote)
    {
        return noteManager.getAllUserPictureNotesByID(idNote);
    }


    @GET
    @Path("/getUserNoteByID/{idNote}")
    @Produces(MediaType.APPLICATION_JSON)
    public Object getUserNoteByID(@PathParam ("idNote") long idNote)
    {
        noteByID.setIdNote(idNote);
        return  noteManager.findNoteByID(noteByID);
    }

    @GET
    @Path("/getNotesByTags")
    @Produces("application/json")
    public Response getNotesByTags(@QueryParam("tags") List<String> tags)
    {
        if(tags.size() == 0)
            return Response.status(Response.Status.NOT_FOUND).build();

        List<Note> foundNotes;
        List<Note> notes = new ArrayList<Note>();

        for(String tag : tags)
        {
            foundNotes = noteManager.getBasicInfoNoteByTag(tag);
            for(Note n : foundNotes)
            {
                if(!notes.contains(n))
                    notes.add(n);
            }
        }

        return Response.status(Response.Status.OK).entity(notes).build();
    }


}
