/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases;

/**
 *
 * @author Angel Aimar
 */
import java.time.LocalDateTime;

public class Factura {
    private int id;
    private int idVenta;
    private LocalDateTime fechaEmision;
    private String rfc;
    private String razonSocial;
    private String direccionFacturacion;
    private double total;

    public Factura(int idVenta, String rfc, String razonSocial, String direccionFacturacion, double total) {
        this.idVenta = idVenta;
        this.rfc = rfc;
        this.razonSocial = razonSocial;
        this.direccionFacturacion = direccionFacturacion;
        this.total = total;
        this.fechaEmision = LocalDateTime.now();
    }

    // Getters y setters...
    public int getId() { return id; }
    
    public int getIdVenta() { return idVenta; }
    
    public LocalDateTime getFechaEmision() { return fechaEmision; }
    
    public String getRfc() { return rfc; }
    
    public String getRazonSocial() { return razonSocial; }
    
    public String getDireccionFacturacion() { return direccionFacturacion; }
    
    public double getTotal() { return total; }
    
    public void setId(int id) { this.id = id; }
    
    public void setFechaEmision(LocalDateTime fechaEmision) { this.fechaEmision = fechaEmision; }
}