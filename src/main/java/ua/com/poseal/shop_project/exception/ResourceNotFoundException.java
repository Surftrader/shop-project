package ua.com.poseal.shop_project.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseShopException{
    public ResourceNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
