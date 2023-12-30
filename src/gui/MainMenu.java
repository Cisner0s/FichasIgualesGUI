package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;

import java.awt.Label;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainMenu extends JFrame implements ActionListener, UndoableEditListener {

	private static final long serialVersionUID = 1L;
	private JPanel panelPrincipal;
	private JButton btCrearJuego;
	private JButton btModificarJuego;
	private JButton btJugar;
	private JLabel lblTituloMenuPrincipal;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws Exception {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu frame = new MainMenu();
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
	public MainMenu() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 932, 505);
		setTitle("Menu Principal");
		setLocationRelativeTo(null);
		setResizable(false);
		iniciarComponentes();
	}
	
	private void iniciarComponentes() {
		panelPrincipal = new JPanel();
		panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		btCrearJuego = new JButton("Crear Juego");
		btCrearJuego.setBounds(374, 327, 167, 43);
		btCrearJuego.addActionListener(this);
		panelPrincipal.add(btCrearJuego);
		
		btModificarJuego = new JButton("Modificar Juego");
		btModificarJuego.setBounds(374, 262, 167, 43);
		btModificarJuego.addActionListener(this);
		panelPrincipal.add(btModificarJuego);
		
		btJugar = new JButton("Jugar");
		btJugar.setBounds(374, 197, 167, 43);
		btJugar.addActionListener(this);
		panelPrincipal.add(btJugar);
		
		lblTituloMenuPrincipal = new JLabel("Fichas Iguales");
		lblTituloMenuPrincipal.setHorizontalAlignment(SwingConstants.CENTER);
		lblTituloMenuPrincipal.setFont(new Font("Arial Black", Font.BOLD, 31));
		lblTituloMenuPrincipal.setBounds(224, 61, 467, 90);
		panelPrincipal.add(lblTituloMenuPrincipal);
		
		JLabel lblAutor = new JLabel("by Diego Cisneros Morales");
		lblAutor.setFont(new Font("Book Antiqua", Font.BOLD | Font.ITALIC, 13));
		lblAutor.setHorizontalAlignment(SwingConstants.CENTER);
		lblAutor.setBounds(285, 126, 345, 43);
		panelPrincipal.add(lblAutor);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == btCrearJuego) { //Logica del boton Crear Juego
			try {
				TamanioMatrizPopup frame = new TamanioMatrizPopup();
				frame.setVisible(true);
			} catch (Exception excp) {
				excp.printStackTrace();
			}
		} else if(e.getSource() == btModificarJuego) { //Logica del boton Crear Juego
			try {
				MenuModificarJuego frame = new MenuModificarJuego();
				frame.setVisible(true);
			} catch (Exception excp) {
				excp.printStackTrace();
			}
		} else if(e.getSource() == btJugar) { //Logica del boton Crear Juego
			try {
				MenuJugar frame = new MenuJugar();
				frame.setVisible(true);	
			} catch (Exception excp) {
				excp.printStackTrace();
			}
		}
	}
	@Override
	public void undoableEditHappened(UndoableEditEvent e) {
		// TODO Auto-generated method stub
		
	}
}

