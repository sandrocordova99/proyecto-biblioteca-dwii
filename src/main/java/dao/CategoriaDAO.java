package dao;

import Entidades.Categoria;
import utils.ConexionBD;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    
    public List<Categoria> getCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        
        String sql = "CALL sp_get_categorias()";
        
        try (Connection conn = ConexionBD.getConnection();
             CallableStatement stmt = conn.prepareCall(sql);
             ResultSet rs = stmt.executeQuery()) {
            
             
            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNombre(rs.getString("nombre"));
                
                categorias.add(categoria);
                
             }
            
             
        } catch (SQLException e) {
             e.printStackTrace();
        }
        
        return categorias;
    }
}