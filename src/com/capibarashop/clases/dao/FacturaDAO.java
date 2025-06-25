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
import com.capibarashop.clases.DatosFacturacion;
import com.capibarashop.clases.TipoDireccion;
import com.capibarashop.clases.Direccion;
import java.sql.*;

public class FacturaDAO {
    public boolean insertar(Factura f) {
        String sql = "INSERT INTO Facturas (id_Venta, id_Facturacion, total) VALUES (?, ?, ?)";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, f.getIdVenta());
            ps.setInt(2, f.getDatosFacturacion().getId());
            ps.setBigDecimal(3, f.getTotal());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    f.setId(rs.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Factura obtenerPorVenta(int idVenta) {
        String sql = "SELECT f.id_Factura, f.total, f.fecha, df.id_Facturacion, df.rfc, df.razonSocial, df.id_Usuario, " +
                     "d.id_Direccion, d.calle, d.ciudad, d.estado, d.codigoPostal, d.pais, d.esPrincipal, td.id_Tipo, td.nombre AS tipoNombre " +
                     "FROM Facturas f " +
                     "JOIN DatosFacturacion df ON f.id_Facturacion = df.id_Facturacion " +
                     "JOIN Direcciones d ON df.id_Direccion = d.id_Direccion " +
                     "JOIN TiposDireccion td ON d.id_Tipo = td.id_Tipo " +
                     "WHERE f.id_Venta = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idVenta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                TipoDireccion tipo = new TipoDireccion(rs.getInt("id_Tipo"), rs.getString("tipoNombre"));
                Direccion direccion = new Direccion(
                        rs.getInt("id_Direccion"),
                        rs.getInt("id_Usuario"),
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
                        rs.getInt("id_Usuario"),
                        rs.getString("rfc"),
                        rs.getString("razonSocial"),
                        direccion
                );

                Factura f = new Factura(
                        rs.getInt("id_Factura"),
                        idVenta,
                        df,
                        rs.getBigDecimal("total"),
                        rs.getTimestamp("fecha").toLocalDateTime()
                );

                return f;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}