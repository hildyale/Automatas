/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * Jframe principal en el cual se maneja toda la manipulacion del automata dinamicamente
 * @author Alejo
 */
public class FormularioCrearAutomataND extends javax.swing.JFrame {

    private ArrayList simbolos = new ArrayList();
    private ArrayList estados = new ArrayList();
    private int cont=0;
    
    /**
     * Creates new form FormularioCrearAutomata
     */
    public FormularioCrearAutomataND() {
        initComponents();
        setLocationRelativeTo(null);
//        setResizable(false);
        AgregarEstado.setEnabled(false);
        EliminarEstado.setEnabled(false);
        EditarEstado.setEnabled(false);
        jTable2.getTableHeader().setEnabled(false);
        jTable1.getTableHeader().setEnabled(false);
        GuardarAutomata.setEnabled(false);
        DefaultTableModel model2 = new MyDefaultTableModel();
        jTable2.setModel(model2);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        model2.addColumn("Inicial?");
        model2.addColumn("Nombre");
        model2.addColumn("Aceptacion?");
        }

    /**
     * Metodo que se encarga de llenar las tablas del formularios con sus respectivos simbolos y estados dado una objeto de la clase Automata
     * @param automata 
     */
    public void transformarAutomata(AutomataND automata){
         simbolos = automata.getSimbolos();
         for(int i=0;i<simbolos.size();i++){
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            DefaultTableModel model2 = (DefaultTableModel) jTable2.getModel();
            model.addRow(new Object[]{simbolos.get(i)});
            model2.addColumn(simbolos.get(i));
         }
         HashMap<String,HashMap> estadosAutomata = automata.getEstados();
         HashMap<String,String> estado;
         Object[] estadosArray = estadosAutomata.keySet().toArray();
         String nombreEstado;
         ArrayList estadosIniciales=automata.getEstadosIniciales();
         for(int i=0;i<estadosArray.length;i++){
             estados.add((String)estadosArray[i]);
             System.out.println(estadosArray[i]);
                DefaultTableModel model = (DefaultTableModel)jTable2.getModel();
                String[] rowData = new String[simbolos.size()+3];
                nombreEstado = (String)estadosArray[i];
                if(estadosIniciales.contains(nombreEstado)){
                   rowData[0]= "1";
                }else{
                   rowData[0]= "0";
                }
                rowData[1]= nombreEstado;
                estado = estadosAutomata.get((String)estadosArray[i]);
                if(automata.getEstadosAceptacion().contains(nombreEstado)){ 
                    rowData[2] = "1";
                }else{
                    rowData[2] = "0";
                }
                for(int j=3;j<rowData.length;j++){
                    rowData[j]= estado.get((String)simbolos.get(j-3));
                }
                model.addRow(rowData);
         }
         
         GuardarAutomata.setEnabled(true);
         EliminarEstado.setEnabled(true);
         AgregarEstado.setEnabled(true);
    }
    
    //Remueve una columna de la tabla y sus datos
    public void removeColumnAndData(JTable table, int vColIndex) {
        MyDefaultTableModel model = (MyDefaultTableModel)table.getModel();
        TableColumn col = table.getColumnModel().getColumn(vColIndex);
        int columnModelIndex = col.getModelIndex();
        Vector data = model.getDataVector();
        Vector colIds = model.getColumnIdentifiers();

        // Remove the column from the table
        table.removeColumn(col);

        // Remove the column header from the table model
        colIds.removeElementAt(columnModelIndex);

        // Remove the column data
        for (int r=0; r<data.size(); r++) {
            Vector row = (Vector)data.get(r);
            row.removeElementAt(columnModelIndex);
        }
        model.setDataVector(data, colIds);

        // Correct the model indices in the TableColumn objects
        // by decrementing those indices that follow the deleted column
        Enumeration enume = table.getColumnModel().getColumns();
        for (; enume.hasMoreElements(); ) {
            TableColumn c = (TableColumn)enume.nextElement();
            if (c.getModelIndex() >= columnModelIndex) {
                c.setModelIndex(c.getModelIndex()-1);
            }
        }
        model.fireTableStructureChanged();
    }

