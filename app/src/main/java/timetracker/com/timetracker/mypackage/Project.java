package timetracker.com.timetracker.mypackage;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase heredada de Activitat. Utilizada para gestionar toda la informaci�n
 * sobre un proyecto. Esta clase contiene listas de tareas y subproyectos que
 * contiene el proyecto. Desde esta clase puedes iniciar un proyecto, pararlo e
 * iniciar y parar alguna tarea del mismo.
 */
@SuppressWarnings("serial")
public class Project extends Activitat {
	private static Logger logger = LoggerFactory.getLogger(Project.class);
	/**
	 * @uml.property   name="proyectList"
	 */
	private ArrayList<Project> proyectList;

	/**
	 * Getter of the property <tt>proyectList</tt>
	 * 
	 * @return Returns the proyectList.
	 * @uml.property name="proyectList"
	 */
	public final ArrayList<Project> getProyectList() {
		return proyectList;
	}

	/**
	 * Setter of the property <tt>proyectList</tt>
	 * 
	 * @param proyectList
	 *            The proyectList to set.
	 * @uml.property name="proyectList"
	 */
	public final void setProyectList(final ArrayList<Project> projectList) {
		assert (projectList != null);
		logger = LoggerFactory.getLogger(this.getClass()); 
		logger.debug("Lista de proyectos modificada, "
				+ "ahora tiene {} subproyectos.", projectList.size());
		this.proyectList = projectList;
	}

	/**
	 * Constructor de Project
	 * 
	 * @param name : nombre del proyecto
	 * @param description : descripci�n del proyecto
	 * @param p  : proyecto padre
	 */
	public Project(final String name, final String description,
			final Project project) {
		super(name, description);
		this.taskList = new ArrayList<Task>();
		this.proyectList = new ArrayList<Project>();
		this.durationTimes = new ArrayList<Interval>();
		this.setFatherProject(project);
		setInitiatedProject(false);
		this.report = new ArrayList<Report>();
		assert (checkInvariant()) : "No se cumplen los requisitos de Project";
		logger = LoggerFactory.getLogger(this.getClass()); 
		logger.info("Constructor con los siguientes par�metros: "
				+ "Nombre del proyecto : {} , proyecto padre: {}, "
				+ "descripci�n del proyecto: {}.", this.getName(), 
				this.fatherProject, this.getDescription());
	}
	
	/**
	 * Invariante de clase. El proyecto debe tener una lista de 
	 * tareas y proyectos. Tambi�n debe tener una lista de informes.
	 * @return 
	 */
	private boolean checkInvariant() {
		return (taskList != null)
		&& (proyectList != null)
		&& (durationTimes != null)
		&& (report != null);
	}
	/**
	 * M�todo que imprime por pantalla el identificador y el nombre de cada
	 * elemento de la lista de tareas del proyecto.
	 * 
	 * @return s: String que contiene lo mencionado anteriormente.
	 */
	public final String listsTasks() {
		String s = "";
		for (int i = 0; i < taskList.size(); i++) {

			s += i + "-" + taskList.get(i).getName() + ", ";
		}
		return s;
	}

	/**
	 * Constructor por defecto de Project
	 */
	public Project() {
		super();
		this.taskList = new ArrayList<Task>();
		this.proyectList = new ArrayList<Project>();
		this.durationTimes = new ArrayList<Interval>();
		setFatherProject(null);
		this.setInterval(new Interval());
		this.setVisitor(new PrintVisitor());
		this.report = new ArrayList<Report>();
		assert (checkInvariant());
		logger = LoggerFactory.getLogger(this.getClass()); 
		logger.info("Constructor con los siguientes par�metros: "
				+ "Nombre del proyecto : {} , proyecto padre: {}, "
				+ "descripci�n del proyecto: {}.", this.getName(), 
				this.fatherProject, this.getDescription());
	}

	/**
	 * M�todo que a�ade un proyecto a la lista de proyectos.
	 * 
	 * @param p
	 *            : subproyecto a a�adir
	 */
	public final void addSubproject(final Project project) {
		assert (project != null);
		int auxiliar = this.proyectList.size();
		logger = LoggerFactory.getLogger(this.getClass()); 
		logger.debug("Proyecto {} a�adido.", project.getName());
		this.proyectList.add(project);
		assert (this.proyectList.size() > auxiliar);
	}

