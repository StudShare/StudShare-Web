package com.StudShare.rest.note;

import com.StudShare.domain.Tag;
import com.StudShare.service.TagManagerDAO;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;

@Path("/tag")
@ComponentScan(basePackageClasses = {TagManagerDAO.class})
public class TagResource
{
    @Autowired
    TagManagerDAO tagManager;

    @GET
    @Path("/getAllTags")
    @Produces({ MediaType.APPLICATION_JSON})
    public JSONArray  getAllTags()
    {
        List<String> valuesTags = new ArrayList<String>();
        for(Tag t : tagManager.getAllTags() )
        {
            valuesTags.add(t.getValue());
        }
        return new JSONArray(valuesTags);
    }

    @GET
@Path("/getTagsByIdNote/{idNote}")
@Produces({ MediaType.APPLICATION_JSON})
public JSONArray  getTagsByIdNote(@PathParam("idNote") long idNote)
{

    List<String> valuesTags = new ArrayList<String>();
    for(Tag t : tagManager.findTagsByIdNote(idNote) )
    {
        valuesTags.add(t.getValue());
    }
    return new JSONArray(valuesTags);
}


}
