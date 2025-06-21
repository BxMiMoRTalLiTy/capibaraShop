/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases;

/**
 *
 * @author Angel Aimar
 */
public class Utilidades {
    
    public static String generarHTMLProducto(Producto p) {
        return "<html>"
            + "<h3 style='color:#2a4a89;'>¿Deseas eliminar el siguiente producto?</h3>"
            + "<table cellpadding='4'>"
            + "<tr><td><b>ID:</b></td><td>" + p.getId() + "</td></tr>"
            + "<tr><td><b>Nombre:</b></td><td>" + p.getNombre() + "</td></tr>"
            + "<tr><td><b>Precio:</b></td><td>$" + String.format("%.2f", p.getPrecio()) + "</td></tr>"
            + "<tr><td><b>Stock:</b></td><td>" + p.getStock() + "</td></tr>"
            + "<tr><td><b>Descripción:</b></td><td>" + p.getDescripcion() + "</td></tr>"
            + "</table>"
            + "</html>";
    }
    
}
