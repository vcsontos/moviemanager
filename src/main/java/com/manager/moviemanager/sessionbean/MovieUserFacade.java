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
import java.util.Arrays;
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
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;

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
    private String message;

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

    public String issueToken(String username) throws JoseException {

        // Generate an RSA key pair, which will be used for signing and verification of the JWT, wrapped in a JWK
        RsaJsonWebKey rsaJsonWebKey = RsaJwkGenerator.generateJwk(2048);

        // Give the JWK a Key ID (kid), which is just the polite thing to do
        rsaJsonWebKey.setKeyId("k1");

        // Create the Claims, which will be the content of the JWT
        JwtClaims claims = new JwtClaims();
        claims.setIssuer("moviemanager");  // who creates the token and signs it
        claims.setAudience(username); // to whom the token is intended to be sent
        claims.setExpirationTimeMinutesInTheFuture(10); // time when the token will expire (10 minutes from now)
        claims.setGeneratedJwtId(); // a unique identifier for the token
        claims.setIssuedAtToNow();  // when the token was issued/created (now)
        claims.setNotBeforeMinutesInThePast(2); // time before which the token is not yet valid (2 minutes ago)
        claims.setSubject(username); // the subject/principal is whom the token is about                

        // A JWT is a JWS and/or a JWE with JSON claims as the payload.
        // In this example it is a JWS so we create a JsonWebSignature object.
        JsonWebSignature jws = new JsonWebSignature();

        // The payload of the JWS is JSON content of the JWT Claims
        jws.setPayload(claims.toJson());

        // The JWT is signed using the private key
        jws.setKey(rsaJsonWebKey.getPrivateKey());

        // Set the Key ID (kid) header because it's just the polite thing to do.
        // We only have one key in this example but a using a Key ID helps
        // facilitate a smooth key rollover process
        jws.setKeyIdHeaderValue(rsaJsonWebKey.getKeyId());

        // Set the signature algorithm on the JWT/JWS that will integrity protect the claims
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

        // Sign the JWS and produce the compact serialization or the complete JWT/JWS
        // representation, which is a string consisting of three dot ('.') separated
        // base64url-encoded parts in the form Header.Payload.Signature
        // If you wanted to encrypt it, you can simply set this jwt as the payload
        // of a JsonWebEncryption object and set the cty (Content Type) header to "jwt".
        String token = jws.getCompactSerialization();        
        return token;
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

    public String getMessage() {
        return message;
    }
}
