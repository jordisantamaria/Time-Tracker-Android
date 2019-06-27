package timetracker.com.timetracker.mypackage;
import java.util.Observer;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Clase Activitat: Aquella que implementa el Observer. Es la clase padre de Task
 * y Project. Con esta clase se gestiona la parte com�n de una tarea
 * y de un proyecto: modificar su nombre,descripci�n y obtener el intervalo
 * de fechas (final, inicial y duraci�n de la actividad).
 */

@SuppressWarnings("serial")
public abstract class Activitat implements Observer, Serializable {

	private static Logger logger = LoggerFactory.getLogger(Activitat.class);
	/**
	 * @uml.property name="name"
	 */
	private String name;

	/**
	 * Getter of the property <tt>name</tt>
	 * 
	 * @return Returns the name.
	 * @uml.property name="name"
	 */
	public final String getName() {
		return name;
	}

	/**
	 * Setter of the property <tt>name</tt>
	 * 
	 * @param name
	 *            The name to set.
	 * @uml.property name="name"
	 */
	public final void setName(final String activityName) {
		assert (activityName != null);
		String auxiliar = activityName;
		
		logger = LoggerFactory.getLogger(this.getClass()); 
		  logger.debug("Nombre de la actividad: {}. " 
		    + "El nombre anterior era: {}.", activityName, name);
		this.name = activityName;
		
		assert (name == auxiliar);
	}

	/**
	 * @uml.property name="description"
	 */
	private String description;

	/**
	 * Getter of the property <tt>description</tt>
	 * 
	 * @return Returns the description.
	 * @uml.property name="description"
	 */
	public final String getDescription() {
		return description;
	}

	/**
	 * Setter of the property <tt>description</tt>
	 * 
	 * @param description
	 *            The description to set.
	 * @uml.property name="description"
	 */
	public final void setDescription(final String activityDescription) {
		String auxiliar = activityDescription;
		
		logger = LoggerFactory.getLogger(this.getClass()); 
		  logger.debug("Descripci�n de la actividad: {}. " 
		    + "La descripci�n anterior era: {}.", 
		    activityDescription, description);
		this.description = activityDescription;
		
		assert (description == auxiliar);
	}

	/**
	 * * Constructor de Activitat, inicializa todas sus variables.
	 * 
	 * @param name
	 *            : nombre de la actividad
	 * @param description
	 *            : descripci�n de la actividad
	 */
	public Activitat(final String activityName,
					 final String activityDescription) {
		
		
		this.name = activityName;
		this.description = activityDescription;
		this.getTimes().setDurationInt(0);
		this.visitor = new PrintVisitor();
		
		assert (checkInvariant()) : "No cumple los requisitos de Activitat";
		
		logger = LoggerFactory.getLogger(this.getClass()); 
		logger.info("Valores del constructor: " 
		    + "name: {}, description: {}, duration: {}",
		    this.name, this.description, this.getTimes().getDuration());

	}

	/**
	 * Invariante de clase. Comprueba que la actividad tiene nombre
	 * y descripci�n, que el tiempo de la actividad no sea nulo y
	 * que exista un visitante de la actividad
	 * @return 
	 */
	private boolean checkInvariant() {
		return (name != null && description != null) 
		&& (times != null)
		&& (visitor != null);
	}

	/**
	 * Funci�n que realiza la impresi�n del objeto, es decir, cuando se haga un
	 * print de esta clase, automaticamente saltar� esta funci�n De este objeto
	 * s�lo queremos que se imprima el nombre, el resto de variables no interesa
	 * que se impriman
	 */
	public final String toString() {
		return this.name;
	}

	/**
	 * Constructor por defecto de Activitat Inicializa la duraci�n de la
	 * actividad a 0.
	 */
	public Activitat() {
		this.getTimes().setDurationInt(0);
	}

	/**
	 * @uml.property name="interval"
	 * @uml.associationEnd inverse="activity:Interval"
	 */
	private Interval interval;

	/**
	 * Getter of the property <tt>interval</tt>
	 * 
	 * @return Returns the interval.
	 * @uml.property name="interval"
	 */
	public final Interval getInterval() {
		return interval;
	}

	/**
	 * Setter of the property <tt>interval</tt>
	 * 
	 * @param interval
	 *            The interval to set.
	 * @uml.property name="interval"
	 */
	public final void setInterval(final Interval activityInterval) {
		assert (activityInterval != null);
		Interval auxiliar = activityInterval;
		
		logger = LoggerFactory.getLogger(this.getClass()); 
		  logger.debug("Intervalo de la actividad: {},",
		    activityInterval);
		this.interval = activityInterval;
		
		assert (interval == auxiliar);
	}

	/**
	 * @uml.property name="project"
	 * @uml.associationEnd inverse="activity:Project"
	 */
	private Project project;

	/**
	 * Getter of the property <tt>project</tt>
	 * 
	 * @return Returns the project.
	 * @uml.property name="project"
	 */
	public final Project getProject() {
		return project;
	}

	/**
	 * Setter of the property <tt>project</tt>
	 * 
	 * @param project
	 *            The project to set.
	 * @uml.property name="project"
	 */
	public final void setProject(final Project activityProject) {
		assert (activityProject != null);
		Project auxiliar = activityProject;
		logger = LoggerFactory.getLogger(this.getClass()); 
		logger.debug("Cambiando el proyecto {} por el proyecto {}",
				this.project.getName(), activityProject.getName());
		this.project = activityProject;
		
		assert (project == auxiliar);
	}

	/**
	 * @uml.property name="times"
	 */
	private Interval times = new Interval();

	/**
	 * Getter of the property <tt>temps</tt>
	 * 
	 * @return Returns the temps.
	 * @uml.property name="times"
	 */
	public final Interval getTimes() {
		return times;
	}

	/**
	 * Setter of the property <tt>temps</tt>
	 * 
	 * @param temps
	 *            The temps to set.
	 * @uml.property name="times"
	 */
	public final void setTimes(final Interval activityTimes) {
		assert (activityTimes != null);
		Interval auxiliar = activityTimes;
		logger = LoggerFactory.getLogger(this.getClass()); 
		logger.debug("Cambiado el intervalo de Activitat de:"
				+ "FechaInicial: {}, FechaFinal: {}, Duraci�n: {}", 
				this.times.getInitDate(), this.times.getFinalDate(),
				this.times.getDuration() + ", por: "
				+ "FechaInicial: {}, FechaFinal: {}, Duraci�n: {}",
				activityTimes.getInitDate(), activityTimes.getFinalDate(),
				activityTimes.getDuration());
		this.times = activityTimes;
		
		assert (times == auxiliar);
	}

	/**
	 * @uml.property name="visitor"
	 */
	private PrintVisitor visitor;

	/**
	 * Getter of the property <tt>visitor</tt>
	 * 
	 * @return Returns the visitor.
	 * @uml.property name="visitor"
	 */
	public final PrintVisitor getVisitor() {
		return visitor;
	}

	/**
	 * Setter of the property <tt>visitor</tt>
	 * 
	 * @param visitor
	 *            The visitor to set.
	 * @uml.property name="visitor"
	 */
	public final void setVisitor(final PrintVisitor activityVisitor) {
		assert (activityVisitor != null);
		this.visitor = activityVisitor;
	}


}
