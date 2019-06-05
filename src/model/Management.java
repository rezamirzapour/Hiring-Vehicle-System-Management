package model;

import java.util.ArrayList;
import java.util.List;

public class Management {
    private List<Store> storeList;
    private List<Garage> garageList;

    public Management(int storeCount, int garageCount) {
        setStoreList(new ArrayList<>(garageCount));
        setStoreList(new ArrayList<>(storeCount));
    }



    public void setGarageList(List<Garage> garageList) {
        this.garageList = garageList;
    }

    public void setStoreList(List<Store> storeList) {
        this.storeList = storeList;
    }

    public List<Garage> getGarageList() {
        return garageList;
    }

    public List<Store> getStoreList() {
        return storeList;
    }
}
