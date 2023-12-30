package gui; 

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import logica.TratoFicheros;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuCrearJuego extends JFrame implements ActionListener, UndoableEditListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel panelPrincipal;
    private JMenuItem menuDeshacer;
    private JMenuItem menuRehacer;
    private UndoManager undoManager = new UndoManager();
    private JMenuBar menuBar;
    private JMenuItem mntmSalir;
    private int filas;
    private int columnas;
	private JButton btCrearJuego;
	private JTextField textField_NombreDelJuego;
	private Container lblNombreDelJuego;
	private JPanel panelMatrizDelJuego;
	private JTextField[][] matriz;
	private JLabel lblMensajeError;
	private JButton btAyuda;

    public MenuCrearJuego(int filas, int columnas) {
        this.filas = filas;
        this.columnas = columnas;

        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setBounds(100, 100, 947, 614);
        setTitle("Crear Juego");
        setLocationRelativeTo(null);
        setResizable(false);
        menuBar = new JMenuBar();
        menuBar.setForeground(SystemColor.activeCaption);
        menuBar.setBackground(SystemColor.desktop);
        setJMenuBar(menuBar);
        iniciarBarra();
        iniciarComponentes();
        iniciarPanel();
    }

	private void iniciarComponentes() {
        panelPrincipal = new JPanel();
        panelPrincipal.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(panelPrincipal);
        panelPrincipal.setLayout(null);

        this.setVisible(true);
        
		lblMensajeError = new JLabel("ERROR: La matriz no tiene todas sus posiciones completas. Inténtelo de nuevo.");
		lblMensajeError.setForeground(Color.RED);
		lblMensajeError.setFont(new Font("Arial", Font.BOLD, 14));
		lblMensajeError.setHorizontalAlignment(SwingConstants.CENTER);
		lblMensajeError.setBounds(122, 34, 686, 25);
		panelPrincipal.add(lblMensajeError);
		lblMensajeError.setVisible(false);
        
        btCrearJuego = new JButton("Crear");
		btCrearJuego.setBackground(SystemColor.activeCaption);
		btCrearJuego.addActionListener(this);
		btCrearJuego.setFont(new Font("Arial", Font.BOLD, 16));
		btCrearJuego.setBounds(353, 461, 113, 29);
		panelPrincipal.add(btCrearJuego);
		btCrearJuego.setVisible(true);
		
		btAyuda = new JButton("Ayuda");
		btAyuda.setBackground(SystemColor.activeCaption);
		btAyuda.addActionListener(this);
		btAyuda.setFont(new Font("Arial", Font.BOLD, 16));
		btAyuda.setBounds(476, 461, 113, 29);
		panelPrincipal.add(btAyuda);
		btAyuda.setVisible(true);
		
		textField_NombreDelJuego = new JTextField();
		textField_NombreDelJuego.setHorizontalAlignment(SwingConstants.CENTER);
		textField_NombreDelJuego.setBounds(330, 405, 269, 29);
		textField_NombreDelJuego.addActionListener(this);
		panelPrincipal.add(textField_NombreDelJuego);
		textField_NombreDelJuego.setColumns(10);
		textField_NombreDelJuego.setVisible(true);
		
		lblNombreDelJuego = new JLabel("Introduzca el nombre del juego:");
		lblNombreDelJuego.setFont(new Font("Arial", Font.BOLD, 14));
		//lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNombreDelJuego.setBounds(86, 406, 676, 25);
		panelPrincipal.add(lblNombreDelJuego);
		lblNombreDelJuego.setVisible(true);
	
		
		panelMatrizDelJuego = new JPanel();
		panelMatrizDelJuego.setBounds(267, 70, 394, 279);
		panelPrincipal.add(panelMatrizDelJuego);
		panelMatrizDelJuego.setVisible(true);
		
	
    }

    private void iniciarBarra() {
        menuRehacer = new JMenuItem("Rehacer");
        menuRehacer.addActionListener(e -> {
            try {
                undoManager.redo();
            } catch (CannotUndoException ex) {
                // No hay acciones disponibles para rehacer
            }
            menuDeshacer.setEnabled(undoManager.canRedo());
            menuRehacer.setEnabled(undoManager.canUndo());
        });

        menuRehacer.setBackground(SystemColor.activeCaption);
        menuRehacer.setForeground(SystemColor.desktop);
        menuRehacer.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menuBar.add(menuRehacer);

        menuDeshacer = new JMenuItem("Deshacer");
        menuDeshacer.addActionListener(e -> {
            try {
                undoManager.undo();
            } catch (CannotRedoException ex) {
                // No hay acciones disponibles para deshacer
            }
            menuDeshacer.setEnabled(undoManager.canRedo());
            menuRehacer.setEnabled(undoManager.canUndo());
        });
        menuDeshacer.setBackground(SystemColor.activeCaption);
        menuDeshacer.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menuBar.add(menuDeshacer);

        mntmSalir = new JMenuItem("Salir");
        mntmSalir.setBackground(SystemColor.activeCaption);
        mntmSalir.setFont(new Font("Segoe UI", Font.BOLD, 14));
        mntmSalir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(mntmSalir);
                frame.dispose();
            }
        });
        menuBar.add(mntmSalir);
    }

	public void iniciarPanel() {
		panelMatrizDelJuego.setLayout(new GridLayout(filas, columnas, 0, 0));
		matriz = new JTextField[filas][columnas];
		for (int i =0;i<filas;i++) {
			for (int j =0;j<columnas;j++) {
				JTextField texto = new JTextField("");	
				texto.setFont(new Font("Tahoma", Font.BOLD, 12));
				texto.setHorizontalAlignment(SwingConstants.CENTER);
				matriz[i][j]= texto;
				matriz[i][j].getDocument().addUndoableEditListener(this);
				panelMatrizDelJuego.add(texto);
			}
		}
	}

	public String getContenidoPanel() {
		StringBuffer contenido = new StringBuffer();
		contenido.append("1\n\n");
		String text;
		for (int i =0;i<filas;i++) {
			for (int j =0;j<columnas;j++) {
				text = matriz[i][j].getText().trim();
				//System.out.println(text + " Este es el texto sin dividir");
				if (!text.isEmpty() && !text.equals(" ")) {
					try {
						if (!("R".equals(text) || "V".equals(text) || "A".equals(text))) {
							//System.out.println(text + " Este contenido es erroneo.");
							return "error";
						}
					}catch(IllegalArgumentException e) {
							return "error2";
					}
				
					if (j == columnas-1) {
						contenido.append(text);
					}else {
						contenido.append(text).append(" ");
					}  
					text = "";
				} else {
					return "error2";
				}
			}
			contenido.append("\n");
		}
		return contenido.toString();
	}
	
	public boolean tablaCompletamenteRellenada() {
		for (int i =0;i<filas;i++) {
			for (int j =0;j<columnas;j++) {
				if (matriz[i][j].getText()== "") {
					return false;
				}
			}
		
		}
		return true;
	}
	
    private void realizarAccionCrearJuego() {
        String contenido = getContenidoPanel();

        if (tablaCompletamenteRellenada() == false || contenido.equals("error2")) {
            lblMensajeError.setText("ERROR: La matriz no tiene todas sus posiciones completas. Inténtelo de nuevo.");
            lblMensajeError.setVisible(true);

        } else if (contenido.equals("error")) {
            lblMensajeError.setText("ERROR: el color seleccionado no se encuentra disponible. Inténtelo de nuevo con V, R o A.");
            lblMensajeError.setVisible(true);

        } else if (textField_NombreDelJuego.getText().isEmpty()) {
            lblMensajeError.setText("ERROR: No se ha introducido un nombre para el archivo.");
            lblMensajeError.setVisible(true);

        } else {
            TratoFicheros arch = new TratoFicheros();
            arch.crearArch(textField_NombreDelJuego.getText() + ".txt", contenido);
            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(lblMensajeError);
            frame.dispose();
        }
    }

    private void realizarAccionAyuda() {
        JOptionPane.showMessageDialog(this,
                "El funcionamiento de este menú es el siguiente:\n"
                        + "\nLa matriz representa el tablero de fichas, donde usted, el usuario,"
                        + "\npuede decidir cómo desea rellenarlo. Las letras mayúsculas A, V y R representan los tres colores admitidos dentro del juego,"
                        + "\nAzul, Verde y Rojo respectivamente. Por lo tanto, cualquier otro valor será reconocido por el programa como un error,"
                        + "\ny se indicará de forma pertinente."
                        + "\n"
                        + "\nUna vez se haya completado la matriz correctamente, se debe dar un nombre al juego creado en el cuadro de texto indicado para ello."
                        + "\nEl programa guardará el archivo representativo del juego en la carpeta Juegos (La cual será creada en caso de ser el primer juego),"
                        + "\nañadiendo la extensión .txt al nombre que usted haya proporcionado.",
                "Ayuda", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuCrearJuego(3, 3); // Ejemplo con 3 filas y 3 columnas
            }
        });
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btCrearJuego) {
            realizarAccionCrearJuego();
        } else if (e.getSource() == btAyuda) {
            realizarAccionAyuda();
        }
    }

	@Override
	public void undoableEditHappened(UndoableEditEvent e) {
		undoManager.addEdit(e.getEdit());
        menuDeshacer.setEnabled(undoManager.canUndo());
        menuRehacer.setEnabled(undoManager.canRedo());
	}
}

