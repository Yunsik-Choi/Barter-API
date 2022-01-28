package com.project.barter.global.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EntityBody<T> {

    private int status;

    private String message;

    private T data;

}
