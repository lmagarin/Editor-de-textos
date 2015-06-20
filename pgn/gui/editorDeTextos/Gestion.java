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
	 */
	static void abrir() {
		if (guardarSiModificado() == ContinuarAbortar.ABORTAR)
			return;

		switch (jFileChooser.showOpenDialog(principalGUI)) {
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
			JOptionPane.showMessageDialog(principalGUI,
					"Fichero no encontrado", "Error",
					JOptionPane.ERROR_MESSAGE, null);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(principalGUI, "Error de E/S",
					"Error", JOptionPane.ERROR_MESSAGE, null);
		}

	}

	/**
	 * Pregunta al usuario si guardar al ser modificado el contenido del editor.
	 * 
	 * @return true si se continúa la acción. false en otro caso (cancelar o
	 *         cerrar)
	 * 
	 */
	static ContinuarAbortar guardarSiModificado() {
		if (isModificado()) {
			switch (JOptionPane.showConfirmDialog(principalGUI,
					"¿Desea guardar los cambios hechos a " + getNombre() + "?",
					"Editor de textos", JOptionPane.YES_NO_CANCEL_OPTION)) {
			case JOptionPane.YES_OPTION:
				guardar();
				break;
			case JOptionPane.NO_OPTION:
				break;
			default:
				return ContinuarAbortar.ABORTAR;// case
												// JOptionPane.CANCEL_OPTION
												// case
			}
		}
		return ContinuarAbortar.CONTINUAR;
	}

	/**
	 * Obtiene el nombre del fichero
	 * 
	 * @return Nombre del fichero. Sin t\u00EDtulo si no hay fichero asociado.
	 */
	private static String getNombre() {
		if (file == null)
			return "Sin t\u00EDtulo ";
		return file.getName();
	}

	static void salir() {
		if (guardarSiModificado() == ContinuarAbortar.ABORTAR)
			return;

		principalGUI.setVisible(false);
		principalGUI.dispose();
		System.exit(0);
	}

	static void guardarComo() {
		do {
			switch (jFileChooser.showSaveDialog(principalGUI)) {
			case JFileChooser.CANCEL_OPTION:
			case JFileChooser.ERROR_OPTION:
				return;
			}
			if (deseaReemplazarlo(jFileChooser.getSelectedFile())) {
				guardar(jFileChooser.getSelectedFile());
				return;
			}
		} while (true);
	}

	/**
	 * Indica si se desea reemplazar el fichero existente.
	 * 
	 * @param file
	 *            Fichero que va a reemplazarse
	 * @return true si se desea reemplazar. false en otro caso
	 */
	private static boolean deseaReemplazarlo(File file) {
		if (file.exists()) {
			switch (JOptionPane.showConfirmDialog(principalGUI, file.getName()
					+ " ya existe. ¿Desea reemplazarlo?",
					"Confirmar Guardar Como", JOptionPane.YES_NO_OPTION)) {
			case JOptionPane.YES_OPTION:
				return true;
			case JOptionPane.NO_OPTION:
			case JOptionPane.CLOSED_OPTION:
				return false;
			}
		}

		return false;
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
			JOptionPane.showMessageDialog(principalGUI, "Error de E/S",
					"Error", JOptionPane.ERROR_MESSAGE, null);
		}
	}

	static void nuevo() {
		if (guardarSiModificado() == ContinuarAbortar.ABORTAR)
			return;

		principalGUI.getTextArea().setText(null);
		resetear(null);
	}

	static void setPrincipalGUI(PrincipalGUI principalGUI) {
		Gestion.principalGUI = principalGUI;

	}

	/**
	 * Restablece los valores del editor de textos. Altera los valores:
	 * 
	 * <li>modificado para no perder la información
	 * 
	 * <li>fichero para asociar el documento al fichero
	 * 
	 * <li>título del editor
	 * 
	 * @param file
	 */
	static void resetear(File file) {
		setModificado(false);
		setFile(file);
		principalGUI.setTitle(getNombre() + ": Bloc de notas");
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
