package ua.com.poseal.shop_project.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BaseShopException extends RuntimeException{

    private final HttpStatus status;

    protected BaseShopException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
