/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.moviemanager.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import com.manager.moviemanager.utils.MovieType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valentin
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MovieUser.findMovieByMovieNameAndUserId", 
            query = "SELECT m FROM Movie m WHERE m.name = :movieName AND m.movieUser.id = :userId"),
    @NamedQuery(name = "MovieUser.findMovieByMovieIdAndUserId", 
            query = "SELECT m FROM Movie m WHERE m.id = :movieId AND m.movieUser.id = :userId"),
    @NamedQuery(name = "MovieUser.findAllMovieByUser", 
            query = "SELECT m FROM Movie m WHERE m.movieUser.id = :userId")
})
public class Movie implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    @Column(name = "moviename")
    private String name;
    
    @NotNull
    @Column(name = "movietype")
    @Enumerated(EnumType.STRING)
    private MovieType type;
    
    @Column(name = "genre")
    private String genre;
    
    @Column(name = "actors")
    private String actors; 
    
    @Column(name = "rating")
    private Integer rating;
    
    @Column(name = "createddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
    
    @Column(name = "image")
    private String image;
    
    @ManyToOne
    @JoinColumn(name = "movieuser", referencedColumnName = "id")
    private MovieUser movieUser;

    public Movie() {
        
    }
    
    public Movie(Long id) {
        this.id = id;        
    }
    
    public Movie(Long id, String name, MovieType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MovieType getType() {
        return type;
    }

    public void setType(MovieType type) {
        this.type = type;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public MovieUser getMovieUser() {
        return movieUser;
    }

    public void setMovieUser(MovieUser movieUser) {
        this.movieUser = movieUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {       
        if (!(object instanceof Movie)) {
            return false;
        }
        Movie other = (Movie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.manager.moviemanager.entity.Movie[ id=" + id + " ]";
    }
    
}
