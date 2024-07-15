/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Views;

import Controller.ArbolBinarioImpl;
import Controller.ControllerAcrededores;
import Controller.ImplListaEnlazada;
import Interfaces.IControllerAcrededores;
import Models.Acrededores;
import Models.Nodo;
import Models.TreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Andre H
 */
public class Consulta_Acreedores extends javax.swing.JPanel {

    /**
     * Creates new form Consulta_Acreedores
     */
    IControllerAcrededores controllerAcrededores = new ControllerAcrededores();
    private ImplListaEnlazada<Acrededores> listaAcrededoresFiltrada;
    private ImplListaEnlazada<Acrededores> listaExportar;
    private ArbolBinarioImpl<Acrededores> arbolPorRUC;

    public Consulta_Acreedores() {
        initComponents();
        cargarDepartamentos();
        agregarListeners();
        
        // Inicializar listaAcrededoresFiltrada con datos
        listaAcrededoresFiltrada = controllerAcrededores.buscar("RUC", ""); // O algún criterio para obtener la lista inicial
        arbolPorRUC = generarArbolDesdeListaPorRUC(listaAcrededoresFiltrada);

        selectDepartamento.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String departamento = (String) selectDepartamento.getSelectedItem();
                cargarProvincias(departamento);
                cargarPliegos(departamento, null, null);
                cargarEjecutoras(departamento, null, null);
            }
        });

        selectProvincia.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String departamento = (String) selectDepartamento.getSelectedItem();
                String provincia = (String) selectProvincia.getSelectedItem();
                cargarDistritos(provincia);
                cargarPliegos(departamento, provincia, null);
                cargarEjecutoras(departamento, provincia, null);
            }
        });

        selectDistrito.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String departamento = (String) selectDepartamento.getSelectedItem();
                String provincia = (String) selectProvincia.getSelectedItem();
                String distrito = (String) selectDistrito.getSelectedItem();
                cargarPliegos(departamento, provincia, distrito);
                cargarEjecutoras(departamento, provincia, distrito);
            }
        });
    }

    private void cargarDepartamentos() {
        ImplListaEnlazada<String> departamentos = controllerAcrededores.obtenerDepartamentos();
        Nodo<String> actual = departamentos.getCabeza();
        selectDepartamento.removeAllItems();
        selectDepartamento.addItem("Seleccionar");
        while (actual != null) {
            selectDepartamento.addItem(actual.getDato());
            actual = actual.getSiguiente();
        }
        selectDepartamento.setSelectedIndex(0); // Selecciona la opción "Seleccionar"
    }

    private void cargarProvincias(String departamento) {
        ImplListaEnlazada<String> provincias = controllerAcrededores.obtenerProvinciasPorDepartamento(departamento);
        Nodo<String> actual = provincias.getCabeza();
        selectProvincia.removeAllItems();
        selectProvincia.addItem("Seleccionar");
        while (actual != null) {
            selectProvincia.addItem(actual.getDato());
            actual = actual.getSiguiente();
        }
        selectProvincia.setSelectedIndex(0); // Selecciona la opción "Seleccionar"
    }

    private void cargarDistritos(String provincia) {
        ImplListaEnlazada<String> distritos = controllerAcrededores.obtenerDistritosPorProvincia(provincia);
        Nodo<String> actual = distritos.getCabeza();
        selectDistrito.removeAllItems();
        selectDistrito.addItem("Seleccionar");
        while (actual != null) {
            selectDistrito.addItem(actual.getDato());
            actual = actual.getSiguiente();
        }
        selectDistrito.setSelectedIndex(0); // Selecciona la opción "Seleccionar"
    }

    private void cargarPliegos(String departamento, String provincia, String distrito) {
        ImplListaEnlazada<String> pliegos = controllerAcrededores.obtenerPliegos(departamento, provincia, distrito);
        Nodo<String> actual = pliegos.getCabeza();
        selectPliego.removeAllItems();
        selectPliego.addItem("Seleccionar");
        while (actual != null) {
            selectPliego.addItem(actual.getDato());
            actual = actual.getSiguiente();
        }
        selectPliego.setSelectedIndex(0); // Selecciona la opción "Seleccionar"
    }

    private void cargarEjecutoras(String departamento, String provincia, String distrito) {
        ImplListaEnlazada<String> ejecutoras = controllerAcrededores.obtenerEjecutoras(departamento, provincia, distrito);
        Nodo<String> actual = ejecutoras.getCabeza();
        selectEjecutora.removeAllItems();
        selectEjecutora.addItem("Seleccionar");
        while (actual != null) {
            selectEjecutora.addItem(actual.getDato());
            actual = actual.getSiguiente();
        }
        selectEjecutora.setSelectedIndex(0); // Selecciona la opción "Seleccionar"
    }
    
    private ArbolBinarioImpl<Acrededores> generarArbolDesdeListaPorRUC(ImplListaEnlazada<Acrededores> lista) {
        return controllerAcrededores.generarArbolDesdeListaPorRUC(lista);
        }

    private ImplListaEnlazada<Acrededores> buscarEnArbolPorRUC(ArbolBinarioImpl<Acrededores> arbol, String ruc) {
        ImplListaEnlazada<Acrededores> resultados = new ImplListaEnlazada<>();
        buscarEnNodo(arbol.getRaiz(), ruc, resultados);
        return resultados;
    }
    private void buscarEnNodo(TreeNode<Acrededores> nodo, String rucParcial, ImplListaEnlazada<Acrededores> resultados) {
        if (nodo == null) return;

        Acrededores acredor = nodo.getValor();
        if (acredor.getRuc().contains(rucParcial)) {
            resultados.insertar(acredor, resultados.getTamaño());
        }

        buscarEnNodo(nodo.getHojaIzquierda(), rucParcial, resultados);
        buscarEnNodo(nodo.getHojaDerecha(), rucParcial, resultados);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txtRUC = new javax.swing.JTextField();
        txtRazonSocial = new javax.swing.JTextField();
        txtPliego = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaResultados = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnBuscar = new javax.swing.JButton();
        btnExportarPDF = new javax.swing.JButton();
        selectDepartamento = new javax.swing.JComboBox<>();
        selectProvincia = new javax.swing.JComboBox<>();
        selectDistrito = new javax.swing.JComboBox<>();
        selectPliego = new javax.swing.JComboBox<>();
        selectEjecutora = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        btnExportarCSV = new javax.swing.JButton();
        btnActualizarTabla = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtRUC.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtRUC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRUCActionPerformed(evt);
            }
        });

        tablaResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "RUC", "RAZON SOCIAL", "DEPARTAMENTO", "PROVINCIA", "DISTRITO"
            }
        ));
        jScrollPane1.setViewportView(tablaResultados);

        jLabel1.setText("RUC");

        jLabel2.setText("Razón Social");

        jLabel3.setText("Pliego");

        btnBuscar.setBackground(new java.awt.Color(255, 161, 1));
        btnBuscar.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnBuscar.setForeground(new java.awt.Color(255, 255, 255));
        btnBuscar.setText("Buscar");
        btnBuscar.setBorder(null);
        btnBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBuscarMouseClicked(evt);
            }
        });
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        btnExportarPDF.setBackground(new java.awt.Color(255, 161, 1));
        btnExportarPDF.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnExportarPDF.setForeground(new java.awt.Color(255, 255, 255));
        btnExportarPDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/icons8-register-20.png"))); // NOI18N
        btnExportarPDF.setText("Exportar PDF");
        btnExportarPDF.setBorder(null);
        btnExportarPDF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExportarPDFMouseClicked(evt);
            }
        });

        selectDepartamento.setBackground(new java.awt.Color(255, 255, 255));
        selectDepartamento.setToolTipText("");

        selectProvincia.setBackground(new java.awt.Color(255, 255, 255));

        selectDistrito.setBackground(new java.awt.Color(255, 255, 255));
        selectDistrito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectDistritoActionPerformed(evt);
            }
        });

        selectPliego.setBackground(new java.awt.Color(255, 255, 255));

        selectEjecutora.setBackground(new java.awt.Color(255, 255, 255));
        selectEjecutora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectEjecutoraActionPerformed(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/icons8-search-20.png"))); // NOI18N

        btnExportarCSV.setBackground(new java.awt.Color(255, 161, 1));
        btnExportarCSV.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnExportarCSV.setForeground(new java.awt.Color(255, 255, 255));
        btnExportarCSV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/icons8-csv-20.png"))); // NOI18N
        btnExportarCSV.setText("Exportar CSV");
        btnExportarCSV.setBorder(null);
        btnExportarCSV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnExportarCSVMouseClicked(evt);
            }
        });

        btnActualizarTabla.setBackground(new java.awt.Color(255, 161, 1));
        btnActualizarTabla.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        btnActualizarTabla.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizarTabla.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Recursos/icons8-actualizar-20.png"))); // NOI18N
        btnActualizarTabla.setText("Actualizar");
        btnActualizarTabla.setBorder(null);
        btnActualizarTabla.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnActualizarTablaMouseClicked(evt);
            }
        });
        btnActualizarTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarTablaActionPerformed(evt);
            }
        });

        jLabel5.setText("Departamento");

        jLabel6.setText("Provincia");

        jLabel7.setText("Distrito");

        jLabel8.setText("Pliego");

        jLabel9.setText("Ejecutora");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 648, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(btnActualizarTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(btnExportarPDF, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                            .addComponent(btnExportarCSV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(28, 28, 28)
                        .addComponent(txtRUC, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(txtPliego, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(selectDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(selectProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(selectDistrito, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(selectPliego, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(selectEjecutora, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txtRUC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtRazonSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3)
                        .addComponent(txtPliego, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(jLabel6)
                        .addComponent(jLabel8)
                        .addComponent(jLabel7))
                    .addComponent(jLabel9))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectDepartamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectProvincia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectDistrito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectPliego, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectEjecutora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnActualizarTabla, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(292, 292, 292)
                        .addComponent(btnExportarPDF, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnExportarCSV, javax.swing.GroupLayout.DEFAULT_SIZE, 22, Short.MAX_VALUE)
                        .addGap(19, 19, 19))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void txtRUCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRUCActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRUCActionPerformed

    private void btnBuscarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBuscarMouseClicked
        /*// TODO add your handling code here:
        String campo = ""; // Inicializa una variable para el campo de búsqueda
        String valor = ""; // Inicializa una variable para el valor de búsqueda

        // Determina cuál campo se va a buscar y obtiene su valor
        if (!txtRUC.getText().isEmpty()) {
            campo = "RUC";
            valor = txtRUC.getText();
        } else if (!txtRazonSocial.getText().isEmpty()) {
            campo = "Razón Social";
            valor = txtRazonSocial.getText();
        } else if (!txtPliego.getText().isEmpty()) {
            campo = "Pliego";
            valor = txtPliego.getText();
        }

        // Verifica que se haya especificado un campo de búsqueda
        if (!campo.isEmpty() && !valor.isEmpty()) {
            // Llama al método buscar del controlador
            listaAcrededoresFiltrada = controllerAcrededores.buscar(campo, valor);

            // Actualiza la tabla con los resultados
            actualizarTabla(listaAcrededoresFiltrada);
        } else {
            // Muestra un mensaje de error si no se especifica ningún campo de búsqueda
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un valor para buscar.", "Error", JOptionPane.ERROR_MESSAGE);
        }*/
        String campo = ""; // Inicializa una variable para el campo de búsqueda
        String valor = ""; // Inicializa una variable para el valor de búsqueda

        // Determina cuál campo se va a buscar y obtiene su valor
        if (!txtRUC.getText().isEmpty()) {
            campo = "RUC";
            valor = txtRUC.getText();
        } else if (!txtRazonSocial.getText().isEmpty()) {
            campo = "Razón Social";
            valor = txtRazonSocial.getText();
        } else if (!txtPliego.getText().isEmpty()) {
            campo = "Pliego";
            valor = txtPliego.getText();
        }

        // Verifica que se haya especificado un campo de búsqueda
        if (!campo.isEmpty() && !valor.isEmpty()) {
            // Llama al método buscar del controlador
            if (campo.equals("RUC")) {
                listaAcrededoresFiltrada = buscarEnArbolPorRUC(arbolPorRUC, valor);
            } else {
                listaAcrededoresFiltrada = controllerAcrededores.buscar(campo, valor);
            }

            // Actualiza la tabla con los resultados
            actualizarTabla(listaAcrededoresFiltrada);
        } else {
            // Muestra un mensaje de error si no se especifica ningún campo de búsqueda
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un valor para buscar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnBuscarMouseClicked

    private void selectDistritoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectDistritoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectDistritoActionPerformed

    private void selectEjecutoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectEjecutoraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectEjecutoraActionPerformed

    private void btnActualizarTablaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarTablaMouseClicked
        // TODO add your handling code here:
        // Obtiene los valores seleccionados de los comboboxes
        String valorDepartamento = (String) selectDepartamento.getSelectedItem();
        String valorProvincia = (String) selectProvincia.getSelectedItem();
        String valorDistrito = (String) selectDistrito.getSelectedItem();
        String valorPliego = (String) selectPliego.getSelectedItem();
        String valorEjecutora = (String) selectEjecutora.getSelectedItem();

        // Mensajes de depuración para comprobar los valores seleccionados
        System.out.println("Departamento: " + valorDepartamento);
        System.out.println("Provincia: " + valorProvincia);
        System.out.println("Distrito: " + valorDistrito);
        System.out.println("Pliego: " + valorPliego);
        System.out.println("Ejecutora: " + valorEjecutora);

        // Verifica que listaAcrededoresFiltrada no sea null o esté vacía
        if (listaAcrededoresFiltrada == null || listaAcrededoresFiltrada.getTamaño() == 0) {
            System.out.println("La lista de acreedores filtrada está vacía o es nula.");
            return;
        }

        // Filtra la lista de acreedores
        ImplListaEnlazada<Acrededores> listaFiltrada = controllerAcrededores.filtrar(
                listaAcrededoresFiltrada,
                valorDepartamento,
                valorProvincia,
                valorDistrito,
                valorPliego,
                valorEjecutora
        );

        // Verifica si la lista filtrada tiene resultados
        if (listaFiltrada.getTamaño() == 0) {
            System.out.println("gaaaaaaaaaaaaaaaa");
        } else {
            actualizarTabla(listaFiltrada);
        }
    }//GEN-LAST:event_btnActualizarTablaMouseClicked

    private void btnExportarPDFMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExportarPDFMouseClicked
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar como PDF");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.endsWith(".pdf")) {
                filePath += ".pdf";
            }
            controllerAcrededores.exportarAPDF(listaExportar, filePath); 
        }
    }//GEN-LAST:event_btnExportarPDFMouseClicked

    private void btnExportarCSVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExportarCSVMouseClicked
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar como CSV");
        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.endsWith(".csv")) {
                filePath += ".csv";
            }
            controllerAcrededores.exportarACSV(listaExportar, filePath); 
        }
    }//GEN-LAST:event_btnExportarCSVMouseClicked

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnActualizarTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarTablaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnActualizarTablaActionPerformed
    private void actualizarTabla(ImplListaEnlazada<Acrededores> resultados) {
        // Define el modelo de la tabla
        listaExportar = resultados;
        DefaultTableModel model = (DefaultTableModel) tablaResultados.getModel();

        // Limpia la tabla antes de agregar nuevos datos
        model.setRowCount(0);

        Nodo<Acrededores> actual = resultados.getCabeza();

        while (actual != null) {
            Acrededores acredor = actual.getDato();
            // Agrega una fila a la tabla con los datos del acreedor
            model.addRow(new Object[]{
                acredor.getRuc(),
                acredor.getRazonSocial(),
                acredor.getRemypeDepartamento(),
                acredor.getRemypeProvincia(),
                acredor.getRemypeDistrito(),
                acredor.getDomicilioFiscal(),
                acredor.getAppInformaticoDemandas(),
                acredor.getSiaf(),
                acredor.getEstadoDeuda(),
                acredor.getTipoDocumento(),
                acredor.getDocDevenOSentenJudi(),
                acredor.getMontoDeuda(),
                acredor.getDescNivelGobierno(),
                acredor.getDescSector(),
                acredor.getDescPliego(),
                acredor.getSecEjec(),
                acredor.getDescEjecutora(),
                acredor.getObservacionGlosa()
            });

            actual = actual.getSiguiente();
        }
    }

    private void agregarListeners() {
        selectDepartamento.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String departamentoSeleccionado = (String) selectDepartamento.getSelectedItem();
                if (departamentoSeleccionado != null) {
                    cargarProvincias(departamentoSeleccionado);
                    selectDistrito.removeAllItems(); // Limpiar distritos
                    selectDistrito.setEnabled(false); // Deshabilitar distritos hasta que se seleccione una provincia
                }
            }
        });

        selectProvincia.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String provinciaSeleccionada = (String) selectProvincia.getSelectedItem();
                if (provinciaSeleccionada != null) {
                    cargarDistritos(provinciaSeleccionada);
                    selectDistrito.setEnabled(true); // Habilitar distritos después de seleccionar una provincia
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarTabla;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnExportarCSV;
    private javax.swing.JButton btnExportarPDF;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> selectDepartamento;
    private javax.swing.JComboBox<String> selectDistrito;
    private javax.swing.JComboBox<String> selectEjecutora;
    private javax.swing.JComboBox<String> selectPliego;
    private javax.swing.JComboBox<String> selectProvincia;
    private javax.swing.JTable tablaResultados;
    private javax.swing.JTextField txtPliego;
    private javax.swing.JTextField txtRUC;
    private javax.swing.JTextField txtRazonSocial;
    // End of variables declaration//GEN-END:variables
}
