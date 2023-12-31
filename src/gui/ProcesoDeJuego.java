package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class ProcesoDeJuego extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton[][] botones;
    private JLabel lbl;
    private int ultimaFila;
    private int ultimaColumna;
    private int fil;
    private int col;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ProcesoDeJuego frame = new ProcesoDeJuego();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public ProcesoDeJuego(JButton[][] botones,int fil,int col, JLabel lbl) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.botones=botones;
        this.lbl=lbl;
        this.fil=fil;
        this.col=col;
	}

	  public void unirBotones() {
	    	char c = 92;
	    	for (int filas = 0; filas < fil; filas++) {
	    		for (int columnas = 0; columnas < col; columnas++) {
	    			JButton boton = botones[filas][columnas];
	    			
	                // Añade un action listener al botón
	                boton.addActionListener(new ActionListener() {
	                    @Override
	                    public void actionPerformed(ActionEvent e) {
	                    	// Obtiene la fila y columna del botón pulsado
	                    	int fila = (int) boton.getClientProperty("fila");
	                        int columna = (int) boton.getClientProperty("columna");
	                        
	                        // Si es el primer botón pulsado, guarda las coordenadas
	                        if (ultimaFila == 0 && ultimaColumna == 0) {
	                        	ultimaFila = fila;
	                            ultimaColumna = columna;
	                            
	                        }else {
	                        
	                            // Si el botón pulsado es par, cambia el texto del botón del medio
	                        	if (fila % 2 == 0 && columna % 2 == 0) {
	                                int diferenciaFila = Math.abs(fila - ultimaFila);
	                                if (diferenciaFila < 0) {
	                                    diferenciaFila = -diferenciaFila;
	                                }
	                                int diferenciaColumna = Math.abs(columna - ultimaColumna);
	                                if (diferenciaColumna < 0) {
	                                    diferenciaColumna = -diferenciaColumna;
	                                }
	                            
	                            // Si la diferencia es
	                            if (diferenciaFila == 0) {
	                                // Si está yendo a la derecha o izquierda
	                                if (columna > ultimaColumna) {
	                                    // Flecha hacia la derecha
	                                	if (botones[fila][columna - 1].getText().equals(" ")) {
	                                		 botones[fila][columna - 1].setText("-");
	                                	}else {
	                                		lbl.setText("ERROR: No se pueden cruzar las casillas.");
	                                		lbl.setForeground(Color.RED);
	                                		lbl.setVisible(true);
	                                		return;
	                                	}
	                                   
	                                } else {
	                                    // Flecha hacia la izquierda
	                                	if (botones[fila][columna + 1].getText().equals(" ")) {
	                                		botones[fila][columna + 1].setText("-");
	                                	}else {
	                                		lbl.setText("ERROR: No se pueden cruzar las casillas.");
	                                		lbl.setForeground(Color.RED);
	                                		lbl.setVisible(true);
	                                		return;
	                                	}   
	                                }
	                            } else if (diferenciaColumna == 0) {
	                                // Si está yendo hacia arriba o abajo
	                                if (fila > ultimaFila) {
	                                    // Flecha hacia abajo
	                                	if ( botones[fila - 1][columna].getText().equals(" ")) {
	                                		botones[fila - 1][columna].setText("|");
	                                	}else {
	                                		lbl.setText("ERROR: No se pueden cruzar las casillas.");
	                                		lbl.setForeground(Color.RED);
	                                		lbl.setVisible(true);
	                                		return;
	                                	}
	                                    
	                                } else {
	                                    // Flecha hacia arriba
	                                	if ( botones[fila + 1][columna].getText().equals(" ")) {
	                                		botones[fila + 1][columna].setText("|");
	                                	}else {
	                                		lbl.setText("ERROR: No se pueden cruzar las casillas.");
	                                		lbl.setForeground(Color.RED);
	                                		lbl.setVisible(true);
	                                		return;
	                                	}
	                                }
	                            } else if (diferenciaFila == 2 && diferenciaColumna == 2) {
	                                // Si está yendo hacia arriba derecha, abajo izquierda, arriba izquierda o abajo derecha
	                                if (fila > ultimaFila) {
	                                    // Flecha hacia abajo derecha o abajo izquierda
	                                    if (columna > ultimaColumna) {
	                                        // Flecha hacia abajo derecha
	                                    	if (botones[fila - 1][columna - 1].getText().equals(" ")) {
	                                    		botones[fila - 1][columna - 1].setText(String.valueOf(c));
	                                    	}else {
	                                    		lbl.setText("ERROR: No se pueden cruzar las casillas.");
	                                    		lbl.setForeground(Color.RED);
	                                    		lbl.setVisible(true);
	                                    		return;
	                                    	}
	                                    } else {
	                                        // Flecha hacia abajo izquierda
	                                    	if (botones[fila - 1][columna + 1].getText().equals(" ")) {
	                                    		 botones[fila - 1][columna + 1].setText("/");
	                                    	}else {
	                                    		lbl.setText("ERROR: No se pueden cruzar las casillas.");
	                                    		lbl.setForeground(Color.RED);
	                                    		lbl.setVisible(true);
	                                    		return;
	                                    	} 
	                                    }
	                                } else {
	                                    // Flecha hacia arriba derecha o arriba izquierda
	                                    if (columna > ultimaColumna) {
	                                        // Flecha hacia arriba derecha
	                                    	if (botones[fila + 1][columna - 1].getText().equals(" ")) {
	                                    		 botones[fila + 1][columna - 1].setText("/");
		                                   	}else {
		                                   		lbl.setText("ERROR: No se pueden cruzar las casillas.");
	                                    		lbl.setForeground(Color.RED);
		                                   		lbl.setVisible(true);
		                                   		return;
		                                   	} 
	                                    } else {
	                                        // Flecha hacia arriba izquierda
	                                    	if (botones[fila + 1][columna + 1].getText().equals(" ")) {
	                                    		 botones[fila + 1][columna + 1].setText(String.valueOf(c));
		                                   	}else {
		                                   		lbl.setText("ERROR: No se pueden cruzar las casillas.");
	                                    		lbl.setForeground(Color.RED);
		                                   		lbl.setVisible(true);
		                                   		return;
		                                   	} 
	                                    }
	                                }
	                            }
	                            } else {
	                                // Mensaje de error al usuario porque el botón pulsado no es par
	                        		lbl.setForeground(Color.RED);
	                            	lbl.setText("ERROR: el botón pulsado no es par.");
	                            	lbl.setVisible(true);
	                            	return;
	                            
	                            }

	                            // Actualiza las coordenadas del último botón pulsado
	                            ultimaFila = fila;
	                            ultimaColumna = columna;
	                        }
	                        if (fila== fil-1 && columna==col-1) {
	                        	lbl.setForeground(Color.black);
	                        	lbl.setText("Has llegado a la ultima casilla.");
	                        	lbl.setVisible(true);
	                        	
	                        	return;
	                        }
	                    }});

	                    // Guarda la fila y columna del botón en una propiedad
	                    boton.putClientProperty("fila", filas);
	                    boton.putClientProperty("columna", columnas);
	                }
	            }
	        }
}
