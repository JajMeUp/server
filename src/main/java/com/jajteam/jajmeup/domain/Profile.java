package com.jajteam.jajmeup.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import javax.annotation.Nullable;
import javax.persistence.*;

@Entity
public class Profile {

    public static final String VISIBILITY_FRIENDS = "FRIENDS";
    public static final String VISIBILITY_WORLD = "WORLD";

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @OneToOne
    @JsonIgnore
    private User user;

    @NotEmpty
    private String visibility;

    @NotEmpty
    @Column(name = "display_name")
    private String displayName;

    @Nullable
    private String picture;

    public Profile() {

    }

    public Profile(User user, String displayName) {
        this.user = user;
        this.displayName = displayName;
        this.visibility = VISIBILITY_FRIENDS;
        this.picture = "";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Nullable
    public String getPicture() {
        return picture;
    }

    public void setPicture(@Nullable String picture) {
        this.picture = picture;
    }
}
