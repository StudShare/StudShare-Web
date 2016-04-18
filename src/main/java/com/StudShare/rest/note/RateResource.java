package com.StudShare.rest.note;


import com.StudShare.domain.Rate;
import com.StudShare.domain.Note;
import com.StudShare.domain.SiteUser;
import com.StudShare.service.NoteManagerDAO;
import com.StudShare.service.RateManagerDAO;
import com.StudShare.service.SiteUserManagerDao;
import com.StudShare.service.TagManagerDAO;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Path("/rate")
@ComponentScan(basePackageClasses = {TagManagerDAO.class})
public class RateResource
{
    @Autowired
    RateManagerDAO rateManager;

    @Autowired
    private NoteManagerDAO noteManager;


    @Autowired
    private SiteUserManagerDao siteUserManager;

    Rate rate = new Rate();
    Note noteByID = new Note();
    Note note = new Note();
    SiteUser siteUser = new SiteUser();

    @GET
    @Path("/getRatesByIdNote/{idNote}")
    @Produces({ MediaType.APPLICATION_JSON})
    public List<Rate> getTagsByIdNote(@PathParam("idNote") long idNote)
    {
        return rateManager.findRatesByIdNote(idNote);
    }



    @POST
    @Path("/addRate")
    @Consumes(value = {MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA})
    public Response addNote(@FormDataParam("value") short value,
                            @FormDataParam("idNote") long idNote,
                            @FormDataParam("login") String login,
                            @FormDataParam("authToken") String authToken) throws IOException
    {
        noteByID.setIdNote(idNote);
        note = noteManager.findNoteByID(noteByID);
        siteUser = siteUserManager.findSiteUserByLogin(login);


        if (value<0 || value>5)
        {
            System.out.println("nieprawidlowa wartosc");
        }
        else {
            rate.setNote(note);
            rate.setValue(value);
            rate.setSiteUser(siteUser);

            rateManager.addRate(rate);

            System.out.println("\n\n\n\n\n");
            System.out.println(idNote);
            System.out.println(value);
            System.out.println(siteUser);
            System.out.println("\n\n\n\n\n");
        }

        return Response.status(Response.Status.CREATED).build();

    }

    @POST
    @Path("/getRatesByIdNoteSiteUser")
    @Consumes(value = {MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA})
    public List<Rate> getTagsByIdNoteSiteUser(@FormDataParam("idNote") long idNote,
                                              @FormDataParam("login") String login)  throws IOException
    {
        SiteUser siteUser1 = new SiteUser();
        siteUser1 = siteUserManager.findSiteUserByLogin(login);
        long a = siteUser1.getIdSiteUser();

        Rate searchRate = new Rate();

        List<Rate> searchRates = rateManager.findRatesByIdNote(a);

        if (searchRates.contains(a)) {
            System.out.println("Account found");
        } else {
            System.out.println("Account not found");
        }

        System.out.println("\n\n\n\n");
        System.out.println(a);
        System.out.println(siteUser1.getLogin());

        for (int i = 0; i < searchRates.size(); i++) {
            System.out.println(searchRates.get(i));
        }

        System.out.println("\n\n\n\n");

        return rateManager.findRatesByIdNote(idNote);
    }


}
