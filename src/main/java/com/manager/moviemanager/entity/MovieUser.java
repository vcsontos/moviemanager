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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valentin
 */
@Entity
@Table(name = "MovieUser", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"username", "token"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MovieUser.findAll", query = "SELECT m FROM MovieUser m"),
    @NamedQuery(name = "MovieUser.findByUsername", query = "SELECT m FROM MovieUser m WHERE m.username = :username"),
    @NamedQuery(name = "MovieUser.findByToken", query = "SELECT m FROM MovieUser m WHERE m.token = :token")
})
public class MovieUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 255)
    @Column(name = "username")
    private String username;

    @NotNull
    @Size(min = 6, max = 255)
    @Column(name = "password")
    private String password;
    
    @Column(name = "token")
    private String token;
    
    @Column(name = "tokenregisterdate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tokenregisterDate;

    @Column(name = "createddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public MovieUser() {
    }

    public MovieUser(Long id) {
        this.id = id;
    }

    public MovieUser(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getTokenregisterDate() {
        return tokenregisterDate;
    }

    public void setTokenregisterDate(Date tokenregisterDate) {
        this.tokenregisterDate = tokenregisterDate;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MovieUser)) {
            return false;
        }
        MovieUser other = (MovieUser) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.manager.moviemanager.entity.MovieUser[ id=" + id + " ]";
    }

}
