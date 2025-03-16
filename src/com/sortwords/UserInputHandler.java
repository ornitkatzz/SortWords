package com.sortwords;

import java.util.Scanner;

public class UserInputHandler {
    public String getSortingOrder() {
        Scanner scanner = new Scanner(System.in);
        String sortOption;
        
        while (true) {
            System.out.println("Please enter the desired sorting type (sort -a for ascending, sort -d for descending):");
            sortOption = scanner.nextLine().trim().toLowerCase();
            if (sortOption.equals("sort -a") || sortOption.equals("sort -d")) {
                break;
            } else {
                System.out.println("Invalid input. Try again.");
            }
        }
        scanner.close();
        return sortOption;
    }
}