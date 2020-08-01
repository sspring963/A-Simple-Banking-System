package banking;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        String fileName = args[1];
        createNewDatabase(fileName);
        connection(fileName);
        createNewTable(fileName);
        Bank bank = new Bank(fileName);
        bank.run();

    }

    public static void createNewDatabase(String fileName) {

        String url = "jdbc:sqlite:" + fileName;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                //System.out.println("The driver name is " + meta.getDriverName());
                //System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void connection(String fileName) {
        String url = "jdbc:sqlite:" + fileName;

        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try(Connection con = dataSource.getConnection()) {
            if(con.isValid(10)) {
                //System.out.println("connection is valid.");
            }
        } catch(SQLException e ) {
            e.printStackTrace();
        }
    }

    public static void createNewTable(String fileName) {
        String url = "jdbc:sqlite:" + fileName;

        String sql = "CREATE tABLe IF NOT EXISTS card (\n"
                + "     id INTEGER PRIMARY KEY AUTOINCREMENT, \n"
                + "     number TEXT NOT NULL, \n"
                + "     pin TEXT NOT NULL, \n"
                + "     balance INTEGER DEFAULT 0\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            //System.out.println("table made");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

