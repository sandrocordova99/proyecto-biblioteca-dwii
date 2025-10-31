/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.ConexionBD;

/**
 *
 * @author Home
 */
public class PrestamoDAO {

    public String prestarLibro(int idLibro, int idUsuario) {
        String sql = "CALL sp_prestar_libro(?, ?)";

        try (Connection conn = ConexionBD.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idLibro);
            stmt.setInt(2, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("mensaje");
                }
            }

        } catch (SQLException e) {
            // Capturar errores personalizados del SP
            if (e.getMessage().contains("libro no existe")) {
                return "ERROR: El libro no existe";
            } else if (e.getMessage().contains("usuario no existe")) {
                return "ERROR: El usuario no existe";
            } else if (e.getMessage().contains("ya está prestado")) {
                return "ERROR: El libro ya está prestado";
            }
            return "ERROR: " + e.getMessage();
        }

        return "Préstamo registrado correctamente";
    }

    public String devolverLibro(int idPrestamo) {
        String sql = "CALL sp_devolver_libro(?)";

        try (Connection conn = ConexionBD.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idPrestamo);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("mensaje");
                }
            }

        } catch (SQLException e) {
            // Capturar errores personalizados del SP
            if (e.getMessage().contains("no encontrado")) {
                return "ERROR: Préstamo no encontrado";
            } else if (e.getMessage().contains("ya fue devuelto")) {
                return "ERROR: El libro ya fue devuelto";
            }
            return "ERROR: " + e.getMessage();
        }

        return "Libro devuelto correctamente";
    }

    //ultima parte 
}
