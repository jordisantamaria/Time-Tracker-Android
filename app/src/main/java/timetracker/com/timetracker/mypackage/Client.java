package timetracker.com.timetracker.mypackage;
import java.util.Date;
import java.util.Scanner;

public class Client {
	
	/**
	 * @uml.property name="START_TASK_OPTION"
	 */
	private static final int START_TASK_OPTION = 3;
	/**
	 * @uml.property name="STOP_TASK_OPTION"
	 */
	private static final int STOP_TASK_OPTION = 4;
	/**
	 * @uml.property name="OPEN_PROJECT_OPTION"
	 */
	private static final int OPEN_PROJECT_OPTION = 5;
	/**
	 * @uml.property name="BACK_FATHER_PROJECT_OPTION"
	 */
	private static final int BACK_FATHER_PROJECT_OPTION = 6;
	/**
	 * @uml.property name="PRINT_TABLE_TIMES_OPTION"
	 */
	private static final int PRINT_TABLE_TIMES_OPTION = 7;
	/**
	 * @uml.property name="SAVE_PROJECT_OPTION"
	 */
	private static final int SAVE_PROJECT_OPTION = 8;
	/**
	 * @uml.property name="READ_PROJECT_OPTION"
	 */
	private static final int READ_PROJECT_OPTION = 9;
	/**
	 * @uml.property name="CREATE_REPORT_OPTION"
	 */
	private static final int CREATE_REPORT_OPTION = 10;
	/**
	 * @uml.property name="CREATE_DETAILED_REPORT_OPTION"
	 */
	private static final int CREATE_DETAILED_REPORT_OPTION = 11;
	/**
	 * @uml.property name="CREATE_ALL_REPORTS"
	 */
	private static final int CREATE_ALL_REPORTS = 12;
	/**
	 * @uml.property name="YEAR_FORMAT_DATECLASS"
	 */
	private static final int YEAR_FORMAT_DATECLASS = 1900;
	/**
	 * @uml.property name="MONTH_FORMAT_DATECLASS"
	 */
	private static final int MONTH_FORMAT_DATECLASS = 1;
	
	/**
	 * Variables para testear la opci�n 12 del men�: Creaci�n
	 * autom�tica de los informes.
	 */
	private static final int  YEAR = 2016;
	private static final int MONTH = 11;
	private static final int DAY = 23;
	private static final int H1 = 2;
	private static final int M1 = 0;
	private static final int H2 = 22;
	private static final int M2 = 30;
	
	/**
	 * @uml.property name="scanner"
	 * Scanner sirve para recoger texto por consola
	 */
	private static Scanner scanner = new Scanner(System.in); 
	/**
	 * Variable select que guardar� la opci�n elegida por el usuario
	 * 
	 * @uml.property name="select"
	 * Para guardar la opci�n elegida por el usuario
	 */
	private static int select = -1;
	/**
	 * Objeto de tipo Project que servir� para tratar los proyectos des de el
	 * main
	 * 
	 * @uml.property name="project"
	 */
	private static Project project = new Project();
	/**
	 * @uml.property name="clock"
	 */
	private static Clock clock = new Clock();
	/**
	 * @uml.property name="serial"
	 */
	private static Serial serial = new Serial();

