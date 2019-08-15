package com.endava.twitterdemo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.Wither;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Wither
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String content;
    @Temporal(TemporalType.DATE)
    private Date dateOfCreation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "tweets")
    private User user;

    public Tweet(@NotBlank String content, String dateOfCreation) throws ParseException {
        this.content = content;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.dateOfCreation = formatter.parse(dateOfCreation);
    }




    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public User getUser() {
        return user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public void setUser(Optional<User> user) {
        this.user = user.get();
    }
}
