/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases;

import com.capibarashop.swing.ScrollBar;
import java.awt.Component;
import java.awt.Container;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

/**
 *
 * @author Angel Aimar
 */
public class Utilidades {
    /* Recursos empleados de manera más simplificada */
    
    public static final String FALTAN_CAMPOS = "/com/capibarashop/resources/capibaraFaltanCampos.png";
    public static final String ERROR_FALLO = "/com/capibarashop/resources/capibaraError.png";
    public static final String LOGO_CAPIBARA = "/com/capibarashop/resources/capibara.png";
    public static final String EXITO_PROCESO_GENERICO = "/com/capibarashop/resources/capibaraExito.png";
    public static final String PRODUCTO_NO_ENCONTRADO = "/com/capibarashop/resources/capibaraNoProductoEncontrado.png";
    public static final String PRODUCTO_ACTUALIZADO = "/com/capibarashop/resources/capibaraProductoActualizado.png";
    public static final String REGISTRO_CORRECTO = "/com/capibarashop/resources/capibaraRegistroCorrecto.png";
    public static final String ELIMINAR_PRODUCTO = "/com/capibarashop/resources/capibaraEliminateProduct.png";
    public static final String AGREGAR_PRODUCTO = "/com/capibarashop/resources/capibaraAddProduct.png";
    public static final String IMG_VENDEDOR = "/com/capibarashop/resources/ccapibaraVendedor.png";
    public static final String ACTUALIZAR_PRODUCTO = "/com/capibarashop/resources/capibaraActualizarProducto.png";
    public static final String IMG_REPARTIDOR = "/com/capibarashop/resources/capibaraRepartidor.png";
    public static final String INFORMACION_USUARIO = "/com/capibarashop/resources/capibaraUsuarioInformacion.png";
    public static final String USUARIO_CORREO_DUPLICADO = "/com/capibarashop/resources/capibaraUsuarioInformacionDuplicado.png";
    public static final String IMG_USUARIO = "/com/capibarashop/resources/capibaraCliente.png";
    public static final String DEFAULT_PRODUCTO = "/com/capibarashop/resources/defaultProducto.png";
    public static final String AGREGAR_DIRECCION = "/com/capibarashop/resources/capibaraAddDireccion.png";
    public static final String SELECCIONAR_DIRECCION = "/com/capibarashop/resources/capibaraSeleccionarDireccion.png";
    public static final String CATEGORIA_AGREGAR = "/com/capibarashop/resources/capibaraAddCategori.png";
    public static final String USUARIO_EDITOR_DESACTIVADO = "/com/capibarashop/resources/capibaraEdicionDesactivada.png";
    public static final String USUARIO_ACTUALIZADO = "/com/capibarashop/resources/capibaraUpdateUser.png";
    
    public static String generarHTMLProducto(Producto p) {
        return "<html>"
            + "<h3 style='color:#2a4a89;'>¿Deseas eliminar el siguiente producto?</h3>"
            + "<table cellpadding='4'>"
            + "<tr><td><b>ID:</b></td><td>" + p.getId() + "</td></tr>"
            + "<tr><td><b>Nombre:</b></td><td>" + p.getNombre() + "</td></tr>"
            + "<tr><td><b>Precio:</b></td><td>$" + String.format("%.2f", p.getPrecio()) + "</td></tr>"
            + "<tr><td><b>Stock:</b></td><td>" + p.getStock() + "</td></tr>"
            + "<tr><td><b>Descripción:</b></td><td>" + p.getDescripcion() + "</td></tr>"
            + "</table>"
            + "</html>";
    }
    
    public void generarMensajeGenerico(Container parent, String urlIcono, String tituloTexto, String texto, String tituloJOpcionPane, int tipo, int anchura, int altura){
        ImageIcon icono = escalarIconoConDPI(urlIcono, anchura, altura);
        String mensaje = String.format("""
            <html>
                <div style='text-align: center;'>
                    <h2 style='color: #2a4a89;'>%s</h2>
                    <p>%s</p>
                </div>
            </html>
            """, tituloTexto, texto);
        JOptionPane.showMessageDialog(parent, mensaje, tituloJOpcionPane, tipo, icono);
    }    
    
    public void generarJOptionPaneImagen(Container parent, String urlIcono, String tituloJOpcionPane, int tipo, int anchura, int altura){
        ImageIcon icono = escalarIconoConDPI(urlIcono, anchura, altura);
        JOptionPane.showMessageDialog(parent, "", tituloJOpcionPane, tipo, icono);
    }  
    
    public static ImageIcon escalarIconoConDPI(String path, int anchoBase, int altoBase) {
        try {
            ImageIcon original = new ImageIcon(Utilidades.class.getResource(path));

            // Detectar escala DPI
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            GraphicsConfiguration gc = gd.getDefaultConfiguration();
            AffineTransform at = gc.getDefaultTransform();
            double escala = at.getScaleX(); // Ej: 1.25 si está en 125%

            int ancho = (int)(anchoBase * escala);
            int alto = (int)(altoBase * escala);

            Image imagen = original.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
            return new ImageIcon(imagen);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo cargar la imagen: " + path, "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    
    public static void aplicarScrollCombo(JComboBox<?> combo) {
        combo.addPopupMenuListener(new PopupMenuListener() {
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                Object comp = combo.getUI().getAccessibleChild(combo, 0);
                if (comp instanceof JPopupMenu popup) {
                    Component scroll = popup.getComponent(0);
                    if (scroll instanceof JScrollPane scrollPane) {
                        scrollPane.setVerticalScrollBar(new ScrollBar());
                    }
                }
            }
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
            public void popupMenuCanceled(PopupMenuEvent e) {}
        });
    }
    
}
