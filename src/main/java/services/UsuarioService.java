package services;

import dao.UsuarioDAO;
import Entidades.Usuario;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Path("/usuario")

public class UsuarioService {
    
    private UsuarioDAO UsuarioDAO = new UsuarioDAO();

    @GET
    @Path("/listar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuarios() {
        try {
            List<Usuario> usuarios = UsuarioDAO.listarUsuarios();

            System.out.println("Usuarios desde DAO: " + usuarios.size());
            for (Usuario usuario : usuarios) {
                System.out.println(" - " + usuario.getNombre() + " | Email: "
                        + usuario.getEmail() + " | Telefono: " + usuario.getTelefono());
            }
            return Response.ok(usuarios).build();
            
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener usuarios: " + e.getMessage())
                    .build();
        }
    }
    
    @POST
    @Path("/crear")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearUsuario(Usuario usuario) {
        try {
            System.out.println("Creando usuario: " + usuario.getNombre());

            int idGenerado = UsuarioDAO.insertarUsuario(usuario);

            if (idGenerado == -1) {
                return Response.status(500).entity("Error al crear el usuario").build();
            }

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Usuario creado exitosamente");
            respuesta.put("id_usuario", idGenerado);
            respuesta.put("nombre", usuario.getNombre());

            return Response.status(201).entity(respuesta).build();

        } catch (Exception e) {
            return Response.status(500).entity("Error creando usuario: " + e.getMessage()).build();
        }
    }
    
    @GET
    @Path("/buscar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarUsuarios(@QueryParam("q") String busqueda) {
        try {
            System.out.println("Buscando usuarios: " + busqueda);

            List<Usuario> usuarios = UsuarioDAO.buscarUsuarios(busqueda);

            System.out.println("Resultados: " + usuarios.size() + " usuarios");

            return Response.ok(usuarios).build();

        } catch (Exception e) {
            System.out.println("Error en búsqueda: " + e.getMessage());
            return Response.status(500).entity("Error en búsqueda: " + e.getMessage()).build();
        }

    }
    
}
