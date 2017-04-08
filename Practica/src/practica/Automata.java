
package practica;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Clase para representar un automata en la cual se almacenan los estados, los simbolos de entrada, estado inicial del automata y sus estados de aceptacion 
 * con sus respectivos setters y getters
 * @author Alejo
 */
public class Automata {
    private HashMap<String,HashMap> estados;
    private ArrayList simbolos;
    private String estadoInicial;
    private ArrayList estadosAceptacion;



    public Automata(){}
    
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

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public ArrayList getEstadosAceptacion() {
        return estadosAceptacion;
    }

    public void setEstadosAceptacion(ArrayList estadosAceptacion) {
        this.estadosAceptacion = estadosAceptacion;
    }

    /**
     * Metodo el cual se encarga de validar una cadena en el automata y decir si esta si queda en un estado de aceptacion o no
     * @param cadena
     * @return 
     */
    public boolean isValid(String cadena){
        String c = cadena;
        String estado="";
        HashMap estadoTemp = estados.get(estadoInicial);
        int j=1;
        for(int i=0;i<cadena.length();i++){
            String simbolo = c.charAt(0)+"";
            c = c.substring(j);
            estado = (String)estadoTemp.get(simbolo);
            if (estado.equals("x")) return false;
            estadoTemp = estados.get(estado);
            System.out.println(simbolo+" => "+estadoTemp);
        } 
        return estadosAceptacion.contains(estado);
    }
    
}
