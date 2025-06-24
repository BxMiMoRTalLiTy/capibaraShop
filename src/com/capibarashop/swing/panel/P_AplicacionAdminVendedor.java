/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.capibarashop.swing.panel;

import com.capibarashop.clases.Categoria;
import com.capibarashop.clases.dao.CategoriaDAO;
import com.capibarashop.clases.Producto;
import com.capibarashop.clases.dao.ProductoDAO;
import com.capibarashop.clases.Usuario;
import com.capibarashop.clases.Utilidades;
import com.capibarashop.clases.EstilosBotones;
import com.capibarashop.dialogs.categoria.DialogActualizarCategoriaBuscar;
import com.capibarashop.dialogs.producto.DialogActualizarProductoBuscar;
import com.capibarashop.dialogs.categoria.DialogAgregarCategoria;
import com.capibarashop.dialogs.producto.DialogAgregarProducto;
import com.capibarashop.dialogs.producto.DialogEliminarProducto;
import com.capibarashop.swing.ScrollBar;
import java.awt.Dimension;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

/**
 *
 * @author Angel Aimar
 */
public class P_AplicacionAdminVendedor extends javax.swing.JPanel {
    
    Utilidades u = new Utilidades();
    /**
     * Creates new form P_ProductosAdmin
     */
    public P_AplicacionAdminVendedor() throws SQLException{
        initComponents();
        ImageIcon icon = new ImageIcon(getClass().getResource("/com/capibarashop/resources/capibara.png"));
        Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // ← Ajusta tamaño aquí
        jCapibaraLogo.setIcon(new ImageIcon(img));
        
        Utilidades.aplicarScrollCombo(jCBFiltroCategorias);
                
        jPanel1.setPreferredSize(new Dimension(1100, 800));
        
        llenarComboBoxCategorias();
        cargarCategorias();
        
        cargarProductosDesdeInicio();
        if (!Usuario.getUsuarioActual().getRol().toString().equalsIgnoreCase("Administrador")) {
            jBAgregarCategoria.setVisible(false);
            jBModificarCategoria.setVisible(false);
        }
        
    }
    
    private void cargarCategorias() throws SQLException {
        ProductoDAO dao = new ProductoDAO();
        Map<Categoria, Integer> conteoMap = dao.contarProductosPorCategoria();
        mostrarCategoriasConProductos(new ArrayList<>(conteoMap.keySet()), conteoMap);
        
        
        // Aplicar estilos a los botones
        EstilosBotones.aplicarEstiloPrimario(jBAgregarCategoria, 24);
        EstilosBotones.aplicarEstiloClaro(jBModificarCategoria, 24);
        EstilosBotones.aplicarEstiloInformativo(jBBuscarCategoria, 24);
    }
    
    private void cargarListadoProductos() throws SQLException{
        ProductoDAO dao = new ProductoDAO();
        
        try {
            List<Producto> productos;
            productos = dao.listarProductosPorUsuario();
            if(Usuario.getUsuarioActual().getRol().toString().equalsIgnoreCase("Administrador")){
                listarMisProductosAdmin(productos, jTPaneProductos, jScrollPaneTablaProductos);
            }
            else{
               listarMisProductos(productos, jTPaneProductos, jScrollPaneTablaProductos);
            }
        } catch (SQLException e) {
            u.generarMensajeGenerico(this, Utilidades.ERROR_FALLO,
                    "¡Hubo un error inesperado!",
                    "Error al Cargar Productos: " + e.getMessage(),
                    "Error Inesperado", JOptionPane.ERROR_MESSAGE, 150, 150);
        }
    }
    
    private void cargarProductosDesdeInicio() throws SQLException{
        
        ProductoDAO dao = new ProductoDAO();

        List<Producto> productos;
        productos = dao.listarProductosPorUsuario();
        if(Usuario.getUsuarioActual().getRol().toString().equalsIgnoreCase("Administrador")){
            listarMisProductosAdmin(productos, jTPaneProductos, jScrollPaneTablaProductos);
        }
        else{
            listarMisProductos(productos, jTPaneProductos, jScrollPaneTablaProductos);
        }
        
        if (productos.isEmpty()) {
            mostrarMensajeSinProductos(); 
            jBActualizar.setEnabled(false);
            jBEliminar.setEnabled(false);
            
            EstilosBotones.cambiarEstiloBotonDesactivado(jBActualizar);
            EstilosBotones.cambiarEstiloBotonDesactivado(jBEliminar);
        } else {
            jBActualizar.setEnabled(true);
            jBEliminar.setEnabled(true);
            EstilosBotones.aplicarEstiloPeligro(jBEliminar, 24);
            EstilosBotones.aplicarEstiloExito(jBActualizar, 24);
        }
    }
    
