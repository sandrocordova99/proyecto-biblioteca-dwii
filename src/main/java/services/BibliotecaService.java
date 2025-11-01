package services;

import Entidades.Autor;
import Entidades.Categoria;
import dao.LibroDAO;
import dao.CategoriaDAO;
import Entidades.Libro;
import Entidades.Prestamo;
import dao.AutorDAO;
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

            List<Libro> libros = libroDAO.buscarLibros(busqueda);

            System.out.println("Resultados: " + libros.size() + " libros");

            return Response.ok(libros).build();

        } catch (Exception e) {
            return Response.status(500).entity("Error en búsqueda: " + e.getMessage()).build();
        }

    }

    @GET
    @Path("/hola")
    @Produces(MediaType.APPLICATION_JSON)
    public Response hola() {

        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Hola mundo desde REST!");
        response.put("funciona", "si");

        return Response.ok(response).build();
    }

    @DELETE
    @Path("/libros/{id}")
    public Response eliminarLibro(@PathParam("id") int idLibro) {
        try {
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

    @GET
    @Path("/prestamos/activos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPrestamosActivos() {
        try {

            PrestamoDAO prestamoDAO = new PrestamoDAO();
            List<Prestamo> prestamos = prestamoDAO.getPrestamosActivos();

            return Response.ok(prestamos).build();

        } catch (Exception e) {
            return Response.status(500).entity("Error: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("/usuarios/{id}/historial")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHistorialUsuario(@PathParam("id") int idUsuario) {
        try {

            PrestamoDAO prestamoDAO = new PrestamoDAO();
            List<Prestamo> historial = prestamoDAO.getHistorialUsuario(idUsuario);

            return Response.ok(historial).build();

        } catch (Exception e) {
            return Response.status(500).entity("Error: " + e.getMessage()).build();
        }
    }

    /* ---------------------------- CATEGORIA ---------------------------- */
    @GET
    @Path("/categorias")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCategorias() {
        try {

            CategoriaDAO categoriaDAO = new CategoriaDAO();
            List<Categoria> categorias = categoriaDAO.getCategorias();

            return Response.ok(categorias).build();

        } catch (Exception e) {
            return Response.status(500).entity("Error: " + e.getMessage()).build();
        }
    }

    /* ---------------------------- CATEGORIA ---------------------------- */
    @GET
    @Path("/autores")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAutores() {
        try {
            AutorDAO autorDAO = new AutorDAO();
            List<Autor> autores = autorDAO.getAutores();
            return Response.ok(autores).build();
        } catch (Exception e) {
            return Response.status(500).entity("Error: " + e.getMessage()).build();
        }
    }

    //ultima linea
}
