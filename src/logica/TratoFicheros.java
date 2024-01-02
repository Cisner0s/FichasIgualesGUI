package logica;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * La clase TratoFicheros proporciona métodos para la manipulación de archivos relacionados con el juego Fichas Iguales.
 */
public class TratoFicheros {
	 
	/**
     * Constructor de la clase TratoFicheros.
     */
	public TratoFicheros() {

    }

	 /**
     * Crea un archivo con el contenido especificado en la ruta indicada.
     *
     * @param ruta Ruta del archivo a crear.
     * @param cont Contenido del archivo.
     */
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
    
    /**
     * Crea un archivo de solución con el contenido, la puntuación y las fichas especificadas en la ruta indicada.
     *
     * @param ruta      Ruta del archivo de solución a crear.
     * @param solucion  Contenido del archivo de solución.
     * @param puntuacion Puntuación de la solución.
     * @param fichas     Número de fichas restantes en la solución.
     */
    public void crearArchSolucion(String ruta, String solucion, int puntuacion, int fichas) {
        try {
            String contenido = solucion;
            contenido = contenido.concat("Puntuación final: " + puntuacion + ", quedando " + fichas + " fichas.");
            File directory = new File("Soluciones");
            
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
    
    /**
     * Edita un archivo en la ruta especificada con el nuevo contenido.
     *
     * @param ruta      Ruta del archivo a editar.
     * @param contenido Nuevo contenido del archivo.
     */
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

    /**
     * Devuelve el contenido de un archivo en forma de matriz de cadenas.
     *
     * @param ruta Ruta del archivo.
     * @param fil  Número de filas en la matriz.
     * @param col  Número de columnas en la matriz.
     * @return Matriz de cadenas con el contenido del archivo.
     */
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

    /**
     * Verifica si un carácter es una ficha válida (R, V, A).
     *
     * @param m Carácter a verificar.
     * @return true si el carácter es una ficha, false de lo contrario.
     */
    public boolean esFicha(char m) {
        return ('R' == m  || 'V' == m  || 'A' == m );
    }

    /**
     * Devuelve el número de columnas en un archivo especificado por la ruta.
     *
     * @param ruta Ruta del archivo.
     * @return Número de columnas en el archivo.
     */
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


    /**
     * Devuelve el número de filas en un archivo especificado por la ruta.
     *
     * @param ruta Ruta del archivo.
     * @return Número de filas en el archivo.
     */
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
