package com.StudShare.service;

import com.StudShare.config.HibernateConfig;
import com.StudShare.domain.Rate;
import com.StudShare.domain.RegToken;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("rateManager")
@Rollback(value = false)
@Transactional
@ComponentScan(basePackageClasses = HibernateConfig.class)
public class RateManagerImpl implements RateManagerDAO
{
    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public Rate addRate(Rate rate)
    {
        long idRate = ((Long) sessionFactory.getCurrentSession().save(rate)).longValue();
        rate.setIdRate(idRate);
        return rate;
    }

    @Override
    public void deleteRate(Rate rate)
    {
        sessionFactory.getCurrentSession().delete(rate);
    }

    @Override
    public List<Rate> findRatesByIdNote(long idNote)
    {
        return sessionFactory.getCurrentSession().getNamedQuery("rate.getByIdNote").setLong("idNote", idNote).list();
    }

    @Override
    public List<Rate> findRatesByIdSiteUser(long idSiteUser)
    {
        return sessionFactory.getCurrentSession().getNamedQuery("rate.getBySiteUser").setLong("idSiteUser", idSiteUser).list();
    }

}
