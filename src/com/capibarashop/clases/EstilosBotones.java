/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author Angel Aimar
 */
public class EstilosBotones {
    public static void aplicarEstiloPrimario(JButton boton, int tamano) {
        boton.setBackground(new Color(66, 115, 185)); // Azul
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(new RoundedBorder(15));
        boton.setFont(new Font("STXinwei", Font.PLAIN, tamano));
    }

    public static void aplicarEstiloExito(JButton boton, int tamano) {
        boton.setBackground(new Color(40, 167, 69)); // Verde
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(new RoundedBorder(15));
        boton.setFont(new Font("STXinwei", Font.PLAIN, tamano));
    }

    public static void aplicarEstiloPeligro(JButton boton, int tamano) {
        boton.setBackground(new Color(220, 53, 69)); // Rojo
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(new RoundedBorder(15));
        boton.setFont(new Font("STXinwei", Font.PLAIN, tamano));
    }

    public static void aplicarEstiloClaro(JButton boton, int tamano) {
        boton.setBackground(new Color(255, 255, 255)); // Blanco
        boton.setForeground(new Color(66, 66, 66)); // Gris oscuro
        boton.setFocusPainted(false);
        boton.setBorder(new RoundedBorder(15));
        boton.setFont(new Font("STXinwei", Font.PLAIN, tamano));
    }
    
    public static void cambiarEstiloBotonDesactivado(JButton boton) {
        boton.setEnabled(false);
        boton.setOpaque(true);
        boton.setBackground(new Color(200, 200, 200)); // gris claro (desactivado)
        boton.setForeground(Color.DARK_GRAY); // texto más tenue
        boton.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }
    
    public static void aplicarEstiloInformativo(JButton boton, int fontSize) {
        boton.setBackground(new Color(59, 130, 246)); // Azul claro
        boton.setForeground(Color.WHITE);
        boton.setFont(new Font("SansSerif", Font.BOLD, fontSize));
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        boton.setFocusPainted(false);
    }
    
}
