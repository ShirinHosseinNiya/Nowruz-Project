package org.project.account.artist;

import org.project.Methods;
import org.project.Session;
import org.project.account.Account;
import org.project.account.user.SimpleUser;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
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
        System.out.println("First Name: ");
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
                break;
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
//        ArtistMain artist = new ArtistMain(firstName, lastName, age, email, username, password);
        infoWriter("src\\main\\java\\org\\project\\files\\usersInfo.txt", Session.currentArtist.username, Session.currentArtist.password, Session.currentArtist.firstName, Session.currentArtist.lastName, Session.currentArtist.age, Session.currentArtist.email);
        usernameWriter("src\\main\\java\\org\\project\\files\\usernames.txt", Session.currentArtist.username);
        greeting();
    }

    @Override
    public void infoWriter(String filename, String username, String password, String firstName, String lastName, int age, String email) {
        try {
            FileWriter writer = new FileWriter(filename, true); // Creates or opens the file
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
            System.out.println("Good Morning, " + Session.currentArtist.firstName + "! ☀️");
        } else if (hour >= 12 && hour < 17) {
            System.out.println("Good Afternoon, " + Session.currentArtist.firstName + "! 🌤️");
        } else if (hour >= 17 && hour < 21) {
            System.out.println("Good Evening, " + Session.currentArtist.firstName + "! 🌆");
        } else {
            System.out.println("Good Night, " + Session.currentArtist.firstName + "! 🌙");
        }
        // guid (must be overridden)
    }
}