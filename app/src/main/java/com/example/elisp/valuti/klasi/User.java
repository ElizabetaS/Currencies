package com.example.elisp.valuti.klasi;

import java.util.ArrayList;

/**
 * Created by elisp on 02.2.2018.
 */

public class User {
    public String name;
    public String mail;
    public String age;
    public String gender;
    public ArrayList<Valuti> favourites = new ArrayList<>();

    public User() {
    }

    public User(String name, String email,String age,String gender) {
        this.name = name;
        this.mail = email;
        this.age = age;
        this.gender = gender;
    }
}
