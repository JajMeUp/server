package com.jajteam.jajmeup.domain;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name="alarm")
public class Alarm {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idVoter")
    @NotNull
    private Profile voter;

    @ManyToOne
    @JoinColumn(name = "idTarget")
    @NotNull
    private Profile target;

    @NotEmpty
    private String link;

    private String message;

    @NotNull
    private Date created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Profile getVoter() {
        return voter;
    }

    public void setVoter(Profile voter) {
        this.voter = voter;
    }

    public Profile getTarget() {
        return target;
    }

    public void setTarget(Profile target) {
        this.target = target;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
