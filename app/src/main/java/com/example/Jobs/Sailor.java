package com.example.Jobs;

public class Sailor extends Human {

    private String rank;

    public Sailor(String name, int age, int childrenCount, String rank) {
        super(name, age, childrenCount);
        this.rank = rank;
    }
}
