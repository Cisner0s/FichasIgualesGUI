package gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TamanioMatrizPopup extends JFrame {

	  private static final long serialVersionUID = 1L;
	    private JTextField filasTextField;
	    private JTextField columnasTextField;
	    private JButton aceptarButton;

	    public TamanioMatrizPopup() {
	        setTitle("Tamaño de la Matriz");
	        setSize(312, 170);
	        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	        setLocationRelativeTo(null);
	        setResizable(false);
	        setAlwaysOnTop(true);

	        JLabel labelMatriz = new JLabel("Matriz");
	        labelMatriz.setFont(new Font("Arial Black", Font.PLAIN, 13));
	        JLabel labelFilas = new JLabel("Filas:");
	        labelFilas.setFont(new Font("Arial Black", Font.PLAIN, 11));
	        JLabel labelColumnas = new JLabel("Columnas:");
	        labelColumnas.setFont(new Font("Arial Black", Font.PLAIN, 11));
	        filasTextField = new JTextField(5);
	        columnasTextField = new JTextField(5);
	        aceptarButton = new JButton("Aceptar");
	        aceptarButton.setFont(new Font("Arial", Font.PLAIN, 11));
	        aceptarButton.setEnabled(false); // Inicialmente deshabilitado

	        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

	        getContentPane().add(labelMatriz);
	        getContentPane().add(labelFilas);
	        getContentPane().add(filasTextField);
	        getContentPane().add(labelColumnas);
	        getContentPane().add(columnasTextField);
	        getContentPane().add(aceptarButton);

	        // Agregar DocumentListener a los campos de texto
	        DocumentListener documentListener = new DocumentListener() {
	            @Override
	            public void insertUpdate(DocumentEvent e) {
	                habilitarBoton();
	            }

	            @Override
	            public void removeUpdate(DocumentEvent e) {
	                habilitarBoton();
	            }

	            @Override
	            public void changedUpdate(DocumentEvent e) {
	                habilitarBoton();
	            }

	        };

	        filasTextField.getDocument().addDocumentListener(documentListener);
	        columnasTextField.getDocument().addDocumentListener(documentListener);

	        aceptarButton.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                aceptarButtonClick();
	            }
	        });
	    }

	    private void aceptarButtonClick() {
	        int filas, columnas;
	        try {
	            filas = Integer.parseInt(filasTextField.getText());
	            columnas = Integer.parseInt(columnasTextField.getText());

	            if (filas < 1 || filas > 20 || columnas < 1 || columnas > 20) {
	                JOptionPane.showMessageDialog(this, "Por favor, ingrese valores válidos (entre 1 y 20).", "Error", JOptionPane.ERROR_MESSAGE);
	                filasTextField.setText("");
	                columnasTextField.setText("");
	            } else {
	                // Llamada al método en MenuCrearJuego con los valores de filas y columnas
	                MenuCrearJuego menuCrearJuego = new MenuCrearJuego(filas, columnas);
	                menuCrearJuego.setVisible(true);
	                dispose(); // Cierra la ventana actual
	            }
	        } catch (NumberFormatException ex) {
	            JOptionPane.showMessageDialog(this, "Por favor, ingrese números enteros válidos.", "Error", JOptionPane.ERROR_MESSAGE);
	            filasTextField.setText("");
	            columnasTextField.setText("");
	        }
	    }

	    private void habilitarBoton() {
	        // Habilitar el botón si ambos campos tienen contenido
	        aceptarButton.setEnabled(!filasTextField.getText().isEmpty() && !columnasTextField.getText().isEmpty());
	    }
	    
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                TamanioMatrizPopup frame = new TamanioMatrizPopup();
	                frame.setVisible(true);
	            }
	        });
	    }
}
