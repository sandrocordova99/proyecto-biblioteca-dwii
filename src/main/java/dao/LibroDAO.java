/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import Entidades.Autor;
import Entidades.Categoria;
import Entidades.Libro;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import utils.ConexionBD;

/**
 *
 * @author Home
 */
public class LibroDAO {
    public List<Libro> listarLibrosCompletos() {
        List<Libro> libros = new ArrayList<>();
        
        String sql = "CALL sp_get_libros_completos()";
        
        try (Connection conn = ConexionBD.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Libro libro = new Libro();
                libro.setIdLibro(rs.getInt("id_libro"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setIsbn(rs.getString("isbn"));
                libro.setAño(rs.getInt("año"));
                
                 
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNombre(rs.getString("categoria"));
                libro.setCategoria(categoria);
                
                 
                Autor autor = new Autor();
                autor.setIdAutor(rs.getInt("id_autor"));
                autor.setNombre(rs.getString("autor"));
                autor.setNacionalidad(rs.getString("nacionalidad"));
                libro.setAutor(autor);
                
                // Asignar imagen portada
                libro.setImagenPortada(rs.getString("imagen_portada"));
                
                libros.add(libro);
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error en LibroDAO.listarLibrosCompletos: " + e.getMessage());
            e.printStackTrace();
        }
        
        return libros;
    }
}

