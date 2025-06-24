/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases.dao;

import com.capibarashop.clases.Conexion;
import com.capibarashop.clases.DetalleCarrito;
import com.capibarashop.clases.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Angel Aimar
 */
public class DetalleCarritoDAO {
    
    public boolean agregarProducto(int idCarrito, Producto producto, int cantidad) {
        String sql = "INSERT INTO Detalle_Carrito (id_Carrito, id_Producto, cantidad) VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCarrito);
            ps.setInt(2, producto.getId());
            ps.setInt(3, cantidad);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean eliminarProducto(int idCarrito, int idProducto) {
        String sql = "DELETE FROM Detalle_Carrito WHERE id_Carrito = ? AND id_Producto = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCarrito);
            ps.setInt(2, idProducto);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<DetalleCarrito> obtenerDetalles(int idCarrito) {
        List<DetalleCarrito> lista = new ArrayList<>();
        String sql = """
            SELECT p.*, d.cantidad FROM Detalle_Carrito d
            JOIN Productos p ON d.id_Producto = p.id_Producto
            WHERE d.id_Carrito = ?
        """;
        
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCarrito);
            ResultSet rs = ps.executeQuery();
            
            ProductoDAO dao = new ProductoDAO();
            
            while (rs.next()) {
                Producto p = dao.construirProducto(rs);
                int cantidad = rs.getInt("cantidad");
                lista.add(new DetalleCarrito(idCarrito, p, cantidad));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
//    public List<DetalleCarrito> obtenerDetallesPorCarrito(int idCarrito) {
//        List<DetalleCarrito> lista = new ArrayList<>();
//        String sql = """
//            SELECT dc.id_Producto, dc.cantidad
//            FROM Detalle_Carrito dc
//            JOIN Productos p ON dc.id_Producto = p.id_Producto
//            WHERE dc.id_Carrito = ?
//        """;
//
//        try (Connection con = Conexion.getConexion();
//             PreparedStatement ps = con.prepareStatement(sql)) {
//            ps.setInt(1, idCarrito);
//            ResultSet rs = ps.executeQuery();
//
//            //ProductoDAO dao = new ProductoDAO();
//
//            while (rs.next()) {
//                int idProd = rs.getInt("id_Producto");
//                System.out.println("Buscando producto con ID: " + idProd);
//
//                ProductoDAO dao = new ProductoDAO();
//                Producto p = dao.buscarProductoIDClientes(idProd);
//
//                //int idProducto = rs.getInt("id_Producto");
//                //Producto p = dao.buscarProductoIDClientes(idProd);
//
//                if (p == null) {
//                    System.err.println("❌ Producto con ID " + idProd + " no encontrado (producto es null)");
//                } else {
//                    System.out.println("✅ Producto encontrado: " + p.getNombre());
//                }
//            }
            
//            while (rs.next()) {
//                Producto p = dao.buscarProductoIDClientes(rs.getInt("id_Producto"));
//
//                if (p != null) {
//                    DetalleCarrito dc = new DetalleCarrito();
//                    dc.setProducto(p);
//                    dc.setCantidad(rs.getInt("cantidad"));
//                    lista.add(dc);
//                } else {
//                    System.err.println("Producto no encontrado con ID: " + rs.getInt("id_Producto"));
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return lista;
//    }
    
}
