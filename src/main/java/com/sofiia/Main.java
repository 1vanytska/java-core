package com.sofiia;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        int threadCount = 4;

        ConsoleInterface console = new ConsoleInterface();
        String attribute = console.askAttribute();

        MovieService service = new MovieService();
        long start = System.nanoTime();
        List<Movie> movies = service.loadMovies(threadCount);
        long end = System.nanoTime();
        long durationMs = (end - start) / 1_000_000;
        System.out.println("Execution time with " + threadCount + " threads: " + durationMs + " ms");
    }
}