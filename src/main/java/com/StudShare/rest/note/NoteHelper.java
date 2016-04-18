package com.StudShare.rest.note;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.ws.rs.core.Response;

import com.StudShare.domain.Note;
import com.StudShare.domain.SiteUser;
import com.StudShare.domain.Tag;
import com.StudShare.service.TagManagerDAO;
import com.sun.corba.se.spi.orbutil.fsm.Input;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@Component(value = "noteHelper")
@ComponentScan(basePackageClasses = { TagManagerDAO.class })
public class NoteHelper
{
    @Autowired
    private TagManagerDAO tagManager;

    public void DecodeImage (String base64String) throws IOException {

        byte[] bytearray = Base64.decode(base64String);

        BufferedImage imag = ImageIO.read(new ByteArrayInputStream(bytearray));
        ImageIO.write(imag, "jpg", new File("C:\\", "snap.jpg"));

    }

    public boolean checkExistingAndOwnerNote(Note note, SiteUser siteUser)
    {
        if(note == null)
            return false;
        if(!note.getSiteUser().getLogin().equals(siteUser.getLogin()))
           return false;
        if(note.getSiteUser().getIdSiteUser() != siteUser.getIdSiteUser())
            return false;

        return true;

    }

    public List<Tag> addTagsToNote(List<String> values)
    {
        List<Tag> tags = new ArrayList<Tag>();
        Tag tag;
        for(String value : values)
        {
            tag = tagManager.findTagByValue(value);
            if(tag == null)
                tag = tagManager.addTag(new Tag(value));

            tags.add(tag);
        }
        return tags;
    }

    public boolean checkNoteContent(String title, InputStream picturecontent, String textcontent, InputStream filecontent, String tagsValues)
    {

        if(title == null)
            return false;
        if(picturecontent== null && textcontent.length() == 0)
            return false;
        if(filecontent == null)
            return false;
        if(tagsValues == null)
            return false;

        return true;
    }

}


