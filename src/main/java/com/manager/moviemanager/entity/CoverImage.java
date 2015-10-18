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
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author valentin
 */
@Entity
@XmlRootElement
public class CoverImage implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "coverimageid")
    private Long coverImageId;

    @NotNull
    @Column(name = "filename")
    private String fileName;

    @Lob
    @Column(name = "content")
    private byte[] content;

    @Column(name = "createddate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    public CoverImage() {

    }

    public CoverImage(Long coverImageId) {
        this.coverImageId = coverImageId;
    }

    public CoverImage(Long coverImageId, String fileName, byte[] content) {
        this.coverImageId = coverImageId;
        this.fileName = fileName;
        this.content = content;
    }

    public Long getCoverImageId() {
        return coverImageId;
    }

    public void setCoverImageId(Long coverImageId) {
        this.coverImageId = coverImageId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
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
        hash += (coverImageId != null ? coverImageId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CoverImage)) {
            return false;
        }
        CoverImage other = (CoverImage) object;
        if ((this.coverImageId == null && other.coverImageId != null) || (this.coverImageId != null && !this.coverImageId.equals(other.coverImageId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.manager.filmandseriesmanager.entity.CoverImage[ id=" + coverImageId + " ]";
    }

}
