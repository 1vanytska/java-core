package com.sofiia;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");
        MovieService service = new MovieService();
        service.loadMovies();
    }
}