package com.project.barter.global.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class ErrorDto {

    private String objectName;

    private String field;

    private String code;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ErrorDto> fields;

}
