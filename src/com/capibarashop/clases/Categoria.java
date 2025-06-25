/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases;

/**
 *
 * @author Angel Aimar
 */
public class Categoria extends EntidadBase{

    public Categoria(){}
    
    public Categoria(int id, String nombre) {
        super(id, nombre);
    }
    
    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Categoria other = (Categoria) obj;
        return this.getId() == other.getId();
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(getId());
    }
    
}
