package com.example.galabuddy;

public class Account {
    int ID = 0;
    String Username = null;
    String Password = null;
    public Account() {
        this.ID = -1;
        Username = null;
        Password = null;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
