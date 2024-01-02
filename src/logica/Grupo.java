package logica;

import java.util.ArrayList;

/**
 * La clase Grupo representa un grupo de fichas en el juego Fichas Iguales.
 * Cada grupo tiene coordenadas, un color, un número de fichas eliminadas, una puntuación
 * y un estado probado que indica si ya se ha evaluado en el juego.
 */
public class Grupo {

    // Lista de coordenadas de las fichas en el grupo
    ArrayList<int[]> listaFichas;

    // Coordenada X de la ficha más a la izquierda y abajo del grupo
    int coordenadaX;

    // Coordenada Y de la ficha más a la izquierda y abajo del grupo
    int coordenadaY;

    // Número de fichas eliminadas en el grupo
    int numFichasEliminadas;

    // Color del grupo de fichas
    char color;

    // Puntuación del grupo de fichas
    int puntos;

    // Estado probado que indica si el grupo ya se ha evaluado
    boolean probado;

    /**
     * Constructor para un Grupo con una lista de fichas, coordenadas, número de fichas eliminadas,
     * color y puntuación.
     *
     * @param listaFichas         Lista de coordenadas de las fichas en el grupo.
     * @param coordenadaX         Coordenada X de la ficha más a la izquierda y abajo del grupo.
     * @param coordenadaY         Coordenada Y de la ficha más a la izquierda y abajo del grupo.
     * @param numFichasEliminadas Número de fichas eliminadas en el grupo.
     * @param color               Color del grupo de fichas.
     * @param puntos              Puntuación del grupo de fichas.
     */
    public Grupo(ArrayList<int[]> listaFichas, int coordenadaX, int coordenadaY, int numFichasEliminadas, char color, int puntos) {
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.numFichasEliminadas = numFichasEliminadas;
        this.color = color;
        this.puntos = puntos;
        probado = false;
        this.listaFichas = new ArrayList<>();
    }

    /**
     * Constructor para un Grupo con coordenadas, número de fichas eliminadas, color y puntuación.
     *
     * @param coordenadaX         Coordenada X de la ficha más a la izquierda y abajo del grupo.
     * @param coordenadaY         Coordenada Y de la ficha más a la izquierda y abajo del grupo.
     * @param numFichasEliminadas Número de fichas eliminadas en el grupo.
     * @param color               Color del grupo de fichas.
     * @param puntos              Puntuación del grupo de fichas.
     */
    public Grupo(int coordenadaX, int coordenadaY, int numFichasEliminadas, char color, int puntos) {
        this.coordenadaX = coordenadaX;
        this.coordenadaY = coordenadaY;
        this.numFichasEliminadas = numFichasEliminadas;
        this.color = color;
        this.puntos = puntos;
        probado = false;
        this.listaFichas = new ArrayList<>();
    }

    /**
     * Constructor por defecto para un Grupo.
     * Inicializa la lista de fichas como una nueva instancia de ArrayList.
     */
    public Grupo() {
        this.listaFichas = new ArrayList<>();
    }

    // Getter methods.

    /**
     * Obtiene la coordenada X de la ficha más a la izquierda y abajo del grupo.
     *
     * @return Coordenada X del grupo.
     */
    public int getCoordenadaX() {
        return coordenadaX;
    }

    /**
     * Obtiene la coordenada Y de la ficha más a la izquierda y abajo del grupo.
     *
     * @return Coordenada Y del grupo.
     */
    public int getCoordenadaY() {
        return coordenadaY;
    }

    /**
     * Obtiene el número de fichas eliminadas en el grupo.
     *
     * @return Número de fichas eliminadas.
     */
    public int getNumFichasEliminadas() {
        return numFichasEliminadas;
    }

    /**
     * Obtiene el color del grupo de fichas.
     *
     * @return Color del grupo.
     */
    public char getColor() {
        return color;
    }

