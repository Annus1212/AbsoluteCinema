package com.absolutecinema.entity;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private String director;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int duration; // duration in minutes

    @Column(nullable = false)
    private String language;

    @Column(name = "releasedate", nullable = false)
    private String releaseDate;

    @Column(name = "ticketssold", nullable = false)
    private int ticketssold;

    @Column(name = "images", columnDefinition = "BYTEA")
    @JsonIgnore
    private byte[] images;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Session> sessions;

    @JsonProperty("images")
    public String getImagesBase64() {
        if (images == null) return null;
        return java.util.Base64.getEncoder().encodeToString(images);
    }

    @JsonProperty("images")
    public void setImagesBase64(String base64) {
        if (base64 == null) {
            this.images = null;
            return;
        }
        this.images = java.util.Base64.getDecoder().decode(base64);
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Session> getSessions() {
        return sessions;
    }

    public void setSessions(List<Session> sessions) {
        this.sessions = sessions;
    }

    public int getTicketssold() {
        return ticketssold;
    }

    public void setTicketssold(int ticketssold) {
        this.ticketssold = ticketssold;
    }

    public byte[] getImages() {
        return images;
    }

    public void setImages(byte[] images) {
        this.images = images;
    }
}