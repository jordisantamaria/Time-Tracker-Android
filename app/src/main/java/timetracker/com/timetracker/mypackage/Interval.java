package timetracker.com.timetracker.mypackage;
import java.sql.Time;
import java.util.Date;
import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class Interval implements Serializable {
	static final int MILISECONDS_TO_SECONDS = 1000;
	private static final int CONVERSION_NUMBER = 60;
	/**
	 * Constructor por defecto de Interval.
	 */
	@SuppressWarnings("deprecation")
	public Interval() {
		duration = new Time(0, 0, 0);
		initDate = new Date();
		assert (checkInvariant()) : "No se cumplen los requisitos de Intervalo";
	}

	private boolean checkInvariant() {
		return (duration != null)
		&& (durationInt >= 0)
		&& (initDate != null)
		&& (finalDate == null);
		
	}

	private Time compareDates(final Date bigDate, final Date littleDate) {
		assert ((finalDate != null) && littleDate != null 
				&& finalDate.getTime() > littleDate.getTime()) 
		: "La data final no puede ser menor que la data inicial";
		
		long diferenciaEnMs = bigDate.getTime() - littleDate.getTime();
		int seconds = (int) (diferenciaEnMs / MILISECONDS_TO_SECONDS);
		this.durationInt = seconds;
		int minuts = 0;
		int hours = 0;
		if (seconds > CONVERSION_NUMBER) {
			minuts = seconds / CONVERSION_NUMBER;
			seconds = seconds % CONVERSION_NUMBER;

		}
		if (minuts > CONVERSION_NUMBER) {
			hours = minuts / CONVERSION_NUMBER;
			minuts = minuts % CONVERSION_NUMBER;
		}
		@SuppressWarnings("deprecation")
		Time t = new Time(hours, minuts, seconds);
		return t;
	}

	/**
	 * @uml.property name="finalDate"
	 */
	private Date finalDate;

	/**
	 * @uml.property name="finalDate"
	 */
	public final Date getFinalDate() {
		return finalDate;
	}

	/**
	 * Setter of the property <tt>dataFinal</tt>
	 * 
	 * @param dataFinal
	 *            The dataFinal to set.
	 * @uml.property name="finalDate"
	 */
	public final void setFinalDate(final Date intervalFinalDate) {
		assert (intervalFinalDate != null);
		logger = LoggerFactory.getLogger(this.getClass());
		logger.debug("Cambiada la fecha final de: {} a {}",
				this.finalDate, intervalFinalDate);
		this.finalDate = intervalFinalDate;
	}

	/**
	 * @uml.property name="initDate"
	 */
	private Date initDate;

	/**
	 * Getter of the property <tt>dataInicial</tt>
	 * 
	 * @return Returns the dataInicial.
	 * @uml.property name="initDate"
	 */
	public final Date getInitDate() {
		return initDate;
	}

	/**
	 * Setter of the property <tt>dataInicial</tt>
	 * 
	 * @param dataInicial
	 *            The dataInicial to set.
	 * @uml.property name="initDate"
	 */
	public final void setInitDate(final Date intervalInitDate) {
		logger = LoggerFactory.getLogger(this.getClass());
		logger.debug("Cambiada la fecha inicial de: {} a {}",
				this.initDate, intervalInitDate);
		this.initDate = intervalInitDate;
	}

	/**
	 * @uml.property name="duration"
	 */
	private Time duration;

	/**
	 * Getter of the property <tt>duration</tt>
	 * 
	 * @return Returns the duration.
	 * @uml.property name="duration"
	 */
	public final Time getDuration() {
		return duration;
	}

	/**
	 * Setter of the property <tt>duration</tt>
	 * 
	 * @param arg1
	 *            The duration to set.
	 * @uml.property name="duration"
	 */
	public final void setDuration(final Object arg1) {
		finalDate = (Date) arg1;
		duration = compareDates(finalDate, initDate);
	}

	/**
	 * @uml.property name="durationInt"
	 */
	private int durationInt;

	/**
	 * @uml.property name="durationInt"
	 */
	public final int getDurationInt() {
		return durationInt;
	}

	/**
	 * @uml.property name="durationInt"
	 */
	public final void setDurationInt(final int intervalDuration) {
		logger = LoggerFactory.getLogger(this.getClass());
		logger.debug("Cambiada la duraci�n en ms de: {} a {}",
				this.durationInt, intervalDuration);
		this.durationInt = intervalDuration;
	}

	/**
	 * M�todo que calcula la diferencia en segundos de dos fechas pasadas por
	 * par�metro
	 * 
	 * @param fechaMayor
	 * @param fechaMenor
	 * @return
	 */
	public final int durationIntCalculator(
		final Date dataInicial, final Date dataFinal) {
		long diferenciaEnMs = dataFinal.getTime() - dataInicial.getTime();
		int seconds = (int) (diferenciaEnMs / MILISECONDS_TO_SECONDS);
		return seconds;
	}

	/**
	 * M�todo que convierte una durada dada en segundos a tipo Time,
	 * "horas,minutos,segundos"
	 */
	@SuppressWarnings("deprecation")
	public final void durationIntToTime() {
		int segons = durationInt;
		int minuts = 0;
		int hours = 0;
		if (segons > CONVERSION_NUMBER) {
			minuts = segons / CONVERSION_NUMBER;
			segons = segons % CONVERSION_NUMBER;

		}
		if (minuts > CONVERSION_NUMBER) {
			hours = minuts / CONVERSION_NUMBER;
			minuts = minuts % CONVERSION_NUMBER;
		}
		this.duration = new Time(hours, minuts, segons);
	}

	/**
	 * Setter of the property <tt>duration</tt>
	 * 
	 * @param time
	 *            The time to set.
	 * @uml.property name="duration"
	 */
	public final void setDuration(final Time time) {
		this.duration = time;
	}
	
	/**
	 * Variable destinada para hacer los mensajes de logback de la aplicaci�n.
	 * @uml.property name="logger"
	 */
	private Logger logger = LoggerFactory.getLogger(Interval.class);
}
