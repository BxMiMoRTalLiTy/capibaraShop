/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases.dao;

/**
 *
 * @author Angel Aimar
 */
import com.capibarashop.clases.Conexion;
import com.capibarashop.clases.Factura;
import java.sql.*;

public class FacturaDAO {
    public boolean insertar(Factura f) {
        String sql = "INSERT INTO Facturas (id_Venta, fechaEmision, rfc, razonSocial, direccionFacturacion, total) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, f.getIdVenta());
            ps.setTimestamp(2, Timestamp.valueOf(f.getFechaEmision()));
            ps.setString(3, f.getRfc());
            ps.setString(4, f.getRazonSocial());
            ps.setString(5, f.getDireccionFacturacion());
            ps.setDouble(6, f.getTotal());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}