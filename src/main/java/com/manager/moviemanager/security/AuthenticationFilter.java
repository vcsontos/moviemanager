/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.moviemanager.security;

import com.manager.moviemanager.constant.Constant;
import com.manager.moviemanager.entity.MovieUser;
import com.manager.moviemanager.exception.JeeApplicationException;
import com.manager.moviemanager.sessionbean.MovieUserFacade;
import com.manager.moviemanager.utils.Base64Coding;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import javax.annotation.Priority;
import javax.ejb.EJB;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author valentin
 */
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @EJB
    MovieUserFacade movieUserFacade;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        try {
            
            if (!isAnnotatedBySecured()) {
                return;
            }
            
            String token = getAuthorizationHeaderValue(requestContext);
            validateToken(requestContext, token);

        } catch (Exception ex) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).entity(ex.getMessage()).build());
        }
    }
    
    private boolean isAnnotatedBySecured() {
        
        Method method = resourceInfo.getResourceMethod();
        if (method != null) {
            Secured secured = method.getAnnotation(Secured.class);
            return !(secured == null);
        } 
        return false;
    }

    private String getAuthorizationHeaderValue(ContainerRequestContext requestContext) {
        // Get the HTTP Authorization header from the request
        String authorizationHeader
                = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Check if the HTTP Authorization header is present and formatted correctly 
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new NotAuthorizedException("Authorization header must be provided");
        }

        // Extract the token from the HTTP Authorization header
        return authorizationHeader.substring("Bearer".length()).trim();
    }

    public void validateToken(ContainerRequestContext requestContext, String token)
            throws JeeApplicationException {
        
        String[] splittedToken = splitToken(token);
        MovieUser user = validateAudience(splittedToken[1]);
        compareToken(token, user.getToken());
        isExpiredToken(user.getTokenRegisterDate());
        requestContext.setProperty(Constant.MOVIE_USER_FOR_IDENTIFICATION, user);
    }
    
    public String[] splitToken(String token) throws JeeApplicationException {
        
        if (StringUtils.isEmpty(token)) {
            throw new JeeApplicationException("Token is invalid");
        }

        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            throw new JeeApplicationException("Token is invalid");
        }
        return parts;
    }
    
    public MovieUser validateAudience(String audience) throws JeeApplicationException {
        
        String decodedAudience = Base64Coding.decodeString(audience);
        List<MovieUser> users = movieUserFacade.getUser(decodedAudience);
        movieUserFacade.checkUsernameExist(users);
        movieUserFacade.checkUsernameIsUnique(users);
        return users.get(0);
    }
    
    public void compareToken(String token, String userToken) throws JeeApplicationException {
        
        if (!token.equals(userToken)) {
            throw new JeeApplicationException("Token is not valid.");
        }
    }
    
    public void isExpiredToken(Date registerDate) throws JeeApplicationException {
        
        long diffInMillies = new Date().getTime() - registerDate.getTime();
        if (diffInMillies > (Constant.TOKEN_EXPIRATION_DATE_IN_MINUTES * 60 * 1000)) {
            throw new JeeApplicationException("Token expired.");
        }
    }

}
