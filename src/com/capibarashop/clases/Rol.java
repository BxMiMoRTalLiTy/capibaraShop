/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases;

/**
 *
 * @author Angel Aimar
 */
public class Rol {
    private final int id;
    private final String nombre_Rol;
    
    public Rol(int id, String nombre_Rol) {
        this.id = id;
        this.nombre_Rol = nombre_Rol;
    }
    
    public int getId(){
        return id;
    }
    
     @Override
    public String toString() {
        return nombre_Rol;
    }
    
}
