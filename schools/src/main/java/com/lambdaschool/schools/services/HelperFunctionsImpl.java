package com.lambdaschool.schools.services;

import com.lambdaschool.schools.models.ValidationError;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

@Service(value = "helperFunctions")
public class HelperFunctionsImpl implements HelperFunctions {



    @Override
    public List<ValidationError> getConstraintViolation(Throwable cause) {

        while ((cause !=null) && !(cause instanceof org.hibernate.exception.ConstraintViolationException || cause instanceof MethodArgumentNotValidException)){
            cause = cause.getCause();
        }

        List<ValidationError> list = new ArrayList<>();
        if (cause!=null){
            if (cause instanceof org.hibernate.exception.ConstraintViolationException){
                org.hibernate.exception.ConstraintViolationException ex = (ConstraintViolationException) cause;

                ValidationError error = new ValidationError();
                error.setCode(ex.getMessage());
                error.setMessage(ex.getConstraintName());

                list.add(error);
            }
            else {
                //    MethodArgumentNotValidException
                MethodArgumentNotValidException ex = (MethodArgumentNotValidException) cause;
                List<FieldError> errors = ex.getBindingResult().getFieldErrors();
                for (FieldError e : errors){
                    ValidationError error = new ValidationError();
                    error.setCode(e.getField());
                    error.setMessage(e.getDefaultMessage());
                    list.add(error);
                }
            }
        }

        return list;
    }
}
