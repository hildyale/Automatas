
package practica;

import com.google.gson.Gson;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * JFrame inicial el cual tienen dos botones uno para crear un nuevo automata o para cargar un automata guardado previamente
 * @author Alejo
 */
public class FormularioInicial extends javax.swing.JFrame {

    /**
     * Creates new form Formulario
     */
    public FormularioInicial() {
        initComponents();
        setLocationRelativeTo(null);
        setResizable(false);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        crear = new javax.swing.JButton();
        cargar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Que desea hacer? ");

        crear.setText("Crear nuevo automata");
        crear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                crearActionPerformed(evt);
            }
        });

        cargar.setText("Cargar Automata");
        cargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cargarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(crear)
                        .addGap(18, 18, 18)
                        .addComponent(cargar)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(crear)
                    .addComponent(cargar))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Metodo que carga un automata guardado previamente haciendo uso de la libreria Gson y la clase Archivos, luego crea una instancia de la clase FormularioCraerAutomata y le manda el automata
     * @param evt 
     */
    private void cargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cargarActionPerformed
        JFileChooser chooser = new JFileChooser(); 
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Seleccione Archivo");
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                //
                // disable the "All files" option.
                //
                chooser.setAcceptAllFileFilterUsed(false);
                //    
                if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { 
                        String b= chooser.getSelectedFile()+"";
                        if (!b.isEmpty()){
                            String c = b.substring(b.length()-4);      
                            String so = System.getProperty("os.name");
                            if (so.contains("Windows"))so = "Windows";
                            switch (so){
                                case "Windows":so = "iso-8859-1";
                                break;
                                case "Linux":so = "utf-8";
                                break;
                            } 
                            if(!c.equals(".txt")){
                                JOptionPane.showMessageDialog(null, "Solo Se Admiten Archivos De Texto (.txt)","Error",JOptionPane.ERROR_MESSAGE);
                            }else{  
                                Archivos a = new Archivos(); 
                                b = a.leertxt(b,so);
                                if(!b.isEmpty()){
                                    Gson gson = new Gson();
                                    Automata automata = gson.fromJson(b, Automata.class);
                                    FormularioCrearAutomata abc = new FormularioCrearAutomata();
                                    abc.setTitle("Crear Automata");
                                    abc.transformarAutomata(automata);
                                    abc.setVisible(true);
                                    dispose();
                                }else{
                                    JOptionPane.showMessageDialog(null, "Texto Vacío","Error",JOptionPane.ERROR_MESSAGE);
                                }

                            }   
                        }else{
                            JOptionPane.showMessageDialog(null,"Porfavor Seleccione el Archivo","Campo Vacío",JOptionPane.ERROR_MESSAGE);
                        }             
                }
    }//GEN-LAST:event_cargarActionPerformed

    /**
     * Crea una instancia de la clase FormularioCrearAutomata
     * @param evt 
     */
    private void crearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_crearActionPerformed
        FormularioCrearAutomata formulario =  new FormularioCrearAutomata();
        formulario.setTitle("Crear Automata");
        formulario.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_crearActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormularioInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormularioInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormularioInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormularioInicial.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormularioInicial().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cargar;
    private javax.swing.JButton crear;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}