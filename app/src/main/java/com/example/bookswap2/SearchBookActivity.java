package com.example.bookswap2;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SearchBookActivity extends AppCompatActivity {
    private EditText searchLocationEditText;
    private RecyclerView bookRecyclerView;
    private BookAdapter bookAdapter;
    private BookDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchbookactivity);

        // Initialize views
        searchLocationEditText = findViewById(R.id.searchLocationEditText);
        bookRecyclerView = findViewById(R.id.bookRecyclerView);
        dbHelper = new BookDatabaseHelper(this);

        // Set RecyclerView properties
        bookRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load all books initially
        loadAllBooks();

        findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = searchLocationEditText.getText().toString();
                if (location.isEmpty()) {
                    loadAllBooks();
                } else {
                    searchBooksByLocation(location);
                }
            }
        });
    }

    private void loadAllBooks() {
        List<Book> bookList = dbHelper.getAllBooks();
        bookAdapter = new BookAdapter(bookList, this);
        bookRecyclerView.setAdapter(bookAdapter);
    }

    private void searchBooksByLocation(String location) {
        List<Book> bookList = dbHelper.searchBooksByLocation(location);
        if (bookList.isEmpty()) {
            Toast.makeText(this, "No books found for this location", Toast.LENGTH_SHORT).show();
        }
        bookAdapter = new BookAdapter(bookList, this);
        bookRecyclerView.setAdapter(bookAdapter);
    }

    // Method to send a request to the book owner (from the adapter)
    public void sendRequestToOwner(String phoneNumber) {
        Toast.makeText(this, "Request sent to: " + phoneNumber, Toast.LENGTH_SHORT).show();
        // Implement the logic for sending the request to the owner (like opening a message or email)
    }
}
