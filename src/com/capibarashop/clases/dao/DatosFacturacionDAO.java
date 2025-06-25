/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases.dao;

import com.capibarashop.clases.Conexion;
import com.capibarashop.clases.DatosFacturacion;
import com.capibarashop.clases.Direccion;
import com.capibarashop.clases.TipoDireccion;
import java.sql.*;
/**
 *
 * @author Angel Aimar
 */
public class DatosFacturacionDAO {
    public boolean insertar(DatosFacturacion df) {
        String sql = "INSERT INTO DatosFacturacion (id_Usuario, rfc, razonSocial, id_Direccion) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, df.getIdUsuario());
            ps.setString(2, df.getRfc());
            ps.setString(3, df.getRazonSocial());
            ps.setInt(4, df.getDireccion().getId());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    df.setId(rs.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public DatosFacturacion obtenerPorUsuario(int idUsuario) {
        String sql = "SELECT df.id_Facturacion, df.rfc, df.razonSocial, df.id_Direccion, td.id_Tipo, td.nombre AS tipoNombre, " +
                     "d.calle, d.ciudad, d.estado, d.codigoPostal, d.pais, d.esPrincipal " +
                     "FROM DatosFacturacion df " +
                     "JOIN Direcciones d ON df.id_Direccion = d.id_Direccion " +
                     "JOIN TiposDireccion td ON d.id_Tipo = td.id_Tipo " +
                     "WHERE df.id_Usuario = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                TipoDireccion tipo = new TipoDireccion(rs.getInt("id_Tipo"), rs.getString("tipoNombre"));

                Direccion direccion = new Direccion(
                    rs.getInt("id_Direccion"),
                    idUsuario,
                    rs.getString("calle"),
                    rs.getString("ciudad"),
                    rs.getString("estado"),
                    rs.getString("codigoPostal"),
                    rs.getString("pais"),
                    tipo,
                    rs.getBoolean("esPrincipal")
                );

                DatosFacturacion df = new DatosFacturacion(
                    rs.getInt("id_Facturacion"),
                    idUsuario,
                    rs.getString("rfc"),
                    rs.getString("razonSocial"),
                    direccion
                );

                return df;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
