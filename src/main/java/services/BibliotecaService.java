package services;

import dao.LibroDAO;
import Entidades.Libro;
import dao.PrestamoDAO;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.StringReader;
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

            System.out.println(" Libros desde DAO: " + libros.size());
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
    @Path("/libros/buscar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarLibros(@QueryParam("q") String busqueda) {
        try {
            System.out.println("Buscando libros: " + busqueda);

            List<Libro> libros = libroDAO.buscarLibros(busqueda);

            System.out.println("Resultados: " + libros.size() + " libros");

            return Response.ok(libros).build();

        } catch (Exception e) {
            System.out.println("Error en búsqueda: " + e.getMessage());
            return Response.status(500).entity("Error en búsqueda: " + e.getMessage()).build();
        }

    }

    @GET
    @Path("/hola")
    @Produces(MediaType.APPLICATION_JSON)
    public Response hola() {
        System.out.println(" ENDPOINT /hola LLAMADO");

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Hola mundo desde REST!");
        response.put("funciona", "si");

        return Response.ok(response).build();
    }

    @DELETE
    @Path("/libros/{id}")
    public Response eliminarLibro(@PathParam("id") int idLibro) {
        try {
            System.out.println("Eliminando libro ID: " + idLibro);
            String resultado = libroDAO.eliminarLibro(idLibro);
            if (resultado.startsWith("ERROR")) {
                return Response.status(400).entity(resultado).build();
            }
            return Response.ok(resultado).build();
        } catch (Exception e) {
            return Response.status(500).entity("Error eliminando libro: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/crearLibros")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response crearLibro(Libro libro) {
        try {
            System.out.println("Creando libro: " + libro.getTitulo());

            int idGenerado = libroDAO.insertarLibro(libro);

            if (idGenerado == -1) {
                return Response.status(500).entity("Error al crear el libro").build();
            }

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Libro creado exitosamente");
            respuesta.put("id_libro", idGenerado);
            respuesta.put("titulo", libro.getTitulo());

            return Response.status(201).entity(respuesta).build();

        } catch (Exception e) {
            return Response.status(500).entity("Error creando libro: " + e.getMessage()).build();
        }
    }

    @PUT
    @Path("/libros/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response actualizarLibro(@PathParam("id") int idLibro, Libro libro) {
        try {
            System.out.println("✏️ Actualizando libro ID: " + idLibro);

            // Asignar el ID de la URL al libro
            libro.setIdLibro(idLibro);

            boolean actualizado = libroDAO.actualizarLibro(libro);

            if (!actualizado) {
                return Response.status(404).entity("Libro no encontrado").build();
            }

            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", "Libro actualizado exitosamente");
            respuesta.put("id_libro", String.valueOf(idLibro));
            respuesta.put("titulo", libro.getTitulo());

            return Response.ok(respuesta).build();

        } catch (Exception e) {
            return Response.status(500).entity("Error actualizando libro: " + e.getMessage()).build();
        }
    }

    /* ---------------------------- PRESTAMOS ---------------------------- */
    @POST
    @Path("/prestamos")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response prestarLibro(String jsonRequest) {
        try {
            // Parsear JSON manualmente (por si no funciona la deserialización automática)
            JsonObject jsonObject = Json.createReader(new StringReader(jsonRequest)).readObject();
            int idLibro = jsonObject.getInt("idLibro");
            int idUsuario = jsonObject.getInt("idUsuario");

            System.out.println(" Prestando libro ID: " + idLibro + " a usuario ID: " + idUsuario);

            PrestamoDAO prestamoDAO = new PrestamoDAO();
            String resultado = prestamoDAO.prestarLibro(idLibro, idUsuario);

            if (resultado.startsWith("ERROR")) {
                return Response.status(400).entity(resultado).build();
            }

            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", resultado);
            respuesta.put("id_libro", String.valueOf(idLibro));
            respuesta.put("id_usuario", String.valueOf(idUsuario));

            return Response.status(201).entity(respuesta).build();

        } catch (Exception e) {
            return Response.status(500).entity("Error en préstamo: " + e.getMessage()).build();
        }
    }

    @POST
    @Path("/prestamos/{id}/devolver")
    public Response devolverLibro(@PathParam("id") int idPrestamo) {
        try {
            System.out.println(" Devolviendo préstamo ID: " + idPrestamo);

            PrestamoDAO prestamoDAO = new PrestamoDAO();
            String resultado = prestamoDAO.devolverLibro(idPrestamo);

            if (resultado.startsWith("ERROR")) {
                return Response.status(400).entity(resultado).build();
            }

            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("mensaje", resultado);
            respuesta.put("id_prestamo", String.valueOf(idPrestamo));

            return Response.ok(respuesta).build();

        } catch (Exception e) {
            return Response.status(500).entity("Error en devolución: " + e.getMessage()).build();
        }
    }
    
    //ultima linea
}
