package com.sofiia.parser;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofiia.model.Movie;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class MovieService {

    public List<Movie> loadMovies(int threads, String folderPath) throws InterruptedException {
        ObjectMapper jsonMapper = JsonParserUtil.getMapper();

        File folder = new File(folderPath);

        if (!folder.exists() || !folder.isDirectory()) {
            System.err.println("Data folder not found or is not a directory.");
            return new ArrayList<>();
        }

        File[] files = folder.listFiles((dir, name) -> name.endsWith(".json"));
        if (files == null || files.length == 0) {
            System.err.println("No JSON files found in folder.");
            return new ArrayList<>();
        }

        List<Movie> allMovies = new ArrayList<>();

        try (ExecutorService executor = Executors.newFixedThreadPool(threads)) {
            List<Future<List<Movie>>> futures = new ArrayList<>();

            for (File file : files) {
                futures.add(executor.submit(() -> {
                    try {
                        return jsonMapper.readValue(file, new TypeReference<List<Movie>>() {});
                    } catch (Exception e) {
                        System.err.println("Error reading file: " + file.getName() + ": " + e.getMessage());
                        return new ArrayList<Movie>();
                    }
                }));
            }

            for (Future<List<Movie>> future : futures) {
                try {
                    allMovies.addAll(future.get());
                } catch (ExecutionException e) {
                    System.err.println("Error in task execution: " + e.getMessage());
                }
            }

            executor.shutdown();
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        }

        System.out.println("Loaded movies: " + allMovies.size());
        return allMovies;
    }
}