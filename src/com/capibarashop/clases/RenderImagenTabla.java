/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases;

import java.awt.Component;
import java.awt.Image;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Angel Aimar
 */
public class RenderImagenTabla extends DefaultTableCellRenderer {
    private final int ancho;
    private final int alto;

    public RenderImagenTabla(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        if (value instanceof ImageIcon icon) {
            Image img = icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            JLabel label = new JLabel(new ImageIcon(img));
            label.setHorizontalAlignment(JLabel.CENTER);
            return label;
        }

        // Si no es una imagen, renderiza como celda vacía centrada
        JLabel label = new JLabel("Sin imagen");
        label.setHorizontalAlignment(JLabel.CENTER);
        return label;
    }
}
