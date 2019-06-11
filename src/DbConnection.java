
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    Connection c;

    public DbConnection() {
        connect();
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

    public void close() {

        try {
            c.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}