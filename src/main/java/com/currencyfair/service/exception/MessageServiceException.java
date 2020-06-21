package com.currencyfair.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE, reason = "something went wrong")
public class MessageServiceException extends RuntimeException {

    public MessageServiceException() {
        super();
    }

    public MessageServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MessageServiceException(final String message) {
        super(message);
    }

    public MessageServiceException(final Throwable cause) {
        super(cause);
    }
}
