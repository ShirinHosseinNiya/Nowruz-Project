package org.project;

import org.project.account.Admin;
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
        boolean userExists = false;             // at first, we assume the user does not exist.
        try {
            File file = new File ("src\\main\\java\\org\\project\\files\\usernames.txt");
            Scanner scanner1 = new Scanner(file);
            while (scanner1.hasNextLine()) {
                String line = scanner1.nextLine();
                if (line.equalsIgnoreCase(username)) {
                    userExists = true;
                    gettingPassword(username);      // if the user already has an account, gettingPassword method will run.
                    break;
                }
            }
            if (!userExists) {
                File possibleArtist = new File("src\\main\\java\\org\\project\\files\\Admin\\Artists\\" + username);
                if (possibleArtist.exists()) {
                    System.out.println("Your Account has not been verified yet.");
                    System.exit(0);
                }
                else {
                    System.out.println("You do not seem to have an existing account.");
                    System.out.println("If you want to edit your username, please enter 'E'.");
                    System.out.println("If you want to create an account, please enter 'C'.");
                    char userChoice;
                    while (true) {
                        userChoice = scanner.next().charAt(0);
                        if (userChoice == 'E' || userChoice == 'e') {
                            login();
                        }
                        else if (userChoice == 'C' || userChoice == 'c') {
                            createAccount();
                        }
                        else {
                            System.out.println("You must enter either 'E', 'C' or 'C'. Please try again.");
                        }
                    }
                }
            }
            scanner1.close();
        }
        catch (FileNotFoundException e) {
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
            default:
                System.out.println("Please choose either 1 or 2.");
                createAccount();
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
                if (line.toLowerCase().equals(username.toLowerCase())) { // the program will read the file until it finds the username user has entered.
                    String truePassword = scanner1.nextLine();
                    if (password.equals(truePassword)) {    // if the password user has entered is true, remaining actions will execute.
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
                            case "admin":
                                Admin.greeting();
                        }
                    }
                    else {
                        System.out.println("Username or password is incorrect. Please try again.");     // if the password user has entered does not match the username.
                        login();
                    }
                    break;
                }
                else {
                    for (int i = 0 ; i < 6 ; i++) {     // if the file has not found the username yet, it doesn't have to read the information about the wrong username.
                        scanner1.nextLine();
                    }
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
