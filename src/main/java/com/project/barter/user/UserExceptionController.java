package com.project.barter.user;

import com.project.barter.user.exception.CustomBindingException;
import com.project.barter.user.exception.LoginIdAlreadyExistsException;
import com.project.barter.user.exception.UserLoginUnavailableException;
import com.project.barter.user.exception.UserNotExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class UserExceptionController {

    @ExceptionHandler(CustomBindingException.class)
    public ResponseEntity bindingException(CustomBindingException exception){
        log.error("Binding Error");
        BindingResult bindingResult = exception.getBindingResult();
        return ResponseEntity.badRequest().body(bindingResult.getAllErrors()
                .stream().map(i -> i.getDefaultMessage()).toArray());
    }

    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity notExistsUser(UserNotExistsException exception){
        log.error("User Not Exists");
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(LoginIdAlreadyExistsException.class)
    public ResponseEntity loginIdAlreadyExists(LoginIdAlreadyExistsException exception){
        log.error("User LoginId Already Exists");
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(UserLoginUnavailableException.class)
    public ResponseEntity userLoginUnavailable(){
        log.error("User Login Fail");
        return ResponseEntity.badRequest().build();
    }

}
