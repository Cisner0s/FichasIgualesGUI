package src;

import java.util.Scanner;
import java.util.ArrayList;

/**
 * La clase MainFichasIguales contiene el método principal para ejecutar el juego Fichas Iguales.
 * Lee la entrada que representa los juegos, ejecuta la estrategia óptima y muestra los resultados en la consola.
 */
public class MainFichasIguales{

     /**
     * Método principal que lee la entrada, ejecuta la estrategia óptima y muestra los resultados en la consola.
     *
     * @param args Argumentos de línea de comandos (no utilizados en este caso).
     * @throws Exception Excepción general que puede ocurrir durante la ejecución.
     */
    public static void main(String[] args) throws Exception {

        // Lista para almacenar las matrices de todos los juegos.
        ArrayList<char[][]> todosLosJuegos = new ArrayList<>();

        try {

            // Lee la entrada y agrega las matrices a la lista.
            leerEntrada(todosLosJuegos);
        } catch (IllegalArgumentException e) {
           // System.out.println("Error en la entrada: " + e.getMessage());
           System.out.println();
        }

        // Itera sobre todas las matrices de juegos.
        for (int i = 0; i < todosLosJuegos.size(); i++) {
            
            // Crea un tablero a partir de la matriz actual.
            char[][] juego = todosLosJuegos.get(i);
            Tablero tablero = new Tablero(juego.length, juego[0].length, juego);

            // Crea una estrategia óptima y ejecuta el juego.
            EstrategiaOptima buscarEstrategiaOptima = new EstrategiaOptima(tablero);
            buscarEstrategiaOptima.jugar(tablero, 0);

            // Muestra los resultados del juego actual.
            System.out.print("Juego " + (++i) + ":" + "\n");
            --i;
            buscarEstrategiaOptima.imprimirSolucionOptima();

            // Agrega una línea en blanco si no es el último juego.
            if(i != todosLosJuegos.size() - 1){
                System.out.print("\n");
            }
        }

        // IMPRESION DE LAS MATRICES DE ENTRADA
        // for (char[][] juego : todosLosJuegos) {
        //     System.out.println("Matriz:");
        //     for (int i = 0; i < juego.length; i++) {
        //         for (int j = 0; j < juego[i].length; j++) {
        //             System.out.print(juego[i][j] + " ");
        //         }
        //         System.out.println(); // Nueva línea para cada fila
        //     }
        //     System.out.println(); // Nueva línea entre matrices
        // }
 
    }

    public static void leerEntrada(ArrayList<char[][]> todosLosJuegos) throws IllegalArgumentException {
        Scanner sc = new Scanner(System.in);

        // Lee el número de juegos
        int numeroDeJuegos = Integer.parseInt(sc.nextLine());

        // Verifica si el número de juegos es válido
        if (numeroDeJuegos < 1) {
            sc.close();
            throw new IllegalArgumentException("El número de juegos debe ser al menos 1.");
        }

        // Salta la primera línea en blanco
        String lineaVacia = sc.nextLine(); // Saltar la primera línea en blanco.
        if (!lineaVacia.isEmpty()) {
            sc.close();
            throw new IllegalArgumentException("No existe la línea en blanco necesaria entre el número que indica la cantidad de juegos y el primer juego.");
        }

        // Lee cada juego y agrega la matriz a la lista
        for (int i = 0; i < numeroDeJuegos; i++) {
            boolean matrizIncorrecta = false;

            // Lee la primera fila para determinar el número de columnas
            String primeraFila = sc.nextLine();
            char[] primeraFilaFragmentada = primeraFila.toCharArray();
            int numeroColumnas = primeraFilaFragmentada.length;

            // Verifica si el número de columnas es válido
            if (numeroColumnas > 20) {
                sc.close();
                throw new IllegalArgumentException("Número de columnas incorrecto en el juego " + (i + 1) + ".");
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
               return;
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

                todosLosJuegos.add(juego);
            } else {
                System.out.print("\n");// No agregamos el juego porque la matriz es incorrecta.
            }
        }
        sc.close();
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

}