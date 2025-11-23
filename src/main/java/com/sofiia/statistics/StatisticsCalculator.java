package com.sofiia.statistics;

import com.sofiia.model.Movie;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticsCalculator {

    public Map<String, Long> calculate(List<Movie> movies, String attribute) {
        Map<String, Long> stats = new HashMap<>();

        for (Movie movie : movies) {
            try {
                Field field = Movie.class.getDeclaredField(attribute);
                field.setAccessible(true);
                Object value = field.get(movie);

                if (value != null) {
                    String stringValue = value.toString();

                    String cleaned = stringValue.replace("[", "").replace("]", "");

                    String[] parts = cleaned.split(",");
                    for (String part : parts) {
                        String trimmed = part.trim();
                        stats.put(trimmed, stats.getOrDefault(trimmed, 0L) + 1);
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                System.err.println("Invalid attribute: " + attribute);
                return Collections.emptyMap();
            }
        }

        return stats.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new
                ));
    }
}