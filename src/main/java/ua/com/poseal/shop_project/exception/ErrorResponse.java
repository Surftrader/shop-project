package ua.com.poseal.shop_project.exception;

public record ErrorResponse(
        int status,
        String message,
        long timestamp
) { }
