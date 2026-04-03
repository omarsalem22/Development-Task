package faw.backend.dto;

public class HelloResponse {
    private String message;
    private String time;

    public HelloResponse(String message) {
        this.message = message;
        this.time = java.time.LocalDateTime.now().toString();
    }

    // Getters and Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }
}