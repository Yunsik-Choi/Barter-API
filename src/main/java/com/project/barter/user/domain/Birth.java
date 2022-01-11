package com.project.barter.user.domain;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Birth {

    private int year;
    private int month;
    private int day;

    public Birth(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }
}
