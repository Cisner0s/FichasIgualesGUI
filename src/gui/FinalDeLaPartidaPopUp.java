package gui;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import logica.TratoFicheros;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.invoke.StringConcatFactory;

public class FinalDeLaPartidaPopUp extends JDialog {

    private static final long serialVersionUID = 1L;
    private String nombreDelArchivoSolucion;
    private String solucion;
    private int puntuacion;
    private int fichas;

    public FinalDeLaPartidaPopUp(String mensaje, int puntuacion, String solucion, int fichas) {
        
    	this.solucion = solucion;
    	this.puntuacion = puntuacion;
    	this.fichas = fichas;
    	
    	// Configurar el JDialog
        setTitle("¡Partida Finalizada!");
        setSize(1047, 242);
        setLocationRelativeTo(null);
        setModal(true);
        getContentPane().setBackground(Color.BLACK); // Fondo negro

        // Crear un JPanel para el contenido
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.BLACK); // Fondo negro

        // Etiqueta para mostrar el mensaje de felicitación
        JLabel labelMensaje = new JLabel(mensaje);
        labelMensaje.setBounds(10, 20, 1011, 40);
        labelMensaje.setHorizontalAlignment(SwingConstants.CENTER);
        labelMensaje.setFont(new Font("Arial", Font.BOLD, 18));
        labelMensaje.setForeground(Color.WHITE); // Letras blancas
        panel.add(labelMensaje);

        // Etiqueta para mostrar la puntuación
        JLabel labelPuntuacion = new JLabel("Puntuación: " + puntuacion);
        labelPuntuacion.setBounds(340, 80, 350, 30);
        labelPuntuacion.setHorizontalAlignment(SwingConstants.CENTER);
        labelPuntuacion.setFont(new Font("Arial", Font.PLAIN, 16));
        labelPuntuacion.setForeground(Color.WHITE); // Letras blancas
        panel.add(labelPuntuacion);

        // Botón Aceptar
        JButton btnAceptar = new JButton("Aceptar");
        btnAceptar.setBounds(363, 150, 150, 30);
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(btnAceptar);

        // Botón Guardar
        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.setBounds(523, 150, 150, 30);
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarVentanaGuardar();
            }
        });
        panel.add(btnGuardar);

        // Agregar el panel al JDialog
        getContentPane().add(panel);
    }

    private void mostrarVentanaGuardar() {
        JTextField textField = new JTextField();
        Object[] message = {
                "Introduce el nombre del archivo:", textField
        };

        int option = JOptionPane.showOptionDialog(null, message, "Guardar Solución",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);

        if (option == JOptionPane.OK_OPTION) {
            nombreDelArchivoSolucion = textField.getText();
            JOptionPane.showMessageDialog(null, "Solución guardada como: " + nombreDelArchivoSolucion);
        }
        
        TratoFicheros tratoFicheros = new TratoFicheros();
        tratoFicheros.crearArchSolucion(nombreDelArchivoSolucion, solucion, puntuacion, fichas);
    }

    public static void main(String[] args) {
        // Ejemplo de uso
        String mensaje = "¡Felicidades! Has completado la partida.";
        int puntuacion = 150;  // Reemplaza esto con la puntuación real obtenida
        String solucion = "solucion.txt";  // Reemplaza esto con el nombre de la solución
        int fichas = 0;

        FinalDeLaPartidaPopUp popUp = new FinalDeLaPartidaPopUp(mensaje, puntuacion, solucion, fichas);
        popUp.setVisible(true);
    }
}
