/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases;

import java.sql.*;
import java.util.*;

/**
 *
 * @author Angel Aimar
 */
public class ProductoDAO {
    
    public boolean insertarProducto(Producto p) throws SQLException{
        String sql = "INSERT INTO Productos(nombre, precio, descripcion, stock, id_categoria)"
                + "VALUES (?, ?, ?, ?, ?)";
        
        try(Connection con = Conexion.getConexion();
            PreparedStatement insert = con.prepareStatement(sql)){
                insert.setString(1, p.getNombre());
                insert.setDouble(2, p.getPrecio());
                insert.setString(3, p.getDescripcion());
                insert.setInt(4, p.getStock());
                insert.setInt(5, p.getIdCategoria());
                
                return insert.executeUpdate() > 0;
        }catch(SQLException e){
            e.printStackTrace();
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
                p.setIdCategoria(rs.getInt("id_categoria"));
                
                lista.add(p);
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        return lista;
    }
    
    public boolean actualizarProducto(Producto p){
        String sql = "UPDATE Productos SET nombre = ?, precio = ?, descripcion = ?, stock = ?,"
                + "id_categoria = ? WHERE id_Producto = ?";
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
