package com.example.bookswap2;

public class Book {
    private String title;
    private String author;
    private String location;
    private String phoneNumber;

    // Constructor
    public Book(String title, String author,  String location, String phoneNumber) {
        this.title = title;
        this.author = author;
        this.location = location;
        this.phoneNumber = phoneNumber;
    }

    // Getters
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getLocation() { return location; }
    public String getPhoneNumber() { return phoneNumber; }
}
