/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases;

import java.sql.*;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Angel Aimar
 */
public class CategoriaDAO {
    
    
    
    public Categoria obtenerCategoria(int id){
        String sql = "SELECT id_Categoria, nombre FROM Categorias";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            Categoria c = new Categoria(
                    rs.getInt("id_Categoria"),
                    rs.getString("nombre")
            );
            
          return c;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar categorías: " + e.getMessage());
        }
        
        return null;
    }
    
    public List<Categoria> listarCategorias() {
    List<Categoria> lista = new ArrayList<>();
    String sql = "SELECT id_Categoria, nombre FROM Categorias";

    try (Connection con = Conexion.getConexion();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Categoria c = new Categoria(
                rs.getInt("id_Categoria"),
                rs.getString("nombre")
            );
            lista.add(c);
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error al cargar categorías: " + e.getMessage());
    }

    return lista;
}
    
}
