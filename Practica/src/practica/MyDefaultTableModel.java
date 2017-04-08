/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica;

import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 * Modelo alternativo para un jTable
 * @author Alejo
 */
class MyDefaultTableModel extends DefaultTableModel {
    public Vector getColumnIdentifiers() {
        return columnIdentifiers;
    }
}