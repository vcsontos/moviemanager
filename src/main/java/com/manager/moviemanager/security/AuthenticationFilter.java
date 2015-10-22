/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.moviemanager.security;

import com.manager.moviemanager.entity.MovieUser;
import com.manager.moviemanager.exception.JeeApplicationException;
import com.manager.moviemanager.sessionbean.MovieUserFacade;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.apache.commons.collections4.CollectionUtils;

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

        Method method = resourceInfo.getResourceMethod();
        if (method != null) {
            Secured secured = method.getAnnotation(Secured.class);
            if (secured == null) {
                return;
            }
        }

        try {
            String token = getAuthorizationHeaderValue(requestContext);
            validateToken(token);

        } catch (Exception ex) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).entity(ex.getMessage()).build());
        }
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

    private void validateToken(String token) throws Exception {
               
        List<MovieUser> users = movieUserFacade.getMovieUserByToken(token);
        
        if (CollectionUtils.isEmpty(users)) {
            throw new JeeApplicationException("Token is not exist.");
        }
        
        if (users.size() > 1) {
            throw new JeeApplicationException("Token is not unique.");
        }
        
        if(movieUserFacade.isExpiredToken(users.get(0).getTokenregisterDate())) {
            throw new JeeApplicationException("Token expired.");
        }
    }

}
