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
            List<Movie> movies = movieFacade.findAllMovieByUser(user.getId());
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

        MovieUser user = (MovieUser) containerRequestContext.getProperty(
                Constant.MOVIE_USER_FOR_IDENTIFICATION);
        List<Movie> movies = movieFacade.findMovieByMovieIdAndUserId(movieId, user.getId());
        containerRequestContext.removeProperty(Constant.MOVIE_USER_FOR_IDENTIFICATION);
        Gson gson = new Gson();
        String jsonResponse;
        if (CollectionUtils.isEmpty(movies)) {
            jsonResponse = gson.toJson("Movie is not exist"); 
        } else {
            Movie movie = movieFacade.transferMovie(movies);            
            jsonResponse = gson.toJson(movie);                       
        }
        return Response.status(Response.Status.OK).entity(jsonResponse).build();
    }

    @POST
    @Secured
    @Path("deleteMovieById/{id}")
    @Produces({"application/json"})
    public Response deleteMovieById(@PathParam("id") Long movieId,
            @Context ContainerRequestContext containerRequestContext) {

        MovieUser user = (MovieUser) containerRequestContext.getProperty(
                Constant.MOVIE_USER_FOR_IDENTIFICATION);
        List<Movie> movies = movieFacade.findMovieByMovieIdAndUserId(movieId, user.getId());
        containerRequestContext.removeProperty(Constant.MOVIE_USER_FOR_IDENTIFICATION);

        if (CollectionUtils.isNotEmpty(movies)) {
            movieFacade.remove(movies.get(0));
        }

        return Response.status(Response.Status.OK).build();
    }

    @POST
    @Secured
    @Path("updateMovie")
    @Produces({"application/json"})
    public Response updateMovie(String movie,
            @Context ContainerRequestContext containerRequestContext) {
        try {
            MovieUser user = (MovieUser) containerRequestContext.getProperty(
                    Constant.MOVIE_USER_FOR_IDENTIFICATION);
            movieFacade.updateMovie(movie, user);
            containerRequestContext.removeProperty(Constant.MOVIE_USER_FOR_IDENTIFICATION);
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
}
