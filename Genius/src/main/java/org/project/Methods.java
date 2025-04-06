package org.project;

import org.project.account.artist.ArtistMain;
import org.project.account.user.SimpleUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Methods {
    public static void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter your username: ");
        String username = scanner.nextLine();
        boolean userExists = false;
        try {
            File file = new File ("src\\main\\java\\org\\project\\files\\usernames.txt");
            Scanner scanner1 = new Scanner(file);
            while (scanner1.hasNextLine()) {
                String line = scanner1.nextLine();
                if (line.equals(username)) {
                    userExists = true;
                    gettingPassword(username);
                    break;
                }
            }
            if (!userExists) {
                System.out.println("You do not seem to have an existing account. Press enter if you want to sign up.");
                scanner.nextLine();
                createAccount();
            }
            scanner1.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            e.printStackTrace();
        }
    }

    public static void createAccount() {
        System.out.println("Please choose your role:");
        System.out.println("1. User");
        System.out.println("2. Artist");
        Scanner scanner = new Scanner(System.in);
        int role = scanner.nextInt();
        switch (role) {
            case 1:
                Session.currentUser = new SimpleUser("", "", 0, "", "", "");
                Session.currentUser.register();
                break;
            case 2:
                Session.currentArtist = new ArtistMain("", "", 0, "", "", "");
                Session.currentArtist.register();
                break;
        }
    }

    public static void gettingPassword(String username) {
        System.out.println("Please enter your password: ");
        Scanner scanner = new Scanner(System.in);
        String password = scanner.nextLine();
        try {
            File file = new File ("src\\main\\java\\org\\project\\files\\usersInfo.txt");
            Scanner scanner1 = new Scanner(file);
            while (scanner1.hasNextLine()) {
                String line = scanner1.nextLine();
                if (line.equals(username)) {
                    String truePassword = scanner1.nextLine();
                    if (password.equals(truePassword)) {
                        String role = scanner1.nextLine();
                        switch (role) {
                            case "user":
                                Session.currentUser = new SimpleUser(scanner1.nextLine(), scanner1.nextLine(), scanner1.nextInt(), scanner1.nextLine(), username, password);
                                Session.currentUser.greeting();
                                break;
                            case "artist":
                                Session.currentArtist = new ArtistMain(scanner1.nextLine(), scanner1.nextLine(), scanner1.nextInt(), scanner1.nextLine(), username, password);
                                Session.currentArtist.greeting();
                                break;
                        }
                    }
                    else {
                        System.out.println("Username or password is incorrect. Please try again.");
                        login();
                    }
                    break;
                }
            }
            scanner1.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            e.printStackTrace();
        }
    }
}