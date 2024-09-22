package com.example.bookswap2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddBookActivity extends AppCompatActivity {
    private EditText bookNameEditText, authorNameEditText, locationEditText, phoneNumberEditText;
    private Button submitBookButton;
    private BookDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        // Initialize database helper
        dbHelper = new BookDatabaseHelper(this);

        // Initialize views
        bookNameEditText = findViewById(R.id.bookNameEditText);
        authorNameEditText = findViewById(R.id.authorNameEditText);
        locationEditText = findViewById(R.id.locationEditText);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        submitBookButton = findViewById(R.id.submitBookButton);

        // Set click listener for submit button
        submitBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String bookName = bookNameEditText.getText().toString();
                String authorName = authorNameEditText.getText().toString();
                String location = locationEditText.getText().toString();
                String phoneNumber = phoneNumberEditText.getText().toString();

                // Validate input
                if (bookName.isEmpty() || authorName.isEmpty() || location.isEmpty() || phoneNumber.isEmpty()) {
                    Toast.makeText(AddBookActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Add book to database
                dbHelper.addBook(bookName, authorName, location, phoneNumber);
                Toast.makeText(AddBookActivity.this, "Book added successfully", Toast.LENGTH_SHORT).show();

                // Clear the input fields
                bookNameEditText.setText("");
                authorNameEditText.setText("");
                locationEditText.setText("");
                phoneNumberEditText.setText("");
            }
        });
    }
}
