/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases.dao;

import com.capibarashop.clases.Conexion;
import com.capibarashop.clases.MetodoPago;
import com.capibarashop.clases.DatosFacturacion;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Angel Aimar
 */
public class VentaDAO {
 
    public boolean realizarVenta(int idCarrito, int idUsuario, DatosFacturacion datosFacturacion, MetodoPago metodoPago) {
        String insertVenta = "INSERT INTO Ventas (id_Usuario, id_MetodoPago, total) VALUES (?, ?, ?)";
        String insertDetalle = "INSERT INTO Detalles_Ventas (id_Venta, id_Producto, cantidad, subtotal) VALUES (?, ?, ?, ?)";
        String insertFactura = "INSERT INTO Facturas (id_Venta, id_Facturacion, total) VALUES (?, ?, ?)";
        String selectProductos = "SELECT dc.id_Producto, dc.cantidad, p.precio FROM Detalle_Carrito dc JOIN Productos p ON dc.id_Producto = p.id_Producto WHERE dc.id_Carrito = ?";
        String limpiarCarrito = "DELETE FROM Detalle_Carrito WHERE id_Carrito = ?";

        Connection con = null;

        try {
            con = Conexion.getConexion();
            con.setAutoCommit(false);

            // Obtener productos
            PreparedStatement psCarrito = con.prepareStatement(selectProductos);
            psCarrito.setInt(1, idCarrito);
            ResultSet rs = psCarrito.executeQuery();

            BigDecimal total = BigDecimal.ZERO;
            List<DetalleVentaTemp> detalles = new ArrayList<>();

            while (rs.next()) {
                int idProducto = rs.getInt("id_Producto");
                int cantidad = rs.getInt("cantidad");
                BigDecimal precio = rs.getBigDecimal("precio");
                BigDecimal subtotal = precio.multiply(BigDecimal.valueOf(cantidad));

                total = total.add(subtotal);
                detalles.add(new DetalleVentaTemp(idProducto, cantidad, subtotal));
            }

            rs.close();
            psCarrito.close();

            // Insertar venta
            PreparedStatement psVenta = con.prepareStatement(insertVenta, Statement.RETURN_GENERATED_KEYS);
            psVenta.setInt(1, idUsuario);
            psVenta.setInt(2, metodoPago.getId()); // este parámetro debes pasarlo al método realizarVenta(...)
            psVenta.setBigDecimal(3, total);
            psVenta.executeUpdate();

            ResultSet rsVenta = psVenta.getGeneratedKeys();
            rsVenta.next();
            int idVenta = rsVenta.getInt(1);
            rsVenta.close();
            psVenta.close();

            // Insertar detalles
            for (DetalleVentaTemp detalle : detalles) {
                PreparedStatement psDetalle = con.prepareStatement(insertDetalle);
                psDetalle.setInt(1, idVenta);
                psDetalle.setInt(2, detalle.idProducto);
                psDetalle.setInt(3, detalle.cantidad);
                psDetalle.setBigDecimal(4, detalle.subtotal);
                psDetalle.executeUpdate();
                psDetalle.close();
            }

            // Insertar factura si aplica
            if (datosFacturacion != null) {
                PreparedStatement psFactura = con.prepareStatement(insertFactura);
                psFactura.setInt(1, idVenta);
                psFactura.setInt(2, datosFacturacion.getId());
                psFactura.setBigDecimal(3, total);
                psFactura.executeUpdate();
                psFactura.close();
            }

            // Limpiar carrito
            PreparedStatement psLimpiar = con.prepareStatement(limpiarCarrito);
            psLimpiar.setInt(1, idCarrito);
            psLimpiar.executeUpdate();
            psLimpiar.close();

            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            return false;
        }
    }
    
    private static class DetalleVentaTemp {
        int idProducto;
        int cantidad;
        BigDecimal subtotal;

        DetalleVentaTemp(int idProducto, int cantidad, BigDecimal subtotal) {
            this.idProducto = idProducto;
            this.cantidad = cantidad;
            this.subtotal = subtotal;
        }
    }
}
