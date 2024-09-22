package com.example.bookswap2;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.List;
import java.util.ArrayList;

public class BookDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "bookswap_db";
    private static final int DB_VERSION = 1;

    // Book table
    private static final String TABLE_BOOKS = "books";
    private static final String BOOK_ID = "id";
    private static final String BOOK_NAME = "book_name";
    private static final String AUTHOR_NAME = "author_name";
    private static final String LOCATION = "location";
    private static final String PHONE_NUMBER = "phone_number";

    // Swap requests table
    private static final String TABLE_SWAP_REQUESTS = "swap_requests";
    private static final String REQUEST_ID = "request_id";
    private static final String BOOK_ID_FK = "book_id"; // Foreign key to books table
    private static final String REQUESTER_USERNAME = "requester_username";  // User who sent the request
    private static final String OWNER_USERNAME = "owner_username";  // Owner of the book
    private static final String STATUS = "status"; // e.g., "pending", "approved", "rejected"

    public BookDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table for books
        String CREATE_BOOKS_TABLE = "CREATE TABLE " + TABLE_BOOKS + "("
                + BOOK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + BOOK_NAME + " TEXT,"
                + AUTHOR_NAME + " TEXT,"
                + LOCATION + " TEXT,"
                + PHONE_NUMBER + " TEXT" + ")";
        db.execSQL(CREATE_BOOKS_TABLE);

        // Create table for swap requests
        String CREATE_SWAP_REQUESTS_TABLE = "CREATE TABLE " + TABLE_SWAP_REQUESTS + "("
                + REQUEST_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + BOOK_ID_FK + " INTEGER,"
                + REQUESTER_USERNAME + " TEXT,"
                + OWNER_USERNAME + " TEXT,"
                + STATUS + " TEXT,"
                + "FOREIGN KEY(" + BOOK_ID_FK + ") REFERENCES " + TABLE_BOOKS + "(" + BOOK_ID + "))";
        db.execSQL(CREATE_SWAP_REQUESTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SWAP_REQUESTS);
        onCreate(db);
    }

    // Method to insert a new swap request
    public void addSwapRequest(int bookId, String requesterUsername, String ownerUsername) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOK_ID_FK, bookId);
        values.put(REQUESTER_USERNAME, requesterUsername);
        values.put(OWNER_USERNAME, ownerUsername);
        values.put(STATUS, "pending"); // Default status is "pending"
        db.insert(TABLE_SWAP_REQUESTS, null, values);
        db.close();
    }

    // Method to fetch swap requests for a user
    public List<SwapRequest> getSwapRequestsForUser(String ownerUsername) {
        List<SwapRequest> requestList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT sr.*, b." + BOOK_NAME + " FROM " + TABLE_SWAP_REQUESTS + " sr " +
                "JOIN " + TABLE_BOOKS + " b ON sr." + BOOK_ID_FK + " = b." + BOOK_ID + " " +
                "WHERE sr." + OWNER_USERNAME + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{ownerUsername});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") SwapRequest request = new SwapRequest(
                        cursor.getInt(cursor.getColumnIndex(REQUEST_ID)),
                        cursor.getString(cursor.getColumnIndex(REQUESTER_USERNAME)),
                        cursor.getString(cursor.getColumnIndex(BOOK_NAME)),
                        cursor.getString(cursor.getColumnIndex(STATUS))
                );
                requestList.add(request);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return requestList;
    }

    // Method to update request status
    public void updateRequestStatus(int requestId, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STATUS, status);
        db.update(TABLE_SWAP_REQUESTS, values, REQUEST_ID + " = ?", new String[]{String.valueOf(requestId)});
        db.close();
    }

    // Method to insert book details
    public void addBook(String bookName, String authorName, String location, String phoneNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(BOOK_NAME, bookName);
        values.put(AUTHOR_NAME, authorName);
        values.put(LOCATION, location);
        values.put(PHONE_NUMBER, phoneNumber);
        db.insert(TABLE_BOOKS, null, values);
        db.close();
    }

    // Method to fetch all books
    public List<Book> getAllBooks() {
        List<Book> bookList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BOOKS, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Book book = new Book(
                        cursor.getString(cursor.getColumnIndex(BOOK_NAME)),
                        cursor.getString(cursor.getColumnIndex(AUTHOR_NAME)),
                        cursor.getString(cursor.getColumnIndex(LOCATION)),
                        cursor.getString(cursor.getColumnIndex(PHONE_NUMBER))
                );
                bookList.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookList;
    }

    // Method to search books by location
    public List<Book> searchBooksByLocation(String location) {
        List<Book> bookList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_BOOKS + " WHERE " + LOCATION + " LIKE ?", new String[]{"%" + location + "%"});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Book book = new Book(
                        cursor.getString(cursor.getColumnIndex(BOOK_NAME)),
                        cursor.getString(cursor.getColumnIndex(AUTHOR_NAME)),
                        cursor.getString(cursor.getColumnIndex(LOCATION)),
                        cursor.getString(cursor.getColumnIndex(PHONE_NUMBER))
                );
                bookList.add(book);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return bookList;
    }
}