    private void mostrarMensajeSinProductos() {
        jTPaneProductos.setEditorKit(new javax.swing.text.html.HTMLEditorKit());
        
        String html = """
                <html>
                    <body style='font-family:sans-serif; color:#444; text-align:center;'>
                        <h1 style='color:#7A59D4;'>¡Sin productos!</h1>
                        <p>No tienes ningún producto registrado.</p>
                        <p>Haz clic en <b>"Agregar Producto"</b> para añadir el primero.</p>
                    </body>
                </html>
            """;
        jTPaneProductos.setContentType("text/html");
        jTPaneProductos.setText(html);
        jTPaneProductos.setCaretPosition(0);
        jTPaneProductos.setPreferredSize(new Dimension(jScrollPaneTablaProductos.getWidth(), jTPaneProductos.getPreferredSize().height));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jInternalFrame1 = new javax.swing.JInternalFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jInternalFrame2 = new javax.swing.JInternalFrame();
        jPanel1 = new javax.swing.JPanel();
        jTPGeneral = new javax.swing.JTabbedPane();
        jPMostrarProductos = new javax.swing.JPanel();
        jScrollPaneTablaProductos = new javax.swing.JScrollPane();
        jTPaneProductos = new javax.swing.JTextPane();
        jPanel2 = new javax.swing.JPanel();
        jBActualizar = new javax.swing.JButton();
        jBAgregar = new javax.swing.JButton();
        jBEliminar = new javax.swing.JButton();
        jPMostrarCategorias = new javax.swing.JPanel();
        jScrollPaneTablaCategorias = new javax.swing.JScrollPane();
        jTPaneCategorias = new javax.swing.JTextPane();
        jPanel3 = new javax.swing.JPanel();
        jBModificarCategoria = new javax.swing.JButton();
        jBAgregarCategoria = new javax.swing.JButton();
        jBBuscarCategoria = new javax.swing.JButton();
        jCBFiltroCategorias = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jCapibaraLogo = new javax.swing.JLabel();
        jLUsuario = new javax.swing.JLabel();

        jInternalFrame1.setVisible(true);

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jInternalFrame2.setVisible(true);

        javax.swing.GroupLayout jInternalFrame2Layout = new javax.swing.GroupLayout(jInternalFrame2.getContentPane());
        jInternalFrame2.getContentPane().setLayout(jInternalFrame2Layout);
        jInternalFrame2Layout.setHorizontalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame2Layout.setVerticalGroup(
            jInternalFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.setBackground(new java.awt.Color(230, 230, 250));
        jPanel1.setForeground(new java.awt.Color(204, 90, 234));

        jPMostrarProductos.setMaximumSize(new java.awt.Dimension(890, 600));
        jPMostrarProductos.setPreferredSize(new java.awt.Dimension(890, 600));

        jScrollPaneTablaProductos.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jTPaneProductos.setBackground(new java.awt.Color(195, 136, 212));
        jScrollPaneTablaProductos.setViewportView(jTPaneProductos);
        jTPaneProductos.setEditable(false);

        jPanel2.setBackground(new java.awt.Color(255, 245, 225));

        jBActualizar.setFont(new java.awt.Font("STXinwei", 0, 24)); // NOI18N
        jBActualizar.setText("Actualizar Un Producto");
        jBActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBActualizarActionPerformed(evt);
            }
        });

        jBAgregar.setFont(new java.awt.Font("STXinwei", 0, 24)); // NOI18N
        jBAgregar.setText("Agregar Producto");
        jBAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAgregarActionPerformed(evt);
            }
        });

        jBEliminar.setFont(new java.awt.Font("STXinwei", 0, 24)); // NOI18N
        jBEliminar.setText("Eliminar Producto");
        jBEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBEliminarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBAgregar)
                .addGap(72, 72, 72)
                .addComponent(jBEliminar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                .addComponent(jBActualizar)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        EstilosBotones.aplicarEstiloExito(jBActualizar, 24);
        EstilosBotones.aplicarEstiloPrimario(jBAgregar, 24);
        EstilosBotones.aplicarEstiloPeligro(jBEliminar, 24);

        javax.swing.GroupLayout jPMostrarProductosLayout = new javax.swing.GroupLayout(jPMostrarProductos);
        jPMostrarProductos.setLayout(jPMostrarProductosLayout);
        jPMostrarProductosLayout.setHorizontalGroup(
            jPMostrarProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneTablaProductos)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPMostrarProductosLayout.setVerticalGroup(
            jPMostrarProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPMostrarProductosLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneTablaProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 24, Short.MAX_VALUE))
        );

        jScrollPaneTablaProductos.setVerticalScrollBar(new ScrollBar());
        jScrollPaneTablaProductos.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jScrollPaneTablaProductos.setViewportBorder(null);
        jScrollPaneTablaProductos.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jScrollPaneTablaProductos.getVerticalScrollBar().setUnitIncrement(20);

        jTPGeneral.addTab("Mis Productos", jPMostrarProductos);

        jPMostrarCategorias.setMaximumSize(new java.awt.Dimension(890, 600));
        jPMostrarCategorias.setPreferredSize(new java.awt.Dimension(890, 600));

        jScrollPaneTablaCategorias.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jTPaneCategorias.setBackground(new java.awt.Color(195, 136, 212));
        jScrollPaneTablaCategorias.setViewportView(jTPaneCategorias);
        jTPaneProductos.setEditable(false);

        jPanel3.setBackground(new java.awt.Color(255, 245, 225));

        jBModificarCategoria.setFont(new java.awt.Font("STXinwei", 0, 24)); // NOI18N
        jBModificarCategoria.setText("Modificar Categoria");
        jBModificarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBModificarCategoriaActionPerformed(evt);
            }
        });

        jBAgregarCategoria.setFont(new java.awt.Font("STXinwei", 0, 24)); // NOI18N
        jBAgregarCategoria.setText("Agregar Categoria");
        jBAgregarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBAgregarCategoriaActionPerformed(evt);
            }
        });

        jBBuscarCategoria.setFont(new java.awt.Font("STXinwei", 0, 24)); // NOI18N
        jBBuscarCategoria.setText("Buscar");
        jBBuscarCategoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBBuscarCategoriaActionPerformed(evt);
            }
        });

        jCBFiltroCategorias.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        jCBFiltroCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCBFiltroCategoriasActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("STXinwei", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Filtro:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jBAgregarCategoria)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBModificarCategoria)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCBFiltroCategorias, 0, 206, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jBBuscarCategoria)
                .addGap(23, 23, 23))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBAgregarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBBuscarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jBModificarCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCBFiltroCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        EstilosBotones.aplicarEstiloExito(jBActualizar, 24);
        EstilosBotones.aplicarEstiloPrimario(jBAgregar, 24);
        EstilosBotones.aplicarEstiloPeligro(jBEliminar, 24);

        javax.swing.GroupLayout jPMostrarCategoriasLayout = new javax.swing.GroupLayout(jPMostrarCategorias);
        jPMostrarCategorias.setLayout(jPMostrarCategoriasLayout);
        jPMostrarCategoriasLayout.setHorizontalGroup(
            jPMostrarCategoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPaneTablaCategorias)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPMostrarCategoriasLayout.setVerticalGroup(
            jPMostrarCategoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPMostrarCategoriasLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneTablaCategorias, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 24, Short.MAX_VALUE))
        );

        jScrollPaneTablaCategorias.setVerticalScrollBar(new ScrollBar());
        jScrollPaneTablaCategorias.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jScrollPaneTablaCategorias.setViewportBorder(null);
        jScrollPaneTablaCategorias.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        jScrollPaneTablaCategorias.getVerticalScrollBar().setUnitIncrement(20);

        jTPGeneral.addTab("Categorias", jPMostrarCategorias);

        jLUsuario.setFont(new java.awt.Font("STXinwei", 0, 18)); // NOI18N
        jLUsuario.setForeground(new java.awt.Color(0, 0, 0));
        jLUsuario.setText("Bienvenido Usuario");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTPGeneral)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jCapibaraLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLUsuario))
                    .addComponent(jCapibaraLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jTPGeneral))
        );

        jLUsuario.setText("Bienvenido "+ Usuario.getUsuarioActual().getUsuarioNombre());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jBActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBActualizarActionPerformed
        DialogActualizarProductoBuscar dialog = new DialogActualizarProductoBuscar(null, true);
        dialog.setVisible(true);
        ProductoDAO dao = new ProductoDAO();
        
        try {
            List<Producto> productos;
            productos = dao.listarProductosPorUsuario();
            if(Usuario.getUsuarioActual().getRol().toString().equalsIgnoreCase("Administrador")){
                listarMisProductosAdmin(productos, jTPaneProductos, jScrollPaneTablaProductos);
            }
            else{
               listarMisProductos(productos, jTPaneProductos, jScrollPaneTablaProductos);
            }
        } catch (SQLException e) {
            u.generarMensajeGenerico(this, Utilidades.ERROR_FALLO,
                    "¡Hubo un error inesperado!",
                    "Error al Cargar Productos: " + e.getMessage(),
                    "Error Inesperado", JOptionPane.ERROR_MESSAGE, 150, 150);
        }
    }//GEN-LAST:event_jBActualizarActionPerformed

    private void jBAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAgregarActionPerformed
        // TODO add your handling code here:
        DialogAgregarProducto dialog = new DialogAgregarProducto(null, true);
        dialog.setVisible(true);
        jBActualizar.setEnabled(true);
        jBEliminar.setEnabled(true);
        EstilosBotones.aplicarEstiloPeligro(jBEliminar, 24);
        EstilosBotones.aplicarEstiloExito(jBActualizar, 24);
        
        
        ProductoDAO dao = new ProductoDAO();
                    
        try {
            List<Producto> productos;
            productos = dao.listarProductosPorUsuario();
            if(Usuario.getUsuarioActual().getRol().toString().equalsIgnoreCase("Administrador")){
                listarMisProductosAdmin(productos, jTPaneProductos, jScrollPaneTablaProductos);
            }
            else{
               listarMisProductos(productos, jTPaneProductos, jScrollPaneTablaProductos);
            }
        } catch (SQLException e) {
            u.generarMensajeGenerico(this, Utilidades.ERROR_FALLO,
                    "¡Hubo un error inesperado!",
                    "Error al Cargar Productos: " + e.getMessage(),
                    "Error Inesperado", JOptionPane.ERROR_MESSAGE, 150, 150);
        }
    }//GEN-LAST:event_jBAgregarActionPerformed

    private void jBEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBEliminarActionPerformed
        // TODO add your handling code here:
        
        DialogEliminarProducto dialog = new DialogEliminarProducto(null, true);
        dialog.setVisible(true);
        
        try {
            cargarListadoProductos();
        } catch (SQLException ex) {
            System.getLogger(P_AplicacionAdminVendedor.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }//GEN-LAST:event_jBEliminarActionPerformed

    private void jBModificarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBModificarCategoriaActionPerformed
        // TODO add your handling code here:
        DialogActualizarCategoriaBuscar dialog = new DialogActualizarCategoriaBuscar(null, true);
        dialog.setVisible(true);
        
        try {
            llenarComboBoxCategorias();
            cargarCategorias();
        } catch (SQLException ex) {
            System.getLogger(P_AplicacionAdminVendedor.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }//GEN-LAST:event_jBModificarCategoriaActionPerformed

    private void jBAgregarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBAgregarCategoriaActionPerformed
        // TODO add your handling code here:
        DialogAgregarCategoria dialog = new DialogAgregarCategoria(null, true);
        dialog.setVisible(true);
        
        try {
            llenarComboBoxCategorias();
            cargarCategorias();
        } catch (SQLException ex) {
            System.getLogger(P_AplicacionAdminVendedor.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        
    }//GEN-LAST:event_jBAgregarCategoriaActionPerformed

    private void jBBuscarCategoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBBuscarCategoriaActionPerformed
        // TODO add your handling code here:
        Categoria seleccionada = (Categoria) jCBFiltroCategorias.getSelectedItem();
        if (seleccionada.getId() != 0) {
            try {
                filtrarProductosPorCategoria(seleccionada);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        else if(seleccionada.getId() == 0){
            try {
                cargarCategorias();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_jBBuscarCategoriaActionPerformed

    private void jCBFiltroCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCBFiltroCategoriasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCBFiltroCategoriasActionPerformed
    
    private void filtrarProductosPorCategoria(Categoria categoria) throws SQLException {
        ProductoDAO dao = new ProductoDAO();
        List<Producto> productos = dao.listarProductosPorCategoria(categoria.getId());

        if (productos.isEmpty()) {
            String html = String.format("""
                    <html>
                        <body style='font-family:sans-serif; color:#444; text-align:center; background-color:#E6E6FA;'>
                            <h1 style='color:#7A59D4;'>¡Sin productos en esta categoría!</h1>
                            <p>No hay productos registrados aún en <b>%s</b>.</p>
                            <p>¿Quieres ser el primero en agregar uno?</p>
                        </body>
                    </html>
                """, categoria.getNombre());
            jTPaneCategorias.setContentType("text/html");
            jTPaneCategorias.setText(html);
            jTPaneCategorias.setCaretPosition(0);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("""
                <html>
                <head>
                    <style>
                        body { font-family: Arial, sans-serif; background-color: #E6E6FA; }
                        h1 { text-align: center; color: #2A4A89; }
                        table {
                            width: 100%;
                            border-collapse: collapse;
                            background-color: #F8F8FF;
                            margin-top: 10px;
                        }
                        th, td {
                            border: 1px solid #CCCCCC;
                            padding: 5px;
                            word-wrap: break-word;
                            text-align: left;
                        }
                        th {
                            background-color: #6C63FF;
                            color: white;
                        }
                    </style>
                </head>
                <body>
            """);

            sb.append("<h1>")
              .append(categoria.getNombre())
              .append(" - Productos: ")
              .append(productos.size())
              .append("</h1>");

            sb.append("""
                <table>
                    <tr>
                        <th>ID</th>
                        <th>Nombre</th>
                        <th>Precio</th>
                        <th>Stock</th>
                        <th>Descripción</th>
                        <th>Imagen</th>
                        <th>Nombre Usuario</th>
                    </tr>
            """);

            for (Producto p : productos) {
                sb.append("<tr>");
                sb.append("<td>").append(p.getId()).append("</td>");
                sb.append("<td>").append(p.getNombre()).append("</td>");
                sb.append("<td>").append(p.getPrecio()).append("</td>");
                sb.append("<td>").append(p.getStock()).append("</td>");
                sb.append("<td>").append(p.getDescripcion()).append("</td>");

                String imgUrl = guardarImagenTemporal(p.getImagen(), p.getId());
                if (imgUrl != null) {
                    sb.append("<td><img src='").append(imgUrl).append("' width='80' height='80'/></td>");
                } else {
                    sb.append("<td>Sin imagen</td>");
                }
                
                sb.append("<td>").append(p.getUsuario().getUsuarioNombre()).append("</td>");
                
                sb.append("</tr>");
            }

            sb.append("</table></body></html>");

            jTPaneCategorias.setContentType("text/html");
            jTPaneCategorias.setText(sb.toString());
            jTPaneCategorias.setCaretPosition(0);
            jTPaneCategorias.setPreferredSize(new Dimension(jScrollPaneTablaCategorias.getWidth(), jTPaneCategorias.getPreferredSize().height));

            jBActualizar.setEnabled(true);
            jBEliminar.setEnabled(true);
        }
    }
    
    public void listarMisProductos(List<Producto> productos, JTextPane textPane, JScrollPane jScroll){
        StringBuilder sb = new StringBuilder();
        textPane.setEditorKit(new javax.swing.text.html.HTMLEditorKit());
        sb.append("""
                  <html>
                        <head>
                          <style>
                            body {
                                    background-color: #E6E6FA;
                                    color: #333333;
                                    font-family: Arial, sans-serif;
                                  }
                            
                                  h1 {
                                    text-align: center;
                                    color: #2A4A89;
                                  }
                            
                                  table {
                                    width: 100%;
                                    border-collapse: collapse;
                                    background-color: #F8F8FF;
                                  }
                            
                                  th, td {
                                    border: 1px solid #CCCCCC;
                                    padding: 5px;
                                    max-width: 200px;
                                    word-wrap: break-word;
                                    white-space: normal;
                                    overflow-wrap: break-word;
                                    word-break: break-all;
                                    text-align: left;
                                    vertical-align: top;
                                  }
                            
                                  th {
                                    background-color: #2A4A89;
                                    color: white;
                                  }
                            
                                  .desc {
                                    max-height: 60px;
                                    overflow-y: auto;
                                    overflow-x: hidden;
                                    word-wrap: break-word;
                                    white-space: normal;
                                  }
                          </style>
                        </head>
                    <body>""");
        
                    sb.append("<h1 style='color:#2A4A89; text-align:center;'>Mis Productos: ")
                      .append(productos.size())
                      .append("</h1>");
                    sb.append("""
                    <table>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Precio</th>
                            <th>Stock</th>
                            <th>Descripción</th>
                            <th>Categoría</th>
                            <th>Imagen</th>
                        </tr>
                    """);
        
        for(Producto p : productos){
            sb.append("<tr>");
            sb.append("<td>").append(p.getId()).append("</td>");
            sb.append("<td>").append(p.getNombre()).append("</td>");
            sb.append("<td>").append(p.getPrecio()).append("</td>");
            sb.append("<td>").append(p.getStock()).append("</td>");
            sb.append("<td> <div class=\"desc\">").append(p.getDescripcion()).append(" </div> </td>");
            String nombreCategoria = (p.getCategoria() != null) ? p.getCategoria().toString() : "Sin categoría";
            sb.append("<td>").append(nombreCategoria).append("</td>");
            
            String imagenURL = guardarImagenTemporal(p.getImagen(), p.getId());
            if (imagenURL != null) {
                sb.append("<td><img src='").append(imagenURL).append("' width='80' height='80'/></td>");
            } else {
                sb.append("<td>Sin imagen</td>");
            }
            
            sb.append("</tr>");
        }
        
        sb.append("""
                </table>
            </body>
        </html>
        """);
        
        textPane.setContentType("text/html");
        textPane.setText(sb.toString());
        textPane.setCaretPosition(0); // Regresa al inicio visual
        textPane.setPreferredSize(new Dimension(jScroll.getWidth(), textPane.getPreferredSize().height));
    }
    
    public void listarMisProductosAdmin(List<Producto> productos, JTextPane textPane, JScrollPane jScroll){
        StringBuilder sb = new StringBuilder();
        jTPaneProductos.setEditorKit(new javax.swing.text.html.HTMLEditorKit());
        sb.append("""
                  <html>
                        <head>
                          <style>
                            body {
                                    background-color: #E6E6FA;
                                    color: #333333;
                                    font-family: Arial, sans-serif;
                                  }
                            
                                  h1 {
                                    text-align: center;
                                    color: #2A4A89;
                                  }
                            
                                  table {
                                    width: 100%;
                                    border-collapse: collapse;
                                    background-color: #F8F8FF;
                                  }
                            
                                  th, td {
                                    border: 1px solid #CCCCCC;
                                    padding: 5px;
                                    max-width: 200px;
                                    word-wrap: break-word;
                                    white-space: normal;
                                    overflow-wrap: break-word;
                                    word-break: break-all;
                                    text-align: left;
                                    vertical-align: top;
                                  }
                            
                                  th {
                                    background-color: #2A4A89;
                                    color: white;
                                  }
                            
                                  .desc {
                                    max-height: 60px;
                                    overflow-y: auto;
                                    overflow-x: hidden;
                                    word-wrap: break-word;
                                    white-space: normal;
                                  }
                          </style>
                        </head>
                    <body>""");
        
                    sb.append("<h1 style='color:#2A4A89; text-align:center;'>Productos en total: ")
                      .append(productos.size())
                      .append("</h1>");
                    sb.append("""
                    <table>
                        <tr>
                            <th>ID</th>
                            <th>Nombre</th>
                            <th>Precio</th>
                            <th>Stock</th>
                            <th>Descripción</th>
                            <th>Categoría</th>
                            <th>Imagen</th>
                            <th>ID Usuario</th>
                        </tr>
                    """);
        
        for(Producto p : productos){
            sb.append("<tr>");
            sb.append("<td>").append(p.getId()).append("</td>");
            sb.append("<td>").append(p.getNombre()).append("</td>");
            sb.append("<td>").append(p.getPrecio()).append("</td>");
            sb.append("<td>").append(p.getStock()).append("</td>");
            sb.append("<td> <div class=\"desc\">").append(p.getDescripcion()).append(" </div> </td>");
            String nombreCategoria = (p.getCategoria() != null) ? p.getCategoria().toString() : "Sin categoría";
            sb.append("<td>").append(nombreCategoria).append("</td>");
            
            String imagenURL = guardarImagenTemporal(p.getImagen(), p.getId());
            if (imagenURL != null) {
                sb.append("<td><img src='").append(imagenURL).append("' width='80' height='80'/></td>");
            } else {
                sb.append("<td>Sin imagen</td>");
            }
            sb.append("<td>").append(p.getUsuario().getId()).append("</td>");
            
            sb.append("</tr>");
        }
        
        sb.append("""
                </table>
            </body>
        </html>
        """);
        
        jTPaneProductos.setContentType("text/html");
        jTPaneProductos.setText(sb.toString());
        jTPaneProductos.setCaretPosition(0); // Regresa al inicio visual
        jTPaneProductos.setPreferredSize(new Dimension(jScrollPaneTablaProductos.getWidth(), jTPaneProductos.getPreferredSize().height));
    }
    
    public void mostrarCategoriasConProductos(List<Categoria> categorias, Map<Categoria, Integer> conteoProductosPorCategoria) {
        jTPaneCategorias.setEditorKit(new javax.swing.text.html.HTMLEditorKit());

        StringBuilder sb = new StringBuilder();
        sb.append("""
            <html>
            <head>
                <style>
                    body { font-family: Arial, sans-serif; background-color: #E6E6FA; }
                    .cat {
                        border: 1px solid #ccc;
                        padding: 10px;
                        margin: 10px;
                        border-radius: 8px;
                        background-color: #FAFAFA;
                    }
                    .nombre {
                        font-size: 20px;
                        font-weight: bold;
                        color: #5D3FD3;
                    }
                    .info {
                        font-size: 14px;
                        color: #333;
                    }
                </style>
            </head>
            <body>
            <h1 style='text-align:center;'>Categorías</h1>
        """);

        if (categorias.isEmpty()) {
            sb.append("<p style='text-align:center;'>No se encontraron categorías.</p>");
        } else {
            for (Categoria c : categorias) {
                int total = conteoProductosPorCategoria.getOrDefault(c, 0);
                sb.append("<div class='cat'>")
                  .append("<div class='nombre'>").append(c.getNombre()).append("</div>")
                  .append("<div class='info'>Productos: ").append(total > 0 ? total : "Sin productos").append("</div>")
                  .append("</div>");
            }
        }

        sb.append("</body></html>");
        jTPaneCategorias.setText(sb.toString());
        jTPaneCategorias.setCaretPosition(0);
    }
    
    private void llenarComboBoxCategorias() throws SQLException {
        
        jCBFiltroCategorias.removeAllItems();
        jCBFiltroCategorias.addItem(new Categoria(0, "Todas las Categorías"));
        
        CategoriaDAO.cargarCategorias(jCBFiltroCategorias);
    }
   
    private String guardarImagenTemporal(byte[] datosImagen, int idProducto) {
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
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBActualizar;
    private javax.swing.JButton jBAgregar;
    private javax.swing.JButton jBAgregarCategoria;
    private javax.swing.JButton jBBuscarCategoria;
    private javax.swing.JButton jBEliminar;
    private javax.swing.JButton jBModificarCategoria;
    private javax.swing.JComboBox<Categoria> jCBFiltroCategorias;
    private javax.swing.JLabel jCapibaraLogo;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JInternalFrame jInternalFrame2;
    private javax.swing.JLabel jLUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPMostrarCategorias;
    private javax.swing.JPanel jPMostrarProductos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneTablaCategorias;
    private javax.swing.JScrollPane jScrollPaneTablaProductos;
    private javax.swing.JTabbedPane jTPGeneral;
    private javax.swing.JTextPane jTPaneCategorias;
    private javax.swing.JTextPane jTPaneProductos;
    // End of variables declaration//GEN-END:variables


}
