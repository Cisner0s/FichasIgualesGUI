package logica;

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * La clase MainFichasIguales contiene el método principal para ejecutar el juego Fichas Iguales.
 * Lee la entrada que representa los juegos, ejecuta la estrategia óptima y muestra los resultados en la consola.
 */
public class MainLogica{
	
	String ruta;
	char[][] juego;
	Tablero tablero;
	
	public MainLogica(String ruta) throws IllegalArgumentException, FileNotFoundException {
		this.ruta = "Juegos/" + ruta;
		ejecucionDelPrograma();
	}
     /**
     * Método principal que lee la entrada, ejecuta la estrategia óptima y muestra los resultados en la consola.
     *
     * @param args Argumentos de línea de comandos (no utilizados en este caso).
     * @throws FileNotFoundException 
     * @throws IllegalArgumentException 
     * @throws Exception Excepción general que puede ocurrir durante la ejecución.
     */
	public void ejecucionDelPrograma() throws IllegalArgumentException, FileNotFoundException {
    	this.juego = leerEntrada();

        this.tablero = new Tablero(juego.length, juego[0].length, juego);

//        // Crea una estrategia óptima y ejecuta el juego.
//        EstrategiaOptima buscarEstrategiaOptima = new EstrategiaOptima(tablero);
//        buscarEstrategiaOptima.jugar(tablero, 0);
//
//        buscarEstrategiaOptima.imprimirSolucionOptima();
        
        for (int i = 0; i < juego.length; i++) {
            for (int j = 0; j < juego[i].length; j++) {  // Corregir aquí usando juego[i].length
                System.out.print(juego[i][j] + " ");
            }
            System.out.println();  // Imprimir una nueva línea después de cada fila
        }
        System.out.print("\n");
    }

    public char[][] leerEntrada() throws IllegalArgumentException, FileNotFoundException {
        File juegoFile = new File(ruta);
        Scanner sc = new Scanner(juegoFile);
        
        // Lee el número de juegos
        int numeroDeJuegos = Integer.parseInt(sc.nextLine());
//
//        // Verifica si el número de juegos es válido
//        if (numeroDeJuegos < 1) {
//            sc.close();
//            throw new IllegalArgumentException("El número de juegos debe ser al menos 1.");
//        }

        // Salta la primera línea en blanco
        String lineaVacia = sc.nextLine(); // Saltar la primera línea en blanco.
        if (!lineaVacia.isEmpty()) {
            sc.close();
            throw new IllegalArgumentException("No existe la línea en blanco necesaria entre el número que indica la cantidad de juegos y el primer juego.");
    }

        // Lee cada juego y agrega la matriz a la lista
//        for (int i = 0; i < numeroDeJuegos; i++) {
            boolean matrizIncorrecta = false;

            // Lee la primera fila para determinar el número de columnas
            String primeraFila = sc.nextLine();
            primeraFila = primeraFila.replace(" ", "");
            char[] primeraFilaFragmentada = primeraFila.toCharArray();
            int numeroColumnas = primeraFilaFragmentada.length;

            // Verifica si el número de columnas es válido
            if (numeroColumnas > 20) {
                sc.close();
                throw new IllegalArgumentException("Número de columnas incorrecto en el juego.");
            }

            // Lista para almacenar los caracteres del juego
            ArrayList<Character> juegoArrayList = new ArrayList<>();

            // Agrega los caracteres de la primera fila a la lista
            for (char fragmento : primeraFilaFragmentada) {
                validarCaracter(fragmento);
                juegoArrayList.add(fragmento);
            }

            // Lee las filas restantes y agrega los caracteres a la lista
            while (sc.hasNextLine()) {
                String linea = sc.nextLine();
                linea = linea.replace(" ", "");
                
                if (linea.equals("")) break;

                char[] lineaFragmentada = linea.toCharArray();
                if (lineaFragmentada.length == numeroColumnas) {
                    for (char fragmento : lineaFragmentada) {
                        validarCaracter(fragmento);
                        juegoArrayList.add(fragmento);
                    }
                } else {
                    matrizIncorrecta = true;
                    break;
                }
            }

            // Si el número de columnas es 0, se ignora el juego
            if(numeroColumnas == 0){
               sc.close();
               return null;
            }

            // Si la matriz no es incorrecta, la agrega a la lista
            if (!matrizIncorrecta) {
                int numeroFilas = juegoArrayList.size() / numeroColumnas;
                char[][] juego = new char[numeroFilas][numeroColumnas];

                for (int j = 0; j < numeroFilas; j++) {
                    for (int k = 0; k < numeroColumnas; k++) {
                        int indiceArrayList = j * numeroColumnas + k;
                        juego[j][k] = juegoArrayList.get(indiceArrayList);
                    }
                }        
                sc.close();
                return juego;
                //todosLosJuegos.add(juego);
            } else {
            	sc.close();
                System.out.print("\n");// No agregamos el juego porque la matriz es incorrecta.
                return null;
            }
		//}
    }

    /**
     * Valida si un carácter es un color válido (V, R, A).
     *
     * @param caracter Carácter a validar.
     * @throws IllegalArgumentException Si el carácter no es válido.
     */
    private static void validarCaracter(char caracter) throws IllegalArgumentException {
        if (caracter != 'V' && caracter != 'R' && caracter != 'A') {
            throw new IllegalArgumentException("No existe el color introducido: " + caracter);
        }
    }
        
	public void realizarJugada(int fila, int columna) {
		tablero.calcularGrupos();
		ArrayList<Grupo> grupos = tablero.getGruposDelTablero();

		Grupo grupoContieneLaFichaElegida = null;
		
		for (Grupo grupo : grupos) { // Recorremos todos los grupos.
			ArrayList<int[]> listaFichas = grupo.getListaFichas();
			
			for(int[] ficha : listaFichas ) { // Recorremos todas las fichas del grupo en el que nos encontramos.
				if(ficha[0] == fila && ficha[1] == columna) {
					grupoContieneLaFichaElegida = grupo;		
				}
			}
		}
		//////////////////////////////////// TEMPORAL IMPLEMENTAR EXCISION ESQUEMA ///////////////////////////////////////
		if(grupoContieneLaFichaElegida == null) {
			System.out.println("Este seria el error de clickar en un grupo de una sola ficha.");
		} else {
			System.out.println("El grupo en el que se encuentra la ficha es: " + grupoContieneLaFichaElegida.color + grupoContieneLaFichaElegida.coordenadaX + grupoContieneLaFichaElegida.coordenadaY 
					+ "\n");
			ArrayList<int[]> listaFichasGrupoSolucion = grupoContieneLaFichaElegida.getListaFichas();
			for(int[] ficha : listaFichasGrupoSolucion ) {
				System.out.println(ficha[0] + ", "+ ficha[1] + "\n");
			}
		}	
		//////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////ELIMINAR FICHAS DEL GRUPO ///////////////////////////////////////
		
		
		//////////////////////////////////////////////////////////////////////////////////////
		////////////////////////////////////MOVER TABLERO///////////////////////////////////////
		
		
		//////////////////////////////////////////////////////////////////////////////////////
	}
}








