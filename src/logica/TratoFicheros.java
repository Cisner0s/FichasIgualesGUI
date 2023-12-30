package logica;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TratoFicheros {
    public TratoFicheros() {

    }

    public void crearArch(String ruta, String cont) {
        try {
            String contenido = cont;
            File directory = new File("Juegos");
            
            // Verificar si el directorio existe, si no, crearlo
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File file = new File(directory, ruta);

            // Si el archivo no existe, es creado
            if (!file.exists()) {
                file.createNewFile();
            }

            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contenido);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void editarArchivo(String ruta, String contenido) {
    	try {
    	      // Abrimos el archivo para escritura
    	      BufferedWriter bw = new BufferedWriter(new FileWriter("Juegos/" + ruta));
    	      
    	      // Escribimos en el archivo el 1 y la linea vacia para que sea igual a la practica intermedia.
    	      bw.write("1\n"
    	      		+ "\n");
    	      
    	      // Escribimos en el archivo el contenido de la matriz.
    	      bw.write(contenido);
    	      
    	      // Cerramos el archivo
    	      bw.close();
    	      
    	}catch(IOException e) {
    	      e.printStackTrace();
    	    }
    }

    public String[][] devolverCont(String ruta, int fil, int col) {
        String[][] contenido = new String[fil][col];
        try {
            // Abrimos el archivo para lectura
            BufferedReader br = new BufferedReader(new FileReader("Juegos/" + ruta));

            // Leemos las dos primeras líneas sin procesar
            for (int i = 0; i < 2; i++) {
                br.readLine(); // Avanzamos hasta la siguiente línea sin hacer nada con su contenido
            }

            // Ahora leemos la tercera línea
            String terceraLinea;
            
            int m = 0;
            
            for (int i = 0; i < fil; i++) {
            	terceraLinea = br.readLine();
                for (int j = 0; j < terceraLinea.length(); j++) {
                    if (m == col) {
                        break;
                    }
                    if (esFicha(terceraLinea.charAt(j))) {
                        contenido[i][m] = String.valueOf(terceraLinea.charAt(j));
                        m++;
                    }
                }
                m = 0;
            }

            // Cerramos el archivo
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return contenido;
    }

    public boolean esFicha(char m) {
        return ('R' == m  || 'V' == m  || 'A' == m );
    }

    public int devolverNumCol(String ruta) {
        int col = 0;
        try {
            // Abrimos el archivo para lectura
            BufferedReader br = new BufferedReader(new FileReader("Juegos/" + ruta));
            
            // Leemos las dos primeras líneas sin procesar
            for (int i = 0; i < 2; i++) {
                br.readLine(); // Avanzamos hasta la siguiente línea sin hacer nada con su contenido
            }

            // Ahora leemos la tercera línea
            String terceraLinea = br.readLine();
            
            for (int j = 0; j < terceraLinea.length(); j++) {
                if (esFicha(terceraLinea.charAt(j))) {
                    col++;
                }
            }

            // Cerramos el archivo
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return col;
    }

    public int devolverNumFil(String ruta) {
        int fil = -2; // Dado que hay que quitar el numero 1 y la linea vacia.
        try {
            // Abrimos el archivo para lectura
            BufferedReader br = new BufferedReader(new FileReader("Juegos/" + ruta));

            // Leemos el archivo línea por línea
            while ((br.readLine()) != null) {
                fil++;
            }

            // Cerramos el archivo
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fil;
    }
}
