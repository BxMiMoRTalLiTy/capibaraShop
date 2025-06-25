/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Angel Aimar
 */
public class Conexion {
    public static Connection getConexion(){
        String url = "jdbc:sqlserver://localhost:1433;"
                + "databaseName=DB_TiendaCapibaraOnline;"
                + "user=sa;"
                + "password=basedatos;"
                + "encrypt=true;"
                + "trustServerCertificate=true;"
                + "loginTimeout=30;";
        
        try{
            return DriverManager.getConnection(url);
        } catch(SQLException e){
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
