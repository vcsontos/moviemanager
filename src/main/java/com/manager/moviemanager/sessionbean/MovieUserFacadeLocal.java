/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.moviemanager.sessionbean;

import com.manager.moviemanager.entity.MovieUser;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author valentin
 */
@Local
public interface MovieUserFacadeLocal {

    void create(MovieUser movieUser);

    void edit(MovieUser movieUser);

    void remove(MovieUser movieUser);

    MovieUser find(Object id);

    List<MovieUser> findAll();

    List<MovieUser> findRange(int[] range);

    int count();

    void addUser(String username, String password);
    
    String login(String username, String password);
    
}