    /**
     * Obtiene la puntuación del grupo de fichas.
     *
     * @return Puntuación del grupo.
     */
    public int getPuntos() {
        return puntos;
    }

    /**
     * Obtiene el estado probado del grupo.
     *
     * @return Estado probado del grupo.
     */
    public boolean getProbado() {
        return probado;
    }

    /**
     * Obtiene la lista de coordenadas de las fichas en el grupo.
     *
     * @return Lista de coordenadas de las fichas.
     */
    public ArrayList<int[]> getListaFichas() {
        return listaFichas;
    }

    // Setter methods.

    /**
     * Establece la coordenada X de la ficha más a la izquierda y abajo del grupo.
     *
     * @param coordenadaX Nueva coordenada X del grupo.
     */
    public void setCoordenadaX(int coordenadaX) {
        this.coordenadaX = coordenadaX;
    }

    /**
     * Establece la coordenada Y de la ficha más a la izquierda y abajo del grupo.
     *
     * @param coordenadaY Nueva coordenada Y del grupo.
     */
    public void setCoordenadaY(int coordenadaY) {
        this.coordenadaY = coordenadaY;
    }

    /**
     * Establece el número de fichas eliminadas en el grupo.
     *
     * @param numFichasEliminadas Nuevo número de fichas eliminadas.
     */
    public void setNumFichasEliminadas(int numFichasEliminadas) {
        this.numFichasEliminadas = numFichasEliminadas;
    }

    /**
     * Establece el color del grupo de fichas.
     *
     * @param color Nuevo color del grupo.
     */
    public void setColor(char color) {
        this.color = color;
    }

    /**
     * Establece la puntuación del grupo de fichas.
     *
     * @param puntos Nueva puntuación del grupo.
     */
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    /**
     * Establece el estado probado del grupo.
     *
     * @param probado Nuevo estado probado del grupo.
     */
    public void setProbado(boolean probado) {
        this.probado = probado;
    }

    /**
     * Establece la lista de coordenadas de las fichas en el grupo.
     *
     * @param listaFichas Nueva lista de coordenadas de las fichas.
     */
    public void setListaFichas(ArrayList<int[]> listaFichas) {
        this.listaFichas = listaFichas;
    }

    /**
     * Añade una ficha a la lista de fichas del grupo.
     *
     * @param ficha Nueva ficha a añadir.
     */
    public void añadirFicha(int[] ficha) {
        listaFichas.add(ficha);
    }

    /**
     * Genera una representación en formato de cadena del movimiento realizado por el grupo.
     *
     * @param grupo    Grupo que realizó el movimiento.
     * @param numFilas Número total de filas en el tablero.
     * @return Cadena que representa el movimiento realizado por el grupo.
     */
    public String generarMovimiento(Grupo grupo, int numFilas) {
        String movimiento;
        if (grupo.getNumFichasEliminadas() == 3) {
            movimiento = (" en (" + (numFilas - grupo.getCoordenadaX()) +
                    ", " + (grupo.getCoordenadaY() + 1) + "): eliminó " + grupo.getNumFichasEliminadas() +
                    " fichas de color " + grupo.getColor() + " y obtuvo " + grupo.getPuntos() + " punto.");
        } else {
            movimiento = (" en (" + (numFilas - grupo.getCoordenadaX()) +
                    ", " + (grupo.getCoordenadaY() + 1) + "): eliminó " + grupo.getNumFichasEliminadas() +
                    " fichas de color " + grupo.getColor() + " y obtuvo " + grupo.getPuntos() + " puntos.");
        }

        return movimiento;
    }
    
    public String generarAyuda(Grupo grupo, int numFilas){
    	String movimiento;
    	movimiento = "El movimiento recomendado es el que contiene la ficha de color " + grupo.getColor() + " en las coordenadas (" + (numFilas - grupo.getCoordenadaX()) +
                ", " + (grupo.getCoordenadaY() + 1) + ")";
    	
    	return movimiento;
    }

}