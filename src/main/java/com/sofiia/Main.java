package com.sofiia;

import com.sofiia.model.Movie;
import com.sofiia.output.XmlWriter;
import com.sofiia.parser.MovieService;
import com.sofiia.statistics.StatisticsCalculator;
import com.sofiia.ui.ConsoleInterface;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        int threadCount = 8;
        String dataFolderPath = "./data";

        ConsoleInterface console = new ConsoleInterface();
        String attribute = console.askAttribute();

        MovieService service = new MovieService();
        long start = System.nanoTime();
        List<Movie> movies = service.loadMovies(threadCount, dataFolderPath);
        long end = System.nanoTime();
        long durationMs = (end - start) / 1_000_000;
        System.out.println("Execution time with " + threadCount + " threads: " + durationMs + " ms");

        StatisticsCalculator calculator = new StatisticsCalculator();
        Map<String, Long> stats = calculator.calculate(movies, attribute);

//        stats.forEach((key, value) ->
//                System.out.println(key + " -> " + value)
//        );

        XmlWriter writer = new XmlWriter();
        writer.write(stats, attribute);
        System.out.println("Statistics created: statistics_by_" + attribute + ".xml");
    }
}