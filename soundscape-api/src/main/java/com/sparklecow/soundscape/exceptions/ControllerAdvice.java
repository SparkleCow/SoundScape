package com.sparklecow.soundscape.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.sparklecow.soundscape.models.common.BusinessErrorCodes.TOKEN_EXPIRED;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<ExceptionResponse> handleMessagingException(ExpiredTokenException e){
        return ResponseEntity
                .status(TOKEN_EXPIRED.getHttpStatus())
                .body(ExceptionResponse.builder()
                        .businessErrorCode(TOKEN_EXPIRED.getErrorCode())
                        .businessErrorDescription(TOKEN_EXPIRED.getMessage() + " " +e.getMessage())
                        .message(e.getMessage())
                        .build());
    }
}
