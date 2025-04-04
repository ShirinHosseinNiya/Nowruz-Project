package org.project;

import org.project.account.artist.Artist;
import org.project.account.artist.ArtistMain;
import org.project.account.user.SimpleUser;
import org.project.account.user.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
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
                User user = new SimpleUser("", "", 0, "", "", "");
                user.register();
                break;
            case 2:
                Artist artist = new ArtistMain("", "", 0, "", "", "");
                artist.register();
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
                    String truePassword = scanner.nextLine();
                    if (password.equals(truePassword)) {
                        String role = scanner1.nextLine();
                        switch (role) {
                            case "user":
                                SimpleUser user = new SimpleUser(scanner1.nextLine(), scanner1.nextLine(), scanner1.nextInt(), scanner1.nextLine(), username, password);
                                break;
                            case "artist":
                                ArtistMain artist = new ArtistMain(scanner1.nextLine(), scanner1.nextLine(), scanner1.nextInt(), scanner1.nextLine(), username, password);
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
        firstView();
    }

    public static void firstView() {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        if (hour >= 5 && hour < 12) {
            System.out.println("Good Morning! ☀️");
        } else if (hour >= 12 && hour < 17) {
            System.out.println("Good Afternoon! 🌤️");
        } else if (hour >= 17 && hour < 21) {
            System.out.println("Good Evening! 🌆");
        } else {
            System.out.println("Good Night! 🌙");
        }
        guid();
    }

    public static void guid() {
        System.out.println("Here is a simple guide on how to use the app.");
        System.out.println("To find a specific artist, album, or song, use the search bar by pressing the 'F' button.");
        Scanner scanner = new Scanner(System.in);
        String userCommand = scanner.nextLine();
        if (userCommand.charAt(userCommand.length() - 1) == 'F' || userCommand.charAt(userCommand.length() - 1) == 'f') {
            searchingFront();
        }
        // other things
    }

    public static void searchingFront() {
        System.out.println("Please enter the name of the artist, album, or song: ");
        Scanner scanner = new Scanner(System.in);
        String searchTerm = scanner.nextLine();
        ArrayList<String> results = new ArrayList<>();
        ArrayList<String> types = new ArrayList<>();
        int[] number = new int[1];
        number[0] = 1;
        System.out.println("Artists: ");
        searchingArtist(searchTerm, number, results, types);
        System.out.println("Albums: ");
        searchingSongAlbum("searchAlbum", searchTerm, number, results, types, "Album");
        System.out.println("Songs: ");
        searchingSongAlbum("searchSong", searchTerm, number, results, types, "Song");
        number[0] = 1;
        System.out.println("Please enter the number of the result you're looking for (enter 0 to exit): ");
        int userChoice = scanner.nextInt();
        if (userChoice == 0) {
            guid();
        }
        else if (types.get(userChoice - 1).equals("artist")) {
            // artist page view
        }
        else if (types.get(userChoice - 1).equals("album")) {
            // album page view
        }
        else if (types.get(userChoice - 1).equals("song")) {
            // song page view
        }
    }

    public static void searchingArtist(String searchTerm, int[] number, ArrayList<String> results, ArrayList<String> types) {
        try {
            boolean found = false;
            File file = new File ("src\\main\\java\\org\\project\\files\\searchArtist.txt");
            Scanner scanner1 = new Scanner(file);
            while (scanner1.hasNextLine()) {
                String artist = scanner1.nextLine();
                if (artist.contains(searchTerm)) {
                    found = true;
                    System.out.println(number[0] + ". " + artist);
                    results.add(artist);
                    types.add("artist");
                    number[0]++;
                }
            }
            if (!found) {
                System.out.println("No match was found.");
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            e.printStackTrace();
        }
    }

    public static void searchingSongAlbum(String fileName, String searchTerm, int[] number, ArrayList<String> results, ArrayList<String> types, String type) {
        try {
            boolean found = false;
            File file = new File ("src\\main\\java\\org\\project\\files\\" + fileName + ".txt");
            Scanner scanner1 = new Scanner(file);
            while (scanner1.hasNextLine()) {
                String result = scanner1.nextLine();
                if (result.contains(searchTerm)) {
                    found = true;
                    String resultArtist = result + " - " + scanner1.nextLine();
                    System.out.println(number[0] + ". " + resultArtist);
                    results.add(resultArtist);
                    types.add(type);
                    number[0]++;
                }
            }
            if (!found) {
                System.out.println("No match was found.");
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            e.printStackTrace();
        }
    }
}