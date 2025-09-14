package org.project.account.user;

import org.project.Methods;
import org.project.Session;
import org.project.account.Account;

import java.io.*;
import java.time.LocalTime;
import java.util.*;

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
        scanner.nextLine();
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
                File file = new File("src\\main\\java\\org\\project\\files\\usernames.txt");
                try {
                    boolean alreadyExists = false;      // if the username is valid, the program will check if it's unique.
                    Scanner scanner1 = new Scanner(file);
                    while (scanner1.hasNextLine()) {
                        if (Session.currentUser.username.equalsIgnoreCase(scanner1.nextLine())) {
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
            Session.currentUser.password = scanner.nextLine();
            if (Session.currentUser.password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[()!@#$%^&*])[A-Za-z\\d()!@#$%^&*]{8,}$")){
                break;
            }
            else {
                System.out.println("Please enter a valid password.");
            }
        }
        infoWriter("src\\main\\java\\org\\project\\files\\usersInfo.txt", Session.currentUser.username, Session.currentUser.password, Session.currentUser.firstName, Session.currentUser.lastName, Session.currentUser.age, Session.currentUser.email);
        usernameWriter("src\\main\\java\\org\\project\\files\\usernames.txt", Session.currentUser.username);

        File file = new File("src\\main\\java\\org\\project\\files\\Users\\" + Session.currentUser.username);
        file.mkdir();
        File following = new File ("src\\main\\java\\org\\project\\files\\Users\\" + Session.currentUser.username + "\\followingArtists.txt");
        try {
            following.createNewFile();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        greeting();
    }

    @Override
    public void infoWriter(String filename, String username, String password, String firstName, String lastName, int age, String email) {
        try {
            FileWriter writer = new FileWriter(filename, true);
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
            System.out.println("Good Morning, " + Session.currentUser.firstName + "!");
        } else if (hour >= 12 && hour < 17) {
            System.out.println("Good Afternoon, " + Session.currentUser.firstName + "!");
        } else if (hour >= 17 && hour < 21) {
            System.out.println("Good Evening, " + Session.currentUser.firstName + "!");
        } else {
            System.out.println("Good Night, " + Session.currentUser.firstName + "!");
        }
        guid();
    }

    @Override
    public void guid() {
        System.out.println("Here is a simple guide on how to use the app.");
        System.out.println("Press 'S' to search for a specific artist, album, or song.");
        System.out.println("Press 'F' to see the list of artists you follow.");
        Scanner scanner = new Scanner(System.in);
        String userCommand = scanner.nextLine();
        if (userCommand.charAt(0) == 'S' || userCommand.charAt(0) == 's') {
            searchingFront();
        }
        else if (userCommand.charAt(0) == 'F' || userCommand.charAt(0) == 'f') {
            followingList();
        }
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
        searchingArtist(searchTerm, number, results, artists, types);
        System.out.println("Albums: ");
        searchingSongAlbum("searchAlbum", searchTerm, number, results, artists, types, "album");
        System.out.println("Songs: ");
        searchingSongAlbum("searchSong", searchTerm, number, results, artists, types, "song");
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
            albumPageView(artists.get(userChoice - 1), results.get(userChoice - 1));
        }
        else if (types.get(userChoice - 1).equals("song")) {
            songPageView(results.get(userChoice - 1), artists.get(userChoice - 1));
        }
    }

    public static void searchingArtist(String searchTerm, int[] number, ArrayList<String> results, ArrayList<String> artists, ArrayList<String> types) {
        try {
            boolean found = false;
            File file = new File ("src\\main\\java\\org\\project\\files\\searchArtist.txt");
            Scanner scanner1 =  new Scanner(file);
            while (scanner1.hasNextLine()) {
                String result = scanner1.nextLine();
                if (result.toLowerCase().contains(searchTerm.toLowerCase())) {
                    found = true;
                    String username = scanner1.nextLine();
                    System.out.println(number[0] + ". " + result + " (" + username + ")");
                    results.add(username);
                    artists.add(username);
                    types.add("artist");
                    number[0]++;
                }
                else {
                    scanner1.nextLine();
                }
            }
            if (!found) {
                System.out.println("No match was found.");
            }
            scanner1.close();
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
                if (result.toLowerCase().contains(searchTerm.toLowerCase())) {
                    found = true;
                    String artistName = scanner1.nextLine();
                    String artistUsername = scanner1.nextLine();
                    System.out.println(number[0] + ". " + result + " - " + artistName);
                    results.add(result);
                    artists.add(artistUsername);
                    types.add(type);
                    number[0]++;
                }
                else {
                    scanner1.nextLine();
                    scanner1.nextLine();
                }
            }
            if (!found) {
                System.out.println("No match was found.");
            }
            scanner1.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            e.printStackTrace();
        }
    }

    public static void albumPageView(String artist, String album) {
        File albumInfo = new File("src\\main\\java\\org\\project\\files\\Artists\\" + artist + "\\albums\\" + album + "\\info.txt");
        File songsFile = new File("src\\main\\java\\org\\project\\files\\Artists\\" + artist + "\\albums\\" + album + "\\songs.txt");
        int numberOfSongs = 0;
        String dateOfRelease = "";
        ArrayList<String> songs = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(albumInfo);
            scanner.nextLine();
            scanner.nextLine();
            numberOfSongs = Integer.parseInt(scanner.nextLine());
            dateOfRelease = scanner.nextLine();
            scanner.close();
            Scanner scanner1 = new Scanner(songsFile);
            while (scanner1.hasNextLine()) {
                songs.add(scanner1.nextLine());
            }
            scanner1.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            e.printStackTrace();
        }
        System.out.println(album + " - " + numberOfSongs + " songs");
        System.out.println(artist);
        System.out.println(dateOfRelease);
        System.out.println("");
        System.out.println("SONGS");
        for (int number = 1; number <= numberOfSongs; number++) {
            System.out.print(number + ". ");
            System.out.println(songs.get(number-1));
        }
        System.out.println("");
        System.out.println("Tips:");
        System.out.println("Press 'S' if you want to see a song in detail.");
        System.out.println("Press '0' to return to the home page.");
        Scanner scanner = new Scanner(System.in);
        char choice;
        while (true) {
            choice = scanner.nextLine().charAt(0);
            if (choice == '0') {
                Session.currentUser.guid();
            }
            else if (choice == 'S') {
                System.out.println("Please enter the number of song you would like to see.");
                int songNumber = Integer.parseInt(scanner.nextLine());
                while (true) {
                    if (songNumber <= 0) {
                        System.out.println("Please enter a number greater than 0.");
                    }
                    else if (songNumber > numberOfSongs) {
                        System.out.println("Please enter a number less than " + numberOfSongs + ".");
                    }
                    else {
                        String songName = songs.get(songNumber-1);
                        songPageView(songName, artist);
                    }
                }

            }
            else {
                System.out.println("Invalid choice. Please enter either '0' or 'S'.");
            }
        }
    }

    public static void songPageView(String songName, String artistUsername) {
        File viewFile = new File ("src\\main\\java\\org\\project\\files\\Artists\\" + artistUsername + "\\views.txt");
        ArrayList<Object> views = new ArrayList<>();
        String song;
        int view;
        try {
            Scanner scanner = new Scanner(viewFile);
            while (scanner.hasNextLine()) {
                song = scanner.nextLine();
                view = Integer.parseInt(scanner.nextLine());
                if (song == songName) {
                    view++;
                }
                views.add(song);
                views.add(view);
            }
            scanner.close();
            FileWriter writer = new FileWriter(viewFile);
            for (Object item : views) {
                if (item instanceof String || item instanceof Integer) {
                    writer.write(item + "\n");
                }
            }
            writer.close();
        }
        catch (Exception e) {
            System.out.println("File Not Found");
        }
        ArrayList<String> lyrics = new ArrayList<>();
        int number = 1;
        try {
            File file = new File ("src\\main\\java\\org\\project\\files\\Artists\\" + artistUsername + "\\songs\\" + songName + "-" + artistUsername + "\\info.txt");
            Scanner scanner = new Scanner(file);
            System.out.println(scanner.nextLine());     //prints name of the song
            System.out.println(scanner.nextLine());     //prints album of the song
            System.out.println(scanner.nextLine());     //prints artist of the song
            System.out.println(scanner.nextLine());     //prints date of release
            System.out.print("Genre: ");
            System.out.println(scanner.nextLine());     //prints genre
            System.out.print("Producer: ");
            System.out.println(scanner.nextLine());     //prints producer
            System.out.print("Composer: ");
            System.out.println(scanner.nextLine());     //prints composer
            System.out.print("Lyricist: ");
            System.out.println(scanner.nextLine());     //prints lyricist
            scanner.close();
            System.out.println("");
            System.out.println("LYRIC");
            File lyricFile = new File("src\\main\\java\\org\\project\\files\\Artists\\" + artistUsername + "\\songs\\" + songName + "-" + artistUsername + "\\lyric.txt");
            Scanner lyricScanner = new Scanner(lyricFile);
            while (lyricScanner.hasNextLine()) {
                String lyric = lyricScanner.nextLine();
                File folder = new File ("src\\main\\java\\org\\project\\files\\Artists\\" + artistUsername + "\\songs\\" + songName + "-" + artistUsername + "\\comments\\" + number);
                if (folder.exists()) {
                    File[] files = folder.listFiles();
                    int numberOfComments = files.length;
                    System.out.println(number + ". " + lyric + " (comments: " + numberOfComments + ")");
                }
                else {
                    System.out.println(number + ". " + lyric);
                }
                lyrics.add(lyric);
                number++;
            }
            lyricScanner.close();
            while (true) {
                System.out.println("tips:");
                System.out.println("Press 'C' to comment on a specific line.");
                System.out.println("Press 'S' to see comments on a specific line.");
                System.out.println("Press 'E' to edit a specific line.");
                System.out.println("Enter '0' to return to the Home page.");
                Scanner scanner1 = new Scanner(System.in);
                char userCommand = scanner1.next().charAt(0);
                if (userCommand == 'C' || userCommand == 'c') {
                    commenting(number, songName, artistUsername);
                }
                else if (userCommand == 'S' || userCommand == 's') {
                    seeingComments(number, songName, artistUsername);
                }
                else if (userCommand == 'E' || userCommand == 'e') {
                    editing(number, songName, artistUsername, lyrics);
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

    public static
    void editing(int lines, String songName, String artistUsername, ArrayList<String> lyrics) {
        Scanner scanner = new Scanner(System.in);
        int lineNumber;
        while (true) {
            System.out.println("Enter the line number you'd like to edit: ");
            lineNumber = scanner.nextInt();
            scanner.nextLine();
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
        try {
            System.out.println("Please enter the correct lyrics for line " + lineNumber);
            String correctLyric  = scanner.nextLine();
            String baseName = "src\\main\\java\\org\\project\\files\\Artists\\" + artistUsername + "\\editRequests\\" + songName + "-" + artistUsername;
            String extensions = ".txt";
            int counter = 1;
            File file = new File (baseName + counter + extensions);
            while (file.exists()){
                counter++;
                file = new File (baseName + counter + extensions);
            }
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                System.out.println("File Not Found");
            }
            File adminFile = new File("src\\main\\java\\org\\project\\files\\Admin\\editRequests\\" + songName + "-" + artistUsername + counter + ".txt");
            try {
                adminFile.createNewFile();
            }
            catch (IOException e) {
                System.out.println("File Not Found");
            }
            FileWriter writer = new FileWriter (file);
            writer.write(songName + "\n");
            writer.write(lineNumber + "\n");
            writer.write(Session.currentUser.username + "\n");
            writer.write(lyrics.get(lineNumber - 1) + "\n");
            writer.write(correctLyric + "\n");
            writer.close();
            FileWriter writer1 = new FileWriter(adminFile);
            writer1.write(songName + "\n");
            writer1.write(artistUsername + "\n");
            writer1.write(lineNumber + "\n");
            writer1.write(Session.currentUser.username + "\n");
            writer1.write(lyrics.get(lineNumber - 1) + "\n");
            writer1.write(correctLyric + "\n");
            writer1.close();
            System.out.println("Your edit request has been sent to the artist.");
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
        File followers = new File("src\\main\\java\\org\\project\\files\\Artists\\" + artist + "\\followers.txt");
        try {
            Scanner scanner = new Scanner(followers);
            System.out.print("Followers: ");
            System.out.println(scanner.nextLine());
            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            e.printStackTrace();
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
        System.out.println("Popular Songs");
        ArrayList<String> popularSongs = new ArrayList<>();
        ArrayList<Integer> views = new ArrayList<>();
        File viewSongs = new File("src\\main\\java\\org\\project\\files\\Artists\\" + artist + "\\views.txt");
        try {
            Scanner scanner = new Scanner(viewSongs);
            while (scanner.hasNextLine()) {
                popularSongs.add(scanner.nextLine());
                views.add(Integer.parseInt(scanner.nextLine()));
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            e.printStackTrace();
        }
        List<Map.Entry<String, Integer>> combined = new ArrayList<>();
        for (int i = 0; i < popularSongs.size(); i++) {
            combined.add(new AbstractMap.SimpleEntry<>(popularSongs.get(i), views.get(i)));
        }
        combined.sort((a, b) -> b.getValue() - a.getValue());
        popularSongs.clear();
        views.clear();
        for (Map.Entry<String, Integer> entry : combined) {
            popularSongs.add(entry.getKey());
            views.add(entry.getValue());
        }
        if (popularSongs.size() < 3) {
            for (int i = 0 ; i < popularSongs.size() ; i++) {
                System.out.println(popularSongs.get(i) + " - " + views.get(i) + " views");
            }
        }
        else {
            for (int i = 0 ; i < 3 ; i++) {
                System.out.println(popularSongs.get(i) + " - " + views.get(i));
            }
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
                following = false;
                unfollowArtist(artist);
                artistPageView(artist);
            }
            else if ((userCommand == 'U' || userCommand == 'u') && !following) {
                System.out.println("You are not following this artist.");
            }
            else if ((userCommand == 'F' || userCommand == 'f') && !following) {
                following = true;
                followArtist(artist);
                artistPageView(artist);
            }
            else if ((userCommand == 'F' || userCommand == 'f') && following) {
                System.out.println("You are following this artist.");
            }
            else if (userCommand == 'A' || userCommand == 'a') {
                System.out.println("Please enter the number of alnum you would like to visit.");
                int albumNum;
                while (true) {
                    albumNum = scanner.nextInt();
                    if (albumNum <= 0) {
                        System.out.println("Please enter a number greater than 0.");
                    }
                    else if (albumNum > singles.size()) {
                        System.out.println("The number you have entered is too big. Please try again.");
                    }
                    else {
                        break;
                    }
                }
                albumPageView(artist, albums.get(albumNum - 1));
            }
            else if (userCommand == 'S' || userCommand == 's') {
                System.out.println("Please enter the number of song you would like to visit.");
                int songNumber;
                scanner.close();
                while (true) {
                     songNumber = scanner.nextInt();
                    if (songNumber <= 0) {
                        System.out.println("Please enter a number greater than 0.");
                    }
                    else if (songNumber > singles.size()) {
                        System.out.println("The number ypu have entered is too big. Please try again.");
                    }
                    else {
                        break;
                    }
                }
                songPageView(singles.get(songNumber - 1), artist);
            }
            else if (userCommand == '0') {
                Session.currentUser.guid();
            }
        }
    }

    public static void followArtist(String artist) {
        File file = new File("src\\main\\java\\org\\project\\files\\Users\\" + Session.currentUser.username + "\\followingArtists.txt");
        File followers = new File ("src\\main\\java\\org\\project\\files\\Artists\\" + artist + "\\followers.txt");
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write(artist + "\n");
            writer.close();
            Scanner scanner = new Scanner(followers);
            int numOfFollowers = scanner.nextInt();
            scanner.nextLine();
            numOfFollowers++;
            scanner.close();
            FileWriter writer2 = new FileWriter(followers, false);
            writer2.write(numOfFollowers + "\n");
            writer2.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unfollowArtist(String artist) {
        File file = new File("src\\main\\java\\org\\project\\files\\Users\\" + Session.currentUser.username + "\\followingArtists.txt");
        File followers = new File ("src\\main\\java\\org\\project\\files\\Artists\\" + artist + "\\followers.txt");
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
            Scanner scanner1 = new Scanner(followers);
            int numOfFollowers = scanner1.nextInt();
            scanner1.nextLine();
            numOfFollowers--;
            scanner1.close();
            FileWriter writer2 = new FileWriter(followers, false);
            writer2.write(numOfFollowers + "\n");
            writer2.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void commenting(int numberOfLines, String songName, String artistUsername) {
        System.out.println("Please enter the number of line you would like to comment on:");
        Scanner scanner = new Scanner(System.in);
        int numberOfLine;
        while (true) {
            numberOfLine = scanner.nextInt();
            scanner.nextLine();
            if (numberOfLine <= 0) {
                System.out.println("Please enter a number greater than 0.");
            }
            else if (numberOfLine > numberOfLines) {
                System.out.println("The number you have entered is greater than the whole song. Please try again.");
            }
            else {
                break;
            }
        }
        System.out.println("Please write your comment on line " + numberOfLine + ": (Write 'done' and press enter when your comment is over.");
        File file = new File("src\\main\\java\\org\\project\\files\\Artists\\" + artistUsername + "\\songs\\" + songName + "-" + artistUsername + "\\coments\\" + numberOfLine);
        file.mkdir();
        int counter = 1;
        File comment = new File("src\\main\\java\\org\\project\\files\\Songs\\" + songName + "-" + artistUsername + "\\comments\\" + numberOfLine + "\\" + counter + ".txt");
        while (comment.exists()) {
            counter++;
            comment = new File("src\\main\\java\\org\\project\\files\\Songs\\" + songName + "-" + artistUsername + "\\comments\\" + numberOfLine + "\\" + counter + ".txt");
        }
        try {
            FileWriter writer = new FileWriter(comment);
            writer.write(Session.currentUser.username + "\n");
            while (true) {
                String line = scanner.nextLine();
                if (line.equalsIgnoreCase("done")) {
                    break;
                }
                else {
                    writer.write(line + "\n");
                }
            }
            writer.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void seeingComments(int numberOfLines, String songName, String artistUsername) {
        System.out.println("Please enter the number of line you would like to see its comments:");
        Scanner scanner = new Scanner(System.in);
        int numberOfLine;
        while (true) {
            numberOfLine = scanner.nextInt();
            scanner.nextLine();
            if (numberOfLine <= 0) {
                System.out.println("Please enter a number greater than 0.");
            }
            else if (numberOfLine > numberOfLines) {
                System.out.println("The number you have entered is greater then the whole song. Please try again.");
            }
            else {
                break;
            }
        }
        File file = new File("src\\main\\java\\org\\project\\files\\Artists\\" + artistUsername + "\\songs\\" + songName + "-" + artistUsername + "\\comments\\" + numberOfLine);
        if (!file.exists()) {
            System.out.println("There is no comments on this line.");
        }
        else {
            File[] numberOfComments = file.listFiles();
            for (int number = 1 ; number <= numberOfComments.length ; number++) {
                File comment = new File("src\\main\\java\\org\\project\\files\\Artists\\" + artistUsername + "\\songs\\" + songName + "-" + artistUsername + "\\comments\\" + numberOfLine + "\\" + number + ".txt");
                if (comment.exists()) {
                    try {
                        Scanner scanner1 = new Scanner(comment);
                        String username = scanner1.nextLine();
                        System.out.println("- " + username + ":");
                        while (scanner1.hasNextLine()) {
                            String line = scanner1.nextLine();
                            System.out.println(line);
                        }
                        scanner1.close();
                    }
                    catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void followingList() {
        System.out.println("FOLLOWING ARTISTS:");
        ArrayList<String> followingArtists = new ArrayList<>();
        int number = 1;
        File file = new File("src\\main\\java\\org\\project\\files\\Users\\" + Session.currentUser.username + "\\followingArtists.txt");
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                followingArtists.add(scanner.nextLine());
                System.out.println(number + ". " + followingArtists.get(number - 1));
                number++;
            }
            scanner.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Press 'V' to view one of the artist's page.");
        System.out.println("Press 'U' to unfollow one of the artists.");
        System.out.println("Press '0' to return to the home page.");
        Scanner scanner = new Scanner(System.in);
        while (true) {
            char usersChoice = scanner.next().charAt(0);
            if (usersChoice == 'V' || usersChoice == 'v') {
                System.out.println("Please enter the number of artist you would like to see their page:");
                System.out.println("Press '0' to return.");
                int artist = scanner.nextInt();
                scanner.nextLine();
                while (true) {
                    if (artist < 0) {
                        System.out.println("Please enter a number greater than 0.");
                    }
                    else if (artist > followingArtists.size()) {
                        System.out.println("There is no artist with this number in your list. Please try again.");
                    }
                    else if (artist == 0) {
                        followingList();
                    }
                    else {
                        artistPageView(followingArtists.get(artist - 1));
                    }
                }
            }
            else if (usersChoice == 'U' || usersChoice == 'u') {
                System.out.println("Please enter the number of artist you would like to unfollow:");
                System.out.println("Press '0' to return.");
                int artist = scanner.nextInt();
                scanner.nextLine();
                while (true) {
                    if (artist < 0) {
                        System.out.println("Please enter a number greater than 0.");
                    }
                    else if (artist > followingArtists.size()) {
                        System.out.println("There is no artist with this number in your list. Please try again.");
                    }
                    else if (artist == 0) {
                        followingList();
                    }
                    else {
                        unfollowArtist(followingArtists.get(artist - 1));
                    }
                }
            }
            else if (usersChoice == '0') {
                Session.currentUser.guid();
            }
            else {
                System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}