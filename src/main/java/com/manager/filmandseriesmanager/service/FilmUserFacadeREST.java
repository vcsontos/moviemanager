/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.filmandseriesmanager.service;

import com.google.gson.Gson;
import com.manager.filmandseriesmanager.entity.FilmUser;
import com.manager.filmandseriesmanager.sessionbean.AbstractFacade;
import com.manager.filmandseriesmanager.sessionbean.FilmUserFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import utils.RegistrationResponse;
import utils.RespStatus;

/**
 *
 * @author valentin
 */
@Stateless
@Path("")
public class FilmUserFacadeREST extends AbstractFacade<FilmUser> {

    @PersistenceContext(unitName = "filmmanager")
    private EntityManager em;

    @EJB
    FilmUserFacade filmUserFacade;

    public FilmUserFacadeREST() {
        super(FilmUser.class);
    }

    @GET
    @Path("addUser")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public Response addUser(
            @FormParam("username") String username,
            @FormParam("password") String password) {
        filmUserFacade.addUser(username, password);
        return assembleResponse(RespStatus.OK, username, "Successful registration.");
    }
    
    public Response assembleResponse(RespStatus status, String username, String message) {
        RegistrationResponse response = new RegistrationResponse();
        response.setStatus(status);
        response.setUsername(username);
        response.setMessage(message);
        
        Gson gson = new Gson();
        String json = gson.toJson(response);
        return Response.status(Response.Status.OK).entity(json).build();
    }

    @GET
    @Path("{id}")
    @Produces({"application/json"})
    @Consumes({"application/json"})
    public FilmUser find(@PathParam("id") Long id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<FilmUser> findAll() {
        return super.findAll();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
