/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases;

/**
 *
 * @author Angel Aimar
 */
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Factura {
    private int id;
    private int idVenta;
    private DatosFacturacion datosFacturacion;
    private BigDecimal total;
    private LocalDateTime fechaEmision;

    public Factura() {}

    public Factura(int id, int idVenta, DatosFacturacion datosFacturacion, BigDecimal total, LocalDateTime fechaEmision) {
        this.id = id;
        this.idVenta = idVenta;
        this.datosFacturacion = datosFacturacion;
        this.total = total;
        this.fechaEmision = fechaEmision;
    }

    public Factura(int idVenta, DatosFacturacion datosFacturacion, BigDecimal total) {
        this.idVenta = idVenta;
        this.datosFacturacion = datosFacturacion;
        this.total = total;
        this.fechaEmision = LocalDateTime.now();
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public DatosFacturacion getDatosFacturacion() {
        return datosFacturacion;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public void setDatosFacturacion(DatosFacturacion datosFacturacion) {
        this.datosFacturacion = datosFacturacion;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }
}