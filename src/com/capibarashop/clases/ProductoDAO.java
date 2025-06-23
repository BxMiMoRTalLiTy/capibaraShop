/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases;

import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Angel Aimar
 */
public class ProductoDAO {
    
    private Producto construirProducto (ResultSet rs) throws SQLException{
        Producto p = new Producto();
        
        p.setId(rs.getInt("id_Producto"));
        p.setNombre(rs.getString("nombre"));
        p.setPrecio(rs.getDouble("precio"));
        p.setDescripcion(rs.getString("descripcion"));
        p.setStock(rs.getInt("stock"));
        p.setImagen(rs.getBytes("imagen"));

        CategoriaDAO cDao = new CategoriaDAO();
        Categoria c = cDao.obtenerCategoria(rs.getInt("id_Categoria"));
        if (c == null) {
            throw new SQLException("Categoría no encontrada para ID: " + rs.getInt("id_Categoria"));
        }
        p.setCategoria(c);

        UsuarioDAO uDao = new UsuarioDAO();
        Usuario u = uDao.buscarUsuarioID(rs.getInt("id_Usuario"));
        if (u == null) {
            throw new SQLException("Usuario no encontrado para ID: " + rs.getInt("id_Usuario"));
        }
        p.setUsuario(u);
        
        return p;
    }
    
    public boolean insertarProducto(Producto p) throws SQLException{
        String sql = "INSERT INTO Productos(nombre, precio, descripcion, stock, imagen, id_Categoria, id_Usuario)"
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try(Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)){
                ps.setString(1, p.getNombre());
                ps.setDouble(2, p.getPrecio());
                ps.setString(3, p.getDescripcion());
                ps.setInt(4, p.getStock());
                ps.setBytes(5, p.getImagen());
                ps.setInt(6, p.getCategoria().getId());
                ps.setInt(7, p.getUsuario().getId());
                
                return ps.executeUpdate() > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
        
    }
    
    public Producto buscarProductoIDAdmin(int idProducto){
        String sql = """
                SELECT * FROM Productos
                WHERE id_Producto = ?
            """;
        
        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProducto);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return construirProducto(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        

        return null;
    }
    
    public Producto buscarProductoIDyUsuario(int idProducto, int idUsuario){
        String sql = """
                SELECT * FROM Productos
                WHERE id_Producto = ? AND id_Usuario = ?
            """;
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProducto);
            ps.setInt(2, idUsuario);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return construirProducto(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    
    //Para cualquier usuario
    public List<Producto> listarProductosPorUsuario() throws SQLException {
        List<Producto> lista = new ArrayList<>();
        
        boolean usuario = Usuario.getUsuarioActual().getRol().toString().equalsIgnoreCase("Administrador");
        String sql;
        
        if(usuario){
            sql = "SELECT * FROM Productos";
        }
        else{
            sql = """
                SELECT * FROM Productos
                WHERE id_Usuario = ?
            """;
        }

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            if(!usuario){
                ps.setInt(1, Usuario.getUsuarioActual().getId());
            }
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(construirProducto(rs));
            }
        }

        return lista;
    }
    
    //Para el ADMIN
//    public List<Producto> listarProductosAdmin() throws SQLException {
//        List<Producto> lista = new ArrayList<>();
//        String sql = "SELECT * FROM Productos";
//
//        try (Connection con = Conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
//
//            while (rs.next()) {
//                lista.add(construirProducto(rs));
//            }
//        }
//
//        return lista;
//    }
    
    public boolean eliminarProducto(int idProducto){
        String sql = "DELETE FROM Productos WHERE id_Producto = ?";

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idProducto);
            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas > 0;

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }
    
    public List<Producto> listarProductosPorCategoria(int idCategoria) throws SQLException {
        List<Producto> lista = new ArrayList<>();
        
        //boolean usuario = Usuario.getUsuarioActual().getRol().toString().equalsIgnoreCase("Administrador");
        String sql;
        
        //if(usuario){
            sql = """
                SELECT * FROM Productos
                WHERE id_Categoria = ?
            """;
        //}
//        else{
//            sql = """
//                SELECT * FROM Productos
//                WHERE id_Categoria = ? AND id_Usuario = ?
//            """;
//        }

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            
            ps.setInt(1, idCategoria);
            //if(!usuario){
                //ps.setInt(2, Usuario.getUsuarioActual().getId());
            //}
            
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(construirProducto(rs));
            }
        }
        
        return lista;
    }
    
    public Map<Categoria, Integer> contarProductosPorCategoria() throws SQLException {
        Map<Categoria, Integer> conteo = new LinkedHashMap<>();

        String sql = """
            SELECT c.id_Categoria, c.nombre, COUNT(p.id_Categoria) AS Total
            FROM Categorias c
            LEFT JOIN Productos p ON c.id_Categoria = p.Id_Categoria
            GROUP BY c.id_Categoria, c.nombre
            ORDER BY c.id_Categoria
        """;

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("id_Categoria"));
                categoria.setNombre(rs.getString("nombre"));

                int total = rs.getInt("Total");

                conteo.put(categoria, total);
            }
        }

        return conteo;
    }
    
//    public List<Producto> listarProductos() throws SQLException{
//        List<Producto> lista = new ArrayList<>();
//        String sql = "SELECT * FROM Productos";
//        
//        try(Connection con = Conexion.getConexion();
//                PreparedStatement insert = con.prepareStatement(sql);
//                ResultSet rs = insert.executeQuery()){
//            while(rs.next()){
//                lista.add(construirProducto(rs));
//            }
//        }catch(SQLException e){
//            e.printStackTrace();
//        }
//        return lista;
//    }
    
    public boolean actualizarProducto(Producto p){
        String sql = "UPDATE Productos SET nombre = ?, precio = ?, descripcion = ?, stock = ?, imagen = ?,"
                + "id_Categoria = ? WHERE id_Producto = ?";
        try(Connection con = Conexion.getConexion();
            PreparedStatement ps = con.prepareStatement(sql)){
                ps.setString(1, p.getNombre());
                ps.setDouble(2, p.getPrecio());
                ps.setString(3, p.getDescripcion());
                ps.setInt(4, p.getStock());
                ps.setBytes(5, p.getImagen());
                ps.setInt(6, p.getCategoria().getId());
                ps.setInt(7, p.getId());
                
                return ps.executeUpdate() > 0;
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    
    
}
