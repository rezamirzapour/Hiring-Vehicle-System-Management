package database;

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

    public void insertVehicle(String model,String factory, int createYear, String description, String vehicleType){
        String insertSQL="INSERT INTO vehicles (model,factory,create_year,description,vehicle_type) VALUES ('"+model+"','"+factory+"','"+createYear+"','"+description+"','"+vehicleType+"');";
        try {
            st.executeUpdate(insertSQL);
            System.out.println("Inserted!");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void updateVehicle(int id, String model, String factory, int createYear, String description) {
        String updateSQL = "UPDATE vehicles SET model = '"+model
                +"', factory = '"+factory
                +"', create_year = " +createYear
                +", description = '" +description
                +"WHERE id = ;"+id+";";
        try {
            Statement st = c.createStatement();
            st.executeQuery(updateSQL);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteVehicle(int id) {
        String deleteSQL = "DELETE FROM vehicles WHERE id = "+id+";";
        try {
            Statement st = c.createStatement();
            st.executeQuery(deleteSQL);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Vehicle> getAllVehicle() {
        List vehicles = new LinkedList();
        String getSQL="SELECT model,factory,create_year,description,vehicle_type FROM vehicles;";
        try {
            Statement st = c.createStatement();
            ResultSet rs=st.executeQuery(getSQL);

            while(rs.next()){
                String type = rs.getString(5);
                Vehicle vehicle = new Vehicle();

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