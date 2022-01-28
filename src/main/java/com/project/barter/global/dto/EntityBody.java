package com.project.barter.global.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class EntityBody<T> {

    private int status;

    private String message;

    private T data;

    public EntityBody(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public EntityBody(String message, T data) {
        this.status = 200;
        this.message = message;
        this.data = data;
    }

    public EntityBody(T data) {
        this.status = 200;
        this.message = "SUCCESS";
        this.data = data;
    }

}
