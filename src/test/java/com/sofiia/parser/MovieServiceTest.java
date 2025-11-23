package com.sofiia.parser;

import com.sofiia.model.Movie;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MovieServiceTest {

    @Test
    public void testLoadMoviesSingleThread() throws InterruptedException {
        MovieService service = new MovieService();
        List<Movie> movies = service.loadMovies(1, "data");

        assertNotNull(movies);
        assertFalse(movies.isEmpty(), "Movies list should not be empty");
    }

    @Test
    public void testLoadMoviesMultiThread() throws InterruptedException {
        MovieService service = new MovieService();
        List<Movie> movies = service.loadMovies(4, "data");

        assertNotNull(movies);
        assertFalse(movies.isEmpty(), "Movies should be loaded with multiple threads");
    }

    @Test
    public void testNoJsonFiles() throws InterruptedException {
        File emptyDir = new File("src/test/resources/empty");

        if (emptyDir.exists() && emptyDir.isDirectory()) {
            File[] files = emptyDir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.delete()) {
                        System.err.println("Failed to delete file: " + file.getAbsolutePath());
                    }
                }
            }
        } else {
            boolean created = emptyDir.mkdirs();
            if (!created) {
                throw new IllegalStateException("Could not create test directory: " + emptyDir.getPath());
            }
        }

        MovieService service = new MovieService();
        List<Movie> movies = service.loadMovies(1, emptyDir.getPath());

        assertTrue(movies.isEmpty(), "Movies list should be empty when no JSON files found");
    }
}