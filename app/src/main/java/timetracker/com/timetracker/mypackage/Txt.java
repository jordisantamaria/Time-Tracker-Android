package timetracker.com.timetracker.mypackage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Clase Txt, heredada de Format, utilizada para 
 * darle un format .txt a los informes.
 *
 */
@SuppressWarnings("serial")
public class Txt extends Format {
	/**
	 * M�todo para exportar el proyecto recibido
	 * hacia un archivo .txt
	 * @param project : Proyecto utilizado para el informe
	 * @param report : Informe a exportar
	 */
	@Override
	public final void export(final Project project, final Report report) 
			throws IOException {
		assert (project != null && report != null);
		File file = new File("report_" + project.getName() + ".txt");
		BufferedWriter buffer = new BufferedWriter(new FileWriter(file));
		try {
			buffer.write(
					"--------------------------------------------------"
					+ "-----------------------------------------------"
					+ "----\nInforme Resumen\n------------------------"
					+ "-----------------------------------------------"
					+ "----------------------------"
					+ "\nPeriodo" + "\nFecha" + "\nDesde "
					+ report.getInitDatePeriod() + "\nHasta "
					+ report.getFinalDatePeriod()
					+ "\nFecha de generaci�n del informe: "
					+ report.getReportDate()
					+ "\n-----------------------------------------------"
					+ "---\n Proyectos raiz\n"
					+ "Padre      Proyecto            Fecha inicial      "
					+ "                      Fecha final            "
					+ "      Duraci�n");
			writeSubprojects(project, report, buffer);
			buffer.close();
			System.out.println("�Informe realizado con exito!");
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	/**
	 * M�todo que escribe en el buffer todos los proyectos que se van
	 * a mostrar en el informe. Si se trata del informe resumido solo
	 * escribir� en el buffer los proyectos raiz, mientras que si es
	 * el detallado escribir� todos ellos.
	 * @param project
	 * @param report
	 * @param buffer
	 */
	private void writeSubprojects(final Project project, 
			final Report report, final BufferedWriter buffer) {
		assert (project != null && report != null && buffer  != null);
		writeProjects(project, report, buffer);
		if (report.getDetailed()) {
			for (Project subproject : project.getProyectList()) {
				writeSubprojects(subproject, report, buffer);
			}
		}
	}
	
	/**
	 * M�todo para escribir en el buffer las tareas que se mostrar�n
	 * en el informe realizado. Se contemplan todos los casos posibles
	 * en los que las fechas del informe y la de la tarea pueden estar.
	 * @param project
	 * @param report
	 * @param buffer
	 */
	private void writeTasks(final Project project, 
			final Report report, final BufferedWriter buffer) {
		assert (project != null && report != null && buffer  != null);
		for (int i = 0; i < project.getTaskList().size(); i++) {
			int compareInitDate = project.getTaskList().
					get(i).getTimes()
					.getInitDate().compareTo(report.
							getInitDatePeriod());
			int compareFinalDate = project.getTaskList().
					get(i).getTimes()
					.getFinalDate().compareTo(report.
							getFinalDatePeriod());
			if (compareInitDate >= 0 && compareFinalDate <= 0) {
				try {
					buffer.write("\n" + project.getName()
							+ "         " 
							+ project.getTaskList().get(i).getName()
							+ "    "
							+ project.getTaskList().get(i).getTimes()
									.getInitDate()
							+ "          "
							+ project.getTaskList().get(i).getTimes()
									.getFinalDate()
							+ "          "
							+ project.getTaskList().get(i).getTimes()
									.getDuration() + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				if (compareInitDate >= 0 && compareFinalDate > 0) {
					try {
						Interval auxiliar = new Interval();
						auxiliar.setInitDate(project.getTaskList().
								get(i).getTimes().getInitDate());
						auxiliar.setFinalDate(report.getFinalDatePeriod());
						auxiliar.setDuration(report.getFinalDatePeriod());
						buffer.write("\n" + project.getName()
								+ "         " 
								+ project.getTaskList().get(i).getName()
								+ "    "
								+ auxiliar.getInitDate()
								+ "          "
								+ auxiliar.getFinalDate()
								+ "          "
								+ auxiliar.getDuration() + "\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					if (compareInitDate < 0 && compareFinalDate <= 0) {
						try {
							Interval auxiliar = new Interval();
							auxiliar.setInitDate(report.getInitDatePeriod());
							auxiliar.setFinalDate(project.getTaskList().
									get(i).getTimes().getFinalDate());
							auxiliar.setDuration(project.getTaskList().
									get(i).getTimes().getFinalDate());
							buffer.write("\n" + project.getName()
									+ "         " 
									+ project.getTaskList().get(i).getName()
									+ "    "
									+ auxiliar.getInitDate()
									+ "          "
									+ auxiliar.getFinalDate()
									+ "          "
									+ auxiliar.getDuration() + "\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		for (Project subproject : project.getProyectList()) {
			writeTasks(subproject, report, buffer);
		}
		
	}
	
	/**
	 * M�todo que escribe en el buffer los intervalos de las tareas
	 * que se van a mostrar en el informe realizado. Se contemplan 
	 * todos los posibles casos en los que se pueden encontrar las
	 * fechas del informe con las de los intervalos, para tratarlas
	 * debidamente.
	 * @param subproject
	 * @param report
	 * @param buffer
	 */
	private void writeIntervals(final Project subproject, 
			final Report report, final BufferedWriter buffer) {
		assert (subproject != null && report != null && buffer  != null);
		for (Task task : subproject.getTaskList()) {
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
				if (compareInitDate >= 0) {
					if (compareFinalDate <= 0) {
						try {
							buffer.write("\n" + task.getName()
									+ "          "
									+ (i + 1) + "      "
									+ task.getIntervalList().get(i).
										getInitDate()
									+ "          "
									+ task.getIntervalList().get(i).
										getFinalDate()
									+ "          "
									+ task.getIntervalList().get(i)
										.getDuration() + "\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						if (compareInitPFinalR < 0) {
							try {
								Interval auxiliar = new Interval();
								auxiliar.setInitDate(task.getIntervalList().
										get(i).getInitDate());
								auxiliar.setFinalDate(report.
										getFinalDatePeriod());
								auxiliar.setDuration(report.
										getFinalDatePeriod());
								buffer.write("\n" + task.getName()
										+ "          "
										+ (i + 1) + "      "
										+ auxiliar.getInitDate()
										+ "          "
										+ auxiliar.getFinalDate()
										+ "          "
										+ auxiliar.getDuration() + "\n");
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
					
				} else {
					if (compareFinalPInitR > 0) {
						if (compareFinalDate > 0) {
							try {
								Interval auxiliar = new Interval();
								auxiliar.setInitDate(report.
										getInitDatePeriod());
								auxiliar.setFinalDate(report.
										getFinalDatePeriod());
								auxiliar.setDuration(report.
										getFinalDatePeriod());
								buffer.write("\n" + task.getName()
										+ "          "
										+ (i + 1) + "      "
										+ auxiliar.getInitDate()
										+ "          "
										+ auxiliar.getFinalDate()
										+ "          "
										+ auxiliar.getDuration() + "\n");
							} catch (IOException e) {
								e.printStackTrace();
							}
						} else {
							try {
								Interval auxiliar = new Interval();
								auxiliar.setInitDate(
										report.getInitDatePeriod());
								auxiliar.setFinalDate(task.getIntervalList().
										get(i).getFinalDate());
								auxiliar.setDuration(task.getIntervalList().
										get(i).getFinalDate());
								buffer.write("\n" + task.getName()
										+ "          "
										+ (i + 1) + "      "
										+ auxiliar.getInitDate()
										+ "          "
										+ auxiliar.getFinalDate()
										+ "          "
										+ auxiliar.getDuration() + "\n");
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		for (Project subproject2 : subproject.getProyectList()) {
			writeIntervals(subproject2, report, buffer);
		}
	}

	/**
	 * M�todo para exportar el informe detallado en el archivo txt.
	 * Para ello va escribiendo en el buffer todas las tablas requeridas.
	 */
	@Override
	public final void detailedExport(final Project project, 
			final Report report) {
		assert (project != null && report != null);
		File file = new File("Detailed_Report_" + project.getName() + ".txt");
		BufferedWriter buffer = null;
		try {
			buffer = new BufferedWriter(new FileWriter(file));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			buffer.write(
					"--------------------------------------------------"
					+ "-----------------------------------------------"
					+ "----\nInforme Detallado\n------------------------"
					+ "-----------------------------------------------"
					+ "----------------------------"
					+ "\nPeriodo" + "\nFecha" + "\nDesde "
					+ report.getInitDatePeriod() + "\nHasta "
					+ report.getFinalDatePeriod()
					+ "\nFecha de generaci�n del informe: "
					+ report.getReportDate()
					+ "\n-----------------------------------------------"
					+ "---\nProyectos raiz de: " + project.getName() + "\n");
			crearCabecera("Padre", "Proyecto", 
					"Fecha Inicial", "Fecha Final", "Duraci�n", buffer);
			writeProjects(project, report, buffer);
			
			buffer.write("\n---------------------------------------------\n");
			buffer.write("\nSubproyectos\nSe incluyen en la siguiente tabla"
					+ "solo los subproyectos que tienen alguna tarea con"
					+ "algun intervalo dentro del periodo.");
			crearCabecera("Padre", "Proyecto", 
					"Fecha Inicial", "Fecha Final", "Duraci�n", buffer);
			for (Project subproject : project.getProyectList()) {
				writeSubprojects(subproject, report, buffer);
			}
			
			buffer.write("\n---------------------------------------------\n");
			buffer.write("\nTareas\nSe incluyen en la siguiente tabla"
					+ "la duraci�n de todas las tareas y el proyecto al"
					+ "cual pertenecen.");
			crearCabecera("Proyecto", "Tarea", 
					"Fecha Inicial", "Fecha Final", "Duraci�n", buffer);
			for (Project subproject : project.getProyectList()) {
				writeTasks(subproject, report, buffer);
			}
			
			buffer.write("\n---------------------------------------------\n");
			buffer.write("\nIntervalos\nSe incluyen en la siguiente tabla"
					+ "la fecha de inicio, la final y la duraci�n de los"
					+ "intervalos con la fecha de inicio y final"
					+ "especificados, as� como la tarea a la que pertenecen");
			crearCabecera("Tarea", "Intervalo", 
					"Fecha Inicial", "Fecha Final", "Duraci�n", buffer);
			for (Project subproject : project.getProyectList()) {
					writeIntervals(subproject, report, buffer);
			}
			buffer.write("\n---------------------------------------------\n");
			buffer.close();
			System.out.println("�Informe realizado con exito!");
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

	/**
	 * M�todo que escribe en el buffer los proyectos de la lista de proyectos
	 * del proyecto recibido por par�metro.
	 * @param project
	 * @param report
	 * @param buffer
	 */
	private void writeProjects(final Project project, final Report report,
			final BufferedWriter buffer) {
		assert (project != null && report != null && buffer  != null);
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
			if (compareInitDate >= 0) {
				if (compareFinalDate <= 0) {
					try {
						buffer.write("\n" + project.getName()
								+ "        "
								+ project.getProyectList().get(i).getName()
								+ "      "
								+ project.getProyectList().get(i).getTimes()
										.getInitDate()
								+ "          "
								+ project.getProyectList().get(i).getTimes()
										.getFinalDate()
								+ "          "
								+ project.getProyectList().get(i).getTimes()
										.getDuration() + "\n");
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					if (compareInitPFinalR < 0) {
						try {
							Interval auxiliar = new Interval();
							auxiliar.setInitDate(project.getProyectList().
									get(i).getTimes().getInitDate());
							auxiliar.setFinalDate(report.getFinalDatePeriod());
							auxiliar.setDuration(report.getFinalDatePeriod());
							buffer.write("\n" + project.getName()
									+ "        "
									+ project.getProyectList().get(i).getName()
									+ "      "
									+ auxiliar.getInitDate()
									+ "          "
									+ auxiliar.getFinalDate()
									+ "          "
									+ auxiliar.getDuration() + "\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
			} else {
				if (compareFinalPInitR > 0) {
					if (compareFinalDate > 0) {
						try {
							Interval auxiliar = new Interval();
							auxiliar.setInitDate(report.getInitDatePeriod());
							auxiliar.setFinalDate(report.getFinalDatePeriod());
							auxiliar.setDuration(report.getFinalDatePeriod());
							buffer.write("\n" + project.getName()
									+ "        "
									+ project.getProyectList().get(i).getName()
									+ "      "
									+ auxiliar.getInitDate()
									+ "          "
									+ auxiliar.getFinalDate()
									+ "          "
									+ auxiliar.getDuration() + "\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						try {
							Interval auxiliar = new Interval();
							auxiliar.setInitDate(report.getInitDatePeriod());
							auxiliar.setFinalDate(project.getProyectList().
									get(i).getTimes().getFinalDate());
							auxiliar.setDuration(project.getProyectList().
									get(i).getTimes().getFinalDate());
							buffer.write("\n" + project.getName()
									+ "        "
									+ project.getProyectList().get(i).getName()
									+ "      "
									+ auxiliar.getInitDate()
									+ "          "
									+ auxiliar.getFinalDate()
									+ "          "
									+ auxiliar.getDuration() + "\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		
	}

	/**
	 * M�todo que escribe en el buffer las cabeceras de las
	 * tablas utilizadas.
	 * @param string
	 * @param string2
	 * @param string3
	 * @param string4
	 * @param string5
	 * @param buffer
	 */
	private void crearCabecera(final String string, 
			final String string2, final String string3, 
			final String string4, final String string5,
			final BufferedWriter buffer) {
		try {
			buffer.write("\n\n" + string + "       " 
					+ string2 + "          " + string3 
					+ "                            " + string4 
					+ "                  " + string5 + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
