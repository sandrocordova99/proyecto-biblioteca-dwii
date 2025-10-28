package utils;

import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args) {
        try {
            Connection conn = ConexionBD.getConnection();
            System.out.println("✅ CONEXIÓN MYSQL EXITOSA!");
            conn.close();
        } catch (Exception e) {
            System.out.println("❌ ERROR: " + e.getMessage());
        }
    }
}