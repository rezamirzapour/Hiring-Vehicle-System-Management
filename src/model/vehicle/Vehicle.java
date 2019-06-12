package model.vehicle;

public class Vehicle {
    protected int id;
    protected String model;
    protected String factory;
    protected int createYear;
    protected String description;
    protected int garageId;

    public Vehicle() {

    }

    public Vehicle(int id, String model, String factory, int createYear, String description, int garageId) {
        setId(id);
        setModel(model);
        setFactory(factory);
        setCreateYear(createYear);
        setDescription(description);
        setGarageId(garageId);
    }


    public int getBasicPrice() {
        return 0;
    }

    public int getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getFactory() {
        return factory;
    }

    public int getCreateYear() {
        return createYear;
    }

    public String getDescription() {
        return description;
    }

    public int getGarageId() { return garageId; }

    public void setId(int id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }

    public void setCreateYear(int createYear) {
        if (createYear > 0)
            this.createYear = createYear;
        else System.out.println("Invalid Year");
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGarageId(int garageId) {
        this.garageId = garageId;
    }
}
