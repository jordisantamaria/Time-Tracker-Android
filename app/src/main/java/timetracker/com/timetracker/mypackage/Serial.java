package timetracker.com.timetracker.mypackage;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class Serial implements Serializable {
	/**
	 * M�todo para guardar en un archivo el proyecto actual 
	 * en el que se encuentra el usuario.
	 * 
	 * @throws IOException
	 */
	public final void save(final Project object) {
		assert (object != null);
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new BufferedOutputStream(
					new FileOutputStream("fichero.bin")));
			logger.info("Guardando " + object.getName() 
					+ "en el archivo 'fichero.bin'");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Error al intentar guardar el proyecto "
					+ "en el fichero 'fichero.bin'");
		}
		try {
			out.writeObject(object);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error al intentar escribir en el output");
		}
		try {
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error al intentar cerrar el output");
		}

	}

	/**
	 * M�todo para leer de un archivo.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public final Project read() throws FileNotFoundException, IOException {
		ObjectInputStream in;
		Project p = null;
		in = new ObjectInputStream(new FileInputStream("~/sdcard/Downloads/fichero.bin"));
		try {
			Object project = null;
			project = in.readObject();
			if (project != null) {
				p = (Project) project;
				logger.info("Cargando el proyecto: " + p.getName());
			}
			in.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("Error, clase no encontrada");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("Error al leer del input");
		}
		return p;
	}

	/**
	 * Constructor por defecto de la clase Serial
	 */
	public Serial() {
	}
	

	
	/**
	 * Variable destinada para hacer los mensajes de logback de la aplicaci�n.
	 * @uml.property name="logger"
	 */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());


}
