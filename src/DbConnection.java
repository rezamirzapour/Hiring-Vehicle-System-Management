
import model.vehicle.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DbConnection {
    Connection c;
    Statement st;

    public DbConnection() {
        connect();
        createTable();
        close();
    }

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:advancedjava.db");
            System.out.println("Connection Created!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void createTable(){
        String tablSQL="CREATE TABLE IF NOT EXISTS vehicles (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL ,model TEXT , factory TEXT, createYear INTEGER, description TEXT, vehicleType TEXT);";
        try {
            st.executeUpdate(tablSQL);
            System.out.println("vehicles TABLE created");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertVehicle(String model,String factory, int createYear, String description, String vehicleType){
        String insertSQL="INSERT INTO vehicles (model,factory) VALUES ('"+model+"','"+factory+"','"+createYear+"','"+description+"','"+vehicleType+"');";
        try {
            st.executeUpdate(insertSQL);
            System.out.println("Inserted!");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public List<Vehicle> getAllVehicle() {
        List vehicles = new LinkedList();
        String getSQL="SELECT model,factory,createYear,description,vehicleType FROM vehicles;";
        try {
            ResultSet rs=st.executeQuery(getSQL);
            while(rs.next()){
                String type = rs.getString(4);
                Vehicle vehicle;

                if (type.equals("Bus")) { vehicle = new Bus(); }
                else if(type.equals("Lorry")) { vehicle = new Lorry();}
                else if(type.equals("Machine")) { vehicle = new Machine();}
                else if(type.equals("Motor")) { vehicle = new Motor();}
                else { vehicle = new Vehicle();}

                vehicle.setModel(rs.getString(1));;
                vehicle.setFactory(rs.getString(2));
                vehicle.setCreateYear(Integer.parseInt(rs.getString(3)));
                vehicle.setDescription(rs.getString(4));

                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return vehicles;
    }

    public void close() {

        try {
            c.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}