package logica;

import java.util.ArrayList;

import javax.swing.SwingWorker;

/**
 * La clase EstrategiaOptima implementa un algoritmo recursivo para encontrar la solución óptima en el juego Fichas Iguales.
 * Utiliza una estrategia de backtracking para evaluar todas las posibles combinaciones y selecciona la solución con la mayor puntuación.
 */
public class EstrategiaOptima extends SwingWorker<String, Void>{

    // Tablero inicial para la estrategia
    Tablero tableroInicial;

    // Puntuación de la solución óptima
    int puntuacionOptima;

    // Número de fichas restantes en la solución óptima
    int fichasRestantes;

    // Lista de grupos en la solución óptima
    ArrayList<Grupo> solucionOptima;

    // Lista temporal de grupos en la solución posible
    ArrayList<Grupo> solucionPosible;

    /**
     * Constructor para la EstrategiaOptima.
     *
     * @param tableroInicial Tablero inicial para la estrategia.
     */
    public EstrategiaOptima(Tablero tableroInicial) {
        this.tableroInicial = tableroInicial;
        this.solucionOptima = new ArrayList<>();
        this.solucionPosible = new ArrayList<>();
        this.puntuacionOptima = 0;
        this.fichasRestantes = 0;
    }

    /**
     * Inicia el proceso de juego y encuentra la solución óptima.
     *
     * @param tableroActual Tablero actual en el proceso de juego.
     * @param puntuacion    Puntuación acumulada durante el proceso de juego.
     */
    public void jugar(Tablero tableroActual, int puntuacion) {
        tableroActual.comprimirTablero(tableroActual);
        tableroActual.calcularGrupos();
    
        if (tableroActual.getGruposDelTablero().isEmpty()) {
            if (solucion(puntuacion, tableroActual.getMatriz())) {
                solucionOptima.clear();
                solucionOptima.addAll(new ArrayList<>(solucionPosible));
            }
            //fichasRestantes = 0;
            return;
        }
    
        for (int i = 0; i < tableroActual.getGruposDelTablero().size(); i++) {
            Grupo grupoSeleccionado = tableroActual.getGruposDelTablero().get(i);
            if (!grupoSeleccionado.getProbado()) {
                grupoSeleccionado.setProbado(true);
    
                puntuacion += grupoSeleccionado.getPuntos();
                solucionPosible.add(grupoSeleccionado);
    
                Tablero tableroNuevo = new Tablero();
                tableroNuevo.copiar(tableroActual);
                tableroNuevo.borrarGrupoSeleccionado(grupoSeleccionado);
    
                jugar(tableroNuevo, puntuacion);
    
                // Deshacer la selección del grupo antes de probar otro
                grupoSeleccionado.setProbado(false);
                puntuacion -= grupoSeleccionado.getPuntos();
                solucionPosible.remove(solucionPosible.size() - 1);
            }
        }
    }
    
    /**
     * Verifica si una solución es mejor que la actual basándose en la puntuación y las fichas restantes.
     *
     * @param puntuacion    Puntuación acumulada en la solución actual.
     * @param matrizFinal   Matriz final del tablero en la solución actual.
     * @return `true` si la solución es mejor, `false` de lo contrario.
     */
    public boolean solucion(int puntuacion, char[][] matrizFinal){
        int fichasRestantesTemporal = 0;
        for (int i = 0; i < matrizFinal.length; i++) {
            for (int j = 0; j < matrizFinal[0].length; j++) {
                if(matrizFinal[i][j] != 'O' & matrizFinal[i][j] != 'X'){
                    fichasRestantesTemporal ++;
                }
            }
        }

        if(fichasRestantesTemporal == 0){
            puntuacion += 1000;
        }

        if(puntuacion > puntuacionOptima || puntuacion == 0 & puntuacionOptima == 0){
            puntuacionOptima = puntuacion;
            fichasRestantes = fichasRestantesTemporal;
            return true;

        //}else if(puntuacion == puntuacionOptima){

        
        }else{
            return false;
        }
    }

//    /**
//     * Imprime la solución óptima.
//     */
//    public void imprimirSolucionOptima(){
//        //for (int i = 0; i < solucionOptima.size(); i++) {
//           Grupo grupoSolucion = solucionOptima.get(0);
//           grupoSolucion.generarAyuda(grupoSolucion, fichasRestantes);
//           System.out.print("\n");
//        //   i--;
//        //}
//
//        if(fichasRestantes == 1){
//            System.out.println("Puntuación final: " + puntuacionOptima +", quedando " + fichasRestantes + " ficha.");
//
//        } else {
//            System.out.println("Puntuación final: " + puntuacionOptima +", quedando " + fichasRestantes + " fichas.");
//        }
//    }

    /**
     * Realiza el trabajo principal en un hilo de fondo.
     *
     * @return La ayuda generada como una cadena de texto.
     * @throws Exception Si ocurre un error durante la ejecución.
     */
	@Override
	protected String doInBackground() throws Exception {
		jugar(this.tableroInicial, 0);

		Grupo grupoSolucion = solucionOptima.get(0);
      
		return grupoSolucion.generarAyuda(grupoSolucion, tableroInicial.getFilas());
	}
	
	  /**
     * Se llama cuando el trabajo principal ha sido completado.
     */
	@Override
    protected void done() {
        // Aquí puedes realizar acciones después de que la tarea ha finalizado
    }
}