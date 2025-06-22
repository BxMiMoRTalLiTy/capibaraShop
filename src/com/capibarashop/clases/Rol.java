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
    private int id;
    private String nombre;
    
    public Rol(){
     }
    
    public Rol(int id, String nombre_Rol) {
        this.id = id;
        this.nombre = nombre_Rol;
    }
    
    public int getId(){
        return id;
    }
    
     @Override
    public String toString() {
        return nombre;
    }
    
    public void setId(int id){
        this.id = id;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
}
