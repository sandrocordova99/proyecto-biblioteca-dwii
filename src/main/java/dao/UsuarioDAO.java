/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import Entidades.Usuario;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import utils.ConexionBD;

/**
 *
 * @author Home
 */
public class UsuarioDAO {
    
    public List<Usuario> listarUsuarios() {
    List<Usuario> usuarios = new ArrayList<>();
    String sql = "CALL sp_get_usuarios()";

    try (Connection conn = ConexionBD.getConnection();
         CallableStatement stmt = conn.prepareCall(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(rs.getInt("id_usuario"));
            usuario.setNombre(rs.getString("nombre"));
            usuario.setEmail(rs.getString("email"));
            usuario.setTelefono(rs.getString("telefono"));
            usuarios.add(usuario);
        }

    } catch (SQLException e) {
        System.out.println("❌ Error en UsuarioDAO.listarUsuarios: " + e.getMessage());
        e.printStackTrace();
    }

    return usuarios;
    }

    
    public List<Usuario> buscarUsuarios(String busqueda) {
        List<Usuario> usuarios = new ArrayList<>();

        String sql = "CALL sp_search_usuarios(?)";

        try (Connection conn = ConexionBD.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            if (busqueda == null || busqueda.trim().isEmpty()) {
                stmt.setNull(1, Types.VARCHAR);
            } else {
                stmt.setString(1, busqueda);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setIdUsuario(rs.getInt("id_usuario"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setTelefono(rs.getString("telefono"));

                    usuarios.add(usuario);
                }
            }

        } catch (SQLException e) {
            System.out.println(" Error en UsuarioDAO.buscarUsuarios: " + e.getMessage());
            e.printStackTrace();
        }

        return usuarios;
    }
    
    public int insertarUsuario(Usuario usuario) {
        String sql = "CALL sp_insert_usuario(?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection(); CallableStatement stmt = conn.prepareCall(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getTelefono());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_usuario_insertado");
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Error en UsuarioDAO.insertarUsuario: " + e.getMessage());
            e.printStackTrace();
        }

        return -1;
    }
    
}
