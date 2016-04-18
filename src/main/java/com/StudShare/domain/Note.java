package com.StudShare.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.Loader;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Base64;
import java.util.List;

@Entity
@NamedQueries(
        {
                @NamedQuery(name = "note.getNoteByID", query = "Select n from Note n where n.idNote = :idNote"),
                @NamedQuery(name = "note.getNoteByTitle", query = "Select n from Note n where n.title = :title"),
                @NamedQuery(name = "note.getNoteByIDSiteUser", query = "Select n from Note n where n.siteUser.idSiteUser = :idSiteUser"),
                @NamedQuery(name = "note.getAll", query = "Select n from Note n"),
                @NamedQuery(name = "note.getBasicInfoNotesByTag", query = "SELECT NEW Note(n.idNote , n.title , n.type) from Note n join n.tags t where t.value = :value"),
                @NamedQuery(name = "note.getBacisInfo", query = "SELECT NEW Note(n.idNote, n.title, n.type) FROM Note n WHERE n.siteUser.login = :login"),
                @NamedQuery(name = "note.getPicturecontent", query = "SELECT n.textcontent, n.picturecontent, n.filecontent FROM Note n WHERE n.idNote = :idNote"),


        })

public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idNote;

    @ManyToOne(targetEntity = SiteUser.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "idSiteUser", nullable = false)
    @JsonIgnore
    private SiteUser siteUser;

    @Type(type = "text")
    private String textcontent;

    @Type(type="text")
    private String picturecontent;


    @Lob
    private byte[] filecontent;


    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String title;


    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "Note_Tag", joinColumns = {@JoinColumn(name = "idNote")}, inverseJoinColumns = {@JoinColumn(name = "idTag")})
    private List<Tag> tags;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "note", cascade = CascadeType.ALL)
    private List<Rate> rates;

    public Note() {}

    public Note(SiteUser siteUser, String textcontent, byte[] picturecontent, byte[] filecontent, String title, String type) {
        this.siteUser = siteUser;
        this.textcontent = textcontent;
        this.picturecontent = picturecontent !=null? Base64.getEncoder().encodeToString(picturecontent): null;
        this.filecontent = filecontent !=null? filecontent: null;
        this.title = title;
        this.type = type;
    }

    public Note(long idNote, String title, String type)
    {
        this.idNote = idNote;
        this.textcontent = null;
        this.picturecontent = null;
        this.filecontent= null;
        this.title = title;
        this.type = type;
    }

    public long getIdNote() {
        return idNote;
    }

    public void setIdNote(long idNote) {
        this.idNote = idNote;
    }

    public SiteUser getSiteUser() {
        return siteUser;
    }

    public void setSiteUser(SiteUser siteUser) {
        this.siteUser = siteUser;
    }

    public String getTextcontent() {
        return textcontent;
    }

    public void setTextcontent(String textcontent) {
        this.textcontent = textcontent;
    }

    public byte[] getPicturecontent() {
        return picturecontent != null? Base64.getDecoder().decode(picturecontent) :  new byte[0];
    }

    public void setPicturecontent(byte[] picturecontent) {
        this.picturecontent =  Base64.getEncoder().encodeToString(picturecontent);
    }

    public byte[] getFilecontent() {
        return filecontent != null? Base64.getDecoder().decode(filecontent) :  new byte[0];
    }

    public void setFilecontent(byte[] filecontent) {
        this.filecontent =  filecontent;
    }




    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Note note = (Note) o;

        return idNote == note.idNote;

    }

    @Override
    public int hashCode() {
        return (int) (idNote ^ (idNote >>> 32));
    }
}
