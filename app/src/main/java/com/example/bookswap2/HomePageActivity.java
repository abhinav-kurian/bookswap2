package com.example.bookswap2;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//import the following
import android.view.View;
import android.widget.TextView;

public class HomePageActivity extends AppCompatActivity {
    //declare textview
    private TextView display_msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //initialize textview
        display_msg=findViewById(R.id.textView6);
        //receive the value(username) passed from LoginActivity and display that on the textview
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null)
        {
            String login_uname=bundle.getString("USERNAME");
            display_msg.setText("Welcome "+login_uname);
        }
    }
    // OnClick method for Add Book button
    public void addBook(View v) {
        // Clicking the "Add Book" button launches AddBookActivity
        Intent i = new Intent(HomePageActivity.this, AddBookActivity.class);
        startActivity(i);
    }
    public void viewSwapRequests(View v) {
        // Clicking the "Add Book" button launches AddBookActivity
        Intent i = new Intent(HomePageActivity.this, SwapRequestsActivity.class);
        startActivity(i);
    }
    public void SearchBook(View v) {
        // Clicking the "Add Book" button launches AddBookActivity
        Intent i = new Intent(HomePageActivity.this, SearchBookActivity.class);
        startActivity(i);
    }

}