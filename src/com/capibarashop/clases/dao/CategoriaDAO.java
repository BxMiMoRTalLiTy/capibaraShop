/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases.dao;

import com.capibarashop.clases.Categoria;
import com.capibarashop.clases.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author Angel Aimar
 */
public class CategoriaDAO {
    
    public Categoria obtenerCategoria(int id){
        String sql = "SELECT * FROM Categorias WHERE id_Categoria = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            
          if (rs.next()) {
                return new Categoria(
                    rs.getInt("id_Categoria"),
                    rs.getString("nombre")
                );
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Sin categoria: " + e.getMessage());
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
    
    public boolean actualizarCategoria(Categoria c){
        String sql = "UPDATE Categorias SET nombre = ? WHERE id_Categoria = ?";
        
        try(Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)){
                ps.setString(1, c.getNombre());
                ps.setInt(2, c.getId());
                return ps.executeUpdate() > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public static void cargarategorias(JComboBox j, String mensaje){
        CategoriaDAO dao = new CategoriaDAO();
        List<Categoria> categorias = dao.listarCategorias();
        
        categorias.sort(Comparator.comparingInt(Categoria :: getId));
        
        j.removeAllItems();
        j.addItem(new Categoria(0, mensaje));
        
        for (Categoria c : categorias) {
            j.addItem(c);
        }
        
        SwingUtilities.invokeLater(() -> {
            Object comp = j.getUI().getAccessibleChild(j, 0);
            if (comp instanceof javax.swing.plaf.basic.ComboPopup popup) {
                JList<?> list = popup.getList();
                JScrollPane scrollPane = (JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, list);
                if (scrollPane != null) {
                    scrollPane.getVerticalScrollBar().setUnitIncrement(30);
                }
            }
        });
    }
    
    public boolean insertarCategoria(Categoria c) throws SQLException{
        String sql = "INSERT INTO Categorias(nombre)"
                + "VALUES (?)";
        
        try(Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)){
                ps.setString(1, c.getNombre());
                return ps.executeUpdate() > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    public int obtenerProximoIdCategoria() {
        String sql = "SELECT IDENT_CURRENT('Categorias') + 1 AS proximoId";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("proximoId");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Error
    }
    
    public boolean existeCategoriaConNombre(String nombre) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Categorias WHERE LOWER(nombre) = LOWER(?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre.toLowerCase());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }
    
}
