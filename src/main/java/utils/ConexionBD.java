package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/PROYECTO_DWII?useSSL=false&allowPublicKeyRetrieval=true";
        String user = "root";
        String password = "mysql";  // Cambia por tu password de MySQL
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("ERROR: No se encontró el driver JDBC -> " + ex.getMessage());
        }
        return DriverManager.getConnection(url, user, password);
    }
}