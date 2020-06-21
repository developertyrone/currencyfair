package com.currencyfair.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "message not found")
public class MessageNotFoundException extends RuntimeException {

    public MessageNotFoundException() {
        super();
    }

    public MessageNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MessageNotFoundException(final String message) {
        super(message);
    }

    public MessageNotFoundException(final Throwable cause) {
        super(cause);
    }
}
