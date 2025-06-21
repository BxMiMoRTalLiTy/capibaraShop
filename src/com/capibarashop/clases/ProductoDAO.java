/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Angel Aimar
 */
public class ProductoDAO {
    
    public boolean insertarProducto(Producto p) throws SQLException{
        String sql = "INSERT INTO Productos(nombre, precio, descripcion, stock, id_Categoria, id_Usuario)"
                + "VALUES (?, ?, ?, ?, ?, ?)";
        
        try(Connection con = Conexion.getConexion();
            PreparedStatement insert = con.prepareStatement(sql)){
                insert.setString(1, p.getNombre());
                insert.setDouble(2, p.getPrecio());
                insert.setString(3, p.getDescripcion());
                insert.setInt(4, p.getStock());
                insert.setInt(5, p.getIdCategoria());
                insert.setInt(6, p.getIdUsuario());
                
                return insert.executeUpdate() > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        
    }
    
    public Producto buscarProductoIDAdmin(int idProducto){
        String sql = """
        SELECT p.id_Producto, p.nombre, p.precio, p.descripcion, p.stock,
               p.id_Categoria, p.id_Usuario, c.nombre AS categoriaNombre
        FROM Productos p
        JOIN Categorias c ON p.id_Categoria = c.id_Categoria
        WHERE p.id_Producto = ?
        """;
        
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProducto);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id_Producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setStock(rs.getInt("stock"));
                p.setIdCategoria(rs.getInt("id_Categoria"));
                p.setIdUsuario(rs.getInt("id_Usuario"));
                p.setNombreCategoria(rs.getString("categoriaNombre"));
                return p;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        

        return null;
    }
    
    public Producto buscarProductoIDyUsuario(int idProducto, int idUsuario){
        String sql = """
        SELECT p.id_Producto, p.nombre, p.precio, p.descripcion, p.stock,
               p.id_Categoria, p.id_Usuario, c.nombre AS categoriaNombre
        FROM Productos p
        JOIN Categorias c ON p.id_Categoria = c.id_Categoria
        WHERE p.id_Producto = ? AND p.id_Usuario = ?
        """;
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProducto);
            ps.setInt(2, idUsuario);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id_Producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setStock(rs.getInt("stock"));
                p.setIdCategoria(rs.getInt("id_Categoria"));
                p.setIdUsuario(rs.getInt("id_Usuario"));
                return p;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    
    //Para cualquier usuario
    public List<Producto> listarProductosPorUsuario(int idUsuario) throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = """
            SELECT p.id_Producto, p.nombre, p.precio, p.descripcion, p.stock,
                   p.id_Categoria, p.id_Usuario, c.nombre AS categoriaNombre
            FROM Productos p
            JOIN Categorias c ON p.id_Categoria = c.id_Categoria
            WHERE p.id_Usuario = ?
        """;

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id_Producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setStock(rs.getInt("stock"));
                p.setIdCategoria(rs.getInt("id_Categoria"));
                p.setIdUsuario(rs.getInt("id_Usuario"));
                p.setNombreCategoria(rs.getString("categoriaNombre"));
                lista.add(p);
            }
        }

        return lista;
    }
    
    //Para el ADMIN
    public List<Producto> listarProductosAdmin() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        String sql = """
        SELECT p.id_Producto, p.nombre, p.precio, p.descripcion, p.stock,
               p.id_Categoria, p.id_Usuario, c.nombre AS categoriaNombre
        FROM Productos p
        JOIN Categorias c ON p.id_Categoria = c.id_Categoria
    """;

        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = new Producto();
                p.setId(rs.getInt("id_Producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setStock(rs.getInt("stock"));
                p.setIdCategoria(rs.getInt("id_Categoria"));
                p.setIdUsuario(rs.getInt("id_Usuario"));
                p.setNombreCategoria(rs.getString("categoriaNombre")); // se añade aquí
                lista.add(p);
            }
        }

    return lista;
}
    
    public boolean eliminarProducto(int idProducto){
        String sql = "DELETE FROM Productos WHERE id_Producto = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProducto);
            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
    
    public List<Producto> listarProductos() throws SQLException{
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Productos";
        
        try(Connection con = Conexion.getConexion();
                PreparedStatement insert = con.prepareStatement(sql);
                ResultSet rs = insert.executeQuery()){
            while(rs.next()){
                Producto p = new Producto();
                p.setId(rs.getInt("id_Producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                p.setDescripcion(rs.getString("descripcion"));
                p.setIdCategoria(rs.getInt("id_Categoria"));
                p.setIdUsuario(rs.getInt("id_Usuario"));
                
                lista.add(p);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return lista;
    }
    
    public boolean actualizarProducto(Producto p){
        String sql = "UPDATE Productos SET nombre = ?, precio = ?, descripcion = ?, stock = ?,"
                + "id_Categoria = ? WHERE id_Producto = ?";
        try(Connection con = Conexion.getConexion();
            PreparedStatement insert = con.prepareStatement(sql)){
                insert.setString(1, p.getNombre());
                insert.setDouble(2, p.getPrecio());
                insert.setString(3, p.getDescripcion());
                insert.setInt(4, p.getStock());
                insert.setInt(5, p.getIdCategoria());
                insert.setInt(6, p.getId());
                
                return insert.executeUpdate() > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    
}
