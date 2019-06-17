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

    public void insertVehicle(String model,String factory, int createYear, String description, String vehicleType, int garageId){
        String insertSQL="INSERT INTO VEHICLE (model,factory,create_year,description,vehicle_type,garage_id) VALUES ('"+model+"','"+factory+"','"+createYear+"','"+description+"','"+vehicleType+"','"+garageId+"');";
        try {
            Statement st = c.createStatement();
            st.executeUpdate(insertSQL);
            System.out.println("Inserted!");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void updateVehicle(int id, String model, String factory, int createYear, String description, String vehicleType) {
        String updateSQL = "UPDATE VEHICLE SET model = '"+model
                +"', factory = '"+factory
                +"', create_year = " +createYear
                +", description = '" +description
                +"', vehicle_type = '" +vehicleType
                +"' WHERE id = "+id+";";
        try {
            Statement st = c.createStatement();
            st.executeQuery(updateSQL);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteVehicle(int id) {
        String deleteSQL = "DELETE FROM VEHICLE WHERE id = "+id+";";
        try {
            Statement st = c.createStatement();
            st.executeQuery(deleteSQL);
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Integer> getGarageIds() {
        List<Integer> garageIds = new LinkedList<>();
        try {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT DISTINCT garage_id FROM VEHICLE");
            while (rs.next()) {
                garageIds.add(Integer.parseInt(rs.getString(1)));
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return  garageIds;
    }

    public List<Vehicle> getAllVehicle() {
        List vehicles = new LinkedList();
        String getSQL="SELECT id,model,factory,create_year,description,vehicle_type,garage_id FROM VEHICLE;";
        try {
            Statement st = c.createStatement();
            ResultSet rs=st.executeQuery(getSQL);
            while(rs.next()){
                String type = rs.getString(6);
                Vehicle vehicle = new Vehicle();

                if (type.equals("Bus")) { vehicle = new Bus(); }
                else if(type.equals("Lorry")) { vehicle = new Lorry();}
                else if(type.equals("Machine")) { vehicle = new Machine();}
                else if(type.equals("Motor")) { vehicle = new Motor();}
                else { vehicle = new Vehicle();}

                vehicle.setId(Integer.parseInt(rs.getString(1)));
                vehicle.setModel(rs.getString(2));;
                vehicle.setFactory(rs.getString(3));
                vehicle.setCreateYear(Integer.parseInt(rs.getString(4)));
                vehicle.setDescription(rs.getString(5));
                vehicle.setVehicleType(rs.getString(6));
                vehicle.setGarageId(Integer.parseInt(rs.getString(7)));

                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return vehicles;
    }

    public List<Vehicle> getAllVehicle(int garageId) {
        List vehicles = new LinkedList();
        String getSQL="SELECT id,model,factory,create_year,description,vehicle_type,garage_id FROM VEHICLE WHERE ID = "+garageId+";";
        try {
            Statement st = c.createStatement();
            ResultSet rs=st.executeQuery(getSQL);
            while(rs.next()){
                String type = rs.getString(6);
                Vehicle vehicle = new Vehicle();

                if (type.equals("Bus")) { vehicle = new Bus(); }
                else if(type.equals("Lorry")) { vehicle = new Lorry();}
                else if(type.equals("Machine")) { vehicle = new Machine();}
                else if(type.equals("Motor")) { vehicle = new Motor();}
                else { vehicle = new Vehicle();}

                vehicle.setId(Integer.parseInt(rs.getString(1)));
                vehicle.setModel(rs.getString(2));;
                vehicle.setFactory(rs.getString(3));
                vehicle.setCreateYear(Integer.parseInt(rs.getString(4)));
                vehicle.setDescription(rs.getString(5));
                vehicle.setVehicleType(rs.getString(6));
                vehicle.setGarageId(Integer.parseInt(rs.getString(7)));

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