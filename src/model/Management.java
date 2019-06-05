package model;

import java.util.ArrayList;
import java.util.List;

public class Management {
    private List<Store> storeList;
    private List<Garage> garageList;

    public Management(int storeCount, int garageCount) {
        this.storeList = new ArrayList<>(storeCount);
        this.garageList = new ArrayList<>(garageCount);
    }
}
