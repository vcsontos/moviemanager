/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.moviemanager.security;

import com.manager.moviemanager.sessionbean.MovieUserFacade;
import java.io.IOException;
import java.lang.reflect.Method;
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
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;

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

        } catch (Exception e) {
            requestContext.abortWith(
                    Response.status(Response.Status.UNAUTHORIZED).build());
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
        
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime() // the JWT must have an expiration time
                .setAllowedClockSkewInSeconds(30) // allow some leeway in validating time based claims to account for clock skew
                .setRequireSubject() // the JWT must have a subject claim
                .setExpectedIssuer("moviemanager") // whom the JWT needs to have been issued by
                .setExpectedAudience("adam") // to whom the JWT is intended for
                //.setVerificationKey(movieUserFacade.getRsaJsonWebKey().getPublicKey()) // verify the signature with the public key
                .build(); // create the JwtConsumer instance

        try {
            //  Validate the JWT and process it to the Claims
            JwtClaims jwtClaims = jwtConsumer.processToClaims(token);
            System.out.println("JWT validation succeeded! " + jwtClaims);
        } catch (InvalidJwtException e) {
            // InvalidJwtException will be thrown, if the JWT failed processing or validation in anyway.
            // Hopefully with meaningful explanations(s) about what went wrong.
            System.out.println("Invalid JWT! " + e);
        }
    }

}
