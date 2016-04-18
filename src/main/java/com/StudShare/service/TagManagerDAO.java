package com.StudShare.service;

import com.StudShare.domain.Tag;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Created by Mat on 06.03.2016.
 */
public interface TagManagerDAO
{
    SessionFactory getSessionFactory();

    void setSessionFactory(SessionFactory sessionFactory);

    List<Tag> getAllTags();

    Tag addTag (Tag tag);

    void deleteTag(Tag tag);

    Tag findTagByID(Tag tag);

    Tag findTagByValue(String value);

    List<Tag> findTagsByIdNote(long idNote);

    Tag updateTag(Tag tag);
}
