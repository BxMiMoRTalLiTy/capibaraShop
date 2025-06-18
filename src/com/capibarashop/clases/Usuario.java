/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases;

import java.sql.Date;

public class Usuario {
    private int id;
    private String usuarioNombre;
    private String contrasena;
    private int idRol;
    private String nombre;
    private String email;
    private Date fechaNacimiento;
    private String tel;
    
    private static Usuario usuarioActual;
    
    //usuarioNombre, nombre, contrasena, email, tel, id_rol
    //Constructor
    public Usuario(String usuarioNombre, String nombre, String contrasena, String email, Date fechaNacimiento, String tel, int idRol){
        this.usuarioNombre = usuarioNombre;
        this.nombre = nombre;
        this.contrasena = contrasena; 
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.tel = tel;
        this.idRol = idRol;
    }
    
    //Getter y Setters
    
    public int getId(){
        return id;
    }
    
    public String getUsuarioNombre(){
        return usuarioNombre;
    }
    
    public String getContrasena(){
        return contrasena;
    }
    
    public int getIdRol(){
        return idRol;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public String getEmail(){
        return email;
    }
    
    public Date getFechaNacimiento(){
        return fechaNacimiento;
    }
    
    public String getTel(){
        return tel;
    }
    
    public static Usuario getUsuarioActual(){
        return usuarioActual;
    }
    
    public void setId(int id){
        this.id=id;
    }
    
    public void setUsuarioNombre(String usuarioNombre){
        this.usuarioNombre = usuarioNombre;
    }
    
    public void setContrasena(String contrasena){
        this.contrasena = contrasena;
    }
    
    public void setIdRol(int idRol){
        this.idRol = idRol;
    }
    
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public void setFechaNacimiento(Date fechaNacimiento){
        this.fechaNacimiento = fechaNacimiento;
    }
    
    public void setTel(String tel){
        this.tel = tel;
    }
    
    public static void setUsuarioActual(Usuario u){
        usuarioActual = u;
    }
        
}
