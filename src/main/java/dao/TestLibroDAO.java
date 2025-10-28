package dao;

import Entidades.Libro;
import java.util.List;
import dao.LibroDAO;

public class TestLibroDAO {
    public static void main(String[] args) {
        LibroDAO libroDAO = new LibroDAO();
        
        System.out.println("📚 Probando listar libros...");
        List<Libro> libros = libroDAO.listarLibrosCompletos();
        
        if (libros.isEmpty()) {
            System.out.println("❌ No se encontraron libros");
        } else {
            System.out.println("✅ Libros encontrados: " + libros.size());
            for (Libro libro : libros) {
                System.out.println("ID: " + libro.getIdLibro() + 
                                 " | Título: " + libro.getTitulo() +
                                 " | Categoría: " + libro.getCategoria().getNombre() +
                                 " | Autor: " + libro.getAutor().getNombre());
            }
        }
    }
}