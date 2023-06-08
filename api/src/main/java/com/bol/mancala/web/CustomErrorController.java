package com.bol.mancala.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<ErrorResponse> handleError(HttpServletRequest request) {
        int statusCode = (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String uri = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        HttpStatus status = HttpStatus.valueOf(statusCode);

        ErrorResponse errorResponse = new ErrorResponse(status, uri);
        return ResponseEntity.status(statusCode).body(errorResponse);
    }
}