	/**
	 * M�todo que imprime por pantalla el identificador y el nombre de cada
	 * elemento de la lista de proyectos.
	 * 
	 * @return s: String que contiene lo mencionado anteriormente.
	 */
	public final String listProjects() {

		String s = "";
		for (int i = 0; i < proyectList.size(); i++) {

			s += i + "-" + proyectList.get(i).getName() + ", ";
		}
		return s;
	}

	/**
	 * M�todo utilizado para parar una tarea. Esto implica que la tarea deje de
	 * observar. Si al parar esta tarea implica que todas las tareas del
	 * proyecto esten paradas, el proyecto tambi�n se detiene.
	 * 
	 * @param clock : reloj a observar.
	 * @param id : identificador de la tarea.
	 */
	public final void stopTask(final Clock clock, final int id) {
		assert (this.taskList.get(id).isInitiatedTask());
		if (id <= this.taskList.size()) {
			Task t = taskList.get(id);
			clock.deleteObserver((Observer) t);
			taskList.get(id).setInitiatedTask(false);
			int allTaskStoped = 0;
			for (int i = 0; i < taskList.size(); i++) {
				if (!taskList.get(i).isInitiatedTask()) {
					allTaskStoped = allTaskStoped + 1;
				}
			}
			int allSubprojectsStoped = 0;
			for (int i = 0; i < proyectList.size(); i++) {
				if (!proyectList.get(i).isInitiatedProject()) {
					allSubprojectsStoped = allSubprojectsStoped + 1;
				}
			}
			if (allTaskStoped == taskList.size()
					&& allSubprojectsStoped == proyectList.size()) {
				this.setInitiatedProject(false);
				clock.deleteObserver(this);
				durationTimes.get(durationTimes.size() - 1).setFinalDate(
						new Date());
			}
			if (fatherProject.getName() != "ProyectoRaiz") {
				allTaskStoped = 0;
				for (int i = 0; i < fatherProject.getTaskList().size(); i++) {
					if (!fatherProject.getTaskList().get(i).
							isInitiatedTask()) {
						allTaskStoped = allTaskStoped + 1;
					}
				}
				allSubprojectsStoped = 0;
				for (int i = 0; i < this.fatherProject.
						getProyectList().size(); i++) {
					if (!this.fatherProject.getProyectList().
							get(i).isInitiatedProject()) {
						allSubprojectsStoped = allSubprojectsStoped + 1;
					}
				}
				if (allTaskStoped == fatherProject.getTaskList().size() 
						&& allSubprojectsStoped == fatherProject.
						getProyectList().size()) {
					fatherProject.setInitiatedProject(false);
					clock.deleteObserver(fatherProject);
					fatherProject.getDurationTimes().get(fatherProject.
						getDurationTimes().size() - 1).setFinalDate(new Date());
				}
			}
		}
		assert (!this.taskList.get(id).isInitiatedTask());
	}

	/**
	 * M�todo utilizado para detener un proyecto en el caso de que todas sus
	 * tareas y todos sus subproyectos hayan sido detenidos.
	 * 
	 * @param clock
	 *            : reloj a observar.
	 */
	public final void update(final Observable arg0, final Object arg1) {
		assert (!durationTimes.isEmpty());
		if (!durationTimes.isEmpty()) {
			Interval intervalActual = durationTimes
					.get(durationTimes.size() - 1);
			intervalActual.setDuration(arg1);
			this.getTimes().setFinalDate(intervalActual.getFinalDate());
			int durationIntervalSum = 0;
			for (int i = 0; i < durationTimes.size(); i++) {
				durationIntervalSum = durationIntervalSum
						+ durationTimes.get(i).getDurationInt();
			}
			this.getTimes().setDurationInt(durationIntervalSum);
			this.getTimes().durationIntToTime();
			this.getVisitor().print(this);
		}
		
	}

	/**
	 * M�todo que a�ade una tarea a la lista de tareas del proyecto.
	 * 
	 * @param task
	 *            : tarea a a�adir.
	 */
	public final void addTask(final Task task) {
		assert (task != null);
		int auxiliar = taskList.size();
		logger = LoggerFactory.getLogger(this.getClass()); 
		logger.debug(" Tarea {} a�adida a la lista de tareas.", task.getName());
		this.taskList.add(task);
		assert (taskList.size() > auxiliar);
	}

