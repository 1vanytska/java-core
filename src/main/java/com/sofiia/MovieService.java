package com.sofiia;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MovieService {
    public List<Movie> loadMovies() {
        ObjectMapper jsonMapper = JsonParser.getMapper();
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);

        File folder = new File("./data");

        if (!folder.exists() || !folder.isDirectory()) {
            System.err.println("Data folder not found or is not a directory.");
            return new ArrayList<>();
        }

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));

        List<Movie> allMovies = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                try {
                    List<Movie> movies = jsonMapper.readValue(
                            file,
                            new TypeReference<List<Movie>>() {}
                    );
                    allMovies.addAll(movies);
                } catch (Exception e) {
                    System.err.println("Error reading file: " + file.getName() + ": " + e.getMessage());
                }
            }
        }
        System.out.println(allMovies.toString());
        return allMovies;
    }
}