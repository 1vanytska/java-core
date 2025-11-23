package com.sofiia;

public class Main {
    public static void main(String[] args) {
        MovieService service = new MovieService();
        service.loadMovies();
    }
}