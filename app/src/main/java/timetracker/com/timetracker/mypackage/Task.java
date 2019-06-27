package timetracker.com.timetracker.mypackage;
import java.util.ArrayList;
import java.util.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @uml.dependency supplier="printable"
 */
@SuppressWarnings("serial")
public class Task extends Activitat {

	/**
	 * @uml.property name="intervalList"
	 */
	private ArrayList<Interval> intervalList;

	/**
	 * Getter of the property <tt>intervalList</tt>
	 * 
	 * @return Returns the intervalList.
	 * @uml.property name="intervalList"
	 */
	public final ArrayList<Interval> getIntervalList() {
		return intervalList;
	}

	/**
	 * Setter of the property <tt>intervalList</tt>
	 * 
	 * @param intervalList
	 *            The intervalList to set.
	 * @uml.property name="intervalList"
	 */
	public final void setIntervalList(
			final ArrayList<Interval> taskIntervalList) {
		
		logger = LoggerFactory.getLogger(this.getClass());
		  logger.debug("Lista de intervalos modificada, "
		    + "ahora tiene {} intervalos.", taskIntervalList.size());
		this.intervalList = taskIntervalList;
	}

	/**
	 * Variable entera que servir� para identificar a cada tarea
	 * 
	 * @uml.property name="id"
	 */
	private int id;

	/**
	 * Getter of the property <tt>id</tt>
	 * 
	 * @return Returns the id.
	 * @uml.property name="id"
	 */
	public final int getId() {
		return id;
	}

	/**
	 * Setter of the property <tt>id</tt>
	 * 
	 * @param id
	 *            The id to set.
	 * @uml.property name="id"
	 */
	public final void setId(final int identity) {
		 logger = LoggerFactory.getLogger(this.getClass());
		  logger.debug("Id de la Task: {} ", this.id);
		this.id = identity;
	}

	/**
	 * M�todo que va actualizando el tiempo final del intervalo actual activo en
	 * la task, suma todos los intervalos de la task, convierte la duraci�n en
	 * segundos a tiempo en horas,minutos y segundos, e imprime con el visitor
	 * toda la informaci�n de la task.
	 */
	public final void update(final Observable arg0, final Object arg1) {
		if (!intervalList.isEmpty()) {
			Interval intervalActual = intervalList.get(intervalList.size() - 1);
			intervalActual.setDuration(arg1);
			this.getTimes().setFinalDate(intervalActual.getFinalDate());
			int durationIntervalSum = 0;
			for (int i = 0; i < intervalList.size(); i++) {
				durationIntervalSum = durationIntervalSum
						+ intervalList.get(i).getDurationInt();
			}
			this.getTimes().setDurationInt(durationIntervalSum);
			this.getTimes().durationIntToTime();
			this.getVisitor().print(this);
		}
	}

	/**
	 * Constructor de Task
	 * 
	 * @param name
	 *            :nombre de la task
	 * @param description
	 *            : descripci�n de la task Inicializamos la lista de intervalos,
	 *            el id de la task
	 */
	public Task(final String name, final String description) {
		super(name, description);
		intervalList = new ArrayList<Interval>();
		id = 0;
		setInitiatedTask(false);
		this.setVisitor(new PrintVisitor());
		assert (checkInvariant()) : "No se cumplen los requisitos de Task";
		logger = LoggerFactory.getLogger(this.getClass()); 
		  logger.info("Constructor con los siguientes par�metros: "
		    + "Nombre de la tarea : {} , descripci�n de la tarea: {}.", 
		    this.getName(), this.getDescription());
	}
	
	/**
	 * Invariante de clase. La tarea debe tener m�nimo un intervalo 
	 * para poder iniciarse. El intervalo general de Tarea no puede
	 * ser nulo. No puede tener un identificador negativo.
	 */
	private boolean checkInvariant() {
		return (intervalList != null)
		&& (id >= 0);
	}
	/**
	 * M�todo que pone el booleano para saber si la task est� iniciada o no a
	 * True, crea un nuevo intervalo para la task y lo a�ade a la lista de
	 * Observers
	 * 
	 * @param c
	 *            : variable de tipo Clock para poderlo a�adir a la lista de
	 *            Observers
	 */
	public final void startTask(final Clock clock) {
		assert (clock != null);
		assert (!isInitiatedTask());
		this.setInitiatedTask(true);
		this.newInterval();
		clock.addObserver(this);
		System.out.println("asd");
		assert (isInitiatedTask());
	}

	/**
	 * M�todo que crea un nuevo intervalo de la task, si la lista de intervalos
	 * est� vac�a, el tiempo de inicio de la actividad, ser� el tiempo de inicio
	 * del primer intervalo de la lista. a�adimos el intervalo a la lista de
	 * intervalos de la task
	 */
	public final void newInterval() {
		Interval interval = new Interval();
		if (intervalList.isEmpty()) {
			this.getTimes().setInitDate(interval.getInitDate());
		}
		intervalList.add(interval);
	}

	/**
	 * @uml.property name="initiatedTask"
	 */
	private boolean initiatedTask;

	/**
	 * Getter of the property <tt>inici</tt>
	 * 
	 * @return Returns the inici.
	 * @uml.property name="initiatedTask"
	 */
	public final boolean isInitiatedTask() {
		return initiatedTask;
	}

	/**
	 * Setter of the property <tt>inici</tt>
	 * 
	 * @param inici
	 *            The inici to set.
	 * @uml.property name="initiatedTask"
	 */
	public final void setInitiatedTask(final boolean taskInit) {
		logger = LoggerFactory.getLogger(this.getClass()); 
		  logger.info("Estado de la tarea: {}"
		    + " (true = encendido / false = apagado)", taskInit);
		this.initiatedTask = taskInit;
	}

	/**
	 * M�todo para aceptar el visitante para utilizarlo con el patr�n
	 * visitor.
	 * @param printVisitor
	 */
	public final void acceptVisitor(final PrintVisitor printVisitor) {
		assert (printVisitor != null);
		printVisitor.print(this);
		
	}
	private Logger logger = LoggerFactory.getLogger(this.getClass());
}
