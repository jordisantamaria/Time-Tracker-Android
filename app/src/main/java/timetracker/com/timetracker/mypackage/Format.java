package timetracker.com.timetracker.mypackage;
import java.io.IOException;
import java.io.Serializable;

/**
 * Clase Format, utilizada para darle formato a los informes
 * sobre los proyectos realizados y despu�s exportarlos
 * cada uno en un fichero.
 */
@SuppressWarnings("serial")
public abstract class Format implements Serializable {

	/**
	 * @throws IOException 
	 */
	public abstract void export(Project project, Report report) 
			throws IOException;
	public abstract void detailedExport(Project project, Report report) 
			throws IOException;

}
