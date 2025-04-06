package org.project.account.user;

import org.project.Methods;
import org.project.Session;
import org.project.account.Account;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
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
        Methods.guid();
    }


}