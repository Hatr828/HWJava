package com.example.Animals;

public class Kangaroo extends Animal {

    public Kangaroo(String name, double foodKg) {
        super(name, false, foodKg);
    }

    @Override
    public String sound() {
        return "Thump";
    }
}
