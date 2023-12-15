package com.multi.repo;


import com.multi.entity.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepo extends JpaRepository<Movies,Integer> {


    /**
     * Custom JPQL query to find movies by actor.
     *
     * @param actor The actor's name.
     * @return List of movies featuring the specified actor.
     */
    @Query("SELECT m FROM Movies m JOIN m.actors a WHERE a = :actor")
    List<Movies> findMovieByActor(@Param("actor") String actor);

    List<Movies> findByYear(String year);

    List<Movies> findByTitle(String search);
}
