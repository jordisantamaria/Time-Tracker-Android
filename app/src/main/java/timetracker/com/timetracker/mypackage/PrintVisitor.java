package timetracker.com.timetracker.mypackage;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Clase que implementa los prints de cada objeto pasado por par�metro
 */
@SuppressWarnings("serial")
public class PrintVisitor implements Serializable {

	/**
	 * Constructor por defecto de PrintVisitor
	 */
	public PrintVisitor() {
	}

	/**
	 * M�todo que imprime la cabecera de la tabla a mostrar
	 */
	public final void print() {
		System.out.println("Nom            Temps inici    " 
		+ "                  Temps Final           " 
		+ "    Durada (hh:mm:ss)");
		System.out.println("----+-------------------------------+"
		+ "----------------------------------+---------------------" 
		+ "-");
	}

	/**
	 * M�todo que, a partir de una lista de proyectos, llama a imprimir cada uno
	 * de ellos.
	 * 
	 * @param project
	 *            : lista de proyectos a tratar.
	 */
	public final void print(final ArrayList<Project> project) {
		for (int i = 0; i < project.size(); i++) {
			print(project.get(i));
		}

	}

	/**
	 * M�todo que imprime las variables necesarias de un proyecto
	 * 
	 * @param project
	 *            Si el proyecto recibido no tiene un proyecto padre, es decir,
	 *            que es el proyecto raiz, no queremos imprimirlo, de ah� el
	 *            condicional.
	 */
	public final void print(final Project project) {
		if (project.getFatherProject() != null) {
			System.out.println(project.getName() + "      "
					+ project.getTimes().getInitDate() + "    "
					+ project.getTimes().getFinalDate() + "         "
					+ project.getTimes().getDuration());
		}
		for (Task task : project.getTaskList()) {
			task.acceptVisitor(this);
		}
		for (Project subproject : project.getProyectList()) {
			subproject.acceptVisitor(this);
		}

	}

	/**
	 * M�todo que imprime las variables necesarias de una tarea
	 * 
	 * @param task
	 *            : tarea a imprimir.
	 */
	public final void print(final Task task) {
		System.out.println(task.getName() + "      "
				+ task.getTimes().getInitDate() + "    "
				+ task.getTimes().getFinalDate() + "         "
				+ task.getTimes().getDuration());
	}

}
