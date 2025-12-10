package back_end.e_commerce.api.model;

public class ErrorResponse {
    private String message;
    private String reason;
    private int statusCode;

    public ErrorResponse() {}

    public ErrorResponse(String message, String reason, int statusCode) {
        this.message = message;
        this.reason = reason;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
