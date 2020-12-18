package com.lambdaschool.schools.handler;

import com.lambdaschool.schools.models.ErrorDetail;
import com.lambdaschool.schools.services.HelperFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Order(Ordered.HIGHEST_PRECEDENCE)
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    HelperFunctions helperFunctions;

    public RestExceptionHandler(){
        super();
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
//        return super.handleExceptionInternal(ex, body, headers, status, request);

        ErrorDetail toReturn = new ErrorDetail();
        toReturn.setTimestamp(new Date());
        toReturn.setStatus(status.value());
        toReturn.setDetail(ex.getMessage());
        toReturn.setDeveloperMessage(ex.getClass().getName());
        toReturn.setErrors(helperFunctions.getConstraintViolation(ex));

        return new ResponseEntity<>(toReturn, null,status);
    }
}
