package org.project.account.user;

import org.project.Methods;
import org.project.Session;
import org.project.account.Account;

import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class User implements Account {
    public String firstName;
    public String lastName;
    public int age;
    public String email;
    public String username;
    public String password;

    // constructor
    public User(String firstName, String lastName, int age, String email, String username, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    @Override
    public void register() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the following information:");
        System.out.println("First Name: ");
        Session.currentUser.firstName = scanner.nextLine();
        System.out.println("Last Name: ");
        Session.currentUser.lastName = scanner.nextLine();
        while (true) {
            System.out.println("Age: ");
            Session.currentUser.age = scanner.nextInt();
            if (Session.currentUser.age < 7) {System.out.println("you're too young for this app lil fella, come back with your parents.");}
            else if (Session.currentUser.age > 110) {System.out.println("aren't you dead already? be for real please.");}
            else {break;}
        }
        while (true) {
            System.out.println("Email: ");
            Session.currentUser.email = scanner.nextLine();
            if (Session.currentUser.email.matches("^(?![.-])([a-zA-Z0-9]+[\\w.-]*[a-zA-Z0-9])@([a-zA-Z0-9]+(?:-[a-zA-Z0-9]+)*(?:\\.[a-zA-Z0-9]+(?:-[a-zA-Z0-9]+)*)*)\\.[a-zA-Z]{2,6}$")) {
                break;
            }
            else {
                System.out.println("Please enter a valid email address.");
            }
        }
        while (true) {
            System.out.println("Username: (can only contain letters, digits and underscores)");
            Session.currentUser.username = scanner.nextLine();
            if (Session.currentUser.username.matches("^[a-zA-Z0-9]+[a-zA-Z0-9_]*$")){
                break;
            }
            else {
                System.out.println("Please enter a valid username.");
            }
        }
        while (true) {
            System.out.println("Password: (must be at least 8 characters long and contain capital and small letters, digits and special characters (!@#$%^&*))");
            Session.currentUser.password = scanner.nextLine();
            if (Session.currentUser.password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[()!@#$%^&*])[A-Za-z\\d()!@#$%^&*]{8,}$")){
                break;
            }
            else {
                System.out.println("Please enter a valid password.");
            }
        }
//        SimpleUser user = new SimpleUser(firstName, lastName, age, email, username, password);
        infoWriter("src\\main\\java\\org\\project\\files\\usersInfo.txt", Session.currentUser.username, Session.currentUser.password, Session.currentUser.firstName, Session.currentUser.lastName, Session.currentUser.age, Session.currentUser.email);
        usernameWriter("src\\main\\java\\org\\project\\files\\usernames.txt", Session.currentUser.username);
        greeting();
    }

    @Override
    public void infoWriter(String filename, String username, String password, String firstName, String lastName, int age, String email) {
        try {
            FileWriter writer = new FileWriter(filename, true); // Creates or opens the file
            writer.write(username + "\n");
            writer.write(password + "\n");
            writer.write("user\n");
            writer.write(firstName + "\n");
            writer.write(lastName + "\n");
            writer.write(age + "\n");
            writer.write(email + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    @Override
    public void usernameWriter(String filename, String username){
        try {
            FileWriter writer = new FileWriter(filename, true); // Creates or opens the file
            writer.write(username + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }

    @Override
    public void greeting() {
        LocalTime now = LocalTime.now();

        int hour = now.getHour();
        if (hour >= 5 && hour < 12) {
            System.out.println("Good Morning, " + Session.currentUser.firstName + "! ☀️");
        } else if (hour >= 12 && hour < 17) {
            System.out.println("Good Afternoon, " + Session.currentUser.firstName + "! 🌤️");
        } else if (hour >= 17 && hour < 21) {
            System.out.println("Good Evening, " + Session.currentUser.firstName + "! 🌆");
        } else {
            System.out.println("Good Night, " + Session.currentUser.firstName + "! 🌙");
        }
        guid();
    }

    @Override
    public void guid() {
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
            Session.currentUser.guid();
        }
        else if (types.get(userChoice - 1).equals("artist")) {
            artistPageView(results.get(userChoice - 1));
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
                    Session.currentUser.guid();
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

    public static void artistPageView(String artist) {
        boolean following = false;
        try{
            File file = new File("src\\main\\java\\org\\project\\files\\Users\\" + Session.currentUser.username + "\\followingArtists.txt");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                if (scanner.nextLine().equals(artist)) {
                    following = true;
                    break;
                }
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            e.printStackTrace();
        }
        System.out.println(artist);
        if (following) {
            System.out.println("followed");
        }
        System.out.println("");
        System.out.println("About Artist");
        try{
            File file = new File("src\\main\\java\\org\\project\\files\\Artists\\" + artist + "\\info.txt");
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                System.out.println(scanner.nextLine());
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            e.printStackTrace();
        }
        System.out.println("");
        System.out.println("Albums");
        ArrayList<String> albums = new ArrayList<>();
        int albumNumber = 1;
        try{
            File file = new File ("src\\main\\java\\org\\project\\files\\Artists\\" + artist + "\\albums.txt");
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String album = scanner.nextLine();
                System.out.println(albumNumber + ". " + album);
                albums.add(album);
                albumNumber++;
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            e.printStackTrace();
        }
        System.out.println("");
        System.out.println("Singles:");
        ArrayList<String> singles = new ArrayList<>();
        int singleNumber = 1;
        try{
            File file = new File ("src\\main\\java\\org\\project\\files\\Artists\\" + artist + "\\singles.txt");
            Scanner scanner = new Scanner(file);
            while(scanner.hasNextLine()){
                String single = scanner.nextLine();
                System.out.println(singleNumber + ". " + single);
                singles.add(single);
                singleNumber++;
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            e.printStackTrace();
        }
        System.out.println("");
        System.out.println("tips:");
        while (true){
            if (following) {
                System.out.println("Press 'U' to unfollow " + artist + ".");
            }
            else {
                System.out.println("Press 'F' to follow " + artist + ".");
            }
            System.out.println("Press 'A' to see one of the albums' info.");
            System.out.println("Press 'S' to see one of the singles' info.");
            System.out.println("Enter '0' to return to the Home page.");
            Scanner scanner = new Scanner(System.in);
            char userCommand = scanner.next().charAt(0);
            if ((userCommand == 'U' || userCommand == 'u') && following) {
                unfollowArtist(artist);
            }
            else if ((userCommand == 'U' || userCommand == 'u') && !following) {
                System.out.println("You are not following this artist.");
            }
            else if ((userCommand == 'F' || userCommand == 'f') && !following) {
                followArtist(artist);
            }
            else if ((userCommand == 'F' || userCommand == 'f') && following) {
                System.out.println("You are following this artist.");
            }
            else if (userCommand == 'A' || userCommand == 'a') {
                // showing albums
            }
            else if (userCommand == 'S' || userCommand == 's') {
                // showing singles
            }
            else if (userCommand == '0') {
                Session.currentUser.guid();
            }
        }
    }

    public static void followArtist(String artist) {
        try {
            File file = new File("src\\main\\java\\org\\project\\files\\Users\\" + Session.currentUser.username + "\\followingArtists.txt");
            FileWriter writer = new FileWriter(file, true);
            writer.write(artist + "\n");
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unfollowArtist(String artist) {
        File file = new File("src\\main\\java\\org\\project\\files\\Users\\" + Session.currentUser.username + "\\followingArtists.txt");
        ArrayList<String> followingArtists = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String following = scanner.nextLine();
                if (!following.equals(artist)) {
                    followingArtists.add(following);
                }
            }
            scanner.close();
            FileWriter writer = new FileWriter(file, false);
            for (String followingArtist : followingArtists) {
                writer.write(followingArtist + "\n");
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}