package com.project.barter.global.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorBody {

    private int status;

    private String message;

    private ErrorDto errors;

}
