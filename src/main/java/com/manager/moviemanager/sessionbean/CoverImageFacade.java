/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.moviemanager.sessionbean;

import com.manager.moviemanager.entity.CoverImage;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author valentin
 */
@Stateless
public class CoverImageFacade extends AbstractFacade<CoverImage> implements CoverImageFacadeLocal {
    @PersistenceContext(unitName = "filmmanager")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CoverImageFacade() {
        super(CoverImage.class);
    }
    
}
