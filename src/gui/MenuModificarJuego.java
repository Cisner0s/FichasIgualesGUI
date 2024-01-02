package gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import logica.TratoFicheros;

/**
 * Clase que representa la ventana de modificación de juego.
 */
public class MenuModificarJuego extends JFrame {

    private static final long serialVersionUID = 1L;
    private JLabel lblNombreFichero;
    private JPanel panelPrincipal;
    private JPanel panelMatrizDelJuego;
    private JTextField textFieldNombreFichero;
    private JTextField matriz[][];
    private JMenuBar menuBar;
    private JMenuItem btRehacer;
    private JMenuItem btDeshacer;
    private JMenuItem btSalir;
    private JButton btTerminado;
    private JButton btSiguiente;
    private UndoManager undoManager = new UndoManager();
    Color magenta = new Color(255, 0, 255); // Instanciación de "Rosa Viejo"


    /**
     * Constructor de la clase `MenuModificarJuego`.
     */
    public MenuModificarJuego() {
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(100, 100, 947, 614);
        setTitle("Modificar Juego");
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
        btSalir.addActionListener(e -> realizarAccionSalir());
        menuBar.add(btSalir);
    }

    /**
     * Inicializa los componentes de la interfaz de usuario.
     */
    private void iniciarComponentes() {
        panelPrincipal = new JPanel();
        panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(panelPrincipal);
        panelPrincipal.setLayout(null);

        lblNombreFichero = new JLabel("Introduzca el nombre del fichero a editar:");
        lblNombreFichero.setFont(new Font("Arial", Font.BOLD, 17));
        lblNombreFichero.setHorizontalAlignment(SwingConstants.CENTER);
        lblNombreFichero.setBounds(76, 370, 374, 37);
        panelPrincipal.add(lblNombreFichero);

        textFieldNombreFichero = new JTextField();
        textFieldNombreFichero.setBounds(460, 371, 259, 38);
        panelPrincipal.add(textFieldNombreFichero);
        textFieldNombreFichero.setColumns(10);

        panelMatrizDelJuego = new JPanel();
        panelMatrizDelJuego.setBounds(256, 31, 418, 291);
        panelPrincipal.add(panelMatrizDelJuego);
        panelMatrizDelJuego.setVisible(false);

        btTerminado = new JButton("Terminado");
        btTerminado.setBackground(SystemColor.activeCaption);
        btTerminado.setFont(new Font("Tahoma", Font.BOLD, 14));
        btTerminado.setBounds(404, 437, 123, 37);
        panelPrincipal.add(btTerminado);
        btTerminado.setVisible(false);

        btSiguiente = new JButton("Siguiente");
        btSiguiente.setFont(new Font("Tahoma", Font.BOLD, 14));
        btSiguiente.setBackground(SystemColor.activeCaption);
        btSiguiente.addActionListener(e -> realizarAccionSiguiente());
        btSiguiente.setBounds(404, 437, 123, 37);
        panelPrincipal.add(btSiguiente);

        btTerminado.addActionListener(e -> realizarAccionTerminado());

        panelPrincipal.setVisible(true);
    }

    /**
     * Realiza la acción de pasar al siguiente paso.
     */
    private void realizarAccionSiguiente() {
        JLabel lblModifiqueLasCasillas = new JLabel("Modifique las casillas para editar el puzzle");
        lblModifiqueLasCasillas.setForeground(Color.RED);
        lblModifiqueLasCasillas.setFont(new Font("Arial", Font.BOLD, 14));
        lblModifiqueLasCasillas.setHorizontalAlignment(SwingConstants.CENTER);
        lblModifiqueLasCasillas.setBounds(114, 333, 686, 20);
        panelPrincipal.add(lblModifiqueLasCasillas);
        
        TratoFicheros file = new TratoFicheros();
        int fil = file.devolverNumFil(textFieldNombreFichero.getText());
        int col = file.devolverNumCol(textFieldNombreFichero.getText());
        String[][] contenido = file.devolverCont(textFieldNombreFichero.getText(), fil, col);
        inicialPanel(fil, col, contenido);
        btTerminado.setVisible(true);
        btSiguiente.setVisible(false);
    }
    
    /**
     * Realiza la acción de finalizar la edición y guardar los cambios.
     */
    private void realizarAccionTerminado() {
        TratoFicheros arch = new TratoFicheros();
        int fil = arch.devolverNumFil(textFieldNombreFichero.getText());
        int col = arch.devolverNumCol(textFieldNombreFichero.getText());
        arch.editarArchivo(textFieldNombreFichero.getText(), getContenidoPanel(fil, col));
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(btTerminado);
        frame.dispose();
    }

    /**
     * Realiza la acción de cerrar la ventana.
     */
    private void realizarAccionSalir() {
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(btSalir);
        frame.dispose();
    }

    /**
     * Obtiene el contenido del panel en forma de cadena de texto.
     * @param fil Número de filas de la matriz.
     * @param col Número de columnas de la matriz.
     * @return Contenido del panel en formato de cadena de texto.
     */
    private String getContenidoPanel(int fil, int col) {
        StringBuilder contenido = new StringBuilder();
        for (int i = 0; i < fil; i++) {
            for (int j = 0; j < col; j++) {
                if (j == col - 1) {
                    contenido.append(matriz[i][j].getText());
                } else {
                    contenido.append(matriz[i][j].getText()).append(" ");
                }
            }
            contenido.append("\n");
        }
        return contenido.toString();
    }

    /**
     * Inicializa el panel con la matriz de contenido proporcionada.
     * @param fil Número de filas de la matriz.
     * @param col Número de columnas de la matriz.
     * @param contenido Matriz de contenido para inicializar el panel.
     */
    private void inicialPanel(int fil, int col, String[][] contenido) {
        panelMatrizDelJuego.setLayout(new GridLayout(fil, col, 0, 0));
        matriz = new JTextField[fil][col];
        for (int i = 0; i < fil; i++) {
            for (int j = 0; j < col; j++) {
                JTextField texto = new JTextField(contenido[i][j]);
                texto.setText(contenido[i][j]);
                texto.setFont(new Font("Tahoma", Font.BOLD, 12));
                texto.setHorizontalAlignment(SwingConstants.CENTER);
                matriz[i][j] = texto;
                panelMatrizDelJuego.add(matriz[i][j]);
            }
        }
        panelMatrizDelJuego.setVisible(true);
    }

    /**
     * Método principal para iniciar la aplicación.
     * @param args Argumentos de la línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MenuModificarJuego frame = new MenuModificarJuego();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}