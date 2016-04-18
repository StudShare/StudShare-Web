package com.StudShare.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@NamedQueries(
        {
                @NamedQuery(name = "rate.byValue", query = "Select r from Rate r where r.value = :value"),
                @NamedQuery(name = "rate.getAll", query = "select r from Rate r"),
                @NamedQuery(name = "rate.getByIdNote", query = "select r from Rate r where r.note.idNote = :idNote"),
                @NamedQuery(name = "rate.getBySiteUser", query = "select r from Rate r where r.siteUser.idSiteUser = :idSiteUser"),
        })
public class Rate
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idRate;

    private short value;

    @ManyToOne(targetEntity = Note.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "idNote")
    @JsonIgnore
    private Note note;

    @ManyToOne(targetEntity = SiteUser.class, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "idSiteUser", nullable = false)
    @JsonIgnore
    private SiteUser siteUser;


    public Rate(){}

    public Rate(short value, Note note, SiteUser siteUser)
    {
        this.value = value;
        this.note = note;
        this.siteUser = siteUser;
    }

    public long getIdRate() {
        return idRate;
    }

    public void setIdRate(long idRate) {
        this.idRate = idRate;
    }

    public short getValue() {
        return value;
    }

    public void setValue(short value) {
        this.value = value;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public SiteUser getSiteUser() {
        return siteUser;
    }

    public void setSiteUser(SiteUser siteUser) {
        this.siteUser = siteUser;
    }

}
