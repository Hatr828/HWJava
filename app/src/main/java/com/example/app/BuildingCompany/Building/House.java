package com.example.app.BuildingCompany.Building;

public class House implements IPart {

    private static final int BASEMENT = 0;
    private static final int WALLS = 1;
    private static final int DOOR = 2;
    private static final int WINDOW = 3;
    private static final int ROOF = 4;

    private static final String[] LABELS = {
        "Basements", "Walls", "Doors", "Windows", "Roofs"
    };

    private final int[] count = new int[5];
    private final int[] req = new int[5];

    public House(int basementReq, int wallsReq, int doorReq, int windowReq, int roofReq) {
        req[BASEMENT] = basementReq;
        req[WALLS] = wallsReq;
        req[DOOR] = doorReq;
        req[WINDOW] = windowReq;
        req[ROOF] = roofReq;
    }

    public void ShowInfo() {
        System.out.println("House construction status:");
        for (int i = 0; i < LABELS.length; i++) {
            System.out.println(LABELS[i] + ": " + count[i] + "/" + req[i]);
        }
    }

    public boolean isCompleted() {
        for (int i = 0; i < count.length; i++) {
            if (count[i] < req[i]) {
                return false;
            }
        }
        return true;
    }

    public void addBasement(int n) {
        if (count[BASEMENT] + n > req[BASEMENT]) {
            return;
        }
        count[BASEMENT] += n;
    }

    public void addWall(int n) {
        if (count[WALLS] + n > req[WALLS]) {
            return;
        }
        count[WALLS] += n;
    }

    public void addDoor(int n) {
        if (count[DOOR] + n > req[DOOR]) {
            return;
        }
        count[DOOR] += n;
    }

    public void addWindow(int n) {
        if (count[WINDOW] + n > req[WINDOW]) {
            return;
        }
        count[WINDOW] += n;
    }

    public void addRoof(int n) {
        if (count[ROOF] + n > req[ROOF]) {
            return;
        }
        count[ROOF] += n;
    }
}
