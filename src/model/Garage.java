package model;

import model.vehicle.Vehicle;

import java.util.LinkedList;
import java.util.List;

public class Garage {
    private List<Vehicle> vehicles = new LinkedList<>();

    public Garage(List<Vehicle> vehicles) {
        setVehicles(vehicles);
    }

    public Garage() {
        this.vehicles = new LinkedList<>();
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }
}
