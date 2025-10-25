package com.example.app.BuildingCompany.Workers;

import java.util.Random;

import com.example.app.BuildingCompany.Building.House;

public class Worker implements IWorker {

    @Override
    public void work(House house) {
        if (house == null) {
            return;
        }

        Random random = new Random();

        int task = random.nextInt(5);

        switch (task) {
            case 0 ->
                house.addBasement(1);
            case 1 ->
                house.addWall(1);
            case 2 ->
                house.addDoor(1);
            case 3 ->
                house.addWindow(1);
            case 4 ->
                house.addRoof(1);
        }
    }

}
