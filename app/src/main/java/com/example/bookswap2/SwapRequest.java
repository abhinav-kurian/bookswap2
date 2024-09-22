package com.example.bookswap2;

public class SwapRequest {
    private int requestId;
    private String requesterUsername;
    private String bookName;
    private String status;

    public SwapRequest(int requestId, String requesterUsername, String bookName, String status) {
        this.requestId = requestId;
        this.requesterUsername = requesterUsername;
        this.bookName = bookName;
        this.status = status;
    }

    // Getters and setters for each field
    public int getRequestId() { return requestId; }
    public String getRequesterUsername() { return requesterUsername; }
    public String getBookName() { return bookName; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
