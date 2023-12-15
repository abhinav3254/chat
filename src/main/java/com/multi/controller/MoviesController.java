package com.multi.controller;

import com.multi.entity.Movies;
import com.multi.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    @Autowired
    private MovieService movieService;

    @PutMapping("/add")
    public ResponseEntity<String> addMovies(@RequestBody Movies movies) {
        return movieService.addMovies(movies);
    }

    @GetMapping("/")
    public ResponseEntity<Page<Movies>> getAllMovies(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return movieService.getAllMovies(page,size);
    }

    @PutMapping("/add/lists")
    public ResponseEntity<String> addMultiple(@RequestBody List<Movies> list) {
        return movieService.addMultipleMovies(list);
    }


    @GetMapping("/actor/{actor}")
    public ResponseEntity<Page<Movies>> findMoviesByActor(@PathVariable String actor,
                                                          @RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size
    ) {
        return movieService.findMoviesByActor(actor,page,size);
    }

}
