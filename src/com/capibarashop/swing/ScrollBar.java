/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.ScrollPane;
import javax.swing.BorderFactory;
import javax.swing.JScrollBar;

/**
 *
 * @author Angel Aimar
 */
public class ScrollBar extends JScrollBar{
    
    public ScrollBar(){
        setUI(new ModernScrollBarUI());
        setPreferredSize(new Dimension(15, 15));
        setBackground(new Color(242, 242, 242));
        
    }
    
}
