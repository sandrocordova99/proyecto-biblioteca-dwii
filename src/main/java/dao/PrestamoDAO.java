/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Entidades.Autor;
import Entidades.Categoria;
import Entidades.Libro;
import Entidades.Prestamo;
import Entidades.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    public List<Prestamo> getPrestamosActivos() {
        List<Prestamo> prestamos = new ArrayList<>();

        try (Connection conn = ConexionBD.getConnection(); CallableStatement stmt = conn.prepareCall("CALL sp_get_prestamos_activos()"); ResultSet rs = stmt.executeQuery()) {

            // Ver metadatos del ResultSet
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            int rowCount = 0;
            while (rs.next()) {
                rowCount++;

                // Mapeo básico
                Prestamo prestamo = new Prestamo();
                prestamo.setIdPrestamo(rs.getInt("id_prestamo"));
                prestamo.setFechaPrestamo(rs.getDate("fecha_prestamo"));
                prestamo.setEstado(rs.getString("estado"));

                Libro libro = new Libro();
                libro.setIdLibro(rs.getInt("id_libro"));
                libro.setTitulo(rs.getString("libro"));
                prestamo.setLibro(libro);

                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setNombre(rs.getString("usuario"));
                prestamo.setUsuario(usuario);

                prestamos.add(prestamo);
            }

        } catch (SQLException e) {
            System.out.println(" Error: " + e.getMessage());
            e.printStackTrace();
        }

        return prestamos;
    }

    public List<Prestamo> getHistorialUsuario(int idUsuario) {
        List<Prestamo> prestamos = new ArrayList<>();

        String sql = "CALL sp_get_historial_usuario(?)";

        try (Connection conn = ConexionBD.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {

 
                int count = 0;
                while (rs.next()) {
                    count++;
                    System.out.println("Préstamo " + count + ":");
                    System.out.println("   ID: " + rs.getInt("id_prestamo"));
                    System.out.println("   Libro: " + rs.getString("libro"));
                    System.out.println("   Estado: " + rs.getString("estado"));

                    Prestamo prestamo = new Prestamo();
                    prestamo.setIdPrestamo(rs.getInt("id_prestamo"));
                    prestamo.setFechaPrestamo(rs.getDate("fecha_prestamo"));
                    prestamo.setFechaDevolucion(rs.getDate("fecha_devolucion"));
                    prestamo.setEstado(rs.getString("estado"));

                     Libro libro = new Libro();
                    libro.setTitulo(rs.getString("libro"));   
                    libro.setIsbn(rs.getString("isbn"));

                     Autor autor = new Autor();
                    autor.setNombre(rs.getString("autor"));   
                    libro.setAutor(autor);

                     Categoria categoria = new Categoria();
                    categoria.setNombre(rs.getString("categoria"));   
                    libro.setCategoria(categoria);

                    prestamo.setLibro(libro);

                    prestamos.add(prestamo);
                }

                System.out.println("Total en historial: " + count);
            }

        } catch (SQLException e) {
             e.printStackTrace();
        }

        return prestamos;
    }

//ultima parte 
}
