package com.example.Jobs;

public class Pilot extends Human {

    private String aircraft;

    public Pilot(String name, int age, int childrenCount, String aircraft) {
        super(name, age, childrenCount);
        this.aircraft = aircraft;
    }
}
