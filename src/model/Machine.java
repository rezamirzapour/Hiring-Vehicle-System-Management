package model;

public class Machine extends Vehicle {
    private enum type {
        Sport, Economic, Luxury;
    }
    @Override
    public int getBasicPrice() {
        return 1500;
    }
}
