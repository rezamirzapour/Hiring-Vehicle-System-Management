package database;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import model.Bill;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import model.vehicle.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Date;
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

    public void insertVehicle(String model, String factory, int createYear, String description, String vehicleType, int garageId) {
        String insertSQL = "INSERT INTO VEHICLE (model,factory,create_year,description,vehicle_type,garage_id) VALUES ('" + model + "','" + factory + "','" + createYear + "','" + description + "','" + vehicleType + "','" + garageId + "');";
        try {
            Statement st = c.createStatement();
            st.executeUpdate(insertSQL);
            System.out.println("Inserted!");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateVehicle(int id, String model, String factory, int createYear, String description, String vehicleType) {
        String updateSQL = "UPDATE VEHICLE SET model = '" + model
                + "', factory = '" + factory
                + "', create_year = " + createYear
                + ", description = '" + description
                + "', vehicle_type = '" + vehicleType
                + "' WHERE id = " + id + ";";
        try {
            Statement st = c.createStatement();
            st.executeQuery(updateSQL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteVehicle(int id) {
        String deleteSQL = "DELETE FROM VEHICLE WHERE id = " + id + ";";
        try {
            Statement st = c.createStatement();
            st.executeQuery(deleteSQL);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Integer> getGarageIds() {
        List<Integer> garageIds = new LinkedList<>();
        try {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT DISTINCT garage_id FROM VEHICLE ORDER BY GARAGE_ID;");
            while (rs.next()) {
                garageIds.add(Integer.parseInt(rs.getString(1)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return garageIds;
    }

    public List<Vehicle> getAllVehicle(int garageId, String vehicleType) {
        List vehicles = new LinkedList();
        String getSQL;
        if (garageId != 0) {
            if (vehicleType.equals("All"))
                getSQL = "SELECT id,model,factory,create_year,description,vehicle_type,garage_id FROM VEHICLE WHERE GARAGE_ID = " + garageId + ";";
            else
                getSQL = "SELECT id,model,factory,create_year,description,vehicle_type,garage_id FROM VEHICLE WHERE GARAGE_ID = " + garageId + " AND VEHICLE_TYPE = '" + vehicleType + "';";
        } else {
            if (vehicleType.equals("All"))
                getSQL = "SELECT id,model,factory,create_year,description,vehicle_type,garage_id FROM VEHICLE;";
            else
                getSQL = "SELECT id,model,factory,create_year,description,vehicle_type,garage_id FROM VEHICLE WHERE VEHICLE_TYPE = '" + vehicleType + "';";
        }

        try {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(getSQL);
            while (rs.next()) {
                String type = rs.getString("VEHICLE_TYPE");
                Vehicle vehicle = new Vehicle();

                if (type.equals("Bus")) {
                    vehicle = new Bus();
                } else if (type.equals("Lorry")) {
                    vehicle = new Lorry();
                } else if (type.equals("Machine")) {
                    vehicle = new Machine();
                } else if (type.equals("Motor")) {
                    vehicle = new Motor();
                } else {
                    vehicle = new Vehicle();
                }

                vehicle.setId(Integer.parseInt(rs.getString("ID")));
                vehicle.setModel(rs.getString("MODEL"));
                vehicle.setFactory(rs.getString("FACTORY"));
                vehicle.setCreateYear(Integer.parseInt(rs.getString("CREATE_YEAR")));
                vehicle.setDescription(rs.getString("DESCRIPTION"));
                vehicle.setVehicleType(rs.getString("VEHICLE_TYPE"));
                vehicle.setGarageId(Integer.parseInt(rs.getString("GARAGE_ID")));

                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return vehicles;
    }

    public void hireVehicle(int vehicleId, String userPhone, String priod) {
        int incomeMoney = 1;
        String getType = "SELECT VEHICLE_TYPE FROM VEHICLE WHERE ID = " + vehicleId;
        try {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(getType);
            switch (rs.getString("VEHICLE_TYPE")) {
                case "Machine":
                    incomeMoney = new Machine().getBasicPrice();
                    break;
                case "Motor":
                    incomeMoney = new Motor().getBasicPrice();
                    break;
                case "Lorry":
                    incomeMoney = new Lorry().getBasicPrice();
                    break;
                case "Bus":
                    incomeMoney = new Bus().getBasicPrice();
                    break;
                default:
                    incomeMoney = 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int date;
        switch (priod) {
            case "1 Day":
                date = 1;
                break;
            case "1 Week":
                date = 7;
                break;
            case "1 Month":
                date = 30;
                break;
            case "1 Year":
                date = 365;
                break;
            default:
                date = 1;
        }

        String insertSQL = "INSERT INTO HIRED_VEHICLE (VEHICLE_ID, USER_PHONE, GET_DATE, RETURN_DATE, INCOME_MONEY) values(" + vehicleId + ", '" + userPhone + "', CURRENT_DATE , CURRENT_DATE + CAST(" + date + " AS DAY ), " + incomeMoney + ");";
        try {
            Statement st = c.createStatement();
            st.executeUpdate(insertSQL);
            System.out.println("Inserted!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Bill> getBill(String vehicleType) {
        List<Bill> bills = new LinkedList<>();
        String getSQL;
        switch (vehicleType) {
            case "All":
                getSQL = "SELECT USER_PHONE, ID, MODEL, FACTORY, CREATE_YEAR, DESCRIPTION, VEHICLE_TYPE, GET_DATE, RETURN_DATE, INCOME_MONEY " +
                        "FROM VEHICLE " +
                        "JOIN HIRED_VEHICLE ON HIRED_VEHICLE.VEHICLE_ID = VEHICLE.ID";
                break;
            default:
                getSQL = "SELECT USER_PHONE, ID, MODEL, FACTORY, CREATE_YEAR, DESCRIPTION, VEHICLE_TYPE, GET_DATE, RETURN_DATE, INCOME_MONEY " +
                        "FROM VEHICLE " +
                        "JOIN HIRED_VEHICLE ON HIRED_VEHICLE.VEHICLE_ID = VEHICLE.ID " +
                        "WHERE VEHICLE.VEHICLE_TYPE = '"+vehicleType+"'";
        }


        try {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(getSQL);
            while (rs.next()) {
                Bill bill = new Bill();
                bill.setUserPhone(rs.getString("USER_PHONE"));
                bill.setId(Integer.parseInt(rs.getString("ID")));
                bill.setModel(rs.getString("MODEL"));
                bill.setFactory(rs.getString("FACTORY"));
                bill.setCreateYear(Integer.parseInt(rs.getString("CREATE_YEAR")));
                bill.setDescription(rs.getString("DESCRIPTION"));
                bill.setVehicleType(rs.getString("VEHICLE_TYPE"));
                bill.setGetDate(rs.getString("GET_DATE"));
                bill.setReturnDate(rs.getString("RETURN_DATE"));
                bill.setIncomeMoney(Integer.parseInt(rs.getString("INCOME_MONEY")));
                bills.add(bill);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return bills;
    }

    public void toPdf(int garageId, String vehicleType) {
        Document pdf = new Document();
        Date date = new Date();
        try {
            PdfWriter.getInstance(pdf, new FileOutputStream("Data-" + new Date().getHours() + "-" + new Date().getMinutes() + "-" + new Date().getSeconds() + ".pdf"));
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        pdf.open();
        PdfPTable table = new PdfPTable(7);
        table.addCell(new PdfPCell(new Phrase("ID")));
        table.addCell(new PdfPCell(new Phrase("MODEL")));
        table.addCell(new PdfPCell(new Phrase("FACTORY")));
        table.addCell(new PdfPCell(new Phrase("CREATE YEAR")));
        table.addCell(new PdfPCell(new Phrase("DESCRIPTION")));
        table.addCell(new PdfPCell(new Phrase("VEHICLE TYPE")));
        table.addCell(new PdfPCell(new Phrase("GARAGE ID")));
        String getSQL;
        if (garageId != 0) {
            if (vehicleType.equals("All"))
                getSQL = "SELECT id,model,factory,create_year,description,vehicle_type,garage_id FROM VEHICLE WHERE GARAGE_ID = " + garageId + ";";
            else
                getSQL = "SELECT id,model,factory,create_year,description,vehicle_type,garage_id FROM VEHICLE WHERE GARAGE_ID = " + garageId + " AND VEHICLE_TYPE = '" + vehicleType + "';";
        } else {
            if (vehicleType.equals("All"))
                getSQL = "SELECT id,model,factory,create_year,description,vehicle_type,garage_id FROM VEHICLE;";
            else
                getSQL = "SELECT id,model,factory,create_year,description,vehicle_type,garage_id FROM VEHICLE WHERE VEHICLE_TYPE = '" + vehicleType + "';";
        }
        try {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(getSQL);
            while (rs.next()) {
                table.addCell(new PdfPCell(new Phrase(rs.getString("ID"))));
                table.addCell(new PdfPCell(new Phrase(rs.getString("MODEL"))));
                table.addCell(new PdfPCell(new Phrase(rs.getString("FACTORY"))));
                table.addCell(new PdfPCell(new Phrase(rs.getString("CREATE_YEAR"))));
                table.addCell(new PdfPCell(new Phrase(rs.getString("DESCRIPTION"))));
                table.addCell(new PdfPCell(new Phrase(rs.getString("VEHICLE_TYPE"))));
                table.addCell(new PdfPCell(new Phrase(rs.getString("GARAGE_ID"))));
            }
            st.close();
            rs.close();
            pdf.add(table);
            pdf.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void toExcel(int garageId, String vehicleType) {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("sheet");
        int rowNum = 1;
        Row row = sheet.createRow(0);
        row.createCell(0).setCellValue("ID");
        row.createCell(1).setCellValue("Model");
        row.createCell(2).setCellValue("Factory");
        row.createCell(3).setCellValue("Create Year");
        row.createCell(4).setCellValue("Description");
        row.createCell(5).setCellValue("Vehicle Type");
        row.createCell(6).setCellValue("Garage ID");
        String getSQL;
        if (garageId != 0) {
            if (vehicleType.equals("All"))
                getSQL = "SELECT id,model,factory,create_year,description,vehicle_type,garage_id FROM VEHICLE WHERE GARAGE_ID = " + garageId + ";";
            else
                getSQL = "SELECT id,model,factory,create_year,description,vehicle_type,garage_id FROM VEHICLE WHERE GARAGE_ID = " + garageId + " AND VEHICLE_TYPE = '" + vehicleType + "';";
        } else {
            if (vehicleType.equals("All"))
                getSQL = "SELECT id,model,factory,create_year,description,vehicle_type,garage_id FROM VEHICLE;";
            else
                getSQL = "SELECT id,model,factory,create_year,description,vehicle_type,garage_id FROM VEHICLE WHERE VEHICLE_TYPE = '" + vehicleType + "';";
        }
        try {
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(getSQL);

            while (rs.next()) {
                row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rs.getString("ID"));
                row.createCell(1).setCellValue(rs.getString("MODEL"));
                row.createCell(2).setCellValue(rs.getString("FACTORY"));
                row.createCell(3).setCellValue(rs.getString("CREATE_YEAR"));
                row.createCell(4).setCellValue(rs.getString("DESCRIPTION"));
                row.createCell(5).setCellValue(rs.getString("VEHICLE_TYPE"));
                row.createCell(6).setCellValue(rs.getString("GARAGE_ID"));
            }
            st.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        FileOutputStream data;
        try {
            data = new FileOutputStream(new File("Data-" + new Date().getHours() + "-" + new Date().getMinutes() + "-" + new Date().getSeconds() + ".xls"));
            workbook.write(data);
            data.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {

        try {
            c.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}