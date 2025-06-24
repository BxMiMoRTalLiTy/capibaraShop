/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases;

/**
 *
 * @author Angel Aimar
 */
public class Direccion {
    private int id;
    private int idUsuario;
    private String calle;
    private String ciudad;
    private String estado;
    private String codigoPostal;
    private String pais;
    private String tipo; // envio o factura
    private boolean esPrincipal;

    public Direccion(int id, int idUsuario, String calle, String ciudad, String estado, String codigoPostal, String pais, String tipo, boolean esPrincipal) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.calle = calle;
        this.ciudad = ciudad;
        this.estado = estado;
        this.codigoPostal = codigoPostal;
        this.pais = pais;
        this.tipo = tipo;
        this.esPrincipal = esPrincipal;
    }

    public Direccion(int idUsuario, String calle, String ciudad, String estado, String codigoPostal, String pais, String tipo, boolean esPrincipal) {
        this(-1, idUsuario, calle, ciudad, estado, codigoPostal, pais, tipo, esPrincipal);
    }

    // Getters y setters...
    public int getId() {
        return id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getCalle() {
        return calle;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getEstado() {
        return estado;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public String getPais() {
        return pais;
    }

    public String getTipo() {
        return tipo;
    }
    
    public boolean getEsPrincipal() {
        return esPrincipal;
    }
    
    public void setEsPrincipal(boolean esPrincipal) {
        this.esPrincipal = esPrincipal;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return calle + ", " + ciudad + ", " + estado + ", " + pais + " (" + tipo + ")" + ", " + codigoPostal + " CP";
    }
}
