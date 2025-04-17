package org.project.account;

import java.util.ArrayList;

public interface Account {
    public void register();

    public void infoWriter(String filename, String username, String password, String firstName, String lastName, int age, String email);

    public void usernameWriter(String filename, String username);

    public void greeting();

    public void guid();

}