package com.booleanuk.library;


import com.booleanuk.library.payload.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

public class HelperUtils {
    /**
     * Private Constructor to avoid instantiation as class holds static helper methods
     */
    private HelperUtils() {}


    public static <T> ResponseEntity<ApiResponse<?>> badRequest(T data) {
        ApiResponse<T> badRequest = new ApiResponse<>("error", data);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(badRequest);
    }

    public static <T> ResponseEntity<ApiResponse<?>> createdRequest(T data) {
        ApiResponse<T> createdRequest = new ApiResponse<>("success", data);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRequest);
    }

    public static <T> ResponseEntity<ApiResponse<?>> okRequest(T data) {
        ApiResponse<T> okRequest = new ApiResponse<>("success", data);
        return ResponseEntity.status(HttpStatus.OK).body(okRequest);
    }
}