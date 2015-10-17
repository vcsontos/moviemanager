/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.filmandseriesmanager.sessionbean;

import com.manager.filmandseriesmanager.entity.FilmUser;
import com.manager.filmandseriesmanager.exception.JeeApplicationException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author valentin
 */
@Local
public interface FilmUserFacadeLocal {

    void create(FilmUser filmUser);

    void edit(FilmUser filmUser);

    void remove(FilmUser filmUser);

    FilmUser find(Object id);

    List<FilmUser> findAll();

    List<FilmUser> findRange(int[] range);

    int count();

    void addUser(String username, String password);
    
}
