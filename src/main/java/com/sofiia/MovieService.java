package com.sofiia;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.util.List;

public class MovieService {
    public List<Movie> loadMovies() {
        //читання
        ObjectMapper jsonMapper = JsonParser.getMapper();
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            List<Movie> movies = jsonMapper.readValue(
                    new File("./data/movies.json"),
                    new TypeReference<List<Movie>>() {}
            );
            System.out.println(movies.toString());
            return movies;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}