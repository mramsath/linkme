package com.arz.linkme.authentication.exceptions;

import com.arz.linkme.authentication.model.APIResponse;
import com.arz.linkme.authentication.model.APIResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;

@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        ex.printStackTrace();
        logger.error("{}", details);
        APIResponse error = new APIResponse(new Date(), APIResponseStatus.SERVER_ERROR , "Please refer error log");
        return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(ResourceAlreadyExistException.class)
    public final ResponseEntity<Object> handleResourceAlreadyExisitException(ResourceAlreadyExistException ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getLocalizedMessage());
        APIResponse error = new APIResponse(new Date(),APIResponseStatus.CONFLICT, details);
        error.setPath(request.getDescription(false));
        return new ResponseEntity(error, CONFLICT);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for(ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getDefaultMessage());
        }
        APIResponse error = new APIResponse( new Date(), APIResponseStatus.BAD_REQUEST, details);
        return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
    }
}