	/**
	 * M�todo que retorna el objeto de la lista de proyectos y que tiene como
	 * identificador el parametro recibido.
	 * 
	 * @param id
	 *            : identificador del proyecto a obtener.
	 * @return proyecto
	 */
	public final Project getSubproject(final int id) {
		return proyectList.get(id);
	}

	/**
	 * M�todo que inicia una tarea Crea un nuevo intervalo de tiempos para dicha
	 * tarea y a�ade la tarea como observadora Adem�s se verifica y actualiza el
	 * estado del proyecto de dicha tarea
	 * 
	 * @param c : reloj a observar
	 * @param id : identificador de la tarea
	 */
	public final void startTask(final Clock clock, final int id) {
		assert (!taskList.get(id).isInitiatedTask());
		int auxiliar = taskList.get(id).getIntervalList().size();
		if (!this.isInitiatedProject()) {
			this.newInterval();
			this.setInitiatedProject(true);
			clock.addObserver(this);
			if (!this.fatherProject.isInitiatedProject() 
					&& this.fatherProject.getName() != "ProyectoRaiz") {
				this.fatherProject.newInterval();
				this.fatherProject.setInitiatedProject(true);
				clock.addObserver(fatherProject);
			}
		}
		Task tarea = this.taskList.get(id);
		tarea.setInitiatedTask(true);
		tarea.newInterval();
		clock.addObserver(tarea);
		assert (taskList.get(id).isInitiatedTask());
		assert (taskList.get(id).getIntervalList().size() > auxiliar);
	}

	/**
	 * @uml.property   name="activity"
	 * @uml.associationEnd   multiplicity="(1 -1)" inverse="project:Activitat"
	 */
	@SuppressWarnings("rawtypes")
	private Collection activity;

	/**
	 * Getter of the property <tt>activity</tt>
	 * 
	 * @return Returns the activity.
	 * @uml.property name="activity"
	 */
	@SuppressWarnings("rawtypes")
	public final Collection getActivity() {
		return activity;
	}

	/**
	 * Setter of the property <tt>activity</tt>
	 * 
	 * @param activity
	 *            The activity to set.
	 * @uml.property name="activity"
	 */
	public final void setActivity(@SuppressWarnings("rawtypes") 
	final Collection activity1) {
		this.activity = activity1;
	}

	/**
	 * @uml.property   name="initiatedProject"
	 */
	private boolean initiatedProject;

	/**
	 * Getter of the property <tt>inici</tt>
	 * 
	 * @return Returns the inici.
	 * @uml.property name="initiatedProject"
	 */
	public final boolean isInitiatedProject() {
		return initiatedProject;
	}

	/**
	 * Setter of the property <tt>inici</tt>
	 * 
	 * @param inici
	 *            The inici to set.
	 * @uml.property name="initiatedProject"
	 */
	public final void setInitiatedProject(final boolean isInit) {
		logger = LoggerFactory.getLogger(this.getClass()); 
		logger.info("Estado del proyecto: {}"
				+ " (true = encendido / false = apagado)", isInit);
		this.initiatedProject = isInit;
		assert (initiatedProject == isInit);
	}

	/**
	 * @uml.property   name="fatherProject"
	 */
	private Project fatherProject;

	/**
	 * Getter of the property <tt>proyectoPadre</tt>
	 * 
	 * @return Returns the proyectoPadre.
	 * @uml.property name="fatherProject"
	 */
	public final Project getFatherProject() {
		return fatherProject;
	}

	/**
	 * Setter of the property <tt>proyectoPadre</tt>
	 * 
	 * @param proyectoPadre
	 *            The proyectoPadre to set.
	 * @uml.property name="fatherProject"
	 */
	public final void setFatherProject(final Project father) {
		this.fatherProject = father;
	}

	/**
	 * @uml.property   name="taskList"
	 */
	private ArrayList<Task> taskList;

	/**
	 * Getter of the property <tt>taskList</tt>
	 * 
	 * @return Returns the taskList.
	 * @uml.property name="taskList"
	 */
	public final ArrayList<Task> getTaskList() {
		return taskList;
	}

	/**
	 * Setter of the property <tt>taskList</tt>
	 * 
	 * @param taskList
	 *            The taskList to set.
	 * @uml.property name="taskList"
	 */
	public final void setTaskList(final ArrayList<Task> taskList1) {
		assert (taskList1 != null);
		this.taskList = taskList1;
	}

	/**
	 * @uml.property   name="durationTimes"
	 */
	private ArrayList<Interval> durationTimes;

