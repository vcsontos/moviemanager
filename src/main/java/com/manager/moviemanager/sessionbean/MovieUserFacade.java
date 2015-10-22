/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.moviemanager.sessionbean;

import com.manager.moviemanager.constant.Constant;
import com.manager.moviemanager.entity.MovieUser;
import com.manager.moviemanager.exception.JeeApplicationException;
import com.manager.moviemanager.security.PassWordHash;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author valentin
 */
@Stateless
public class MovieUserFacade extends AbstractFacade<MovieUser> {

    @PersistenceContext(unitName = "filmmanager")
    private EntityManager em;

    private String username;
    private String password;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MovieUserFacade() {
        super(MovieUser.class);
    }

    public void addUser(String username, String password)
            throws JeeApplicationException, NoSuchAlgorithmException, InvalidKeySpecException {

        validateUsername(username);
        validatePassword(password);
        isAlreadyExistUsername(username);
        String hashedPassword = createHashPasswordAndSalt(password);
        getEntityManager().persist(setMovieUser(username, hashedPassword));
    }

    public void authenticate(String username, String password)
            throws JeeApplicationException, NoSuchAlgorithmException, InvalidKeySpecException {

        List<MovieUser> users = getMovieUserByUsername(username);
        usernameIsNotExist(users);
        boolean validPassword = PassWordHash.validatePassword(password, users.get(0).getPassword());
        checkPassword(validPassword);
    }

    public String issueToken(String username) {

        UUID token = UUID.randomUUID();
        String strToken = token.toString();
        updateMovieUserWithToken(username, strToken, new Date());
        return strToken;
    }
    
    private void updateMovieUserWithToken(String username, String token, Date tokenregisterDate) {
        
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<MovieUser> update = builder.
                createCriteriaUpdate(MovieUser.class);
        Root<MovieUser> root = update.from(MovieUser.class);
        EntityType<MovieUser> movieuser_ = root.getModel();
              
        Predicate usernameCondition = builder.equal(root.get(movieuser_.getSingularAttribute("username", String.class)) , username);

        update.set(root.get(movieuser_.getSingularAttribute("token", String.class)), token);
        update.set(root.get(movieuser_.getSingularAttribute("tokenregisterDate", Date.class)), tokenregisterDate);
        update.where(usernameCondition);
        getEntityManager().createQuery(update).executeUpdate();
    }

    public void usernameIsNotExist(List<MovieUser> users) throws JeeApplicationException {
        if (CollectionUtils.isEmpty(users)) {
            Logger.getLogger(MovieUserFacade.class.getName()).log(Level.SEVERE, Constant.USERNAME_IS_EMPTY_OR_NULL);
            throw new JeeApplicationException(Constant.INVALID_USERNAME_OR_PASSWORD);
        }

        if (users.size() > 1) {
            Logger.getLogger(MovieUserFacade.class.getName()).log(Level.SEVERE, "The given username is more elements.");
        }
    }

    public void checkPassword(boolean validPassword) throws JeeApplicationException {
        if (!validPassword) {
            Logger.getLogger(MovieUserFacade.class.getName()).log(Level.SEVERE, Constant.INVALID_PASSWORD);
            throw new JeeApplicationException(Constant.INVALID_USERNAME_OR_PASSWORD);
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

        List<MovieUser> users = getMovieUserByUsername(username);

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

    private MovieUser setMovieUser(String username, String hashPassword) {
        MovieUser movieUser = new MovieUser();
        movieUser.setUsername(username);
        movieUser.setPassword(hashPassword);
        movieUser.setCreatedDate(new Date());
        return movieUser;
    }

    private List<MovieUser> getMovieUserByUsername(String username) {
        Map<String, Object> params = new HashMap<>(0);
        params.put("username", username);
        return calledNamedQuery("MovieUser.findByUsername", params);
    }
    
    public List<MovieUser> getMovieUserByToken(String token) {
        Map<String, Object> params = new HashMap<>(0);
        params.put("token", token);
        return calledNamedQuery("MovieUser.findByToken", params);
    }
    
    public boolean isExpiredToken(Date registerDate) {
        long diffInMillies = new Date().getTime() - registerDate.getTime();
        return diffInMillies > (Constant.TOKEN_EXPIRATION_DATE_IN_MINUTES * 60 * 1000);
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
