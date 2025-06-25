/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases.dao;

import com.capibarashop.clases.Conexion;
import com.capibarashop.clases.TipoDireccion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Angel Aimar
 */


public class TipoDireccionDAO {
    public List<TipoDireccion> obtenerTodos() {
        List<TipoDireccion> lista = new ArrayList<>();
        String sql = "SELECT id_Tipo, nombre FROM TiposDireccion";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                TipoDireccion tipo = new TipoDireccion(rs.getInt("id_Tipo"), rs.getString("nombre"));
                lista.add(tipo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
}
