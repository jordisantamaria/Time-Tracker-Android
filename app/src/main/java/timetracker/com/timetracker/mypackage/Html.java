package timetracker.com.timetracker.mypackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Clase Html, heredada de Format, utilizada para dar
 * un formato html a los informes e exportarlos en .html.
 * 
 */
@SuppressWarnings("serial")
public class Html extends Format {
	/**
	 * M�todo para exportar el proyecto recibido
	 * hacia un archivo .html
	 * @param project : Proyecto utilizado para el informe
	 * @param report : Informe a exportar
	 * @throws IOException 
	 */
	@Override
	public final void export(final Project project, final Report report) 
			throws IOException {
		assert (project != null && report != null);
		
		File file = new File("report_" + project.getName() + ".html");
		BufferedWriter buffer = new BufferedWriter(new FileWriter(file));
		try {
			buildHeaderTags(buffer);
			buildHeader(buffer, "Resumen");
			ArrayList<Object> table = new ArrayList<Object>();
			buildResum(project, report, buffer, table);
			insertSubProjectsInTable(project, table, report);
			buildTable(table, buffer);
			buildEndTags(buffer);
			buffer.close();
			System.out.println("�Informe realizado con exito!");
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * M�todo para exportar el proyecto recibido
	 * hacia un archivo .html con detalle
	 * @param project : Proyecto utilizado para el informe
	 * @param report : Informe a exportar
	 * @throws IOException 
	 */
	@Override
	public final void detailedExport(final Project project, 
			final Report report) throws IOException {
		assert (project != null && report != null);
		
		File file = new File("detailedReport_" + project.getName() + ".html");
		BufferedWriter buffer = new BufferedWriter(new FileWriter(file));
		try {
			buildHeaderTags(buffer);
			buildHeader(buffer, "Detallado");
			ArrayList<Object> table = new ArrayList<Object>();
			buildResum(project, report, buffer, table);
			insertProjectsInTable(project, table, report);
			buildTable(table, buffer);
			
			buildLine(buffer);
			buffer.write("<h1>Subproyectos</h1>");
			buffer.write("<p>Se incluyen en la siguiente tabla solo "
					+ "los subproyectos que tengan alguna tarea con "
					+ "algun intervalo dentro del periodo</p>");
			ArrayList<Object> subprojectTable = new ArrayList<Object>();
			ArrayList<Object> row = new ArrayList<Object>();
			row.add("Padre");
			row.add("Proyecto");
			row.add("Fecha Inicio");
			row.add("Fecha Final");
			row.add("Duraci�n");
			subprojectTable.add(row);
			for (Project subproject : project.getProyectList()) {
				insertSubProjectsInTable(subproject, subprojectTable, report);
			}
			buildTable(subprojectTable, buffer);
			
			buildLine(buffer);
			buffer.write("<h1>Tareas</h1>");
			buffer.write("<p>Se incluyen en la siguiente tabla la "
					+ "duraci�n de todas las tareas y el proyecto "
					+ "al que pertenecen.</p>");
			ArrayList<Object> taskTable = new ArrayList<Object>();
			row = new ArrayList<Object>();
			row.add("Padre");
			row.add("Proyecto");
			row.add("Fecha Inicio");
			row.add("Fecha Final");
			row.add("Duraci�n");
			taskTable.add(row);
			for (Project subproject : project.getProyectList()) {
				insertTasksInTable(subproject, taskTable, report);
			}
			buildTable(taskTable, buffer);
			
			buildLine(buffer);
			buffer.write("<h1>Intervalos</h1>");
			buffer.write("<p>Se incluyen en la siguiente tabla el tiempo "
					+ "de inicio, final y duraci�n de todos los intervalos "
					+ "entre las fechas especificadas, as� como la tarea y"
					+ "el proyecto al que pertenecen.</p>");
			ArrayList<Object> intervalTable = new ArrayList<Object>();
			row = new ArrayList<Object>();
			row.add("Proyecto");
			row.add("Tarea");
			row.add("Intervalo");
			row.add("Fecha Inicio");
			row.add("Fecha Final");
			row.add("Duraci�n");
			intervalTable.add(row);
			for (Project subproject : project.getProyectList()) {
				insertIntervalsInTable(subproject, intervalTable, report);
			}
			buildTable(intervalTable, buffer);
			buildLine(buffer);
			
			buildEndTags(buffer);
			buffer.close();
			System.out.println("�Informe detallado realizado con exito!");
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
	}
	/**
	 * M�todo para insertar en una tabla los proyectos cn sus fechas y duraci�n.
	 * Llama a la funci�n que inserta los proyectos de la lista de proyectos
	 * actual y itera con estos proyectos.
	 * @param project
	 * @param table
	 * @param report
	 */
	private void insertSubProjectsInTable(final Project project,
			final ArrayList<Object> table, final Report report) {
		assert (project != null && report != null && table != null);
		
		insertProjectsInTable(project, table, report);
		if (report.getDetailed()) {
			for (Project subproject : project.getProyectList()) {
				insertProjectsInTable(subproject, table, report);
			}
		}
	}
	
	/**
	 * M�todo que crea en el buffer los tags finales del html
	 * @param buffer
	 */
	private void buildEndTags(final BufferedWriter buffer) {
		assert (buffer != null);
		try {
			buffer.write("</body></html>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * M�todo que crea los tags iniciales del html
	 * @param buffer
	 */
	private void buildHeaderTags(final BufferedWriter buffer) {
		assert (buffer != null);
		try {
			buffer.write("<!DOCTYPE HTML><html><head>"
					+ "<meta charset='ISO-8859-1'>"
						+ "<title>TimeTracker</title></head>"
						+ "<body>");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * M�todo que crea la cabecera del informe html
	 * @param buffer
	 * @param title
	 */
	private void buildHeader(final BufferedWriter buffer, final String title) {
		assert (buffer != null && title != null);
		try {
			buildLine(buffer);
			buffer.write("<center><h1>Informe " + title + "</h1></center>");
			buildLine(buffer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * M�todo que crea en el buffer la tabla recibida por
	 * par�metro, poniendole los tags correspondientes.
	 * @param table
	 * @param buffer
	 */
	@SuppressWarnings("unchecked")
	private void buildTable(final ArrayList<Object> table, 
			final BufferedWriter buffer) {
		assert (buffer != null && table != null);
		try {
			buffer.write("<table style= 'text-align: center; "
					+ "width: 842px;' border='1' cellpadding='2'"
					+ " cellspacing='2'>");
			for (int i = 0; i < table.size(); i++) {
				buffer.write("<tr>");
				for (int j = 0; j < ((ArrayList<Object>) 
						table.get(i)).size(); j++) {
					if (i == 0) {
						buffer.write("<th style='background-color:"
								+ "rgb(102, 255, 255);'>");
						buffer.write("" + ((ArrayList<Object>) 
								table.get(i)).get(j));
						buffer.write("</th>");
					} else {
						buffer.write("<td style='background-color:"
								+ "rgb(204, 255, 255);'>");
						buffer.write("" + ((ArrayList<Object>) 
								table.get(i)).get(j));
						buffer.write("</td>");
					}
				}
				buffer.write("</tr>");
			}
			buffer.write("</table>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * M�todo que crea en el buffer una linea separadora para
	 * los elementos del html
	 * @param buffer
	 */
	private void buildLine(final BufferedWriter buffer) {
		assert (buffer != null);
		try {
			buffer.write("<hr />");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * M�todo que crea en el buffer las primeras tablas de los
	 * dos informes, la tabla del intervalo de tiempo del 
	 * informe y la de los proyectos raiz.
	 * @param project
	 * @param report
	 * @param buffer
	 * @param table
	 * @throws IOException
	 */
	private void buildResum(final Project project, 
			final Report report, final BufferedWriter buffer, 
			final ArrayList<Object> table) throws IOException {
		assert (buffer != null && table != null);
		try {
			
			buffer.write("<h1>Periodo</h1>");
			ArrayList<Object> periodTable = new ArrayList<Object>();
			ArrayList<Object> row = new ArrayList<Object>();
			row.add("Desde ");
			row.add("Hasta ");
			row.add("Fecha de generaci�n");
			periodTable.add(row);
			row = new ArrayList<Object>();
			row.add(report.getInitDatePeriod());
			row.add(report.getFinalDatePeriod()); 
			row.add(report.getReportDate()); 
			periodTable.add(row);
			buildTable(periodTable, buffer);
			buildLine(buffer);
			
			buffer.write("<h1>Proyectos raiz</h1>");
			row = new ArrayList<Object>();
			row.add("Padre");
			row.add("Proyecto");
			row.add("Fecha Inicio");
			row.add("Fecha Final");
			row.add("Duraci�n");
			table.add(row);
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * M�todo que inserta todos los proyectos a mostrar en el informe en una
	 * tabla para que despu�s sea escrita en el buffer.
	 * En el m�todo se contemplan todos los posibles casos en
	 * los que los intervalos de los proyectos se pueden encontrar para 
	 * mostrarlos correctamente concorde las fechas del informe.
	 * @param project
	 * @param subprojectTable
	 * @param report
	 */
	private void insertProjectsInTable(final Project project,
			final ArrayList<Object> subprojectTable, final Report report) {
		assert (project != null && subprojectTable != null && report != null);
		for (int i = 0; i < project.getProyectList().size(); i++) {
			int compareInitDate = project.getProyectList().
					get(i).getTimes()
					.getInitDate().compareTo(report.
							getInitDatePeriod());
			int compareFinalDate = project.getProyectList().
					get(i).getTimes()
					.getFinalDate().compareTo(report.
							getFinalDatePeriod());
			int compareInitPFinalR = project.getProyectList().get(i).getTimes()
					.getInitDate().compareTo(report.
							getFinalDatePeriod());
			int compareFinalPInitR = project.getProyectList().get(i).getTimes()
					.getFinalDate().compareTo(report.
							getInitDatePeriod());
			ArrayList<Object> row = new ArrayList<Object>();
			
			if (compareInitDate >= 0) {
				if (compareFinalDate <= 0) {
					row = new ArrayList<Object>();
					row.add(project.getProyectList().get(i)
							.getFatherProject().getName());
					row.add(project.getProyectList().get(i).getName());
					row.add(project.getProyectList().get(i)
							.getTimes().getInitDate());
					row.add(project.getProyectList().get(i)
							.getTimes().getFinalDate());
					row.add(project.getProyectList().get(i)
							.getTimes().getDuration());
					subprojectTable.add(row);
				} else {
					if (compareInitPFinalR < 0) {
						Interval auxiliar = new Interval();
						auxiliar.setInitDate(project.getProyectList()
								.get(i).getTimes().getInitDate());
						auxiliar.setFinalDate(report.getFinalDatePeriod());
						auxiliar.setDuration(report.getFinalDatePeriod());
						row = new ArrayList<Object>();
						row.add(project.getProyectList().get(i)
								.getFatherProject().getName());
						row.add(project.getProyectList().get(i).getName());
						row.add(auxiliar.getInitDate());
						row.add(auxiliar.getFinalDate());
						row.add(auxiliar.getDuration());
						subprojectTable.add(row);
					}
				}
				
			} else {
				if (compareFinalPInitR > 0) {
					if (compareFinalDate > 0) {
						Interval auxiliar = new Interval();
						auxiliar.setInitDate(report.getInitDatePeriod());
						auxiliar.setFinalDate(report.getFinalDatePeriod());
						auxiliar.setDuration(report.getFinalDatePeriod());
						row = new ArrayList<Object>();
						row.add(project.getProyectList().get(i)
								.getFatherProject().getName());
						row.add(project.getProyectList().get(i).getName());
						row.add(auxiliar.getInitDate());
						row.add(auxiliar.getFinalDate());
						row.add(auxiliar.getDuration());
						subprojectTable.add(row);
					} else {
						Interval auxiliar = new Interval();
						auxiliar.setInitDate(report.getInitDatePeriod());
						auxiliar.setFinalDate(project.getProyectList()
								.get(i).getTimes().getFinalDate());
						auxiliar.setDuration(project.getProyectList()
								.get(i).getTimes().getFinalDate());
						row = new ArrayList<Object>();
						row.add(project.getProyectList().get(i)
								.getFatherProject().getName());
						row.add(project.getProyectList().get(i).getName());
						row.add(auxiliar.getInitDate());
						row.add(auxiliar.getFinalDate());
						row.add(auxiliar.getDuration());
						subprojectTable.add(row);
					}
				}
			}
		}
	}

	/**
	 * M�todo que inserta todas las tareas que se van a mostrar en el informe
	 * en una tabla que despu�s ser� escrita en el buffer.
	 * En el m�todo se contemplan todos los posibles casos en
	 * los que los intervalos de la tarea se pueden encontrar para 
	 * mostrarlos correctamente concorde las fechas del informe.
	 * @param project
	 * @param taskTable
	 * @param report
	 */
	private void insertTasksInTable(final Project project,
			final ArrayList<Object> taskTable, final Report report) {
		assert (project != null && taskTable != null && report != null);
		for (int i = 0; i < project.getTaskList().size(); i++) {
			int compareInitDate = project.getTaskList().
					get(i).getTimes()
					.getInitDate().compareTo(report.
							getInitDatePeriod());
			int compareFinalDate = project.getTaskList().
					get(i).getTimes()
					.getFinalDate().compareTo(report.
							getFinalDatePeriod());
			int compareInitPFinalR = project.getTaskList().get(i).getTimes()
					.getInitDate().compareTo(report.
							getFinalDatePeriod());
			int compareFinalPInitR = project.getTaskList().get(i).getTimes()
					.getFinalDate().compareTo(report.
							getInitDatePeriod());
			ArrayList<Object> row = new ArrayList<Object>();
			
			if (compareInitDate >= 0) {
				if (compareFinalDate <= 0) {
					row = new ArrayList<Object>();
					row.add(project.getName());
					row.add(project.getTaskList().get(i).getName());
					row.add(project.getTaskList().get(i)
							.getTimes().getInitDate());
					row.add(project.getTaskList().get(i)
							.getTimes().getFinalDate());
					row.add(project.getTaskList().get(i)
							.getTimes().getDuration());
					taskTable.add(row);
				} else {
					if (compareInitPFinalR < 0) {
						Interval auxiliar = new Interval();
						auxiliar.setInitDate(project.getTaskList()
								.get(i).getTimes().getInitDate());
						auxiliar.setFinalDate(report.getFinalDatePeriod());
						auxiliar.setDuration(report.getFinalDatePeriod());
						row = new ArrayList<Object>();
						row.add(project.getName());
						row.add(project.getTaskList().get(i).getName());
						row.add(auxiliar.getInitDate());
						row.add(auxiliar.getFinalDate());
						row.add(auxiliar.getDuration());
						taskTable.add(row);
					}
				}
				
			} else {
				if (compareFinalPInitR > 0) {
					if (compareFinalDate > 0) {
						Interval auxiliar = new Interval();
						auxiliar.setInitDate(report.getInitDatePeriod());
						auxiliar.setFinalDate(report.getFinalDatePeriod());
						auxiliar.setDuration(report.getFinalDatePeriod());
						row = new ArrayList<Object>();
						row.add(project.getName());
						row.add(project.getTaskList().get(i).getName());
						row.add(auxiliar.getInitDate());
						row.add(auxiliar.getFinalDate());
						row.add(auxiliar.getDuration());
						taskTable.add(row);
					} else {
						Interval auxiliar = new Interval();
						auxiliar.setInitDate(report.getInitDatePeriod());
						auxiliar.setFinalDate(project.getTaskList()
								.get(i).getTimes().getFinalDate());
						auxiliar.setDuration(project.getTaskList()
								.get(i).getTimes().getFinalDate());
						row = new ArrayList<Object>();
						row.add(project.getName());
						row.add(project.getProyectList().get(i).getName());
						row.add(auxiliar.getInitDate());
						row.add(auxiliar.getFinalDate());
						row.add(auxiliar.getDuration());
						taskTable.add(row);
					}
				}
			}
		}
		if (report.getDetailed()) {
			for (Project subproject : project.getProyectList()) {
				insertTasksInTable(subproject, taskTable, report);
			}
		}
	}
	
	/**
	 * M�todo que inserta en una tabla todos los intervalos que se mostrar�n
	 * en el informe. En el m�todo se contemplan todos los posibles casos en
	 * los que el intervalo se puede encontrar para mostrarlo correctamente
	 * concorde las fechas del informe.
	 * @param project
	 * @param intervalTable
	 * @param report
	 */
	private void insertIntervalsInTable(final Project project,
			final ArrayList<Object> intervalTable, final Report report) {
		assert (project != null && intervalTable != null && report != null);
		for (Task task : project.getTaskList()) {
			for (int i = 0; i < task.getIntervalList().size(); i++) {
				int compareInitDate = task.getIntervalList().get(i)
						.getInitDate().compareTo(report.
								getInitDatePeriod());
				int compareFinalDate = task.getIntervalList().
						get(i).getFinalDate().compareTo(report.
								getFinalDatePeriod());
				int compareInitPFinalR = task.getIntervalList().get(i)
						.getInitDate().compareTo(report.
								getFinalDatePeriod());
				int compareFinalPInitR = task.getIntervalList().get(i)
						.getFinalDate().compareTo(report.
								getInitDatePeriod());
				ArrayList<Object> row = new ArrayList<Object>();
				if (compareInitDate >= 0) {
					if (compareFinalDate <= 0) {
						row = new ArrayList<Object>();
						row.add(project.getName());
						row.add(task.getName());
						row.add(i + 1);
						row.add(task.getIntervalList().get(i)
								.getInitDate());
						row.add(task.getIntervalList().get(i)
								.getFinalDate());
						row.add(task.getIntervalList().get(i)
								.getDuration());
						intervalTable.add(row);
					} else {
						if (compareInitPFinalR < 0) {
							Interval auxiliar = new Interval();
							auxiliar.setInitDate(task.getIntervalList()
									.get(i).getInitDate());
							auxiliar.setFinalDate(report.getFinalDatePeriod());
							auxiliar.setDuration(report.getFinalDatePeriod());
							row = new ArrayList<Object>();
							row.add(project.getName());
							row.add(task.getName());
							row.add(i + 1);
							row.add(auxiliar.getInitDate());
							row.add(auxiliar.getFinalDate());
							row.add(auxiliar.getDuration());
							intervalTable.add(row);
						}
					}
					
				} else {
					if (compareFinalPInitR > 0) {
						if (compareFinalDate > 0) {
							Interval auxiliar = new Interval();
							auxiliar.setInitDate(report.getInitDatePeriod());
							auxiliar.setFinalDate(report.getFinalDatePeriod());
							auxiliar.setDuration(report.getFinalDatePeriod());
							row = new ArrayList<Object>();
							row.add(project.getName());
							row.add(task.getName());
							row.add(i + 1);
							row.add(auxiliar.getInitDate());
							row.add(auxiliar.getFinalDate());
							row.add(auxiliar.getDuration());
							intervalTable.add(row);
						} else {
							Interval auxiliar = new Interval();
							auxiliar.setInitDate(report.getInitDatePeriod());
							auxiliar.setFinalDate(task.getIntervalList()
									.get(i).getFinalDate());
							auxiliar.setDuration(task.getIntervalList()
									.get(i).getFinalDate());
							row = new ArrayList<Object>();
							row.add(project.getName());
							row.add(task.getName());
							row.add(i + 1);
							row.add(auxiliar.getInitDate());
							row.add(auxiliar.getFinalDate());
							row.add(auxiliar.getDuration());
							intervalTable.add(row);
						}
					}
				}
			}
		}
		for (Project subproject2 : project.getProyectList()) {
			insertIntervalsInTable(subproject2, intervalTable, report);
		}
	}
}