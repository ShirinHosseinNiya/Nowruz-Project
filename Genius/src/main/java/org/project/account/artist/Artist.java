package org.project.account.artist;

import org.project.Methods;
import org.project.Session;
import org.project.account.Account;
import org.project.account.user.SimpleUser;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Artist implements Account {
    public String firstName;
    public String lastName;
    public int age;
    public String email;
    public String username;
    public String password;

    // constructor
    public Artist(String firstName, String lastName, int age, String email, String username, String password) {
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
        System.out.println("First Name: (Users will use your first and last name to search for your account)");
        Session.currentArtist.firstName = scanner.nextLine();
        System.out.println("Last Name: ");
        Session.currentArtist.lastName = scanner.nextLine();
        while (true) {
            System.out.println("Age: ");
            Session.currentArtist.age = scanner.nextInt();
            if (Session.currentArtist.age < 7) {System.out.println("you're too young for this app lil fella, come back with your parents.");}
            else if (Session.currentArtist.age > 110) {System.out.println("aren't you dead already? be for real please.");}
            else {break;}
        }
        scanner.nextLine();
        while (true) {
            System.out.println("Email: ");
            Session.currentArtist.email = scanner.nextLine();
            if (Session.currentArtist.email.matches("^(?![.-])([a-zA-Z0-9]+[\\w.-]*[a-zA-Z0-9])@([a-zA-Z0-9]+(?:-[a-zA-Z0-9]+)*(?:\\.[a-zA-Z0-9]+(?:-[a-zA-Z0-9]+)*)*)\\.[a-zA-Z]{2,6}$")) {
                break;
            }
            else {
                System.out.println("Please enter a valid email address.");
            }
        }
        while (true) {
            System.out.println("Username: (can only contain letters, digits and underscores)");
            Session.currentArtist.username = scanner.nextLine();
            if (Session.currentArtist.username.matches("^[a-zA-Z0-9]+[a-zA-Z0-9_]*$")){
                File file = new File("src\\main\\java\\org\\project\\files\\usernames.txt");
                try {
                    boolean alreadyExists = false;      // if the username is valid, the program will check if it's unique.
                    Scanner scanner1 = new Scanner(file);
                    while (scanner1.hasNextLine()) {
                        if (Session.currentArtist.username.equalsIgnoreCase(scanner1.nextLine())) {
                            System.out.println("This username is already in use.");
                            alreadyExists = true;
                            break;
                        }
                    }
                    scanner1.close();
                    if (alreadyExists == false) {
                        break;
                    }
                }
                catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                System.out.println("Please enter a valid username.");
            }
        }
        while (true) {
            System.out.println("Password: (must be at least 8 characters long and contain capital and small letters, digits and special characters (!@#$%^&*))");
            Session.currentArtist.password = scanner.nextLine();
            if (Session.currentArtist.password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[()!@#$%^&*])[A-Za-z\\d()!@#$%^&*]{8,}$")){
                break;
            }
            else {
                System.out.println("Please enter a valid password.");
            }
        }
        File verify = new File("src\\main\\java\\org\\project\\files\\Admin\\Artists\\" + Session.currentArtist.username + "\\info.txt");
        try {
            verify.getParentFile().mkdirs();
            verify.createNewFile();
            FileWriter writer = new FileWriter(verify);
            writer.write(Session.currentArtist.firstName + "\n" +
                    Session.currentArtist.lastName + "\n" +
                    Session.currentArtist.age + "\n" +
                    Session.currentArtist.email + "\n" +
                    Session.currentArtist.username + "\n" +
                    Session.currentArtist.password);
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Please enter some info about yourself. Type '--exit--' when you're done.");
        File about = new File("src\\main\\java\\org\\project\\files\\Admin\\Artists\\" + Session.currentArtist.username + "\\about.txt");
        try {
            about.getParentFile().mkdirs();
            about.createNewFile();
            FileWriter writer = new FileWriter(about, true);
            while (true) {
                String line = scanner.nextLine();
                if (line.equalsIgnoreCase("--exit--")) {
                    break;
                }
                else {
                    writer.write(line + "\n");
                }
            }
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Thank you for using this app. You will be able to login once an Admin verifies your account.");
        System.exit(0);
    }

    @Override
    public void infoWriter(String filename, String username, String password, String firstName, String lastName, int age, String email) {
        try {
            FileWriter writer = new FileWriter(filename, true);
            writer.write(username + "\n");
            writer.write(password + "\n");
            writer.write("artist\n");
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
            FileWriter writer = new FileWriter(filename, true);
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
            System.out.println("Good Morning, " + Session.currentArtist.firstName + "!");
        } else if (hour >= 12 && hour < 17) {
            System.out.println("Good Afternoon, " + Session.currentArtist.firstName + "!");
        } else if (hour >= 17 && hour < 21) {
            System.out.println("Good Evening, " + Session.currentArtist.firstName + "!");
        } else {
            System.out.println("Good Night, " + Session.currentArtist.firstName + "!");
        }
        Session.currentArtist.guid();
    }

    @Override
    public void guid () {
        System.out.println("Press 'S' to add a new Single.");
        System.out.println("Press 'A' to add a new Album.");
        System.out.println("Press 'E' to edit the lyric of a song.");
        System.out.println("Press 'R' to see edit requests of users.");
        Scanner scanner = new Scanner(System.in);
        char artistInput = scanner.nextLine().charAt(0);
        if (artistInput == 'S' || artistInput == 's') {
            addingSongSingle();
        }
        else if (artistInput == 'A' || artistInput == 'a') {
            addingAlbum();
        }
        else if (artistInput == 'E' || artistInput == 'e') {
            editing();
        }
        else if (artistInput == 'R' || artistInput == 'r') {
            approvingEdits();
        }
    }

    public static void addingSongSingle() {
        System.out.println("Please enter the following information:");
        System.out.println("Name of the song:");
        Scanner scanner = new Scanner(System.in);
        String songName = scanner.nextLine().trim();
        File singles = new File("src\\main\\java\\org\\project\\files\\Artists\\" + Session.currentArtist.username + "\\singles.txt");
        File searchSong = new File("src\\main\\java\\org\\project\\files\\searchSong.txt");
        File view = new File ("src\\main\\java\\org\\project\\files\\Artists\\" + Session.currentArtist.username + "\\views.txt");
        try {
            FileWriter writer = new FileWriter(singles, true);
            writer.write(songName + "\n");
            writer.close();
            FileWriter writer2 = new FileWriter(searchSong, true);
            writer2.write(songName + "\n" +
                            Session.currentArtist.firstName + " " + Session.currentArtist.lastName + "\n" +
                            Session.currentArtist.username + "\n");
            FileWriter writer3 = new FileWriter(view, true);
            writer3.write(songName + "\n");
            writer3.write("0" + "\n");
            writer3.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        String dateOfRelease;
        System.out.println("Date of Release: (YYYY-MM-DD)");
        dateOfRelease = scanner.nextLine().trim();
        System.out.println("genre:");
        String genre = scanner.nextLine().trim();
        System.out.println("Producer:");
        String producer = scanner.nextLine().trim();
        System.out.println("Composer:");
        String composer = scanner.nextLine().trim();
        System.out.println("Lyricist:");
        String lyricist = scanner.nextLine().trim();

        File songFolder = new File("src\\main\\java\\org\\project\\files\\Artists\\" + Session.currentArtist.username + "\\songs\\" + songName + "-" + Session.currentArtist.username);
        songFolder.mkdir();
        File commentsFolder = new File(songFolder.getAbsolutePath() + "\\comments");
        commentsFolder.mkdir();
        File info = new File(songFolder.getAbsolutePath() + "\\info.txt");
        File aboutSong = new File(songFolder.getAbsolutePath() + "\\about.txt");
        File lyric = new File(songFolder.getAbsolutePath() + "\\lyric.txt");
        try {
            FileWriter writer = new FileWriter(info);
            writer.write(songName + "\n" +
                            "Single" + "\n" +
                            Session.currentArtist.username + "\n" +
                            dateOfRelease + "\n" +
                            genre + "\n" +
                            producer + "\n" +
                            composer + "\n" +
                            lyricist + "\n");
            writer.close();
            System.out.println("Please write some info about your song. Type '--exit--' when you're done.");
            String line;
            FileWriter writer2 = new FileWriter(aboutSong);
            while (true) {
                line = scanner.nextLine();
                if (line.equalsIgnoreCase("--exit--")) {
                    break;
                }
                else {
                    writer2.write(line + "\n");
                }
            }
            writer2.close();
            System.out.println("Pleas write the lyric of your song. Type '--exit--' when you're done.");
            FileWriter writer3 = new FileWriter(lyric);
            while (true) {
                line = scanner.nextLine();
                if (line.equalsIgnoreCase("--exit--")) {
                    break;
                }
                else {
                    writer3.write(line + "\n");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Song has been added successfully.");
        Session.currentArtist.guid();
    }

    public static void addingAlbum() {
        System.out.println("Please enter the following information:");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Name of the Album:");
        String albumName = scanner.nextLine().trim();
        File albumFolder = new File("src\\main\\java\\org\\project\\files\\Artists\\" + Session.currentArtist.username + "\\albums\\" + albumName);
        albumFolder.mkdir();
        File searchAlbum = new File("src\\main\\java\\org\\project\\files\\searchAlbum.txt");
        try {
            FileWriter writer = new FileWriter(searchAlbum, true);
            writer.write(albumName + "\n" +
                    Session.currentArtist.firstName + " " + Session.currentArtist.lastName + "\n" +
                    Session.currentArtist.username + "\n");
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Number of Songs:");
        int noOfSongs = Integer.parseInt(scanner.nextLine().trim());
        System.out.println("Date of Release: (YYYY-MM-DD)");
        String dateOfRelease = scanner.nextLine().trim();
        File info = new File(albumFolder.getAbsolutePath() + "\\info.txt");
        try {
            FileWriter writer = new FileWriter(info);
            writer.write(albumName + "\n");
            writer.write(Session.currentArtist.username + "\n");
            writer.write(noOfSongs + "\n");
            writer.write(dateOfRelease + "\n");
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Please enter the names of the songs:");
        File songs = new File(albumFolder.getAbsolutePath() + "\\songs.txt");
        String songName;
        try {
            FileWriter writer = new FileWriter(songs, true);
            for (int i = 1 ; i <= noOfSongs ; i++) {
                System.out.println(i + ":");
                songName = scanner.nextLine().trim();
                writer.write(songName + "\n");
                addingSongToAlbum(songName, albumName, dateOfRelease, i);
            }
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Session.currentArtist.guid();
    }

    public static void addingSongToAlbum(String songName, String albumName, String dateOfRelease, int songNumber) {
        File songFolder = new File("src\\main\\java\\org\\project\\files\\Artists\\" + Session.currentArtist.username + "\\songs\\" + songName + "-" + Session.currentArtist.username);
        songFolder.mkdir();
        File commentsFolder = new File(songFolder.getAbsolutePath() + "\\comments");
        commentsFolder.mkdir();
        File searchSong = new File("src\\main\\java\\org\\project\\files\\searchSong.txt");
        File view = new File ("src\\main\\java\\org\\project\\files\\Artists\\" + Session.currentArtist.username + "\\views.txt");
        try {
            FileWriter writer = new FileWriter(searchSong, true);
            writer.write(songName + "\n" +
                            Session.currentArtist.firstName + " " + Session.currentArtist.lastName + "\n" +
                            Session.currentArtist.username + "\n");
            writer.close();
            FileWriter writer1 = new FileWriter(view, true);
            writer1.write(songName + "\n");
            writer1.write("0" + "\n");
            writer1.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter the following information:");
        System.out.println("genre:");
        String genre = scanner.nextLine().trim();
        System.out.println("Producer:");
        String producer = scanner.nextLine().trim();
        System.out.println("Composer:");
        String composer = scanner.nextLine().trim();
        System.out.println("Lyricist:");
        String lyricist = scanner.nextLine().trim();

        File info = new File(songFolder.getAbsolutePath() + "\\info.txt");
        File aboutSong = new File(songFolder.getAbsolutePath() + "\\about.txt");
        File lyric = new File(songFolder.getAbsolutePath() + "\\lyric.txt");
        try {
            FileWriter writer = new FileWriter(info);
            writer.write(songName + "\n" +
                            albumName + " (song NO. " + songNumber + ")" + "\n" +
                            Session.currentArtist.username + "\n" +
                            dateOfRelease + "\n" +
                            genre + "\n" +
                            producer + "\n" +
                            composer + "\n" +
                            lyricist + "\n");
            writer.close();
            System.out.println("Please write some info about your song. Type '--exit--' when you're done.");
            String line;
            FileWriter writer2 = new FileWriter(aboutSong);
            while (true) {
                line = scanner.nextLine();
                if (line.equalsIgnoreCase("--exit--")) {
                    break;
                }
                else {
                    writer2.write(line + "\n");
                }
            }
            writer2.close();
            System.out.println("Pleas write the lyric of your song. Type '--exit--' when you're done.");
            FileWriter writer3 = new FileWriter(lyric);
            while (true) {
                line = scanner.nextLine();
                if (line.equalsIgnoreCase("--exit--")) {
                    break;
                }
                else {
                    writer3.write(line + "\n");
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Song has been added successfully.");
    }

    public static void editing() {
        System.out.println("Please enter the name of the song you would like to edit:");
        Scanner scanner = new Scanner(System.in);
        String songName = scanner.nextLine().trim() + "-" + Session.currentArtist.username;
        File songFolder = new File("src\\main\\java\\org\\project\\files\\Artists\\" + Session.currentArtist.username + "\\songs\\" + songName);
        ArrayList<String> lyrics = new ArrayList<>();
        if (songFolder.exists()) {
            File lyricFile = new File(songFolder.getAbsolutePath() + "\\lyric.txt");
            try {
                Scanner scanner1 = new Scanner(lyricFile);
                int number = 1;
                while (scanner1.hasNextLine()) {
                    String Lyric = scanner1.nextLine();
                    lyrics.add(Lyric);
                    System.out.println(number + ". " + Lyric);
                    number++;
                }
                scanner1.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("please enter the number of the line you would like to edit:");
            int toBeEdited;
            while (true) {
                toBeEdited = Integer.parseInt(scanner.nextLine().trim());
                if (toBeEdited <= 0) {
                    System.out.println("Please enter a number greater than 0.");
                }
                else if (toBeEdited > lyrics.size()) {
                    System.out.println("The number you have entered is greater than the whole song. Please try again.");
                }
                else {
                    break;
                }
            }
            System.out.println("Please enter the correct lyric for line " + toBeEdited + ":");
            String correctLyric = scanner.nextLine().trim();
            try {
                FileWriter writer = new FileWriter(lyricFile, false);
                for (int i = 0 ; i < lyrics.size() ; i++) {
                    if (i + 1 == toBeEdited) {
                        writer.write(correctLyric + "\n");
                    }
                    else {
                        writer.write(lyrics.get(i) + "\n");
                    }
                }
                writer.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Song has been successfully edited.");
            Session.currentArtist.guid();
        }
        else {
            System.out.println("Song does not exist.");
            System.out.println("Enter '1' to try again or '0' to exit");
            char choice;
            while (true) {
                choice = scanner.nextLine().charAt(0);
                if (choice == '1') {
                    editing();
                }
                else if (choice == '0') {
                    Session.currentArtist.guid();
                }
                else {
                    System.out.println("Invalid choice. Please enter either '1' or '0'.");
                }
            }
        }
    }

    public static void approvingEdits() {
        Scanner scanner = new Scanner(System.in);
        File file = new File("src\\main\\java\\org\\project\\files\\Artists\\" + Session.currentArtist.username + "\\editRequests");
        File[] editRequests = file.listFiles();
        int numberOfRequests = editRequests.length;
        System.out.println("You have " + numberOfRequests + " edit request(s).");
        if (numberOfRequests == 0) {
            System.out.println("Press any key to exit");
            scanner.nextLine();
            Session.currentArtist.guid();
        }
        else {
            System.out.println("Press 'V' to view requests or '0' to exit.");
            char choice;
            while (true) {
                choice = scanner.nextLine().charAt(0);
                if (choice == '0') {
                    Session.currentArtist.guid();
                }
                else if (choice == 'V' || choice == 'v') {
                    for (File editRequest : editRequests) {
                        try {
                            Scanner scanner1 = new Scanner(editRequest);
                            String songName = scanner1.nextLine();
                            int lineNumber = scanner1.nextInt();
                            scanner1.nextLine();
                            String user = scanner1.nextLine();
                            String preLyric = scanner1.nextLine();
                            String suggestedLyric = scanner1.nextLine();
                            scanner1.close();
                            System.out.println("You have an edit request from @" + user + " on line " + lineNumber + " of the song " + songName + ".");
                            System.out.print("Current lyric: ");
                            System.out.println(preLyric);
                            System.out.print("Suggested lyric: ");
                            System.out.println(suggestedLyric);
                            System.out.println("Press 'A' to accept this request, 'D' to reject it ot '0' to return to the home page.");
                            char AorD;
                            while (true) {
                                AorD = scanner.nextLine().charAt(0);
                                if (AorD == 'A' || AorD == 'a') {
                                    acceptRequests(songName, lineNumber, suggestedLyric);
                                    File adminFile = new File("src\\main\\java\\org\\project\\files\\Admin\\editRequests\\" + editRequest.getName());
                                    adminFile.delete();
                                    editRequest.delete();
                                    System.out.println("The lyric has been edited. Press any key t continue.");
                                    scanner.nextLine();
                                    break;
                                }
                                else if (AorD == 'D' || AorD == 'd') {
                                    File adminFile = new File("src\\main\\java\\org\\project\\files\\Admin\\editRequests\\" + editRequest.getName());
                                    adminFile.delete();
                                    editRequest.delete();
                                    System.out.println("The request has been deleted. Press any key to continue.");
                                    scanner.nextLine();
                                    break;
                                }
                                else if (AorD == '0') {
                                    Session.currentArtist.guid();
                                }
                                else {
                                    System.out.println("Invalid input. Please enter either 'A', 'D' or '0'.");
                                }
                            }

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Session.currentArtist.guid();
                }
                else {
                    System.out.println("Invalid choice. Please enter either 'V' or '0'.");
                }
            }
        }
    }

    public static void acceptRequests(String songName, int lineNumber, String correctLyric) {
        File lyricFile = new File("src\\main\\java\\org\\project\\files\\Artists\\" + Session.currentArtist.username + "\\songs\\" + songName + "-" + Session.currentArtist.username + "lyric.txt");
        ArrayList<String> lyrics = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(lyricFile);
            for (int i = 0 ; i < lyrics.size() ; i++) {
                if (i + 1 == lineNumber) {
                    lyrics.add(correctLyric);
                }
                else {
                    lyrics.add(scanner.nextLine());
                }
            }
            scanner.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            FileWriter writer = new FileWriter(lyricFile, false);
            for (String lyric : lyrics) {
                writer.write(lyric + "\n");
            }
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}