package org.project.account.artist;

import org.project.Methods;
import org.project.account.Account;
import org.project.account.user.SimpleUser;
import java.io.FileWriter;
import java.io.IOException;
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
        firstName = scanner.nextLine();
        System.out.println("Last Name: ");
        lastName = scanner.nextLine();
        while (true) {
            System.out.println("Age: ");
            age = scanner.nextInt();
            if (age < 7) {System.out.println("you're too young for this app lil fella, come back with your parents.");}
            else if (age > 110) {System.out.println("aren't you dead already? be for real please.");}
            else {break;}
        }
        while (true) {
            System.out.println("Email: ");
            email = scanner.nextLine();
            if (email.matches("^(?![.-])([a-zA-Z0-9]+[\\w.-]*[a-zA-Z0-9])@([a-zA-Z0-9]+(?:-[a-zA-Z0-9]+)*(?:\\.[a-zA-Z0-9]+(?:-[a-zA-Z0-9]+)*)*)\\.[a-zA-Z]{2,6}$")) {
                break;
            }
            else {
                System.out.println("Please enter a valid email address.");
            }
        }
        while (true) {
            System.out.println("Username: (can only contain letters, digits and underscores)");
            username = scanner.nextLine();
            if (username.matches("^[a-zA-Z0-9]+[a-zA-Z0-9_]*$")){
                break;
            }
            else {
                System.out.println("Please enter a valid username.");
            }
        }
        while (true) {
            System.out.println("Password: (must be at least 8 characters long and contain capital and small letters, digits and special characters (!@#$%^&*))");
            password = scanner.nextLine();
            if (password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[()!@#$%^&*])[A-Za-z\\d()!@#$%^&*]{8,}$")){
                break;
            }
            else {
                System.out.println("Please enter a valid password.");
            }
        }
        ArtistMain artist = new ArtistMain(firstName, lastName, age, email, username, password);
        infoWriter("src\\main\\java\\org\\project\\files\\usersInfo.txt", username, password, firstName, lastName, age, email);
        usernameWriter("src\\main\\java\\org\\project\\files\\usernames.txt", username);
        Methods.firstView();
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
}