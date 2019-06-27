package timetracker.com.timetracker.mypackage;
import java.util.Date;
import java.util.Observable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Clock extends Observable {
	static final int MILISECONDS_TO_SLEEP = 2000;
	public interface Serializable {
	}

	/**
	 * @uml.property name="date"
	 */
	private Date date;

	/**
	 * Subclase de Clock, implementa Thread para poder pararlelizar varias
	 * funciones al mismo tiempo
	 */
	public class ClockTimer extends Thread {
		/**
		 * M�todo que duerme el thread activo dos segundos y llama al m�todo
		 * tick para notificar que el Clock ha sido modificado
		 */
		public final void run() {
			while (true) {
				try {
					sleep(MILISECONDS_TO_SLEEP);
				} catch (InterruptedException e) {
					e.printStackTrace();
					logger = LoggerFactory.getLogger(this.getClass());
					logger.error("Error al intentar esperar (sleep)");
				}
				tick();
			}
		}

		/**
		 * M�todo que marca que el objeto Clock ha sido modificado
		 */
		public final void tick() {
			date = new Date();
			setChanged();
			notifyObservers(date);

		}

	}

	/**
	 * M�todo que hace que un thread empiece a funcionar, implica que el m�todo
	 * llame a la funci�n run()
	 */
	public final void startClock() {
		clockTimer.start();
	}

	/**
	 * Constructor del Clock que crea los objetos clockTimer y data
	 */
	public Clock() {
		clockTimer = new ClockTimer();
		date = new Date();
		assert (checkInvariant()) : "La data del Clock no puede ser nula";
	}

	/**
	 * Invariante de clase. Un reloj no puede tener una fecha nula.
	 * @return 
	 */
	private boolean checkInvariant() {
		return (date != null);
	}

	/**
	 * @uml.property name="clockTimer"
	 */
	private static ClockTimer clockTimer;
	
	/**
	 * Variable destinada para hacer los mensajes de logback de la aplicaci�n.
	 * @uml.property name="logger"
	 */
	private Logger logger = LoggerFactory.getLogger(Clock.class);

}
