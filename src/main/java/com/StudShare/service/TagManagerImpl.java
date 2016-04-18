package com.StudShare.service;

import com.StudShare.config.HibernateConfig;
import com.StudShare.domain.Tag;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("tagManager")
@Rollback(value = false)
@Transactional
@ComponentScan(basePackageClasses = HibernateConfig.class)
public class TagManagerImpl implements TagManagerDAO{


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
    public Tag addTag (Tag tag) {
        long idTag = ((Long) sessionFactory.getCurrentSession().save(tag)).longValue();
        tag.setIdTag(idTag);
        return tag;
    }

    public List<Tag> getAllTags()
    {
        return sessionFactory.getCurrentSession().getNamedQuery("tag.getAll").list();
    }
    public void deleteTag(Tag tag) {
        sessionFactory.getCurrentSession().delete(tag);
    }


    public Tag findTagByID(Tag tag) {
        return sessionFactory.getCurrentSession().get(Tag.class, tag.getIdTag());
    }

    public Tag findTagByValue(String value){
        return (Tag) sessionFactory.getCurrentSession().getNamedQuery("tag.getTagByValue").setString("value", value).uniqueResult();

    }
    public List<Tag> findTagsByIdNote(long idNote)
    {
        return sessionFactory.getCurrentSession().getNamedQuery("tag.getByIdNote").setLong("idNote", idNote).list();
    }
    public Tag updateTag(Tag tag){

        return (Tag) sessionFactory.getCurrentSession().merge(tag);
    }

}


