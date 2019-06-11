
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

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

    public void close() {

        try {
            c.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}