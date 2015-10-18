/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.moviemanager.sessionbean;

import com.manager.moviemanager.entity.CoverImage;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author valentin
 */
@Local
public interface CoverImageFacadeLocal {

    void create(CoverImage coverImage);

    void edit(CoverImage coverImage);

    void remove(CoverImage coverImage);

    CoverImage find(Object id);

    List<CoverImage> findAll();

    List<CoverImage> findRange(int[] range);

    int count();
    
}
