/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

/**
 *
 * @author Home
 */
public class Libro {
    private int idLibro;
    private String titulo;
    private String isbn;
    private int año;
    private Categoria categoria;    // OBJETO COMPLETO
    private Autor autor;            // OBJETO COMPLETO  
    private String imagenPortada; 
    
    public Libro(){
        
    }

    public Libro(int idLibro, String titulo, String isbn, int año, Categoria categoria, Autor autor, String imagenPortada) {
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.isbn = isbn;
        this.año = año;
        this.categoria = categoria;
        this.autor = autor;
        this.imagenPortada = imagenPortada;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getAño() {
        return año;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Autor getAutor() {
        return autor;
    }

    public String getImagenPortada() {
        return imagenPortada;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAño(int año) {
        this.año = año;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public void setImagenPortada(String imagenPortada) {
        this.imagenPortada = imagenPortada;
    }
    
    
    
    
    
}
