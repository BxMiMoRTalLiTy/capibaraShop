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
import com.capibarashop.clases.Direccion;
import java.sql.*;
import java.util.*;

public class DireccionDAO {
    public boolean insertar(Direccion d) {
        String sql = "INSERT INTO Direcciones (id_Usuario, calle, ciudad, estado, codigoPostal, pais, tipo, esPrincipal) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, d.getIdUsuario());
            ps.setString(2, d.getCalle());
            ps.setString(3, d.getCiudad());
            ps.setString(4, d.getEstado());
            ps.setString(5, d.getCodigoPostal());
            ps.setString(6, d.getPais());
            ps.setString(7, d.getTipo());
            ps.setBoolean(8, d.getEsPrincipal());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Direccion> listarPorUsuario(int idUsuario) {
        List<Direccion> lista = new ArrayList<>();
        String sql = "SELECT * FROM Direcciones WHERE id_Usuario = ?";
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Direccion d = new Direccion(
                    rs.getInt("id_Direccion"),
                    rs.getInt("id_Usuario"),
                    rs.getString("calle"),
                    rs.getString("ciudad"),
                    rs.getString("estado"),
                    rs.getString("codigoPostal"),
                    rs.getString("pais"),
                    rs.getString("tipo"),
                    rs.getBoolean("esPrincipal")
                );
                lista.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public boolean marcarComoPrincipal(int idDireccion, int idUsuario) {
        String sqlReset = "UPDATE Direcciones SET esPrincipal = 0 WHERE id_Usuario = ?";
        String sqlSet = "UPDATE Direcciones SET esPrincipal = 1 WHERE id_Direccion = ?";
        try (Connection con = Conexion.getConexion()) {
            try (PreparedStatement ps1 = con.prepareStatement(sqlReset)) {
                ps1.setInt(1, idUsuario);
                ps1.executeUpdate();
            }
            try (PreparedStatement ps2 = con.prepareStatement(sqlSet)) {
                ps2.setInt(1, idDireccion);
                ps2.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean tieneDireccionPrincipal(int idUsuario) {
        String sql = "SELECT COUNT(*) FROM Direcciones WHERE id_Usuario = ? AND esPrincipal = 1";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public boolean marcarTodasNoPrincipal(int idUsuario) {
        String sql = "UPDATE Direcciones SET esPrincipal = 0 WHERE id_Usuario = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public Direccion obtenerDireccionPrincipal(int idUsuario) {
        String sql = "SELECT TOP 1 * FROM Direcciones WHERE id_Usuario = ? AND esPrincipal = 1";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Direccion(
                    rs.getInt("id_Direccion"),
                    rs.getInt("id_Usuario"),
                    rs.getString("calle"),
                    rs.getString("ciudad"),
                    rs.getString("estado"),
                    rs.getString("codigoPostal"),
                    rs.getString("pais"),
                    rs.getString("tipo"),
                    rs.getBoolean("esPrincipal")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}