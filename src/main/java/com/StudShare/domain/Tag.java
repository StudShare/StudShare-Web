package com.StudShare.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries(
        {
                @NamedQuery(name = "tag.getTagByValue", query = "Select t from Tag t where t.value = :value"),
                @NamedQuery(name = "tag.getAll", query = "select t from Tag t"),
                @NamedQuery(name = "tag.getByIdNote", query = "select t from Tag t join t.notes n where n.idNote = :idNote")
        })
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idTag;

    private String value;

    @ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Note> notes;


    public  Tag(){}

    public Tag(String value)
    {
        this.value = value;
    }

    public long getIdTag() {
        return idTag;
    }

    public void setIdTag(long idTag) {
        this.idTag = idTag;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
