package com.sofiia.ui;

import java.util.Scanner;

public class ConsoleInterface {
    private final Scanner scanner = new Scanner(System.in);

    public String askAttribute() {
        System.out.println("Choose attribute for statistics:");
        System.out.println("1 - director");
        System.out.println("2 - year");
        System.out.println("3 - genre");
        System.out.print("Enter option number: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        return switch (choice) {
            case 1 -> "director";
            case 2 -> "year";
            case 3 -> "genre";
            default -> {
                System.out.println("Invalid choice, defaulting to 'director'");
                yield "director";
            }
        };
    }
}
