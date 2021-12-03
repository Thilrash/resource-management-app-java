/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package resourcemanagementapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author thilr_88qp6ap
 */
public class DBConnection {
    // Database connection
    static String url = "jdbc:mysql://localhost:3306/resource_management_app";
    static String username = "root";
    static String password = "";
    static Connection con = null;
    // Method
    static public Connection DBConnector() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            // Get connection
            con = (Connection) DriverManager.getConnection(url, username, password);
            return con;
        } catch(ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}
