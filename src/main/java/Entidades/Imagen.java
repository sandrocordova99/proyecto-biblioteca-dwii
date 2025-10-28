/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.util.Date;

/**
 *
 * @author Home
 */
public class Imagen {

    private int idImagen;
    private String pictureLocation;
    private int idLibro;
    private Date fechaSubida;

    
    public Imagen(){
        
    }

    public Imagen(int idImagen, String pictureLocation, int idLibro, Date fechaSubida) {
        this.idImagen = idImagen;
        this.pictureLocation = pictureLocation;
        this.idLibro = idLibro;
        this.fechaSubida = fechaSubida;
    }

    public int getIdImagen() {
        return idImagen;
    }

    public String getPictureLocation() {
        return pictureLocation;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public Date getFechaSubida() {
        return fechaSubida;
    }

    public void setIdImagen(int idImagen) {
        this.idImagen = idImagen;
    }

    public void setPictureLocation(String pictureLocation) {
        this.pictureLocation = pictureLocation;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public void setFechaSubida(Date fechaSubida) {
        this.fechaSubida = fechaSubida;
    }
    
    
}
