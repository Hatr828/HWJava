package com.example.Animals;

public class Wolf extends Animal {

    public Wolf(String name, double foodKg) {
        super(name, true, foodKg);
    }

    @Override
    public String sound() {
        return "Awoo";
    }
}
