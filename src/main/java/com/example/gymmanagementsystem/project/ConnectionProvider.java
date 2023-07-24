package com.example.gymmanagementsystem.project;
import java.sql.*;

public class ConnectionProvider {
    // Note: User and Password must be changed to match your MySQL Database credentials
    public static Connection getCon() {
        try {
            Class.forName(Driver.class.getName());
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/gms", "root", "Password");
            assert con != null;
            return con;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
