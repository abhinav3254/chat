package com.multi.service;


import com.multi.entity.Movies;
import com.multi.repo.MovieRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Service class for managing movie-related operations.
 *
 * @author AbKumar
 */
@Service
public class MovieService {

    // Logger for logging messages
    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    // Autowired MovieRepo for database operations
    @Autowired
    private MovieRepo movieRepo;


    /**
     * Adds a single movie to the database.
     *
     * @param movies The movie to be added.
     * @return ResponseEntity with a success message or an error message.
     */
    public ResponseEntity<String> addMovies(Movies movies) {
        try {
            movieRepo.save(movies);
            return new ResponseEntity<>("movies saved",HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("add movie went into some issue",e);
            return new ResponseEntity<>(e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Retrieves all movies from the database with pagination.
     *
     * @param page Page number for pagination.
     * @param size Number of movies per page.
     * @return ResponseEntity with a page of movies or an error message.
     */
    public ResponseEntity<Page<Movies>> getAllMovies(int page,int size) {
        try {
            Pageable pageable = PageRequest.of(page,size);
            Page<Movies> movies = movieRepo.findAll(pageable);
            return new ResponseEntity<>(movies,HttpStatus.OK);
        } catch (Exception e) {
            logger.error("can't fetch movies right now",e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Adds multiple movies to the database.
     *
     * @param list List of movies to be added.
     * @return ResponseEntity with a success message or an error message.
     */
    public ResponseEntity<String> addMultipleMovies(List<Movies> list) {
        try {

                for (Movies movies:list) {
                    movieRepo.save(movies);
                }

                return new ResponseEntity<>("all movies added",HttpStatus.OK);

        } catch (Exception e) {
            logger.error("add multiple movies gives an error ",e);
            return new ResponseEntity<>(e.getLocalizedMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Retrieves a paginated list of movies featuring a specific actor.
     *
     * @param actor The actor's name.
     * @param page  The page number.
     * @param size  The number of items per page.
     * @return ResponseEntity containing the paginated list of movies featuring the specified actor.
     */
    public ResponseEntity<Page<Movies>> findMoviesByActor(String actor, Integer page, Integer size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            List<Movies> list = movieRepo.findMovieByActor(actor);
            Page<Movies> movies = new PageImpl<>(list,pageable,list.size());
            return new ResponseEntity<>(movies, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Find movies by actor encountered an error", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Finds movies based on the provided year, paginates the result, and returns a ResponseEntity.
     *
     * @param year The year to search for movies.
     * @param page The page number for pagination.
     * @param size The size of each page for pagination.
     * @return ResponseEntity containing a page of Movies or INTERNAL_SERVER_ERROR if an exception occurs.
     */
    public ResponseEntity<Page<Movies>> findMoviesByYear(String year, Integer page, Integer size) {
        try {

            List<Movies> list = movieRepo.findByYear(year);

            Pageable pageable = PageRequest.of(page,size);
            Page<Movies> moviesPage = new PageImpl<>(list,pageable,list.size());

            return new ResponseEntity<>(moviesPage,HttpStatus.OK);

        } catch (Exception e) {
            logger.error("something went wrong in search movie by year",e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Searches for movies based on the provided search term, paginates the result, and returns a ResponseEntity.
     *
     * @param search The search term to find movies.
     * @param page   The page number for pagination.
     * @param size   The size of each page for pagination.
     * @return ResponseEntity containing a page of Movies or INTERNAL_SERVER_ERROR if an exception occurs.
     */
    public ResponseEntity<Page<Movies>> searchMovies(String search,Integer page,Integer size) {
        try {

            List<Movies> list = movieRepo.findByTitle(search);
            Pageable pageable = PageRequest.of(page,size);
            Page<Movies> moviesPage = new PageImpl(list,pageable,list.size());
            return new ResponseEntity<>(moviesPage,HttpStatus.OK);

        } catch (Exception e) {
            logger.error("error in search movies",e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
