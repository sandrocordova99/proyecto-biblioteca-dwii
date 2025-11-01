/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import Entidades.Autor;
import utils.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Home
 */
public class AutorDAO {
     public List<Autor> getAutores() {
        List<Autor> autores = new ArrayList<>();
        
        String sql = "CALL sp_get_autores()";
        
        try (Connection conn = ConexionBD.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            
             
            while (rs.next()) {
                Autor autor = new Autor();
                autor.setIdAutor(rs.getInt("id_autor"));
                autor.setNombre(rs.getString("nombre"));
                autor.setNacionalidad(rs.getString("nacionalidad"));
            
                autores.add(autor);
                
            }
            
             
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return autores;
    }
}
