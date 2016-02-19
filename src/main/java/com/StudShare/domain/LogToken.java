package com.StudShare.domain;


import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Entity
@NamedQueries(
        {
                @NamedQuery(name = "logToken.bySSID", query = "Select l from LogToken l where l.ssid = :ssid"),
                @NamedQuery(name = "logToken.byIdSiteUser", query = "Select l from LogToken l where l.siteUser.idSiteUser = :idSiteUser"),
                @NamedQuery(name = "logToken.getAll", query = "select l from LogToken l")

        })
public class LogToken
{
    private static final int EXPIRATION_LOG_TOKEN = 60*2;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idLogToken;

    @ManyToOne(targetEntity = SiteUser.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "idSiteUser")
    private SiteUser siteUser;

    @Column(nullable = false, unique = true)
    private String ssid;

    @Column(nullable = false)
    private Date expiryDate;

    public LogToken() {}

    public LogToken(String token, SiteUser siteUser)
    {
        this.ssid = token;
        this.siteUser = siteUser;
        this.expiryDate = calculateExpiryDate(EXPIRATION_LOG_TOKEN);
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
    public long getIdLogToken()
    {
        return idLogToken;
    }

    public void setIdLogToken(long idLogToken)
    {
        this.idLogToken = idLogToken;
    }

    public SiteUser getSiteUser()
    {
        return siteUser;
    }

    public void setSiteUser(SiteUser person)
    {
        this.siteUser = person;
    }

    public String getSsid()
    {
        return ssid;
    }

    public void setSsid(String token)
    {
        this.ssid = token;
    }

    public Date getExpiryDate()
    {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate)
    {
        this.expiryDate = expiryDate;
    }
}
