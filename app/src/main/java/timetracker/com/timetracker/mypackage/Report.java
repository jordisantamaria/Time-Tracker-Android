package timetracker.com.timetracker.mypackage;

import java.util.Date;
import java.io.IOException;
import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("serial")
public class Report implements Serializable {

	/**
	 * Constructor del informe. Inicializa las fechas de 
	 * inicio y final, asi coo la fecha de generaci�n del
	 * informe y su formato.
	 */
	public Report(final Date initDate, final Date finalDate) {
		this.initDatePeriod = initDate;
		this.finalDatePeriod = finalDate;
		this.reportDate = new Date();
		this.format = new Txt();
		assert (checkInvariant()) : "No se cumplen los requisitos de Report";
		logger = LoggerFactory.getLogger(this.getClass());
		logger.info("Constructor con los siguientes par�metros:"
				+ "Fecha inicia del reporte: {} , fecha final: "
				+ "{}, fecha de generaci�n: {} ", initDatePeriod,
				finalDatePeriod, reportDate);
	}

	/**
	 * Invariante de clase. Las fechas inicio y final no pueden ser nulas.
	 * Debe tener una fecha de generaci�n del informe. El formato del 
	 * informe no puede ser nulo. La fecha inicial no puede ser mayor
	 * que la final.
	 * @return 
	 */
	private boolean checkInvariant() {
		return (initDatePeriod != null)
		&& (finalDatePeriod != null)
		&& (reportDate != null)
		&& (format != null)
		&& (finalDatePeriod.getTime() > initDatePeriod .getTime());
		
	}

	/**
	 * @uml.property name="ReportDate" readOnly="true"
	 */
	private Date reportDate;

	/**
	 * Getter of the property <tt>ReportDate</tt>
	 * 
	 * @return Returns the reportDate.
	 * @uml.property name="ReportDate"
	 */
	public final Date getReportDate() {
		return reportDate;
	}

	/**
	 * @uml.property name="initDatePeriod"
	 */
	private Date initDatePeriod;

	/**
	 * Getter of the property <tt>initDatePeriod</tt>
	 * 
	 * @return Returns the initDatePeriod.
	 * @uml.property name="initDatePeriod"
	 */
	public final Date getInitDatePeriod() {
		return initDatePeriod;
	}

	/**
	 * Setter of the property <tt>initDatePeriod</tt>
	 * 
	 * @param initDatePeriod
	 *            The initDatePeriod to set.
	 * @uml.property name="initDatePeriod"
	 */
	public final void setInitDatePeriod(final Date reportInitDatePeriod) {
		assert (reportInitDatePeriod != null);
		Date auxiliar = reportInitDatePeriod;
		logger = LoggerFactory.getLogger(this.getClass());
		logger.info("Fecha inicial del informe modificada. Antes: {}"
				 + ", ahora : {}", initDatePeriod, reportInitDatePeriod);
		this.initDatePeriod = reportInitDatePeriod;
		assert (initDatePeriod == auxiliar);
	}

	/**
	 * @uml.property name="finalDatePeriod"
	 */
	private Date finalDatePeriod;

	/**
	 * Getter of the property <tt>finalDatePeriod</tt>
	 * 
	 * @return Returns the finalDatePeriod.
	 * @uml.property name="finalDatePeriod"
	 */
	public final Date getFinalDatePeriod() {
		return finalDatePeriod;
	}

	/**
	 * Setter of the property <tt>finalDatePeriod</tt>
	 * 
	 * @param finalDatePeriod
	 *            The finalDatePeriod to set.
	 * @uml.property name="finalDatePeriod"
	 */
	public final void setFinalDatePeriod(final Date reportFinalDatePeriod) {
		assert (reportFinalDatePeriod != null);
		Date auxiliar = reportFinalDatePeriod;
		logger = LoggerFactory.getLogger(this.getClass());
		logger.info("Fecha final del informe modificada. Antes: {}"
				 + ", ahora : {}", finalDatePeriod, reportFinalDatePeriod);
		this.finalDatePeriod = reportFinalDatePeriod;
		assert (finalDatePeriod == auxiliar);
		
	}

	

	/**
	 * @uml.property name="format"
	 * @uml.associationEnd inverse="report:mypackage.Format"
	 */
	private Format format;
	/**
	 * @uml.property name="detailed"
	 */
	private boolean detailed = false;
	
	/**
	 * Getter of the property <tt>detailed</tt>
	 * 
	 * @return Returns the detailed.
	 * @uml.property name="detailed"
	 */
	public final boolean getDetailed() {
		return detailed;
	}

	/**
	 * Setter of the property <tt>detailed</tt>
	 * 
	 * @param detailed
	 *            The detailed to set.
	 * @uml.property name="detailed"
	 */
	public final void setDetailed(final boolean detailedReport) {
		logger = LoggerFactory.getLogger(this.getClass());
		logger.info("Indicador de un informe detallado,"
				+ "(true = detallado, false = resumen). {}", detailedReport);
		this.detailed = detailedReport;
	}
	
	/**
	 * Getter of the property <tt>format</tt>
	 * 
	 * @return Returns the format.
	 * @uml.property name="format"
	 */
	public final Format getFormat() {
		return format;
	}

	/**
	 * Setter of the property <tt>format</tt>
	 * 
	 * @param format
	 *            The format to set.
	 * @uml.property name="format"
	 */
	public final void setFormat(final Format reportFormat) {
		this.format = reportFormat;
	}

	/**
	 * M�todo utilizado para exportar el informe resumido.
	 * Indica que no es un informe detallado y llama a la funci�n
	 * para exportar
	 * @param project
	 */
	public final void exportSmallReport(final Project project) {
		assert (project != null);
		try {
			this.setDetailed(false);
			this.format.export(project, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * M�todo utilizado para exportar el informe detallado
	 * Indica que se trata de un informe detallado y llama a la
	 * funci�n para exportar.
	 * @param project
	 */
	public final void exportDetailedReport(final Project project) {
		assert (project != null);
		try {
			this.setDetailed(true);
			this.format.detailedExport(project, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static Logger logger = LoggerFactory.getLogger(Report.class);
}
