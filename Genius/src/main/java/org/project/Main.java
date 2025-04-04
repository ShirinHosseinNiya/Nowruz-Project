package org.project;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Genius!");
        System.out.println("To continue using the program, please log in or create a new account.");
        System.out.println("1. login");
        System.out.println("2. create a new account");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                Methods.login();
                break;
            case 2:
                Methods.createAccount();
                break;
        }
    }
}