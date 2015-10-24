/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.moviemanager.service;

import com.google.gson.Gson;
import com.manager.moviemanager.constant.Constant;
import com.manager.moviemanager.entity.Movie;
import com.manager.moviemanager.entity.MovieUser;
import com.manager.moviemanager.exception.JeeApplicationException;
import com.manager.moviemanager.security.Secured;
import com.manager.moviemanager.sessionbean.MovieFacade;
import com.manager.moviemanager.utils.MovieHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response;
import org.apache.commons.collections4.CollectionUtils;

/**
 *
 * @author valentin
 */
@Stateless
@Path("")
public class MovieFacadeREST {

    @EJB
    MovieFacade movieFacade;

    @POST
    @Secured
    @Path("addMovie")
    @Produces({"application/json"})
    public Response addMovie(String movie,
            @Context ContainerRequestContext containerRequestContext) {
        try {
            MovieUser user = (MovieUser) containerRequestContext.getProperty(
                    Constant.MOVIE_USER_FOR_IDENTIFICATION);
            movieFacade.createMovie(movie, user);
            containerRequestContext.removeProperty(Constant.MOVIE_USER_FOR_IDENTIFICATION);
            return Response.status(Response.Status.OK).build();
        } catch (JeeApplicationException ex) {
            Logger.getLogger(MovieFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.OK).entity(ex.getMessage()).build();
        }
    }

    @POST
    @Secured
    @Path("getAllMovie")
    @Produces({"application/json"})
    public Response findAllMovie(@Context ContainerRequestContext containerRequestContext) {
        try {
            MovieUser user = (MovieUser) containerRequestContext.getProperty(
                    Constant.MOVIE_USER_FOR_IDENTIFICATION);
            List<Movie> movies = movieFacade.findAllByUser(user.getId());
            String jsonResponse = createJsonResponse(movies);
            containerRequestContext.removeProperty(Constant.MOVIE_USER_FOR_IDENTIFICATION);
            return Response.status(Response.Status.OK).entity(jsonResponse).build();
        } catch (JeeApplicationException ex) {
            Logger.getLogger(MovieFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.OK).entity(ex.getMessage()).build();
        }
    }

    @POST
    @Secured
    @Path("getMovieById/{id}")
    @Produces({"application/json"})
    public Response getMovieById(@PathParam("id") Long movieId,
            @Context ContainerRequestContext containerRequestContext) {
        try {
            MovieUser user = (MovieUser) containerRequestContext.getProperty(
                    Constant.MOVIE_USER_FOR_IDENTIFICATION);
            List<Movie> movies = movieFacade.findMovieByMovieIdAndUserId(movieId, user.getId());
            Movie movie = movieFacade.transferMovie(movies);
            Gson gson = new Gson();
            String jsonResponse = gson.toJson(movie);
            containerRequestContext.removeProperty(Constant.MOVIE_USER_FOR_IDENTIFICATION);
            return Response.status(Response.Status.OK).entity(jsonResponse).build();
        } catch (JeeApplicationException ex) {
            Logger.getLogger(MovieFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.OK).entity(ex.getMessage()).build();
        }
    }
    
    @POST
    @Secured
    @Path("deleteMovieById/{id}")
    @Produces({"application/json"})
    public Response deleteMovieById(@PathParam("id") Long movieId,
            @Context ContainerRequestContext containerRequestContext) {
        try {
            MovieUser user = (MovieUser) containerRequestContext.getProperty(
                    Constant.MOVIE_USER_FOR_IDENTIFICATION);
            List<Movie> movies = movieFacade.findMovieByMovieIdAndUserId(movieId, user.getId());
            containerRequestContext.removeProperty(Constant.MOVIE_USER_FOR_IDENTIFICATION);
            
            if (CollectionUtils.isNotEmpty(movies)) {
                movieFacade.deleteMovie(movies.get(0));
            }
            
            return Response.status(Response.Status.OK).build();
        } catch (JeeApplicationException ex) {
            Logger.getLogger(MovieFacadeREST.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.OK).entity(ex.getMessage()).build();
        }
    }

    public String createJsonResponse(List<Movie> movies) {

        List<MovieHelper> movieHelpers = new ArrayList<>(movies.size());

        for (Movie movie : movies) {
            MovieHelper movieHelper = new MovieHelper();
            movieHelper.setId(movie.getId());
            movieHelper.setName(movie.getName());
            movieHelper.setType(movie.getType());
            movieHelper.setRating(movie.getRating());
            movieHelper.setImage(movie.getImage());

            movieHelpers.add(movieHelper);
        }

        Gson gson = new Gson();
        return gson.toJson(movieHelpers);
    }

//    @PUT
//    @Path("{id}")
//    @Consumes({"application/xml", "application/json"})
//    public void edit(@PathParam("id") Long id, Movie entity) {
//        super.edit(entity);
//    }
//
//    @DELETE
//    @Path("{id}")
//    public void remove(@PathParam("id") Long id) {
//        super.remove(super.find(id));
//    }
//
//    @GET
//    @Path("{id}")
//    @Produces({"application/xml", "application/json"})
//    public Movie find(@PathParam("id") Long id) {
//        return super.find(id);
//    }
//
//    @GET
//    @Override
//    @Produces({"application/xml", "application/json"})
//    public List<Movie> findAll() {
//        return super.findAll();
//    }
//
//    @GET
//    @Path("{from}/{to}")
//    @Produces({"application/xml", "application/json"})
//    public List<Movie> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
//        return super.findRange(new int[]{from, to});
//    }
//
//    @GET
//    @Path("count")
//    @Produces("text/plain")
//    public String countREST() {
//        return String.valueOf(super.count());
//    }
}
