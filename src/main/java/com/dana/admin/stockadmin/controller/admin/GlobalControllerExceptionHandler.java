package com.dana.admin.stockadmin.controller.admin;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalControllerExceptionHandler {

    //private static final Logger LOG = Logger.getLogger(GlobalControllerExceptionHandler.class);



    @ExceptionHandler(value = { Exception.class })
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Exception unknownException(Exception ex) {

        System.out.println("--------------------------ERR-GlobalControllerExceptionHandler---"+ex);
        return new Exception ( "--5000---Internal Server Error"+ex);
    }
}