package com.multi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Movies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 2000)
    private String title;
    private String year;

    @ElementCollection
    @CollectionTable(name = "movies_genres", joinColumns = @JoinColumn(name = "movies_id"))
    @Column(name = "genre")
    private List<String> genres;

    @ElementCollection
    @CollectionTable(name = "movies_ratings", joinColumns = @JoinColumn(name = "movies_id"))
    @Column(name = "rating")
    private List<Integer> ratings;

    @Column(length = 2000)
    private String contentRating;

    @Column(length = 2000)
    private String duration;

    @Column(length = 2000)
    private String releaseDate;

    @Column(length = 2000)
    private int averageRating;

    @Column(length = 2000)
    private String originalTitle;

    @Column(length = 8000)
    private String storyline;

    @ElementCollection
    @CollectionTable(name = "movies_actors", joinColumns = @JoinColumn(name = "movies_id"))
    @Column(name = "actor")
    private List<String> actors;

    private double imdbRating;
    private String posterurl;
}
