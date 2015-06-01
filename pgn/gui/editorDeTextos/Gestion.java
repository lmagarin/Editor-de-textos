package pgn.gui.editorDeTextos;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Gestion {
	static FileNameExtensionFilter filter = new FileNameExtensionFilter(
			"Documentos de texto", "txt");
	static JFileChooser jFileChooser = new JFileChooser();
	private static PrincipalGUI principalGUI;
	/**
	 * Indica si el texto está modificado. Facilita el guardar el texto previo a
	 * su pérdida.
	 */
	private static boolean modificado;
	/**
	 * Fichero asociado al texto. En caso de no estar guardado previamente,
	 * null.
	 */
	private static File file;

	static {
		jFileChooser.setFileFilter(filter);
	}

	/**
	 * Abre un documento de texto
	 * 
	 * @param jTextArea
	 *            donde se muestra el documento de texto recién abierto
	 */
	static void abrir() {
		guardarSiModificado();

		switch (jFileChooser.showOpenDialog(null)) {
		case JFileChooser.CANCEL_OPTION:
		case JFileChooser.ERROR_OPTION:
			return;
		}

		File file = jFileChooser.getSelectedFile();
		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(
				file))) {

			principalGUI.getTextArea().read(bufferedReader, null);
			resetear(file);

		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "Fichero no encontrado",
					"Error", JOptionPane.ERROR_MESSAGE, null);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error de E/S", "Error",
					JOptionPane.ERROR_MESSAGE, null);
		}

	}

	/**
	 * Guarda si se ha modificado el contenido del editor
	 * 
	 * @return true si no se cancela la acción. false en otro caso
	 * 
	 */
	static boolean guardarSiModificado() {
		// TODO
		return true;
	}

	static void salir() {
		if (guardarSiModificado()) {
			principalGUI.setVisible(false);
			principalGUI.dispose();
			System.exit(0);
		}

	}

	static void guardarComo() {
		switch (jFileChooser.showSaveDialog(null)) {
		case JFileChooser.CANCEL_OPTION:
		case JFileChooser.ERROR_OPTION:
			return;
		}
		guardar(jFileChooser.getSelectedFile());
	}

	static void guardar() {
		if (getFile() == null)
			guardarComo();
		else
			guardar(getFile());
	}

	private static void guardar(File file) {
		try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(
				file))) {
			principalGUI.getTextArea().write(bufferedWriter);
			resetear(file);

		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error de E/S", "Error",
					JOptionPane.ERROR_MESSAGE, null);
		}
	}

	static void nuevo() {
		if (guardarSiModificado()) {
			principalGUI.getTextArea().setText(null);
			resetear(null);
		}

	}

	static void setPrincipalGUI(PrincipalGUI principalGUI) {
		Gestion.principalGUI = principalGUI;

	}

	/**
	 * Restablece los valores del editor de textos. Altera los valores: <li>
	 * modificado para no perder la información <li>fichero para asociar el
	 * documento al fichero <li>título del editor
	 * 
	 * @param file
	 */
	static void resetear(File file) {
		setModificado(false);
		setFile(file);

		if (file == null)
			principalGUI.setTitle("Sin t\u00EDtulo: Bloc de notas");
		else
			principalGUI.setTitle(file.getName() + ": Bloc de notas");
	}

	private static void setFile(File file) {
		Gestion.file = file;
	}

	/**
	 * @return the modificado
	 */
	private static boolean isModificado() {
		return modificado;
	}

	/**
	 * @param modificado
	 *            the modificado to set
	 */
	static void setModificado(boolean modificado) {
		Gestion.modificado = modificado;
	}

	/**
	 * @return the file
	 */
	private static File getFile() {
		return file;
	}

}
