package gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import logica.MainLogica;
import logica.TratoFicheros;

import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuJugar extends JFrame implements ActionListener, UndoableEditListener{

	private static final long serialVersionUID = 1L;
	private JPanel panelPrincipal;
	private JTextField textField_NombreJuego;
	private JButton btSiguiente;
	private JPanel panelMatrizDelJuego;
//	private TratoFicheros file;
	private JLabel lblMensajeError;
	private JButton btReintentar;
	private JButton btTerminado;
	private JMenuItem btRehacer;
	private JMenuItem btDeshacer;
	private UndoManager undoManager = new UndoManager();
	private JButton[][] botones;
	private JMenuItem btSalir;
	private JMenuBar menuBar;
	private JLabel lblNombreFichero;
	private JTextField[][] matriz;
	private MainLogica jugarLogica;;
	
	/**
	 * Launch the application.
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
	 * Create the frame.
	 */
	public MenuJugar() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 947, 614);
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
		panelMatrizDelJuego.setBounds(262, 52, 391, 269);
		panelPrincipal.add(panelMatrizDelJuego);
		panelMatrizDelJuego.setVisible(false);
		
		lblMensajeError = new JLabel("ERROR");
		lblMensajeError.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensajeError.setForeground(Color.RED);
		lblMensajeError.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblMensajeError.setBounds(115, 349, 686, 23);
		panelPrincipal.add(lblMensajeError);
		lblMensajeError.setVisible(false);
		
		btReintentar = new JButton("Reintentar");
		btReintentar.setFont(new Font("Tahoma", Font.BOLD, 14));
		btReintentar.setBackground(SystemColor.activeCaption);
		btReintentar.addActionListener(this);
		btReintentar.setBounds(530, 402, 123, 37);
		panelPrincipal.add(btReintentar);
		btReintentar.setVisible(false);
			
		btTerminado = new JButton("Terminado");
		btTerminado.setFont(new Font("Tahoma", Font.BOLD, 14));
		btTerminado.setBackground(SystemColor.activeCaption);
		btTerminado.addActionListener(this);
		btTerminado.setBounds(262, 402, 123, 37);
		panelPrincipal.add(btTerminado);
		btTerminado.setVisible(false);
		
		panelMatrizDelJuego.setVisible(false);	
	}
	
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
				
				funcionalidadBoton(boton, i, j);
				
				if (contenido[m][n].equals("A")) { 			// Fichas azules.
					boton.setBackground(azulAgradable);
				
				} else if (contenido[m][n].equals("V")) {	// Fichas verdes.
					boton.setBackground(verdeAgradable);
				
				} else if (contenido[m][n].equals("R"))  {	// Fichas rojas.
					boton.setBackground(rojoAgradable);
					
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
        btReintentar.setVisible(true);
        textField_NombreJuego.setVisible(false);
        lblNombreFichero.setVisible(false);
        btTerminado.setVisible(true);
	}
	
	
	private void funcionalidadBoton(JButton boton, int fila, int columna){
		boton.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	jugarLogica.realizarJugada(fila, columna);
	        	               
	        }
	    });
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btSiguiente) {
			realizarAccionSiguiente();
			
		} else if (e.getSource()==btReintentar) {

		} else if(e.getSource()==btTerminado) {
			JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(btTerminado);
			frame.dispose(); 
		}
		
	}

	@Override
	public void undoableEditHappened(UndoableEditEvent e) {
		// TODO Auto-generated method stub
		undoManager.addEdit(e.getEdit());
        btDeshacer.setEnabled(undoManager.canUndo());
        btRehacer.setEnabled(undoManager.canRedo());
	}

}
