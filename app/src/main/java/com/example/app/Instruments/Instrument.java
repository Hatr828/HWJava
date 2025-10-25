package com.example.app.Instruments;

public class Instrument implements IInfo {

    public String name;
    public String sound;
    public String description;
    public String history;

    public Instrument(String name, String sound, String description, String history) {
        this.name = name;
        this.sound = sound;
        this.description = description;
        this.history = history;
    }

    @Override
    public void Sound() {
        System.out.println(sound);
    }

    @Override
    public void Show() {
        System.out.println(name);
    }

    @Override
    public void Desc() {
        System.out.println(description);
    }

    @Override
    public void History() {
        System.out.println(history);
    }
}
