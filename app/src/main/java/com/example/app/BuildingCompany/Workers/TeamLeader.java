package com.example.app.BuildingCompany.Workers;

import com.example.app.BuildingCompany.Building.House;

public class TeamLeader implements IWorker {

    @Override
    public void work(House house) {
        house.ShowInfo();
        System.out.println("");
    }

}
