package com.ptit.sqa.controller.advice;

import com.ptit.sqa.exception.WaterAppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerException {
    @ExceptionHandler({ WaterAppException.class })
    public ResponseEntity<?> applicationErrorHandler(WaterAppException e) {
        HttpStatus status = parseHttpStatus(e.getErr().getErrCode());
        String message = e.getErr().getMessage();
        return new ResponseEntity<>(message, status);
    }

    private static HttpStatus parseHttpStatus(int errCode) {
        return HttpStatus.valueOf(errCode/1000);
    }


}
