/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.filmandseriesmanager.sessionbean;

import com.manager.filmandseriesmanager.constant.Constant;
import com.manager.filmandseriesmanager.entity.FilmUser;
import com.manager.filmandseriesmanager.exception.JeeApplicationException;
import com.manager.filmandseriesmanager.security.PassWordHash;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author valentin
 */
@Stateless
public class FilmUserFacade extends AbstractFacade<FilmUser> implements FilmUserFacadeLocal {

    @PersistenceContext(unitName = "filmmanager")
    private EntityManager em;

    private String username;
    private String password;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FilmUserFacade() {
        super(FilmUser.class);
    }

    @Override
    public void addUser(String username, String password) {
        try {
            validateUsername(username);
            validatePassword(password);
            isAlreadyExistUsername(username);
            String hashedPassword = createHashPasswordAndSalt(password);         
            getEntityManager().persist(setFilmUser(username, hashedPassword));
        } catch (JeeApplicationException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(FilmUserFacade.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void validateUsername(String username) throws JeeApplicationException {
        if (StringUtils.isEmpty(username)) {
            throw new JeeApplicationException(Constant.USERNAME_IS_EMPTY_OR_NULL);
        }

        if (username.length() < 3) {
            throw new JeeApplicationException(Constant.USERNAME_HAS_LESS_THAN_THREE_CHARACTERS);
        }
    }

    public void validatePassword(String password) throws JeeApplicationException {
        if (StringUtils.isEmpty(password)) {
            throw new JeeApplicationException(Constant.PASSWORD_IS_EMPTY_OR_NULL);
        }

        if (password.length() < 6) {
            throw new JeeApplicationException(Constant.PASSWORD_HAS_LESS_THAN_SIX_CHARACTERS);
        }
    }

    public void isAlreadyExistUsername(String username) throws JeeApplicationException {

        Map<String, Object> params = new HashMap<>(0);
        params.put("username", username);
        List<FilmUser> users = calledNamedQuery("FilmUser.findByUsername", params);

        if (CollectionUtils.isNotEmpty(users)) {
            throw new JeeApplicationException(Constant.USERNAME_IS_ALREADY_EXIST);
        }
    }

    public String createHashPasswordAndSalt(String password)
            throws JeeApplicationException, NoSuchAlgorithmException, InvalidKeySpecException {

        String hashedResult = PassWordHash.createHash(password);
        
        if (StringUtils.isEmpty(password)) {
            throw new JeeApplicationException(Constant.CREATE_HASH_PASSWORD_WAS_WRONG);
        }

        return hashedResult;
    }
    
    private FilmUser setFilmUser(String username, String hashPassword) {
        FilmUser filmUser = new FilmUser();
        filmUser.setUsername(username);
        filmUser.setPassword(password);
        filmUser.setCreatedDate(new Date());
        return filmUser;
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
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

}
