package app.application.adapters.api.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private String errorCode;
    private Long timestamp;

    public ApiResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }

    public ApiResponse(boolean success, String message, T data, String errorCode) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.errorCode = errorCode;
        this.timestamp = System.currentTimeMillis();
    }
}
