
package practica;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clase para representar un automata en la cual se almacenan los estados, los simbolos de entrada, estado inicial del automata y sus estados de aceptacion 
 * con sus respectivos setters y getters
 * @author Alejo
 */
public class AutomataND {
    private HashMap<String,HashMap> estados;
    private ArrayList simbolos;
    private ArrayList estadosIniciales;
    private ArrayList estadosAceptacion;



    public AutomataND(){}

    public HashMap<String, HashMap> getEstados() {
        return estados;
    }

    public void setEstados(HashMap<String, HashMap> estados) {
        this.estados = estados;
    }

    public ArrayList getSimbolos() {
        return simbolos;
    }

    public void setSimbolos(ArrayList simbolos) {
        this.simbolos = simbolos;
    }

    public ArrayList getEstadosIniciales() {
        return estadosIniciales;
    }

    public void setEstadosIniciales(ArrayList estadosIniciales) {
        this.estadosIniciales = estadosIniciales;
    }

    public ArrayList getEstadosAceptacion() {
        return estadosAceptacion;
    }

    public void setEstadosAceptacion(ArrayList estadosAceptacion) {
        this.estadosAceptacion = estadosAceptacion;
    }

      
    
}
