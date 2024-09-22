package com.example.bookswap2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SwapRequestAdapter extends RecyclerView.Adapter<SwapRequestAdapter.ViewHolder> {
    private List<SwapRequest> swapRequestList;
    private Context context;
    private BookDatabaseHelper dbHelper;

    public SwapRequestAdapter(List<SwapRequest> swapRequestList, Context context) {
        this.swapRequestList = swapRequestList;
        this.context = context;
        this.dbHelper = new BookDatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_swap_requests, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SwapRequest request = swapRequestList.get(position);
        holder.requesterTextView.setText(request.getRequesterUsername());
        holder.bookTextView.setText(request.getBookName());
        holder.statusTextView.setText(request.getStatus());

        holder.approveButton.setOnClickListener(v -> {
            dbHelper.updateRequestStatus(request.getRequestId(), "approved");
            request.setStatus("approved");
            notifyDataSetChanged();
        });

        holder.rejectButton.setOnClickListener(v -> {
            dbHelper.updateRequestStatus(request.getRequestId(), "rejected");
            request.setStatus("rejected");
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return swapRequestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView requesterTextView, bookTextView, statusTextView;
        Button approveButton, rejectButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            requesterTextView = itemView.findViewById(R.id.requesterTextView);
            bookTextView = itemView.findViewById(R.id.bookTextView);
            statusTextView = itemView.findViewById(R.id.statusTextView);
            approveButton = itemView.findViewById(R.id.approveButton);
            rejectButton = itemView.findViewById(R.id.rejectButton);
        }
    }
}

