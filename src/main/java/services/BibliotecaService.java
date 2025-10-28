package services;

import dao.LibroDAO;
import Entidades.Libro;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.HashMap;
import java.util.Map;


@Path("/biblioteca")
public class BibliotecaService {

    private LibroDAO libroDAO = new LibroDAO();

    @GET
    @Path("/libros")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getLibros() {
        try {
            List<Libro> libros = libroDAO.listarLibrosCompletos();

            // DEBUG: Ver qué está devolviendo el DAO
            System.out.println("📚 Libros desde DAO: " + libros.size());
            for (Libro libro : libros) {
                System.out.println(" - " + libro.getTitulo() + " | Categoría: "
                        + (libro.getCategoria() != null ? libro.getCategoria().getNombre() : "null"));
            }

            return Response.ok(libros).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener libros: " + e.getMessage())
                    .build();
        }
    }
    
    
     @GET
    @Path("/hola")
    @Produces(MediaType.APPLICATION_JSON)
    public Response hola() {
        System.out.println("🎯 ENDPOINT /hola LLAMADO");

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Hola mundo desde REST!");
        response.put("funciona", "si");

        return Response.ok(response).build();
    }
}
