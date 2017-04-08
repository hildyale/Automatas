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
public class FormularioCrearAutomata extends javax.swing.JFrame {

    private ArrayList simbolos = new ArrayList();
    private ArrayList estados = new ArrayList();
    private int cont=0;
    
    /**
     * Creates new form FormularioCrearAutomata
     */
    public FormularioCrearAutomata() {
        initComponents();
        setLocationRelativeTo(null);
//        setResizable(false);
        AgregarEstado.setEnabled(false);
        EliminarEstado.setEnabled(false);
        ProbarAutomata.setEnabled(false);
        jTable2.getTableHeader().setEnabled(false);
        jTable1.getTableHeader().setEnabled(false);
        GuardarAutomata.setEnabled(false);
        DefaultTableModel model2 = new MyDefaultTableModel();
        jTable2.setModel(model2);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable2.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        model2.addColumn("Nombre");
        model2.addColumn("Aceptacion?");
        
        HashMap<String,String> array = new HashMap<String,String>();
        array.put("a", "b");
        
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
                String value;
                int rowg = 0;
                int colg = 0;
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int size = estados.size();
                    int row = jTable2.rowAtPoint(evt.getPoint());
                    int col = jTable2.columnAtPoint(evt.getPoint());
                    if (row >= 0 && col > 1 ) {
                        if(cont>=size || (rowg!=row || colg!=col)){
                          cont=0;
                        }
                            value = (String)estados.get(cont);
                            jTable2.setValueAt(value, row, col);
                            cont++;
                        rowg = row;
                        colg = col;
//                        System.out.print(" cont: "+cont);
//                        System.out.print(" size: "+size+"\n");
                    }
                    
                     if(row >= 0 && col==1){
                       value = (String)jTable2.getValueAt(row, col);
                       if(value=="0"){
                          jTable2.setValueAt("1", row, col);
                       }else{
                          jTable2.setValueAt("0",row, col); 
                       }
                    }
                     
                    }
            });
        
        

    }

    /**
     * Metodo que se encarga de llenar las tablas del formularios con sus respectivos simbolos y estados dado una objeto de la clase Automata
     * @param automata 
     */
    public void transformarAutomata(Automata automata){
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
         for(int i=0;i<estadosArray.length;i++){
             estados.add((String)estadosArray[i]);
             System.out.println(estadosArray[i]);
                DefaultTableModel model = (DefaultTableModel)jTable2.getModel();
                String[] rowData = new String[simbolos.size()+2];
                rowData[0]= (String)estadosArray[i];
                estado = estadosAutomata.get((String)estadosArray[i]);
                if(automata.getEstadosAceptacion().contains(estadosArray[i])){ 
                    rowData[1] = "1";
                }else{
                    rowData[1] = "0";
                }
                for(int j=2;j<rowData.length;j++){
                    rowData[j]= estado.get((String)simbolos.get(j-2));
                }
                model.addRow(rowData);
         }
         
         GuardarAutomata.setEnabled(true);
         EliminarEstado.setEnabled(true);
         AgregarEstado.setEnabled(true);
         ProbarAutomata.setEnabled(true);
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
    public Automata crearAutomata(){
            String[] estadosArray = new String[estados.size()];
            estadosArray = (String[])estados.toArray(estadosArray);
            Automata automata = null;
            String selectedValue = (String)JOptionPane.showInputDialog(null,
            "Selecciones el estado inicial", "Input",
            JOptionPane.INFORMATION_MESSAGE, null,
            estadosArray, estadosArray[0]);
            if(selectedValue!=null){
                automata = new Automata();
                HashMap<String,HashMap> estadosAutomata = new HashMap<>();
                ArrayList estadosAceptacion = new ArrayList();
                HashMap<String,String> estado;

                automata.setSimbolos(simbolos);

                DefaultTableModel model = (DefaultTableModel)jTable2.getModel();
                for(int i=0;i<estados.size();i++){
                    estado = new HashMap<>();
                    for(int j=0;j<simbolos.size()+1;j++){
                        if(j!=simbolos.size()){
                          estado.put((String)simbolos.get(j),(String)model.getValueAt(i, j+2));
                        }else{
                         if(model.getValueAt(i, 1).equals("1")){
                           estadosAutomata.put((String)estados.get(i),estado);
                           estadosAceptacion.add((String)estados.get(i));
                         }else{
                           estadosAutomata.put((String)estados.get(i),estado);
                         }
                        }
                    }
                }


                automata.setEstados(estadosAutomata);
                automata.setEstadosAceptacion(estadosAceptacion);
                automata.setEstadoInicial(selectedValue);
            }
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
        ProbarAutomata = new javax.swing.JButton();
        GuardarAutomata = new javax.swing.JButton();

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

        ProbarAutomata.setText("Probar Automata");
        ProbarAutomata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ProbarAutomataActionPerformed(evt);
            }
        });

        GuardarAutomata.setText("Guardar Automata");
        GuardarAutomata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GuardarAutomataActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(AgregarEstado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(ProbarAutomata))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(EliminarEstado)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(GuardarAutomata)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(AgregarEstado)
                    .addComponent(ProbarAutomata))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EliminarEstado)
                    .addComponent(GuardarAutomata))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(40, Short.MAX_VALUE))
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
            inputValue = inputValue.trim().charAt(0)+"";
            if (!inputValue.equals("") ){
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
                        EliminarEstado.setEnabled(true);
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
            DefaultTableModel model2 = (DefaultTableModel) jTable2.getModel();
            model.removeRow(i);
            simbolos.remove(i);
            removeColumnAndData(jTable2,i+2);
            if(table.getRowCount()== 0){
                AgregarEstado.setEnabled(false);
                EliminarEstado.setEnabled(false);
                jTable2.setEnabled(false);
                GuardarAutomata.setEnabled(false);
                ProbarAutomata.setEnabled(false);
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
        String inputValue = JOptionPane.showInputDialog("Nombre del estado");
        if (inputValue != null){
            inputValue = inputValue.trim();
            if (!inputValue.equals("") ){
                if(!estados.contains(inputValue)){
                    DefaultTableModel model = (DefaultTableModel)jTable2.getModel();
                    String[] rowData = new String[simbolos.size()+3];
                    rowData[0]= inputValue;
                    rowData[1] = "0";
                    for(int i=2;i<rowData.length;i++){
                        rowData[i]= "x";
                    }
                    model.addRow(rowData);
                    estados.add(inputValue);
                    if(jTable2.getRowCount()== 1){
                        EliminarEstado.setEnabled(true);
                        ProbarAutomata.setEnabled(true);
                        GuardarAutomata.setEnabled(true);
                    }
                }else{
                   JOptionPane.showMessageDialog(null, "Estado ya existe","Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
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
                       ProbarAutomata.setEnabled(false);
                       GuardarAutomata.setEnabled(false);
                   }
            }
    }//GEN-LAST:event_EliminarEstadoActionPerformed

    /**
     * Metodo que invoca el metodo crearAutomata() y crear una instancia de otro jframe mandandole el automata para probarlo
     * @param evt 
     */
    private void ProbarAutomataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ProbarAutomataActionPerformed
            Automata automata = crearAutomata();
            if(automata!=null){
                TestAutomata testAutomata = new TestAutomata(this,true,automata);
                testAutomata.setTitle("Probando Automata");
                testAutomata.setVisible(true);
            }
    }//GEN-LAST:event_ProbarAutomataActionPerformed

    /**
     * Metodo que Guarda el automata usando la libreria Gson y la clase de Archivos
     * @param evt 
     */
    private void GuardarAutomataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GuardarAutomataActionPerformed
            Automata automata = crearAutomata();
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

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormularioCrearAutomata().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AgregarEstado;
    private javax.swing.JButton AgregarSimbolo;
    private javax.swing.JButton EliminarEstado;
    private javax.swing.JButton EliminarSimbolo;
    private javax.swing.JButton GuardarAutomata;
    private javax.swing.JButton ProbarAutomata;
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