	/**
	 * Getter of the property <tt>durationTimes</tt>
	 * 
	 * @return Returns the durationTimes.
	 * @uml.property name="durationTimes"
	 */
	public final ArrayList<Interval> getDurationTimes() {
		return durationTimes;
	}

	/**
	 * Setter of the property <tt>durationTimes</tt>
	 * 
	 * @param durationTimes
	 *            The durationTimes to set.
	 * @uml.property name="durationTimes"
	 */
	public final void setDurationTimes(final ArrayList<Interval> 
	durationInterval) {
		assert (durationInterval != null);
		this.durationTimes = durationInterval;
	}

	/**
	 * M�todo que a�ade un nuevo intervalo a la lista de tiempos del proyecto.
	 * Crea un nuevo intervalo, le a�ade la fecha inicial y lo a�ade a la lista.
	 */
	public final void newInterval() {
		int auxiliar = durationTimes.size();
		Interval interval = new Interval();
		if (durationTimes.isEmpty()) {
			this.getTimes().setInitDate(interval.getInitDate());
		}
		durationTimes.add(interval);
		assert (durationTimes.size() > auxiliar);
	}

	/**
	 * @uml.property   name="report"
	 * @uml.associationEnd   multiplicity="(0 -1)" 
	 * ordering="true" inverse="project:mypackage.Report"
	 */
	private ArrayList<Report> report;

	/**
	 * Getter of the property <tt>report</tt>
	 * @return  Returns the report.
	 * @uml.property  name="report"
	 */
	public final ArrayList<Report> getReport() {
		return report;
	}

	/**
	 * Setter of the property <tt>report</tt>
	 * @param report  The report to set.
	 * @uml.property  name="report"
	 */
	public final void setReport(final ArrayList<Report> reportFile) {
		assert (reportFile != null);
		this.report = reportFile;
	}

	/**
	 * M�todo que crea un informe resumido del proyecto a partir de un
	 * intervalo de tiempo establecido por el usuario. Dentro de este intervalo
	 * de tiempo, se muestra cuanto tiempo ha estado en funcionamiento 
	 * el proyecto, informando sobre fechas y subproyectos.
	 * @param initDate : Fecha inicial del intervalo 
	 * 						de tiempo que se quiere evaluar
	 * @param finalDate : Fecha final del intervalo d
	 * 						e tiempo que se quiere evaluar
	 * @param reportFormat : Formato del informe
	 */
	public final void createSmallReport(
			final Date initDate, final Date finalDate, 
			final Format reportFormat) {
		assert (initDate != null && finalDate != null);
		assert (reportFormat != null);
		assert (initDate.compareTo(finalDate) < 0);
		int auxiliar = this.getReport().size();
		Report reporte = new Report(initDate, finalDate);
		reporte.setFormat(reportFormat);
		this.getReport().add(reporte);
		this.getReport().get(this.getReport().size() - 1).
			exportSmallReport(this);
		assert (this.getReport().size() > auxiliar);
	}
	/**
	 * M�todo que crea un informe detallado del proyecto a partir de un
	 * intervalo de tiempo establecido por el usuario. Dentro de este intervalo
	 * de tiempo, se muestra cuanto tiempo ha estado en funcionamiento 
	 * el proyecto, informando sobre fechas, subproyectos, tareas e intervalos.
	 * @param initDate : Fecha inicial del intervalo 
	 * 						de tiempo que se quiere evaluar
	 * @param finalDate : Fecha final del intervalo d
	 * 						e tiempo que se quiere evaluar
	 * @param reportFormat : Formato del informe
	 */
	public final void createDetailedReport(
			final Date initDate, final Date finalDate, 
			final Format reportFormat) {
		assert (initDate != null && finalDate != null);
		assert (reportFormat != null);
		assert (initDate.compareTo(finalDate) < 0);
		
		int auxiliar = this.getReport().size();
		Report reporte = new Report(initDate, finalDate);
		reporte.setFormat(reportFormat);
		this.getReport().add(reporte);
		this.getReport().get(this.getReport().size() - 1).
			exportDetailedReport(this);
		
		assert (this.getReport().size() > auxiliar);
	}
	/**
	 * M�todo que forma parte del patr�n Visitor utilizado.
	 * Utilizado para imprimir el proyecto
	 * @param printVisitor
	 */
	public final void acceptVisitor(final PrintVisitor printVisitor) {
		assert (printVisitor != null);
		printVisitor.print(this);
		
	}

}
