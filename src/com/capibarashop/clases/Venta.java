/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author Angel Aimar
 */
public class Venta {
    private int id;
    private int idUsuario;
    private LocalDateTime fecha;
    private BigDecimal total;

    public Venta(int id, int idUsuario, LocalDateTime fecha, BigDecimal total) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.total = total;
    }

    public Venta(int idUsuario, BigDecimal total) {
        this.idUsuario = idUsuario;
        this.total = total;
        this.fecha = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
