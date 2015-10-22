/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.moviemanager.service;

import com.google.gson.Gson;
import com.manager.moviemanager.constant.Constant;
import com.manager.moviemanager.exception.JeeApplicationException;
import com.manager.moviemanager.sessionbean.MovieUserFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import com.manager.moviemanager.utils.RegistrationResponse;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author valentin
 */
@Stateless
@Path("")
public class MovieUserFacadeREST  {

    @EJB
    MovieUserFacade movieUserFacade;

    @POST
    @Path("addUser")
    @Produces({"application/json"})
    public Response addUser(
            @FormParam("username") String username,
            @FormParam("password") String password) {
        try {
            movieUserFacade.addUser(username, password);                
            return assembleResponse(username, Constant.SUCCESSFUL_REGISTRATION);
        } catch (JeeApplicationException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(MovieUserFacadeREST.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return assembleResponse(username, ex.getMessage());
        }
    }
    
    @POST
    @Path("login")
    @Produces({"application/json"})
    public Response authenticateUser(
            @FormParam("username") String username,
            @FormParam("password") String password) {
        try {
            movieUserFacade.authenticate(username, password);
            String token = movieUserFacade.issueToken(username);        
            return Response.ok(token).build();
        } catch (JeeApplicationException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
            Logger.getLogger(MovieUserFacadeREST.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } 
    }
    
    public Response assembleResponse(String username, String message) {
        RegistrationResponse response = new RegistrationResponse();
        response.setUsername(username);
        response.setMessage(message);
        
        Gson gson = new Gson();
        String json = gson.toJson(response);
        return Response.status(Response.Status.OK).entity(json).build();
    }

}
