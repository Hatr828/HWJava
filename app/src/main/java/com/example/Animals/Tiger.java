package com.example.Animals;

public class Tiger extends Animal {

    public Tiger(String name, double foodKg) {
        super(name, true, foodKg);
    }

    @Override
    public String sound() {
        return "Rrr!";
    }
}
