/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases.dao;

import com.capibarashop.clases.Conexion;
import com.capibarashop.clases.MetodoPago;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Angel Aimar
 */
public class MetodosPagoDAO {
    
    
    
    public List<MetodoPago> obtenerTodos() {
        List<MetodoPago> lista = new ArrayList<>();
        String sql = "SELECT * FROM MetodosPago ORDER BY id_MetodoPago";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MetodoPago mp = new MetodoPago();
                mp.setId(rs.getInt("id_MetodoPago"));
                mp.setNombre(rs.getString("tipo"));
                mp.setDescripcion(rs.getString("descripcion"));
                lista.add(mp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public MetodoPago obtenerPorId(int id) {
        String sql = "SELECT * FROM MetodosPago WHERE id_MetodoPago = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                MetodoPago mp = new MetodoPago();
                mp.setId(rs.getInt("id_MetodoPago"));
                mp.setNombre(rs.getString("tipo"));
                mp.setDescripcion(rs.getString("descripcion"));
                return mp;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean insertar(MetodoPago mp) {
        String sql = "INSERT INTO MetodosPago (tipo, descripcion) VALUES (?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, mp.getNombre());
            ps.setString(2, mp.getDescripcion());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
