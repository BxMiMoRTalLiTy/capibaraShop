/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases.dao;

import com.capibarashop.clases.Conexion;
import com.capibarashop.clases.Direccion;
import java.sql.*;


/**
 *
 * @author Angel Aimar
 */
public class VentaDAO {
    
    public boolean realizarVentaDesdeCarrito(int idCarrito, int idUsuario, Direccion direccion) {
        String insertVenta = "INSERT INTO Ventas (id_Usuario, total) VALUES (?, ?)";
        String insertDetalle = "INSERT INTO Detalles_Ventas (id_Venta, id_Producto, cantidad, subtotal) VALUES (?, ?, ?, ?)";
        String insertFactura = "INSERT INTO Facturas (id_Venta, rfc, razonSocial, direccionFacturacion, total) VALUES (?, ?, ?, ?, ?)";
        String selectProductos = "SELECT dc.id_Producto, dc.cantidad, p.precio FROM Detalle_Carrito dc JOIN Productos p ON dc.id_Producto = p.id_Producto WHERE dc.id_Carrito = ?";
        String updateStock = "UPDATE Productos SET stock = stock - ? WHERE id_Producto = ?";

        try (Connection con = Conexion.getConexion()) {
            con.setAutoCommit(false);
            
            PreparedStatement psCarrito = con.prepareStatement(selectProductos);
            psCarrito.setInt(1, idCarrito);
            ResultSet rs = psCarrito.executeQuery();

            double total = 0;
            new DetalleCarritoDAO();
            
            while (rs.next()) {
                int cantidad = rs.getInt("cantidad");
                double precio = rs.getDouble("precio");
                total += cantidad * precio;
            }
            rs.close();
            psCarrito.close();

            // Insertar venta
            PreparedStatement psVenta = con.prepareStatement(insertVenta, Statement.RETURN_GENERATED_KEYS);
            psVenta.setInt(1, idUsuario);
            psVenta.setDouble(2, total);
            psVenta.executeUpdate();
            ResultSet rsVenta = psVenta.getGeneratedKeys();
            rsVenta.next();
            int idVenta = rsVenta.getInt(1);

            // Insertar detalles de venta
            psCarrito = con.prepareStatement(selectProductos);
            psCarrito.setInt(1, idCarrito);
            rs = psCarrito.executeQuery();

            while (rs.next()) {
                int idProducto = rs.getInt("id_Producto");
                int cantidad = rs.getInt("cantidad");
                double precio = rs.getDouble("precio");

                PreparedStatement psDetalle = con.prepareStatement(insertDetalle);
                psDetalle.setInt(1, idVenta);
                psDetalle.setInt(2, idProducto);
                psDetalle.setInt(3, cantidad);
                psDetalle.setDouble(4, cantidad * precio);
                psDetalle.executeUpdate();
                psDetalle.close();
                
                // Actualizar stock
                PreparedStatement psStock = con.prepareStatement(updateStock);
                psStock.setInt(1, cantidad);
                psStock.setInt(2, idProducto);
                psStock.executeUpdate();
                psStock.close();
            }

            // Insertar factura
            PreparedStatement psFactura = con.prepareStatement(insertFactura);
            psFactura.setInt(1, idVenta);
            psFactura.setString(2, "XAXX010101000"); // RFC genérico
            psFactura.setString(3, "Cliente Capibara");
            psFactura.setString(4, direccion.toString());
            psFactura.setDouble(5, total);
            psFactura.executeUpdate();
            psFactura.close();

            con.commit();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            try (Connection con = Conexion.getConexion()) {
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }
}
