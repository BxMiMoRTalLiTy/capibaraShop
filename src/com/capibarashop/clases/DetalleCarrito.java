/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases;

/**
 *
 * @author Angel Aimar
 */
public class DetalleCarrito {
    private int id;
    private int idCarrito;
    private Producto producto;
    private int cantidad;

    public DetalleCarrito(){}
    
    public DetalleCarrito(int idCarrito, Producto producto, int cantidad) {
        this.idCarrito = idCarrito;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public int getIdCarrito() {
        return idCarrito;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setId(int id) { 
        this.id = id; 
    }
    
    public void setProducto(Producto Producto){
        this.producto = producto;
    }
    
    public void setCantidad(int cantidad) { 
        this.cantidad = cantidad; 
    }
}
