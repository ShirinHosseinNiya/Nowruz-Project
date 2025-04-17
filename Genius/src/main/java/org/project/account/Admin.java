package org.project.account;

import org.project.Session;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin {
    public static void greeting() {
        LocalTime now = LocalTime.now();
        int hour = now.getHour();
        if (hour >= 5 && hour < 12) {
            System.out.println("Good Morning!");
        } else if (hour >= 12 && hour < 17) {
            System.out.println("Good Afternoon!");
        } else if (hour >= 17 && hour < 21) {
            System.out.println("Good Evening!");
        } else {
            System.out.println("Good Night!");
        }
        guid();
    }

    public static void guid() {
        System.out.println("Here is a simple guid of how to use the app.");
        System.out.println("Press 'V' to verify new artists.");
        System.out.println("Press 'R' to see edit requests of users.");
        Scanner scanner = new Scanner(System.in);
        char userChoice;
        while (true) {
            userChoice = scanner.nextLine().charAt(0);
            if (userChoice == 'V' || userChoice == 'v') {
                verifying();
            }
            else if (userChoice == 'R' || userChoice == 'r') {
                approvingEdits();
            }
            else {
                System.out.println("Invalid input. Try again.");
            }
        }
    }

    public static void verifying() {
        File file = new File("src\\main\\java\\org\\project\\files\\Admin\\Artists");
        File[] artists = file.listFiles();
        if (artists.length == 0) {
            System.out.println("No artists were found to verify.");
            guid();
        }
        else {
            for (File artist : artists) {
                String firstName = "";
                String lastName = "";
                int age = 0;
                String email = "";
                String username = "";
                String password = "";
                File info = new File(artist.getAbsolutePath() + "\\info.txt");
                File about = new File(artist.getAbsolutePath() + "\\about.txt");
                try {
                    Scanner scanner = new Scanner(info);
                    firstName = scanner.nextLine();
                    lastName = scanner.nextLine();
                    age = Integer.parseInt(scanner.nextLine());
                    email = scanner.nextLine();
                    username = scanner.nextLine();
                    password = scanner.nextLine();
                    System.out.println("First Name: " + firstName);
                    System.out.println("Last Name: " + lastName);
                    System.out.println("Age: " + age);
                    System.out.println("Email: " + email);
                    System.out.println("Username: " + password);
                    scanner.close();
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("");
                System.out.println("About:");
                try {
                    Scanner scanner = new Scanner(about);
                    while (scanner.hasNextLine()) {
                        System.out.println(scanner.nextLine());
                    }
                    scanner.close();
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                System.out.println("Press 'Y' if you want to verify this artist.");
                System.out.println("Press 'N' if don't want to verify this artist.");
                Scanner scanner = new Scanner(System.in);
                char adminChoice;
                while (true) {
                    adminChoice = scanner.nextLine().charAt(0);
                    if (adminChoice == 'Y' || adminChoice == 'y') {
                        importingData(username, password, firstName, lastName, age, email);
                        info.delete();
                        about.delete();
                        artist.delete();
                        break;
                    }
                    else if (adminChoice == 'N' || adminChoice == 'n') {
                        info.delete();
                        about.delete();
                        artist.delete();
                        break;
                    }
                    else {
                        System.out.println("Invalid input. Try again.");
                    }
                }
                System.out.println("");
                System.out.println("Press 'C' if you want to continue verifying.");
                System.out.println("Press '0' if you want to return to the home page.");
                while (true) {
                    adminChoice = scanner.nextLine().charAt(0);
                    if (adminChoice == '0') {
                        guid();
                    }
                    else if (adminChoice == 'C' || adminChoice == 'c') {
                        verifying();
                    }
                    else {
                        System.out.println("Invalid input. Try again.");
                    }
                }
            }
        }
    }

    public static void importingData(String username, String password, String firstName, String lastName, int age, String email) {
        infoWriter("src\\main\\java\\org\\project\\files\\usersInfo.txt", username, password, firstName, lastName, age, email);
        usernameWriter("src\\main\\java\\org\\project\\files\\usernames.txt", username);

        File mainFolder = new File("src\\main\\java\\org\\project\\files\\Artists\\" + username);
        mainFolder.mkdir();
        File editFolder = new File(mainFolder.getAbsolutePath() + "\\editRequests");
        editFolder.mkdir();
        File albumsFolder = new File(mainFolder.getAbsolutePath() + "\\albums");
        albumsFolder.mkdir();
        File songsFolder = new File(mainFolder.getAbsolutePath() + "\\songs");
        songsFolder.mkdir();
        File finalInfo = new File(mainFolder.getAbsolutePath() + "\\info.txt");
        File info = new File("src\\main\\java\\org\\project\\files\\Admin\\Artists\\" + username + "info.txt");
        String line;
        try {
            Scanner scanner = new Scanner(info);
            FileWriter writer = new FileWriter(finalInfo);
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                writer.write(line + "\n");
            }
            scanner.close();
            writer.close();
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        File albums = new File(mainFolder.getAbsolutePath() + "\\albums.txt");
        File singles = new File(mainFolder.getAbsolutePath() + "\\singles.txt");
        File views = new File(mainFolder.getAbsolutePath() + "\\views.txt");
        File followers = new File(mainFolder.getAbsolutePath() + "\\followers.txt");
        try {
            albums.createNewFile();
            singles.createNewFile();
            views.createNewFile();
            followers.createNewFile();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        File searchFile = new File ("src\\main\\java\\org\\project\\files\\searchArtist.txt");
        try {
            FileWriter writer = new FileWriter(searchFile, true);
            writer.write(firstName + " " + lastName + "\n");
            writer.write(username + "\n");
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void approvingEdits() {
        Scanner scanner = new Scanner(System.in);
        File file = new File("src\\main\\java\\org\\project\\files\\Admin\\editRequests");
        File[] editRequests = file.listFiles();
        int numberOfRequests = editRequests.length;
        System.out.println("There is " + numberOfRequests + " edited request(s).");
        if (numberOfRequests == 0) {
            System.out.println("Press any key to exit");
            scanner.nextLine();
            guid();
        }
        else {
            System.out.println("Press 'V' to view requests or '0' to exit.");
            char choice;
            while (true) {
                choice = scanner.nextLine().charAt(0);
                if (choice == '0') {
                    guid();
                }
                else if (choice == 'V') {
                    for (File editRequest : editRequests) {
                        long lastModified = file.lastModified();
                        LocalDate modifiedDate = Instant.ofEpochMilli(lastModified)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate();

                        LocalDate today = LocalDate.now();
                        long daysOld = ChronoUnit.DAYS.between(modifiedDate, today);
                        if (daysOld <= 7) {
                            System.out.println("You don't have access to this request yet.");
                        }
                        else {
                            try {
                                Scanner scanner1 = new Scanner(editRequest);
                                String songName = scanner1.nextLine();
                                String artist = scanner1.nextLine();
                                int lineNumber = Integer.parseInt(scanner1.nextLine());
                                String user = scanner1.nextLine();
                                String preLyric = scanner1.nextLine();
                                String suggestedLyric = scanner1.nextLine();
                                scanner1.close();
                                System.out.println("You have an edit request from @" + user + " on line " + lineNumber + " of the song " + songName + " from " + artist + ".");
                                System.out.print("Current lyric: ");
                                System.out.println(preLyric);
                                System.out.print("Suggested lyric: ");
                                System.out.println(suggestedLyric);
                                System.out.println("Press 'A' to accept this request, 'D' to reject it ot '0' to return to the home page.");
                                char AorD;
                                while (true) {
                                    AorD = scanner.nextLine().charAt(0);
                                    if (AorD == 'A' || AorD == 'a') {
                                        acceptRequests(artist, songName, lineNumber, suggestedLyric);
                                        File artistFile = new File("src\\main\\java\\org\\project\\files\\Artists\\" + artist + "\\editRequests\\" + editRequest.getName() + ".txt");
                                        artistFile.delete();
                                        editRequest.delete();
                                        System.out.println("The lyric has been edited. Press any key t continue.");
                                        scanner.nextLine();
                                        break;
                                    }
                                    else if (AorD == 'D' || AorD == 'd') {

                                        File artistFile = new File("src\\main\\java\\org\\project\\files\\Artists\\" + artist + "\\editRequests\\" + editRequest.getName() + ".txt");
                                        artistFile.delete();
                                        editRequest.delete();
                                        System.out.println("The request has been deleted. Press any key to continue.");
                                        scanner.nextLine();
                                        break;
                                    }
                                    else if (AorD == '0') {
                                        guid();
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
                    }
                }
                else {
                    System.out.println("Invalid choice. Please enter either 'V' or '0'.");
                }
            }
        }
    }

    public static void acceptRequests(String username, String songName, int lineNumber, String correctLyric) {
        File lyricFile = new File("src\\main\\java\\org\\project\\files\\Artists\\" + username + "\\songs\\" + songName + "-" + username + "lyric.txt");
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

    public static void infoWriter(String filename, String username, String password, String firstName, String lastName, int age, String email) {
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

    public static void usernameWriter(String filename, String username){
        try {
            FileWriter writer = new FileWriter(filename, true);
            writer.write(username + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the file.");
            e.printStackTrace();
        }
    }
}