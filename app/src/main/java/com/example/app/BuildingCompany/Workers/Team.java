package com.example.app.BuildingCompany.Workers;

import com.example.app.BuildingCompany.Building.House;

public class Team implements IWorker {

    private final IWorker[] workers;

    public Team(IWorker... workers) {
        this.workers = workers;
    }

    @Override
    public void work(House house) {
        for (IWorker worker : workers) {
            worker.work(house);
        }
    }

    public boolean isCompleted(House house) {
        return house.isCompleted();
    }
}
