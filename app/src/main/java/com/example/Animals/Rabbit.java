package com.example.Animals;

public class Rabbit extends Animal {

    public Rabbit(String name, double foodKg) {
        super(name, false, foodKg);
    }

    @Override
    public String sound() {
        return "Squeak";
    }
}
