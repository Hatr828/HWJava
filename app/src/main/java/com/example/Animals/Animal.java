package com.example.Animals;

public abstract class Animal {

    private String name;
    private boolean predator;
    private double daily;

    public Animal(String name, boolean predator, double daily) {
        this.name = name;
        this.predator = predator;
        this.daily = daily;
    }

    public String getName() {
        return name;
    }

    public boolean isPredator() {
        return predator;
    }

    public double getDaily() {
        return daily;
    }

    public abstract String sound();
}
