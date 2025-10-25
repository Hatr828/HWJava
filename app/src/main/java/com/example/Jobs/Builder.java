package com.example.Jobs;

public class Builder extends Human {

    private String specialty;

    public Builder(String name, int age, int childrenCount, String specialty) {
        super(name, age, childrenCount);
        this.specialty = specialty;
    }
}
