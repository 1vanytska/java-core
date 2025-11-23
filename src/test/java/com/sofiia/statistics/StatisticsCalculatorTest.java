package com.sofiia.statistics;

import com.sofiia.model.Movie;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class StatisticsCalculatorTest {

    @Test
    public void testCalculateGenreStatistics() {
        Movie m1 = new Movie();
        m1.setTitle("Title1");
        m1.setDirector("Director1");
        m1.setYear(2020);
        m1.setGenre(List.of("Drama", "Sci-Fi"));

        Movie m2 = new Movie();
        m2.setTitle("Title2");
        m2.setDirector("Director2");
        m2.setYear(2021);
        m2.setGenre(List.of("Drama"));

        Movie m3 = new Movie();
        m3.setTitle("Title3");
        m3.setDirector("Director3");
        m3.setYear(2022);
        m3.setGenre(List.of("Sci-Fi"));

        List<Movie> movies = List.of(m1, m2, m3);

        StatisticsCalculator calculator = new StatisticsCalculator();
        Map<String, Long> stats = calculator.calculate(movies, "genre");

        assertEquals(2, stats.get("Drama"));
        assertEquals(2, stats.get("Sci-Fi"));
    }

    @Test
    public void testCalculateDirectorStatistics() {
        Movie m1 = new Movie();
        m1.setTitle("Title1");
        m1.setDirector("James Cameron");
        m1.setYear(2020);
        m1.setGenre(List.of("Action"));

        Movie m2 = new Movie();
        m2.setTitle("Title2");
        m2.setDirector("James Cameron");
        m2.setYear(2021);
        m2.setGenre(List.of("Sci-Fi"));

        Movie m3 = new Movie();
        m3.setTitle("Title3");
        m3.setDirector("Robert Rodriguez");
        m3.setYear(2022);
        m3.setGenre(List.of("Action"));

        List<Movie> movies = List.of(m1, m2, m3);

        StatisticsCalculator calculator = new StatisticsCalculator();
        Map<String, Long> stats = calculator.calculate(movies, "director");

        assertEquals(2, stats.get("James Cameron"));
        assertEquals(1, stats.get("Robert Rodriguez"));
    }
    @Test
    public void testEmptyMoviesList() {
        StatisticsCalculator calculator = new StatisticsCalculator();
        Map<String, Long> stats = calculator.calculate(List.of(), "genre");
        assertTrue(stats.isEmpty(), "Statistics should be empty for empty movie list");
    }

    @Test
    public void testUnknownAttribute() {
        Movie m = new Movie();
        m.setTitle("Test");
        m.setDirector("Someone");
        m.setYear(2020);
        m.setGenre(List.of("Drama"));

        StatisticsCalculator calculator = new StatisticsCalculator();
        Map<String, Long> stats = calculator.calculate(List.of(m), "unknown");
        assertTrue(stats.isEmpty(), "Statistics should be empty for unknown attribute");
    }
}