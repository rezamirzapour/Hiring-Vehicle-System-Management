package model;

public class Bill {
    public Bill() {
    }

    public Bill(String[] bill) {
        this.userPhone = bill[0];
        this.id = Integer.parseInt(bill[1]);
        this.model = bill[2];
        this.factory = bill[3];
        this.createYear = Integer.parseInt(bill[4]);
        this.description = bill[5];
        this.description = bill[6];
        this.getDate = bill[7];
        this.returnDate = bill[8];
    }

    private String userPhone;
    private int id;
    private String model;
    private String factory;
    private int createYear;
    private String description;
    private String vehicleType;
    private String getDate;
    private String returnDate;
    private int incomeMoney;

    public String getUserPhone() {
        return userPhone;
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

    public String getVehicleType() {
        return vehicleType;
    }

    public String getGetDate() {
        return getDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public int getIncomeMoney() {
        return incomeMoney;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
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
        this.createYear = createYear;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setGetDate(String getDate) {
        this.getDate = getDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public void setIncomeMoney(int incomeMoney) {
        this.incomeMoney = incomeMoney;
    }
}
