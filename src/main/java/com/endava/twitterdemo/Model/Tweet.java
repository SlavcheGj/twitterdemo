package com.endava.twitterdemo.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Optional;

@NoArgsConstructor
@Entity
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    private String content;
    private Date dateOfCreation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = "tweets")
    private User user;

    public Tweet(@NotBlank String content, Date dateOfCreation) {
        this.content = content;
        this.dateOfCreation = dateOfCreation;
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
