package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import logica.EstrategiaOptima;
import logica.MainLogica;
import logica.TratoFicheros;

/**
 * La clase MenuJugar representa la interfaz gráfica para el juego.
 */
public class MenuJugar extends JFrame implements ActionListener, UndoableEditListener{

	private static final long serialVersionUID = 1L;
	private JLabel lblNombreFichero;
	private JLabel lblMensajeError;
	private JPanel panelPrincipal;
	private JPanel panelMatrizDelJuego;
	private JTextField textField_NombreJuego;
	private JButton btSiguiente;
	private JButton btAyuda;
	private JButton btTerminado;
	private JButton[][] botones;
	private JMenuItem btRehacer;
	private JMenuItem btDeshacer;
	private JMenuItem btSalir;
	private JMenuBar menuBar;
	private JDialog dialog;
	private MainLogica jugarLogica;
	private UndoManager undoManager = new UndoManager();
	
	  /**
     * Método principal para lanzar la aplicación.
     * 
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuJugar frame = new MenuJugar();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	  /**
     * Constructor de la clase MenuJugar.
     */
	public MenuJugar() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 947, 550);
		setTitle("Jugar");
		setLocationRelativeTo(null);
		setResizable(false);
		menuBar = new JMenuBar();
        menuBar.setForeground(SystemColor.activeCaption);
        menuBar.setBackground(SystemColor.desktop);
        setJMenuBar(menuBar);
		iniciarBarra();
		iniciarComponentes();
		
	}
	
    /**
     * Inicializa la barra de menú.
     */
	private void iniciarBarra() {
		btRehacer = new JMenuItem("Rehacer");
        btRehacer.addActionListener(e -> {
            try {
                undoManager.redo();
            } catch (CannotUndoException ex) {
                // No hay acciones disponibles para rehacer
            }
            btDeshacer.setEnabled(undoManager.canRedo());
            btRehacer.setEnabled(undoManager.canUndo());
        });
        
        btRehacer.setBackground(SystemColor.activeCaption);
        btRehacer.setForeground(SystemColor.desktop);
        btRehacer.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menuBar.add(btRehacer);

        btDeshacer = new JMenuItem("Deshacer");
        btDeshacer.addActionListener(e -> {
            try {
                undoManager.undo();
            } catch (CannotRedoException ex) {
                // No hay acciones disponibles para deshacer
            }
            btDeshacer.setEnabled(undoManager.canRedo());
            btRehacer.setEnabled(undoManager.canUndo());
        });
        btDeshacer.setBackground(SystemColor.activeCaption);
        btDeshacer.setForeground(SystemColor.desktop);
        btDeshacer.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menuBar.add(btDeshacer);

        btSalir = new JMenuItem("Salir");
        btSalir.setBackground(SystemColor.activeCaption);
        btSalir.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(btSalir);
			    frame.dispose();
			}
		});
        menuBar.add(btSalir);
	}

	
    /**
     * Inicializa los componentes de la interfaz gráfica.
     */
	private void iniciarComponentes() {
		panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		lblNombreFichero = new JLabel("Introduzca el nombre del fichero con el puzzle a resolver: ");
		lblNombreFichero.setFont(new Font("Arial", Font.BOLD, 17));
		lblNombreFichero.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreFichero.setBounds(175, 137, 565, 35);
		panelPrincipal.add(lblNombreFichero);
		lblNombreFichero.setVisible(true);
		
		textField_NombreJuego = new JTextField();
		textField_NombreJuego.setColumns(10);
		textField_NombreJuego.getDocument().addUndoableEditListener(this);
		textField_NombreJuego.setBounds(376, 201, 163, 35);
		panelPrincipal.add(textField_NombreJuego);
		textField_NombreJuego.setVisible(true);
		
		btSiguiente = new JButton("Siguiente");
		btSiguiente.setFont(new Font("Tahoma", Font.BOLD, 14));
		btSiguiente.setBackground(SystemColor.activeCaption);
		btSiguiente.addActionListener(this);
		btSiguiente.setBounds(396, 278, 123, 37);
		panelPrincipal.add(btSiguiente);
		btSiguiente.setVisible(true);
		
		panelMatrizDelJuego = new JPanel();
		//panelMatrizDelJuego.getDocument().addUndoableEditListener(this);
		panelMatrizDelJuego.setBounds(262, 52, 391, 269);
		panelPrincipal.add(panelMatrizDelJuego);
		panelMatrizDelJuego.setVisible(false);
		
		lblMensajeError = new JLabel("ERROR: El grupo seleccionado solo contiene una ficha. No se puede seleccionar");
		lblMensajeError.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensajeError.setForeground(Color.RED);
		lblMensajeError.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMensajeError.setBounds(115, 349, 686, 23);
		panelPrincipal.add(lblMensajeError);
		lblMensajeError.setVisible(false);
		
		btAyuda = new JButton("Ayuda");
		btAyuda.setFont(new Font("Tahoma", Font.BOLD, 14));
		btAyuda.setBackground(SystemColor.activeCaption);
		btAyuda.addActionListener(this);
		btAyuda.setBounds(530, 402, 123, 37);
		panelPrincipal.add(btAyuda);
		btAyuda.setVisible(false);
			
		btTerminado = new JButton("Terminado");
		btTerminado.setFont(new Font("Tahoma", Font.BOLD, 14));
		btTerminado.setBackground(SystemColor.activeCaption);
		btTerminado.addActionListener(this);
		btTerminado.setBounds(262, 402, 123, 37);
		panelPrincipal.add(btTerminado);
		btTerminado.setVisible(false);
		
		panelMatrizDelJuego.setVisible(false);	
	}
	

    /**
     * Crea los botones para la interfaz gráfica del juego.
     * 
     * @param fil       Número de filas.
     * @param col       Número de columnas.
     * @param contenido Contenido del juego.
     * @param botones   Matriz de botones.
     */
	public void crearBotones(int fil, int col, String[][] contenido, JButton[][] botones) {
		Color azulAgradable = new Color(30, 144, 255);  // Dodger Blue
		Color verdeAgradable = new Color(60, 179, 113);  // Medium Sea Green
		Color rojoAgradable = new Color(255, 69, 0);  // Red-Orange

		int n = 0;
		int m = 0;
		for (int i = 0 ; i < fil ; i++) {
			for (int j = 0 ; j < col ; j++){
				JButton boton = new JButton("");
				boton.setText(contenido[m][n]);
				boton.setFont(new Font("Arial", Font.BOLD, 14));
				
				if (contenido[m][n].equals("A")) { 			// Fichas azules.
					boton.setBackground(azulAgradable);
					funcionalidadBoton(boton, i, j);
				
				} else if (contenido[m][n].equals("V")) {	// Fichas verdes.
					boton.setBackground(verdeAgradable);
					funcionalidadBoton(boton, i, j);
				
				} else if (contenido[m][n].equals("R"))  {	// Fichas rojas.
					boton.setBackground(rojoAgradable);
					funcionalidadBoton(boton, i, j);
					
				} else { 									// Huecos sin ficha.
					boton.setBackground(Color.BLACK);
				}
				
				botones[i][j] = boton;
				panelMatrizDelJuego.add(boton);
				n++;
			}
				n = 0;
				m++;
		}
	}
	

    /**
     * Realiza la acción asociada al botón "Siguiente".
     */
	private void realizarAccionSiguiente() {
    	try {
			this.jugarLogica = new MainLogica(textField_NombreJuego.getText());
		} catch (IllegalArgumentException e) {

			e.printStackTrace();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
    	
        TratoFicheros file = new TratoFicheros();
        int fil = file.devolverNumFil(textField_NombreJuego.getText());
        int col = file.devolverNumCol(textField_NombreJuego.getText());
		String[][] contenido = file.devolverCont(textField_NombreJuego.getText(), fil, col);
		
		botones = new JButton[fil][col];
		panelMatrizDelJuego.setLayout(new GridLayout(fil, col, 0, 0));
		crearBotones(fil,col, contenido,botones);

		panelMatrizDelJuego.setVisible(true);
        btSiguiente.setVisible(false);
        btAyuda.setVisible(true);
        textField_NombreJuego.setVisible(false);
        lblNombreFichero.setVisible(false);
        btTerminado.setVisible(true);
	}
	
	
    /**
     * Agrega funcionalidades a los botones de la interfaz gráfica.
     * 
     * @param boton   Botón al que se le agregará funcionalidad.
     * @param fila    Fila del botón.
     * @param columna Columna del botón.
     */
	private void funcionalidadBoton(JButton boton, int fila, int columna){
		boton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	if(boton.getBackground() == Color.BLACK ) {
	        		
	        	} else {
	        		boolean jugadaPosible = jugarLogica.realizarJugada(fila, columna, botones);
	        	   	
		        	if(jugadaPosible) {
		        		lblMensajeError.setVisible(false);
		        		for (int i = 0; i < botones.length; i++) {
							for (int j = 0; j < botones[0].length; j++) {
								panelMatrizDelJuego.add(botones[i][j]);
							}	
						}
		        	} else {
		        		lblMensajeError.setVisible(true);
		        	}
	        	}
	        }
	    });
	}
	
	
	  /**
     * Ofrece ayuda al usuario mostrando la solución óptima.
     */
	public void ofrecerAyuda() {
	    String mensaje = "Se está calculando la jugada más óptima...";
	    mostrarMensaje(mensaje, "Ayuda", 700, 200);  // Ajusté el ancho de la ventana

	    EstrategiaOptima estrategiaOptima = new EstrategiaOptima(jugarLogica.getTablero());
	    estrategiaOptima.execute();
	    estrategiaOptima.addPropertyChangeListener(evt -> {
	        if ("state".equals(evt.getPropertyName()) && SwingWorker.StateValue.DONE.equals(evt.getNewValue())) {
	            try {
	                // Obtener la solución óptima
	                String optimalSolution = estrategiaOptima.get();

	                // Manejar la solución óptima en el EDT (actualizar la interfaz de usuario, etc.)
	                // Por ejemplo, podrías mostrar la solución en algún componente de tu interfaz.
	                mostrarSolucionEnInterfaz(optimalSolution);
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	    });
	}

	
    /**
     * Muestra la solución óptima en la interfaz gráfica.
     * 
     * @param optimalSolution Solución óptima del juego.
     */
	private void mostrarSolucionEnInterfaz(String optimalSolution) {
	    // Actualiza el contenido del JLabel en el JDialog con la solución óptima
	    if (dialog != null) {
	        JLabel label = (JLabel) ((JPanel) dialog.getContentPane().getComponent(0)).getComponent(0);
	        label.setText(optimalSolution);

	        // Remueve la barra de progreso y añade un botón "Aceptar"
	        JPanel panel = (JPanel) dialog.getContentPane().getComponent(0);
	        panel.remove(1);  // Remueve la barra de progreso
	        panel.add(crearBotonAceptar(), BorderLayout.SOUTH);  // Añade el botón "Aceptar"
	        
	        // Redimensiona la ventana para que quepa el botón "Aceptar"
	        dialog.setSize(1000, 250);  // Ajusté el ancho de la ventana

	        // Actualiza el estado del JDialog para que se redibuje correctamente
	        dialog.revalidate();
	        dialog.repaint();
	        dialog.setLocationRelativeTo(null);
	    }
	}

	
    /**
     * Crea un botón "Aceptar" para el diálogo de ayuda.
     * 
     * @return Panel que contiene el botón "Aceptar".
     */
	private JPanel crearBotonAceptar() {
	    JButton botonAceptar = new JButton("Aceptar");
	    botonAceptar.addActionListener(e -> {
	        // Cierra el JDialog al hacer clic en el botón "Aceptar"
	        if (dialog != null) {
	            dialog.dispose();
	        }
	    });

	    // Centra el botón en el JPanel
	    JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
	    panelBoton.add(botonAceptar);
	    
	    return panelBoton;
	}

    /**
     * Muestra un mensaje en un diálogo con una barra de progreso.
     * 
     * @param mensaje Mensaje a mostrar.
     * @param titulo  Título del diálogo.
     * @param ancho   Ancho del diálogo.
     * @param alto    Alto del diálogo.
     */
	private void mostrarMensaje(String mensaje, String titulo, int ancho, int alto) {
	    dialog = new JDialog();
	    dialog.setTitle(titulo);

	    JPanel panel = new JPanel(new BorderLayout());
	    JLabel label = new JLabel(mensaje);
	    label.setFont(new Font("Arial", Font.BOLD, 16));
	    label.setHorizontalAlignment(JLabel.CENTER);
	    //label.setVerticalAlignment(JLabel.CENTER);
	    panel.add(label, BorderLayout.CENTER);

	    JProgressBar progressBar = new JProgressBar();
	    progressBar.setIndeterminate(true);
	    panel.add(progressBar, BorderLayout.SOUTH);

	    dialog.getContentPane().add(panel);
	    dialog.setSize(ancho, alto);
	    dialog.setLocationRelativeTo(null);
	    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

	    // Muestra la ventana
	    dialog.setVisible(true);
	}


	   /**
     * Maneja las acciones de los botones y menús.
     * 
     * @param e Evento de acción.
     */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btSiguiente) {
			menuBar.setVisible(false);
			realizarAccionSiguiente();
			
		} else if (e.getSource()==btAyuda) {
			ofrecerAyuda();

		} else if(e.getSource()==btTerminado) {
			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(btTerminado);
			frame.dispose(); 
		}
		
	}

	
    /**
     * Maneja los eventos de edición deshacer y rehacer.
     * 
     * @param e Evento de edición deshacer.
     */
	@Override
	public void undoableEditHappened(UndoableEditEvent e) {
		// TODO Auto-generated method stub
		undoManager.addEdit(e.getEdit());
        btDeshacer.setEnabled(undoManager.canUndo());
        btRehacer.setEnabled(undoManager.canRedo());
	}

}
