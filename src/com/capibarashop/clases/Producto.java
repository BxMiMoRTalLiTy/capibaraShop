/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases;

/**
 *
 * @author Angel Aimar
 */
public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private String descripcion;
    private int stock;
    private Categoria categoria;
    private Usuario usuario;
    private byte[] imagen;
    
    public Producto(){
    
    }
    
    public Producto(String nombre, double precio, String descripcion, int stock, byte[] imagen, Categoria categoria, Usuario usuario){
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.stock = stock;
        this.categoria = categoria;
        this.usuario = usuario;
        this.imagen = imagen;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return the precio
     */
    public double getPrecio() {
        return precio;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * @return the idCategoria
     */
    public Categoria getCategoria() {
        return categoria;
    }
    
    public Usuario getUsuario() {
        return usuario;
    }
    
    public byte[] getImagen(){
        return imagen;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @param idCategoria the idCategoria to set
     */
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void setImagen(byte[] imagen){
        this.imagen = imagen;
    }
    
}
