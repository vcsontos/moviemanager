/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.manager.moviemanager.sessionbean;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.manager.moviemanager.entity.Movie;
import com.manager.moviemanager.entity.MovieUser;
import com.manager.moviemanager.exception.JeeApplicationException;
import com.manager.moviemanager.utils.MovieType;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
public class MovieFacade extends AbstractFacade<Movie> {

    @PersistenceContext(unitName = "filmmanager")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public MovieFacade() {
        super(Movie.class);
    }

    public void createMovie(String json, MovieUser user) throws JeeApplicationException {

        Movie movie = getMovieFromJson(json, user);
        movie.setCreatedDate(new Date());
        create(movie);
    }
    
    public void updateMovie(String json, MovieUser user) throws JeeApplicationException {

        Movie movie = getMovieFromJson(json, user);
        
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<Movie> update = builder.
                createCriteriaUpdate(Movie.class);
        Root<Movie> root = update.from(Movie.class);
        EntityType<Movie> movie_ = root.getModel();

        List<Predicate> predicates = new ArrayList<>();
        Predicate movieNameCondition = builder.equal(root.get(movie_.getSingularAttribute("name", Long.class)), movie.getName());
        predicates.add(movieNameCondition);
        Predicate userIdCondition = builder.equal(root.get(movie_.getSingularAttribute("id", Long.class)), user.getId());
        predicates.add(userIdCondition);
        
        if (StringUtils.isNotEmpty(movie.getActors())) {
            update.set(root.get(movie_.getSingularAttribute("actors", String.class)), movie.getActors());
        }
        
        if (StringUtils.isNotEmpty(movie.getGenre())) {
            update.set(root.get(movie_.getSingularAttribute("genre", String.class)), movie.getGenre());
        }
        
        if (movie.getRating() != null) {
            update.set(root.get(movie_.getSingularAttribute("rating", Integer.class)), movie.getRating());
        }
        
        if (movie.getImage().length > 0) {
            update.set(root.get(movie_.getSingularAttribute("image", byte[].class)), movie.getImage());
        }
        
        update.where(predicates.toArray(new Predicate[]{}));
        getEntityManager().createQuery(update).executeUpdate();
    }
    
    public List<Movie> findAllMovieByUser(Long userId) throws JeeApplicationException {
               
        Map<String, Object> params = new HashMap<>(1);
        params.put("userId", userId);
        List<Movie> movies = calledNamedQuery("MovieUser.findAllMovieByUser", params);
        
        if (CollectionUtils.isEmpty(movies)) {
            return new ArrayList<>();
        }
        
        return movies;
    }

    public Movie getMovieFromJson(String json, MovieUser user) throws JeeApplicationException {

        if (StringUtils.isEmpty(json)) {
            throw new JeeApplicationException("Movie name and type required.");
        }

        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();

        Movie movie = new Movie();

        JsonElement nameElement = jsonObject.get("name");
        if (nameElement == null) {
            throw new JeeApplicationException("Movie name and type required.");
        }
        String movieName = nameElement.getAsString();
        movie.setName(movieName);
        
        checkUserHasThisMovie(movieName, user.getId());

        JsonElement typeElement = jsonObject.get("type");
        if (typeElement == null) {
            throw new JeeApplicationException("Movie name and type required.");
        }

        String type = typeElement.getAsString().trim().toUpperCase();
        if (type.equals(MovieType.MOVIE.toString()) || type.equals(MovieType.SERIES.toString())) {
            movie.setType(MovieType.valueOf(type));
        } else {
            throw new JeeApplicationException("Invalid movie type");
        }

        JsonElement genreElement = jsonObject.get("genre");
        if (genreElement != null) {
            String genre = genreElement.getAsString();
            if (genre.matches("^[a-zA-Z, ]+$")) {
                movie.setGenre(genre);
            } else {
                throw new JeeApplicationException("Genre contains only letters and commas.");
            }
        }

        JsonElement actorsElement = jsonObject.get("actors");
        if (actorsElement != null) {
            String actors = actorsElement.getAsString();
            if (actors.matches("^[a-zA-Z, ]+$")) {
                movie.setActors(actors);
            } else {
                throw new JeeApplicationException("Actors contains only letters and commas.");
            }
        }

        JsonElement ratingElement = jsonObject.get("rating");
        if (ratingElement != null) {
            int rating = ratingElement.getAsInt();
            if (rating > 0 && rating < 6) {
                movie.setRating(rating);
            } else {
                throw new JeeApplicationException("Invalid rating value");
            }
        }
        
        JsonElement imageElement = jsonObject.get("image");
        if (imageElement != null) {
            byte[] image = imageElement.getAsString().getBytes(StandardCharsets.UTF_8);
            movie.setImage(image);
        }
        
        movie.setMovieUser(user);

        System.out.println("username: " + user.getUsername());
        System.out.println("name: " + movie.getName());
        System.out.println("type: " + movie.getType());
        System.out.println("genre: " + movie.getGenre());
        System.out.println("actors: " + movie.getActors());
        System.out.println("rating: " + movie.getRating());
        System.out.println("image: " + Arrays.toString(movie.getImage()));
        return movie;
    }
    
    public void checkUserHasThisMovie(String movieName, Long userId) throws JeeApplicationException {
       
        List<Movie> movies = findMovieByMovieNameAndUser(movieName, userId);
       
       if (CollectionUtils.isNotEmpty(movies)) {
           throw new JeeApplicationException("Movie is already exist.");
       }  
    }
    
    public List<Movie> findMovieByMovieNameAndUser(String movieName, Long userId) throws JeeApplicationException {
        
        Map<String, Object> params = new HashMap<>(2);
        params.put("movieName", movieName);
        params.put("userId", userId);
        List<Movie> movies = calledNamedQuery("MovieUser.findMovieByMovieNameAndUserId", params);
        
        return movies;
    }
    
    public List<Movie> findMovieByMovieIdAndUserId(Long movieId, Long userId) throws JeeApplicationException {
        
        Map<String, Object> params = new HashMap<>(2);
        params.put("movieId", movieId);
        params.put("userId", userId);
        List<Movie> movies = calledNamedQuery("MovieUser.findMovieByMovieIdAndUserId", params);
        
        return movies;
    }
    
    public Movie transferMovie(List<Movie> movies) {
        
        Movie movie = null;
        if (CollectionUtils.isNotEmpty(movies)) {
            movie = new Movie();
            movie.setId(movies.get(0).getId());
            movie.setName(movies.get(0).getName());
            movie.setType(movies.get(0).getType());
            movie.setGenre(movies.get(0).getGenre());
            movie.setActors(movies.get(0).getActors());
            movie.setRating(movies.get(0).getRating());
            movie.setImage(movies.get(0).getImage());
        }
        
        return movie;
    }

//    public void createImage() {
//        File file = new File("C://Users//valentin//Downloads//wallpapers//barca//barcalogo.png");
//
//        try {
//            // Reading a Image file from file system
//            FileInputStream imageInFile = new FileInputStream(file);
//            byte imageData[] = new byte[(int) file.length()];
//            imageInFile.read(imageData);
//            // Converting Image byte array into Base64 String
//            String imageDataString = Base64Coding.encodeImage(imageData);
//            System.out.println(imageDataString);
//        } catch (Exception ex) {
//
//        }
//    }
}
