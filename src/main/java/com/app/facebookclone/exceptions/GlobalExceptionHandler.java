package com.app.facebookclone.exceptions;

import com.app.facebookclone.response.ResponseHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<Object> handleAllExc(Exception exception){
        exception.printStackTrace();
        System.out.println(exception.getMessage());
        return ResponseHandler.generateResponse(500, "Internal server error");

    }
    @ExceptionHandler
    public ResponseEntity<Object> handleAlreadyExistExc(AlreadyExistException alreadyExistException){
        return ResponseHandler.generateResponse(400, alreadyExistException.getMessage());

    }
    @ExceptionHandler
    public ResponseEntity<Object> handleNotFoundExc(NotFoundException notFoundException){
        return ResponseHandler.generateResponse(404, notFoundException.getMessage());

    }
    @ExceptionHandler
    public ResponseEntity<Object> handleWrongInputExc(WrongInputException wrongInputException){
        return ResponseHandler.generateResponse(400, wrongInputException.getMessage());

    }
    @ExceptionHandler
    public ResponseEntity<Object> handleBadCredentials(BadCredentialsException badCredentialsException){
        return ResponseHandler.generateResponse(400, badCredentialsException.getMessage());

    }

}
