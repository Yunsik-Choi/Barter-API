package com.project.barter.user.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
public class CustomBindingException extends RuntimeException {

    private final BindingResult bindingResult;

    public CustomBindingException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

}
