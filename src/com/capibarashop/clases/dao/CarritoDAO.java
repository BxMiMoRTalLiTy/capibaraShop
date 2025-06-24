/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases.dao;

import com.capibarashop.clases.Carrito;
import com.capibarashop.clases.Conexion;
import com.capibarashop.clases.DetalleCarrito;
import com.capibarashop.clases.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Angel Aimar
 */
public class CarritoDAO {
    
    public int crearCarrito(int idUsuario) {
        int idCarrito = obtenerCarritoActivoID(idUsuario);
    
        if (idCarrito != -1) {
            return idCarrito; // Ya existe uno activo
        }

        String sql = "INSERT INTO Carritos (id_Usuario, fechaCreacion, activo) VALUES (?, GETDATE(), 1)";
        
        try (Connection con = Conexion.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, idUsuario);
            int rows = ps.executeUpdate();

            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // Devuelve el ID del nuevo carrito
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // Error
    }

    public Carrito obtenerCarritoActivo(int idUsuario) {
        String sql = "SELECT * FROM Carritos WHERE id_Usuario = ? AND activo = 1";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Carrito(
                    rs.getInt("id_Carrito"),
                    rs.getInt("id_Usuario"),
                    rs.getBoolean("activo")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean cerrarCarrito(int idCarrito) {
        String sql = "UPDATE Carritos SET activo = 0 WHERE id_Carrito = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCarrito);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public int obtenerCarritoActivoID(int idUsuario) {
        String sql = "SELECT id_Carrito FROM Carritos WHERE id_Usuario = ? AND activo = 1";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_Carrito");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public boolean agregarProducto(int idCarrito, int idProducto, int cantidad) {
        String sqlCheck = "SELECT * FROM Detalle_Carrito WHERE id_Carrito = ? AND id_Producto = ?";
        String sqlInsert = "INSERT INTO Detalle_Carrito (id_Carrito, id_Producto, cantidad) VALUES (?, ?, ?)";
        String sqlUpdateStock = "UPDATE Productos SET stock = stock - ? WHERE id_Producto = ? AND stock >= ?";
        
        try (Connection con = Conexion.getConexion()) {
            // Verificar si ya existe
            try (PreparedStatement psCheck = con.prepareStatement(sqlCheck)) {
                psCheck.setInt(1, idCarrito);
                psCheck.setInt(2, idProducto);
                ResultSet rs = psCheck.executeQuery();
                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Este producto ya está en el carrito.");
                    return false;
                }
            }

            // Descontar stock
            try (PreparedStatement psStock = con.prepareStatement(sqlUpdateStock)) {
                psStock.setInt(1, cantidad);
                psStock.setInt(2, idProducto);
                psStock.setInt(3, cantidad); // Verifica que hay stock suficiente

                int actualizado = psStock.executeUpdate();
                if (actualizado == 0) {
                    JOptionPane.showMessageDialog(null, "No hay suficiente stock.");
                    return false;
                }
            }

            // Insertar en el carrito
            try (PreparedStatement psInsert = con.prepareStatement(sqlInsert)) {
                psInsert.setInt(1, idCarrito);
                psInsert.setInt(2, idProducto);
                psInsert.setInt(3, cantidad);
                return psInsert.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean eliminarProducto(int idCarrito, int idProducto) {
        String sqlGetCantidad = "SELECT cantidad FROM Detalle_Carrito WHERE id_Carrito = ? AND id_Producto = ?";
        String sqlDelete = "DELETE FROM Detalle_Carrito WHERE id_Carrito = ? AND id_Producto = ?";
        String sqlRestoreStock = "UPDATE Productos SET stock = stock + ? WHERE id_Producto = ?";

        try (Connection con = Conexion.getConexion()) {
            con.setAutoCommit(false);

            int cantidad = 0;

            try (PreparedStatement psGet = con.prepareStatement(sqlGetCantidad)) {
                psGet.setInt(1, idCarrito);
                psGet.setInt(2, idProducto);
                ResultSet rs = psGet.executeQuery();
                if (rs.next()) {
                    cantidad = rs.getInt("cantidad");
                } else {
                    con.rollback();
                    return false;
                }
            }

            try (PreparedStatement psDelete = con.prepareStatement(sqlDelete);
                 PreparedStatement psRestore = con.prepareStatement(sqlRestoreStock)) {

                psDelete.setInt(1, idCarrito);
                psDelete.setInt(2, idProducto);
                psDelete.executeUpdate();

                psRestore.setInt(1, cantidad);
                psRestore.setInt(2, idProducto);
                psRestore.executeUpdate();

                con.commit();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<DetalleCarrito> obtenerProductosDelCarrito(int idCarrito) {
        List<DetalleCarrito> lista = new ArrayList<>();

        String sql = "SELECT dc.*, p.nombre, p.precio, p.descripcion, p.imagen "
                   + "FROM Detalle_Carrito dc "
                   + "JOIN Productos p ON dc.id_Producto = p.id_Producto "
                   + "WHERE dc.id_Carrito = ?";

        try (Connection con = Conexion.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idCarrito);
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id_Producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setImagen(rs.getBytes("imagen"));

                DetalleCarrito detalle = new DetalleCarrito();
                detalle.setId(rs.getInt("id_Carrito"));
                detalle.setProducto(producto);
                detalle.setCantidad(rs.getInt("cantidad"));

                lista.add(detalle);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    
}
