/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.capibarashop.clases;

import com.capibarashop.clases.dao.CategoriaDAO;
import com.capibarashop.swing.ScrollBar;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Comparator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
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
    public static final String CARRITO_AGREGANDO_PRODUCTO = "/com/capibarashop/resources/capibaraSeleccionandoProducto.png";
    
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
    
    public boolean generarOptionYes_NoDisenoProducto(Container parent, Producto p, String titulo, String textoPane, int cantidad){
        String imagenURL = guardarImagenTemporal(p.getImagen(), p.getId());
        
        if(imagenURL == null){
            imagenURL = "";
        }

        String mensaje = String.format("""
            <html>
              <head>
                <style>
                  body { font-family: Arial, sans-serif; }
                        h3 { color: #5D3FD3; margin-top: 0; }
                        table {
                          width: 100%%;
                          border-collapse: collapse;
                          margin-top: 10px;
                        }
                        td {
                          padding: 5px;
                          border: 1px solid #ccc;
                          vertical-align: top;
                        }
                        td:first-child {
                          font-weight: bold;
                          background-color: #f0f0f0;
                          width: 100px;
                        }
                        .desc {
                          max-height: 100px;
                          overflow-y: auto;
                        }
                </style>
              </head>
              <body>
                <h3>%s</h3>
                <table>
                  <tr><td>Nombre</td><td>%s</td></tr>
                  <tr><td>Precio</td><td>$%.2f</td></tr>
                  <tr><td>Cantidad</td><td>%d</td></tr>
                  <tr><td>Descripción</td><td class="desc">%s</td></tr>
                  <tr><td>Imagen</td><td><img src="%s" width="100" height="100"/></td></tr>
                </table>
              </body>
            </html>
            """, 
                titulo,
                p.getNombre(),
                p.getPrecio().doubleValue(),
                cantidad,
                p.getDescripcion().replace("\n", "<br>"),
                imagenURL
            );
        
        JEditorPane editorPane = new JEditorPane("text/html", mensaje);
        editorPane.setEditable(false);
        editorPane.setCaretPosition(0);

        JScrollPane scrollPane = new JScrollPane(editorPane);
        scrollPane.setPreferredSize(new Dimension(450, 250));
        
        int confirmacion = JOptionPane.showConfirmDialog(parent, scrollPane, textoPane, JOptionPane.YES_NO_OPTION);
        
        return (confirmacion == JOptionPane.YES_OPTION);
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
                        scrollPane.getVerticalScrollBar().setUnitIncrement(30);
                    }
                }
            }
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {}
            public void popupMenuCanceled(PopupMenuEvent e) {}
        });
    }
    
    public String guardarImagenTemporal(byte[] datosImagen, int idProducto) {
        try {
            // Si no hay imagen proporcionada, carga la imagen por defecto desde recursos
            if (datosImagen == null) {
                InputStream is = getClass().getResourceAsStream(Utilidades.DEFAULT_PRODUCTO);
                if (is != null) {
                    datosImagen = is.readAllBytes();
                } else {
                    System.err.println("Imagen por defecto no encontrada.");
                    return null;
                }
            }

            // Crear directorio si no existe
            File tempDir = new File("temp/imagenes");
            if (!tempDir.exists()) tempDir.mkdirs();
            
            // Guardar archivo temporalmente
            File imagenFile = new File(tempDir, "producto_" + idProducto + ".png");
            Files.write(imagenFile.toPath(), datosImagen);
            return imagenFile.toURI().toURL().toString(); // Ruta accesible por HTML
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static <T> void cargarJComboBox(
        JComboBox<T> comboBox,
        List<T> elementos,
        T elementoInicial,
        Comparator<T> comparator,
        String mensajeInicial
    ) {
        comboBox.removeAllItems();

        if (mensajeInicial != null && elementoInicial != null) {
            comboBox.addItem(elementoInicial);
        }

        if (comparator != null) {
            elementos.sort(comparator);
        }

        for (T item : elementos) {
            comboBox.addItem(item);
        }

        // Aumentar velocidad del scroll
        SwingUtilities.invokeLater(() -> {
            Object comp = comboBox.getUI().getAccessibleChild(comboBox, 0);
            if (comp instanceof javax.swing.plaf.basic.ComboPopup popup) {
                JList<?> list = popup.getList();
                JScrollPane scrollPane = (JScrollPane) SwingUtilities.getAncestorOfClass(JScrollPane.class, list);
                if (scrollPane != null) {
                    scrollPane.getVerticalScrollBar().setUnitIncrement(30);
                }
            }
        });
    }
    
}
