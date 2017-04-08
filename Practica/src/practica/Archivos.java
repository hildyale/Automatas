
package practica;

import java.io.*;
import javax.swing.JOptionPane;

/**
 * Clase que se encarga de manejar la lectura y escritura de los archivos txt
 * @author Alejo
 */
public class Archivos {
    
    /**
     * metodo que se encarga de leer un archivo txt dada una direccion y una codificacion
     * @param direccion
     * @param codificacion
     * @return 
     */
    public String leertxt(String direccion,String codificacion){// recibe la direccion de un archivo txt y devuelve un string con el contenido del archivo
        String texto="";
        try{
            FileInputStream fstream = new FileInputStream(direccion);
            InputStreamReader fichero = new InputStreamReader(fstream, codificacion);
            BufferedReader ab = new BufferedReader(fichero);
            String temp ="";
            String bfread;
            while((bfread = ab.readLine())!= null){
                temp = temp + bfread;
            }
            texto = temp;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null,"No se encontro el Archivo","Campo Vacío",JOptionPane.ERROR_MESSAGE);
        }  
        return texto;  
    }
    
    /**
     * Metodo que se encarga de crear un archivo txt dada una direccion, un nombre para el archivo y su contenido
     * @param direccion
     * @param archivo
     * @param Nombre
     * @return 
     */
    public boolean creartxt(String direccion,String archivo,String Nombre){
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter(direccion+"\\"+Nombre+".txt");
            pw = new PrintWriter(fichero);
            pw.println(archivo);

        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            try {
            // Nuevamente aprovechamos el finally para 
            // asegurarnos que se cierra el fichero.
            if (null != fichero)
              fichero.close();
            } catch (Exception e2) {
              e2.printStackTrace();
            }
        }
        return true;
    }
    
}
