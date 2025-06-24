/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases;

/**
 *
 * @author Angel Aimar
 */
public class Carrito {
    private int id;
    private int idUsuario;
    private boolean activo;

    public Carrito(int id, int idUsuario, boolean activo) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.activo = activo;
    }

    public Carrito(int idUsuario) {
        this.idUsuario = idUsuario;
        this.activo = true;
    }

    public int getId() { return id; }
    public int getIdUsuario() { return idUsuario; }
    public boolean isActivo() { return activo; }

    public void setId(int id) { this.id = id; }
    public void setActivo(boolean activo) { this.activo = activo; }
    
}
