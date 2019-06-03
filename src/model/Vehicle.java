package model;

public class Vehicle {
    protected int id;
    protected String model;
    protected String factory;
    protected int createYear;
    protected String description;

    public Vehicle() {
        this.id = (int) (Math.random() * 1000 + 1000);
    }

    public Vehicle(String model, String factory, int createYear, String description) {
        this.id = (int) (Math.random() * 1000 + 1000);
        this.model = model;
        this.factory = factory;
        this.createYear = createYear;
        this.description = description;
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

}
