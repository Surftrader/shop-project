package ua.com.poseal.shop_project.exception;

import org.springframework.http.HttpStatus;

public class BusinessLogicException extends BaseShopException {
    public BusinessLogicException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
