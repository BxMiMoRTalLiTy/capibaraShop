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
import com.capibarashop.clases.TipoDireccion;
import java.sql.*;
import java.util.*;

public class DireccionDAO {
    
    private Direccion construirDireccion (ResultSet rs) throws SQLException{
        TipoDireccion tipo = new TipoDireccion(rs.getInt("id_Tipo"), rs.getString("tipoNombre"));

        Direccion d = new Direccion(
            rs.getInt("id_Direccion"),
            rs.getInt("id_Usuario"),
            rs.getString("calle"),
            rs.getString("ciudad"),
            rs.getString("estado"),
            rs.getString("codigoPostal"),
            rs.getString("pais"),
            tipo,
            rs.getBoolean("esPrincipal"));
            
            return d;
    }
    
    public boolean insertar(Direccion d) {
        String sql = "INSERT INTO Direcciones (id_Usuario, calle, ciudad, estado, codigoPostal, pais, id_Tipo, esPrincipal) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexion.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, d.getIdUsuario());
            ps.setString(2, d.getCalle());
            ps.setString(3, d.getCiudad());
            ps.setString(4, d.getEstado());
            ps.setString(5, d.getCodigoPostal());
            ps.setString(6, d.getPais());
            ps.setInt(7, d.getTipo().getId());
            ps.setBoolean(8, d.getEsPrincipal());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    d.setId(rs.getInt(1));
                }
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Direccion> listarPorUsuario(int idUsuario) {
        List<Direccion> lista = new ArrayList<>();
        String sql = "SELECT d.*, td.id_Tipo, td.nombre AS tipoNombre FROM Direcciones d "
                   + "JOIN TiposDireccion td ON d.id_Tipo = td.id_Tipo WHERE d.id_Usuario = ?";

        try (Connection con = Conexion.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(construirDireccion(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Direccion obtenerDireccionPrincipal(int idUsuario) {
        String sql = "SELECT TOP 1 d.*, td.id_Tipo, td.nombre AS tipoNombre FROM Direcciones d "
                   + "JOIN TiposDireccion td ON d.id_Tipo = td.id_Tipo "
                   + "WHERE d.id_Usuario = ? AND esPrincipal = 1";

        try (Connection con = Conexion.getConexion(); 
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return construirDireccion(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
}