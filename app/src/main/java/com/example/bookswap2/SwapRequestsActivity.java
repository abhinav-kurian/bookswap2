package com.example.bookswap2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SwapRequestsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private SwapRequestAdapter adapter;
    private List<SwapRequest> swapRequestList;
    private BookDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swap_requests);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        dbHelper = new BookDatabaseHelper(this);
        String currentUser = "owner_username";  // Replace with actual logged-in user's username
        swapRequestList = dbHelper.getSwapRequestsForUser(currentUser);

        adapter = new SwapRequestAdapter(swapRequestList, this);
        recyclerView.setAdapter(adapter);
    }
}
