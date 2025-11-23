package com.sofiia;

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

        switch (choice) {
            case 1: return "director";
            case 2: return "year";
            case 3: return "genre";
            default:
                System.out.println("Invalid choice, defaulting to 'director'");
                return "director";
        }
    }
}