    /**
     * Metodo que se encarga de dado los estados y los simbolos de las tablas crear un objeto de la clase Automata y llenarlos respectivamente
     * @return 
     */
    public AutomataND crearAutomata(){
            String[] estadosArray = new String[estados.size()];
            estadosArray = (String[]) estados.toArray(estadosArray);
            AutomataND automata = new AutomataND();
                HashMap<String,HashMap> estadosAutomata = new HashMap<>();
                ArrayList estadosAceptacion = new ArrayList();
                ArrayList estadosIniciales = new ArrayList();
                HashMap<String,String> estado;

                automata.setSimbolos(simbolos);

                DefaultTableModel model = (DefaultTableModel)jTable2.getModel();
                for(int i=0;i<estados.size();i++){
                    estado = new HashMap<>();
                    for(int j=0;j<simbolos.size()+1;j++){
                        if(j!=simbolos.size()){
                          estado.put((String)simbolos.get(j),(String)model.getValueAt(i, j+3));
                        }else{
                         if(model.getValueAt(i, 2).equals("1")){
                           estadosAceptacion.add((String)estados.get(i));
                         }
                         if(model.getValueAt(i, 0).equals("1")){
                           estadosIniciales.add((String)estados.get(i));
                         }
                         estadosAutomata.put((String)estados.get(i),estado);
                         
                        }
                    }
                }


                automata.setEstados(estadosAutomata);
                automata.setEstadosAceptacion(estadosAceptacion);
                automata.setEstadosIniciales(estadosIniciales);
            
            return automata;    
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        AgregarSimbolo = new javax.swing.JButton();
        EliminarSimbolo = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; //Disallow the editing of any cell
            }
        };
        jPanel1 = new javax.swing.JPanel();
        AgregarEstado = new javax.swing.JButton();
        EliminarEstado = new javax.swing.JButton();
        GuardarAutomata = new javax.swing.JButton();
        EditarEstado = new javax.swing.JButton();

        jMenuItem1.setText("jMenuItem1");

        jMenuItem2.setText("jMenuItem2");

        jMenuItem3.setText("jMenuItem3");

        jMenuItem4.setText("jMenuItem4");

        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("jRadioButtonMenuItem1");

        jMenu1.setText("jMenu1");

        jMenu2.setText("jMenu2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        DefaultTableModel model = new javax.swing.table.DefaultTableModel();
        jTable1.setModel(model);
        model.addColumn("Simbolos de entrada");
        jTable1.setColumnSelectionAllowed(true);
        jScrollPane2.setViewportView(jTable1);

        AgregarSimbolo.setText("Agregar Simbolo");
        AgregarSimbolo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarSimboloActionPerformed(evt);
            }
        });

        EliminarSimbolo.setText("Eliminar Simbolo");
        EliminarSimbolo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarSimboloActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(

        ));
        jScrollPane1.setViewportView(jTable2);

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        AgregarEstado.setText("Agregar Estado");
        AgregarEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgregarEstadoActionPerformed(evt);
            }
        });

        EliminarEstado.setText("Eliminar Estado");
        EliminarEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliminarEstadoActionPerformed(evt);
            }
        });

        GuardarAutomata.setText("Guardar Automata");
        GuardarAutomata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarAutomataActionPerformed(evt);
            }
        });

        EditarEstado.setText("Editar Estado");
        EditarEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditarEstadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(AgregarEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(EditarEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(EliminarEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(GuardarAutomata, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AgregarEstado)
                    .addComponent(GuardarAutomata))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EditarEstado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EliminarEstado)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(AgregarSimbolo)
                            .addComponent(EliminarSimbolo)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(AgregarSimbolo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(EliminarSimbolo))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Metodo que agrega un simbolo a la tabla dado una caracter
     * @param evt 
     */
    private void AgregarSimboloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarSimboloActionPerformed
        String inputValue = JOptionPane.showInputDialog("Nombre del simbolo");
        if (inputValue != null){  
            if (!inputValue.equals("") ){
                inputValue = inputValue.trim().charAt(0)+"";
                if(!simbolos.contains(inputValue)){
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    DefaultTableModel model2 = (DefaultTableModel) jTable2.getModel();
                    model.addRow(new Object[]{inputValue});
                    simbolos.add(inputValue);
                    //TableColumn tcol = jTable2.getColumnModel().getColumn(simbolos.size()+1);
                    //jTable2.removeColumn(jTable2.getColumnModel().getColumn(simbolos.size()+1));
                    //removeColumnAndData(jTable2,simbolos.size()+1);
                    model2.addColumn(inputValue);
                    for(int i=0;i<estados.size();i++){
                        jTable2.setValueAt("x", i, jTable2.getColumnCount()-1);
                    }
                    //jTable2.getColumnModel().addColumn(tcol);
                    if(jTable1.getRowCount()== 1){
                        AgregarEstado.setEnabled(true);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Simbolo ya existe","Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }//GEN-LAST:event_AgregarSimboloActionPerformed

    /**
     * Metodo que elimina el simbolo que este seleccionado en la tabla
     * @param evt 
     */
    private void EliminarSimboloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarSimboloActionPerformed
        JTable table = this.jTable1;
        int i = table.getSelectedRow();
        if (i>=0){
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.removeRow(i);
            simbolos.remove(i);
            removeColumnAndData(jTable2,i+3);
            if(table.getRowCount()== 0){
                AgregarEstado.setEnabled(false);
                jTable2.setEnabled(false);
                GuardarAutomata.setEnabled(false);
                DefaultTableModel model3 = new MyDefaultTableModel();
                jTable2.setModel(model3);
                model3.addColumn("Nombre");
                model3.addColumn("Aceptacion?");
                estados.clear();
            }
        }
        
        

    }//GEN-LAST:event_EliminarSimboloActionPerformed

    /**
     * Metodo que Agrega un estado a la tabla dado una cadena
     * @param evt 
     */
    private void AgregarEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgregarEstadoActionPerformed
         String[] estadosArray = new String[estados.size()+1];
         estadosArray = (String[])estados.toArray(estadosArray);
         estadosArray[estados.size()] = "x";
         String[] simbolosArray = new String[simbolos.size()];
         simbolosArray = (String[])simbolos.toArray(simbolosArray);
         CrearEstado crearEstado = new CrearEstado(this, rootPaneCheckingEnabled,estadosArray,simbolosArray);
         crearEstado.setVisible(true);
        String inputValue = crearEstado.nombre;
        if (inputValue != null){
            inputValue = inputValue.trim();
            if (!inputValue.equals("") ){
                if(!estados.contains(inputValue)){
                    DefaultTableModel model = (DefaultTableModel)jTable2.getModel();
                    String[] rowData = new String[simbolos.size()+3];
                    rowData[1]= inputValue;
                    rowData[0] = (crearEstado.inicial.isSelected()) ? "1" : "0";
                    rowData[2] = (crearEstado.aceptacion.isSelected()) ? "1" : "0";
                    ArrayList array;
                    for(int i=3;i<rowData.length;i++){
                        array = crearEstado.estadoSimbolo[i-3];
                        String x="";
                        if(!array.isEmpty()){
                            for(int j=0;j<array.size();j++){
                              JComboBox combo = (JComboBox)array.get(j);
                              String temp = (String)combo.getSelectedItem();
                              temp = (temp.equals("")) ? "x": temp;
                              if(x.equals("")){
                                  x = temp;
                              }else{
                                  x = x+","+temp;
                              }
                            }
                            rowData[i]= x;
                        }else{
                            rowData[i]= "x";
                        }
                    }
                    model.addRow(rowData);
                    estados.add(inputValue);
                    if(jTable2.getRowCount()== 1){
                        EliminarEstado.setEnabled(true);
                        EditarEstado.setEnabled(true);
                        GuardarAutomata.setEnabled(true);
                    }
                }else{
                   JOptionPane.showMessageDialog(null, "Estado ya existe","Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        
         estados.forEach((x)->{
             System.out.println(x);
         });
         
    }//GEN-LAST:event_AgregarEstadoActionPerformed

    /**
     * Metodo que elimina el  estado de la tabla que este seleccionado
     * @param evt 
     */
    private void EliminarEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliminarEstadoActionPerformed
            int i = jTable2.getSelectedRow();
            if (i>=0){
                DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                model.removeRow(i);
                if(jTable2.getRowCount()== 0){
                       EliminarEstado.setEnabled(false);
                       EditarEstado.setEnabled(false);
                       GuardarAutomata.setEnabled(false);
                   }
            }
    }//GEN-LAST:event_EliminarEstadoActionPerformed

    /**
     * Metodo que Guarda el automata usando la libreria Gson y la clase de Archivos
     * @param evt 
     */
    private void GuardarAutomataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarAutomataActionPerformed
            AutomataND automata = crearAutomata();
            if(automata!=null){
                Gson gson = new Gson();
                String representacionJSON = gson.toJson(automata); 

                JFileChooser chooser = new JFileChooser(); 
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Seleccione donde guardar");
                chooser.setApproveButtonText("Guardar");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                //
                // disable the "All files" option.
                //
                chooser.setAcceptAllFileFilterUsed(false);
                //    
                if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
                      String inputValue = JOptionPane.showInputDialog("Nombre del archivo");
                      if (inputValue != null){
                        inputValue = inputValue.trim();
                        if (!inputValue.equals("")){
                            Archivos a = new Archivos();
                            boolean funciono = a.creartxt(chooser.getSelectedFile().toString(), representacionJSON,inputValue);
                            if(funciono){
                                JOptionPane.showMessageDialog(rootPane, "Archivo Creado Correctamente");
                            }
                            System.out.println(funciono);
                        }else{
                          JOptionPane.showMessageDialog(null, "El nombre no puede ser vacio","Error", JOptionPane.ERROR_MESSAGE);
                        }
                      }
                }
            }  
    }//GEN-LAST:event_GuardarAutomataActionPerformed

    private void EditarEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditarEstadoActionPerformed
          int k = jTable2.getSelectedRow();
            if (k>=0){
                DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                String nombre = (String) model.getValueAt(k, 1);
                boolean inicial = model.getValueAt(k, 0).equals("1");
                boolean aceptacion = model.getValueAt(k, 2).equals("1");
                
                String[] estadosArray = new String[estados.size()+1];
                estadosArray = (String[])estados.toArray(estadosArray);
                estadosArray[estados.size()] = "x";
                String[] simbolosArray = new String[simbolos.size()];
                simbolosArray = (String[])simbolos.toArray(simbolosArray);
                ArrayList[] estadoSimbolos = new ArrayList[simbolos.size()];
                ArrayList xx;
                for(int l=0;l<simbolosArray.length;l++){
                    xx = new ArrayList();
                    String go = (String) model.getValueAt(k, l+3);
                    String[] parts = go.split(",");
                    for(int m=0;m<parts.length;m++){
                      JComboBox comboBox = new javax.swing.JComboBox<>();
                      comboBox.setModel(new javax.swing.DefaultComboBoxModel<>(estadosArray));
                      comboBox.setSelectedItem(parts[m]);
                        for(int z=0;z<estadosArray.length;z++){
                            System.out.println(estadosArray[z]);
                        }
                        
                      xx.add(comboBox);
                    }
                    estadoSimbolos[l] = xx;
                }
                
                CrearEstado crearEstado = new CrearEstado(this, rootPaneCheckingEnabled,estadosArray,simbolosArray);
                crearEstado.jTextField1.setText(nombre);
                crearEstado.jTextField1.setEnabled(false);
                crearEstado.aceptacion.setSelected(aceptacion);
                crearEstado.inicial.setSelected(inicial);
                crearEstado.estadoSimbolo = estadoSimbolos;
                crearEstado.actualizar();
                crearEstado.setVisible(true);
        
                String[] rowData = new String[simbolos.size()+3];
                String temp;
                temp = (crearEstado.inicial.isSelected()) ? "1" : "0";
                model.setValueAt(temp, k, 0);
                temp = (crearEstado.aceptacion.isSelected()) ? "1" : "0";
                model.setValueAt(temp, k, 2);
                ArrayList array;
                for(int i=3;i<rowData.length;i++){
                    array = crearEstado.estadoSimbolo[i-3];
                    String x="";
                    if(!array.isEmpty()){
                        for(int j=0;j<array.size();j++){
                          JComboBox combo = (JComboBox)array.get(j);
                          temp = (String)combo.getSelectedItem();
                          temp = (temp.equals("")) ? "x": temp;
                          if(x.equals("")){
                              x = temp;
                          }else{
                              x = x+","+temp;
                          }
                        }
                       model.setValueAt(x, k, i);
                    }else{
                       model.setValueAt("x", k, i);
                    }
                }
            }
    }//GEN-LAST:event_EditarEstadoActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormularioCrearAutomataND().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AgregarEstado;
    private javax.swing.JButton AgregarSimbolo;
    private javax.swing.JButton EditarEstado;
    private javax.swing.JButton EliminarEstado;
    private javax.swing.JButton EliminarSimbolo;
    private javax.swing.JButton GuardarAutomata;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    // End of variables declaration//GEN-END:variables
}
