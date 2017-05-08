package com.db.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GeneralControllerExceptionHandler {
	
	private static final Logger log = LoggerFactory.getLogger(GeneralControllerExceptionHandler.class);

	@ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(Exception exception) {
        log.error("General error: ", exception);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public String handleException(IllegalArgumentException exception) {
        return "{\"Handle IllegalArgumentException \":\"" + exception.getLocalizedMessage() + "\"}";
    }
    

}
