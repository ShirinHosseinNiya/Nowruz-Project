package org.project;

import org.project.account.artist.Artist;
import org.project.account.artist.ArtistMain;
import org.project.account.user.SimpleUser;
import org.project.account.user.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
        ArrayList<String> artists = new ArrayList<>();
        ArrayList<String> types = new ArrayList<>();
        int[] number = new int[1];
        number[0] = 1;
        System.out.println("Artists: ");
        searchingArtist(searchTerm, number, results, types);
        System.out.println("Albums: ");
        searchingSongAlbum("searchAlbum", searchTerm, number, results, artists, types, "Album");
        System.out.println("Songs: ");
        searchingSongAlbum("searchSong", searchTerm, number, results, artists, types, "Song");
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
            songPageView(results.get(userChoice - 1), artists.get(userChoice - 1));
        }
    }

    public static void searchingArtist(String searchTerm, int[] number, ArrayList<String> results, ArrayList<String> types) {
        try {
            boolean found = false;
            File file = new File ("src\\main\\java\\org\\project\\files\\searchArtist.txt");
            Scanner scanner1 =  new Scanner(file);
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

    public static void searchingSongAlbum(String fileName, String searchTerm, int[] number, ArrayList<String> results, ArrayList<String> artists, ArrayList<String> types, String type) {
        try {
            boolean found = false;
            File file = new File ("src\\main\\java\\org\\project\\files\\" + fileName + ".txt");
            Scanner scanner1 = new Scanner(file);
            while (scanner1.hasNextLine()) {
                String result = scanner1.nextLine();
                if (result.contains(searchTerm)) {
                    found = true;
                    String artist = scanner1.nextLine();
                    String resultArtist = result + " - " + artist;
                    System.out.println(number[0] + ". " + resultArtist);
                    results.add(resultArtist);
                    artists.add(artist);
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

    public static void songPageView(String songArtist, String artist) {
        ArrayList<String> lyrics = new ArrayList<>();
        int number = 1;
        try {
            File file = new File ("src\\main\\java\\org\\project\\files\\Songs\\" + songArtist + "\\info.txt");
            Scanner scanner = new Scanner(file);
            System.out.println(scanner.nextLine());     //prints name of the song
            System.out.println(scanner.nextLine());     //prints album of the song
            System.out.println(scanner.nextLine());     //prints artist of the song
            System.out.println("");
            while (scanner.hasNextLine()) {
                String lyric = scanner.nextLine();
                System.out.println(number + ". " + lyric);
                lyrics.add(lyric);
                number++;
            }
            while (true) {
                System.out.println("tips:");
                System.out.println("Press 'C' to comment on a specific line");
                System.out.println("Press 'E' to edit a specific line.");
                System.out.println("Enter '0' to return to the Home page.");
                Scanner scanner1 = new Scanner(System.in);
                char userCommand = scanner1.next().charAt(0);
                if (userCommand == 'C' || userCommand == 'c') {
                    // comment method
                }
                else if (userCommand == 'E' || userCommand == 'e') {
                    editing(number, songArtist, artist);
                }
                else if (userCommand == '0') {
                    guid();
                }
            }
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found");
        }
    }

    public static void editing(int lines, String artist, String songArtist) {
        Scanner scanner = new Scanner(System.in);
        int lineNumber;
        while (true) {
            System.out.println("Enter the line number you'd like to edit: ");
            lineNumber = scanner.nextInt();
            if (lineNumber > lines) {
                System.out.println("The number you have entered is greater than the whole song. Please try again.");
            }
            else if (lineNumber <= 0) {
                System.out.println("Enter a number greater than 0. Please try again.");
            }
            else {
                break;
            }
        }
        File folder = new File("src\\main\\java\\org\\project\\files\\Artists\\" + artist + "\\editRequests");
        if (!folder.exists() && !folder.mkdirs()) {
            System.out.println("Folder could not be created.");
        }
        try {
            File file = new File ("src\\main\\java\\org\\project\\files\\Songs\\" + songArtist + "\\editRequests\\numberOfEdits.txt");
            Scanner scanner1 = new Scanner(file);
            int number = scanner1.nextInt();
            number++;
            FileWriter writer = new FileWriter("src\\main\\java\\org\\project\\files\\Songs\\" + songArtist + "\\editRequests\\numberOfEdits.txt");
            writer.write(number);
            writer.close();
            FileWriter writer1 = new FileWriter("src\\main\\java\\org\\project\\files\\Songs\\" + songArtist + "\\editRequests\\" + number + ".txt");
            System.out.println("Please enter the correct lyrics for line" + lineNumber);
            String correctLyric  = scanner.nextLine();
            writer1.write(lineNumber + "\n" + correctLyric);
            writer1.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}