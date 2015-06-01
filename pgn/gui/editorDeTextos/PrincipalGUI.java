package pgn.gui.editorDeTextos;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class PrincipalGUI extends JFrame {

	private JPanel contentPane;
	private JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PrincipalGUI frame = new PrincipalGUI();
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
	public PrincipalGUI() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				Gestion.salir();
			}
		});
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnArchivo = new JMenu("Archivo");
		mnArchivo.setMnemonic('A');
		menuBar.add(mnArchivo);

		JMenuItem mntmNewMenuItem = new JMenuItem("Nuevo");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Gestion.nuevo();
			}
		});
		mnArchivo.add(mntmNewMenuItem);

		JMenuItem mntmAbrir = new JMenuItem("Abrir...");
		mntmAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Gestion.abrir();
			}
		});
		mnArchivo.add(mntmAbrir);

		JMenuItem mntmGuardar = new JMenuItem("Guardar");
		mntmGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Gestion.guardar();
			}
		});
		mnArchivo.add(mntmGuardar);

		JMenuItem mntmGuardarComo = new JMenuItem("Guardar como...");
		mntmGuardarComo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Gestion.guardarComo();
			}
		});
		mnArchivo.add(mntmGuardarComo);

		JSeparator separator = new JSeparator();
		mnArchivo.add(separator);

		JMenuItem mntmSalir = new JMenuItem("Salir");
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Gestion.salir();
			}
		});
		mnArchivo.add(mntmSalir);

		JMenu mnEditar = new JMenu("Edici\u00F3n");
		mnEditar.setMnemonic('E');
		menuBar.add(mnEditar);

		JMenuItem mntmCortar = new JMenuItem("Cortar");
		mntmCortar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				InputEvent.CTRL_MASK));
		mnEditar.add(mntmCortar);

		JMenuItem mntmCopiar = new JMenuItem("Copiar");
		mntmCopiar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				InputEvent.CTRL_MASK));
		mnEditar.add(mntmCopiar);

		JMenuItem mntmPegar = new JMenuItem("Pegar");
		mntmPegar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
				InputEvent.CTRL_MASK));
		mnEditar.add(mntmPegar);

		JSeparator separator_1 = new JSeparator();
		mnEditar.add(separator_1);

		JMenuItem mntmBuscar = new JMenuItem("Buscar");
		mntmBuscar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,
				InputEvent.CTRL_MASK));
		mnEditar.add(mntmBuscar);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		setTextArea(new JTextArea());
		getTextArea().setWrapStyleWord(true);
		getTextArea().setLineWrap(true);
		scrollPane.setViewportView(getTextArea());
		Gestion.setPrincipalGUI(this);
		Gestion.resetear(null);
	}

	public JTextArea getTextArea() {
		return textArea;
	}

	/**
	 * @param textArea
	 *            the textArea to set
	 */
	private void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
}
