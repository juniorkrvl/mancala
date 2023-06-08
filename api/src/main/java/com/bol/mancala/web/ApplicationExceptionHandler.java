package com.bol.mancala.web;

import com.bol.mancala.domain.*;
import com.bol.mancala.web.ErrorResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(MancalaException.class)
    public ResponseEntity<ErrorResponse> handleCanNotMakeMoveException(@org.jetbrains.annotations.NotNull MancalaException e) {
        ErrorResponse error = new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(error.getStatus()).body(error);
    }

    @ExceptionHandler(GameNotFound.class)
    public ResponseEntity<ErrorResponse> handleGameNotFoundException(@NotNull GameNotFound e) {
        ErrorResponse error = new ErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
        return ResponseEntity.status(error.getStatus()).body(error);
    }

}