	@SuppressWarnings("deprecation")
	public static final void main(final String[] args) {
		project.setName("PRaiz");
		clock.startClock();
		while (select != 0) {
			try {
				System.out.println("Proyecto en el que est�s:"
						+ project.getName());
				System.out
						.println("Elige opci�n:\n1.- Crear Proyecto"
								+ "\n2.- Crear Tarea\n3.- Iniciar "
								+ "Tarea\n4.- Parar Tarea\n"
								+ "5.- Abrir Subproyecto\n" 
								+ "6.- Volver al Proyecto Padre\n"
								+ "7.- Imprimir tabla de tiempos\n"
								+ "8.- Guardar Proyecto\n"
								+ "9.- Cargar Proyecto\n"
								+ "10.- Crear Reporte resumido\n"
								+ "11.- Crear Reporte detallado\n"
								+ "12.- Crear Todos los informes");
				select = Integer.parseInt(scanner.nextLine());
				switch (select) {
				case 1:
					createProject();
					break;
				case 2:
					createTask();
					break;
				case START_TASK_OPTION:
					startTask();
					break;
				case STOP_TASK_OPTION:
					stopTask();
					break;
				case OPEN_PROJECT_OPTION:
					openSubProject();
					break;
				case BACK_FATHER_PROJECT_OPTION:
					backToFatherProject();
					break;
				case PRINT_TABLE_TIMES_OPTION:
					visitor.print();
					project.acceptVisitor(visitor);
					break;
				case SAVE_PROJECT_OPTION:
					serial.save(project);
					System.out.println("�Proyecto guardado con exito!");
					break;
				case READ_PROJECT_OPTION:
					project = serial.read();
					System.out.println("�Proyecto cargado con exito!\n" 
					+ "�Utiliza la opci�n 7 para poder ver el proyecto" 
					+ " cargado o cualquier otra opci�n para seguir donde" 
					+ " lo dejaste! ");
					break;
				case CREATE_REPORT_OPTION:
					Date initDate1 = scanReportInitDate();
					Date finalDate1 = scanReportFinalDate();
					System.out
					.println("Elige el formato del informe:\n1.- Txt"
							+ "\n2.- Html\n");
					select = Integer.parseInt(scanner.nextLine());
					switch (select) {
					case 1:
						Format reportFormat = new Txt();
						project.createSmallReport(initDate1, 
								finalDate1, reportFormat);
						break;
					case 2:
						reportFormat = new Html();
						project.createSmallReport(initDate1, 
								finalDate1, reportFormat);
						break;
					default:
						System.out.println("Opci�n no v�lida.");
						break;
					}
					break;
				case CREATE_DETAILED_REPORT_OPTION:
					initDate1 = scanReportInitDate();
					finalDate1 = scanReportFinalDate();
					System.out
					.println("Elige el formato del informe:\n1.- Txt"
							+ "\n2.- Html\n");
					select = Integer.parseInt(scanner.nextLine());
					switch (select) {
					case 1:
						Format reportFormat = new Txt();
						project.createDetailedReport(initDate1, 
								finalDate1, reportFormat);
						break;
					case 2:
						reportFormat = new Html();
						project.createDetailedReport(initDate1, 
								finalDate1, reportFormat);
						break;
					default:
						System.out.println("Opci�n no v�lida.");
						break;
					}
					break;
				case CREATE_ALL_REPORTS:
					
					Date initD = new Date(YEAR - YEAR_FORMAT_DATECLASS, 
							MONTH - MONTH_FORMAT_DATECLASS, 
							DAY, H1, M1, 0);
					Date finalD = new Date(YEAR - YEAR_FORMAT_DATECLASS, 
							MONTH - MONTH_FORMAT_DATECLASS, 
							DAY, H2, M2, 0);
					Format form = new Txt();
					project.createSmallReport(initD, 
							finalD, form);
					project.createDetailedReport(initD, 
							finalD, form);
					form = new Html();
					project.createSmallReport(initD, 
							finalD, form);
					project.createDetailedReport(initD, 
							finalD, form);
					break;
				default:
					System.out.println("No has seleccionado ninguna opci�n");
					break;
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	/**
	 * Pedimos al usuario nombre y descripci�n de la tarea que quiere crear, y
	 * creamos el objeto tasca que ser� a�adido a la lista de tareas.
	 */
	public static void createTask() {
		System.out.println("Task name: ");
		String name = scanner.nextLine();
		System.out.println("Task description: ");
		String description = scanner.nextLine();
		Task task = new Task(name, description);

		project.addTask(task);
	}

	public Client() {
	}

	/**
	 * Se muestra por pantalla todos los proyectos guardados en la lista y se
	 * recoge aquel que ha elegido
	 */
	private static void openSubProject() {
		System.out.println("Id del subproyecto que quieres abrir: "
				+ project.listProjects());
		String id = scanner.nextLine();
		project = project.getSubproject(Integer.parseInt(id));
	}

	/**
	 * Se le muestran al usuario todas las tareas activas en ese momento y se
	 * recoge la que ha elegido para pausar-la
	 */
	private static void stopTask() {
		System.out.println("Id de la tarea a pausar: " + project.listsTasks());
		String id = scanner.nextLine();
		project.stopTask(clock, Integer.parseInt(id));
	}

	/**
	 * Comprobamos que no estamos ya en el proyecto ra�z, y si no lo estamos
	 * volvemos a el
	 */
	private static void backToFatherProject() {
		if (project.getFatherProject() == null) {
			System.out.println("Est�s en el proyecto raiz," 
					+ " no puedes ir m�s para atr�s");
		} else {
			project = project.getFatherProject();
		}
	}

	/**
	 * Se pide al usuario un nombre y una descripcion para el proyecto que
	 * quiere crear y se a�ade a la lista de proyectos creados.
	 */
	public static void createProject() {
		System.out.println("Project name: ");
		String name = scanner.nextLine();
		System.out.println("Project description: ");
		String description = scanner.nextLine();
		Project proj = new Project(name, description, project);

		project.addSubproject(proj);
	}

	/**
	 * Se le pide al usuario la tarea a iniciar, se recoge con el scanner y se
	 * llama a startTask que iniciar� la tarea.
	 */
	public static void startTask() {
		System.out.println("Id de la tarea a iniciar: " + project.listsTasks());
		String id = scanner.nextLine();
		project.startTask(clock, Integer.parseInt(id));
	}

	/**
	 * @uml.property name="printVisitor"
	 * @uml.associationEnd inverse="client:PrintVisitor"
	 */
	private PrintVisitor printVisitor;

	/**
	 * Getter of the property <tt>printVisitor</tt>
	 * 
	 * @return Returns the printVisitor.
	 * @uml.property name="printVisitor"
	 */
	public final PrintVisitor getPrintVisitor() {
		return printVisitor;
	}

	/**
	 * Setter of the property <tt>printVisitor</tt>
	 * 
	 * @param printVisitor
	 *            The printVisitor to set.
	 * @uml.property name="printVisitor"
	 */
	public final void setPrintVisitor(final PrintVisitor clientVisitor) {
		this.printVisitor = clientVisitor;
	}
	/**
	 * @uml.property name="visitor"
	 */
	private static PrintVisitor visitor = new PrintVisitor();
	
	public static final Date scanReportInitDate() {
		System.out.println("Introduce la fecha de inicio:\n");
		System.out.println("Dia: ");
		Scanner day = new Scanner(System.in);
		int dayInt = day.nextInt();
		System.out.println("Mes: ");
		Scanner month = new Scanner(System.in);
		int monthInt = month.nextInt();
		System.out.println("A�o: ");
		Scanner year = new Scanner(System.in);
		int yearInt = year.nextInt();
		System.out.println("Hora: ");
		Scanner hour = new Scanner(System.in);
		int hourInt = hour.nextInt();
		System.out.println("Minuto: ");
		Scanner min = new Scanner(System.in);
		int minInt = min.nextInt();
		@SuppressWarnings("deprecation")
		Date initDate = new Date(yearInt - YEAR_FORMAT_DATECLASS, 
				monthInt - MONTH_FORMAT_DATECLASS, 
				dayInt, hourInt, minInt, 0);
		return initDate;
	}
	public static final Date scanReportFinalDate() {
		System.out.println("Introduce la fecha final:\n");
		System.out.println("Dia: ");
		Scanner day = new Scanner(System.in);
		int dayInt = day.nextInt();
		System.out.println("Mes: ");
		Scanner month = new Scanner(System.in);
		int monthInt = month.nextInt();
		System.out.println("A�o: ");
		Scanner year = new Scanner(System.in);
		int yearInt = year.nextInt();
		System.out.println("Hora: ");
		Scanner hour = new Scanner(System.in);
		int hourInt = hour.nextInt();
		System.out.println("Minuto: ");
		Scanner min = new Scanner(System.in);
		int minInt = min.nextInt();
		@SuppressWarnings("deprecation")
		Date finalDate = new Date(yearInt - YEAR_FORMAT_DATECLASS, 
				monthInt - MONTH_FORMAT_DATECLASS,
				dayInt, hourInt, minInt, 0);
		return finalDate;
	}
}
