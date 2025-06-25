/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases;

/**
 *
 * @author Angel Aimar
 */
public class DatosFacturacion {
    private int id;
    private int idUsuario;         // FK a Usuarios
    private String rfc;
    private String razonSocial;
    private Direccion direccion;  // FK a Direcciones (puedes usar int idDireccion si prefieres)

    public DatosFacturacion() {}

    public DatosFacturacion(int id, int idUsuario, String rfc, String razonSocial, Direccion direccion) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.rfc = rfc;
        this.razonSocial = razonSocial;
        this.direccion = direccion;
    }

    public DatosFacturacion(int idUsuario, String rfc, String razonSocial, Direccion direccion) {
        this(-1, idUsuario, rfc, razonSocial, direccion);
    }

    // Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return razonSocial + " - RFC: " + rfc;
    }
}
