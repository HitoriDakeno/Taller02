package taller02;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * 
 * Gestor principal de la fonda. Maneja los arreglos de participantes y consumos.
 * 
 */

public class SistemaFonda
{
	//lector global
	private Scanner lector;
	
	//arreglos estaticos y contadores de control
	private String[] participantes = new String[200];
	private int cantidadParticipantes = 0;
	
	private Consumo[] consumos = new Consumo[200];
	
	private int cantidadConsumos = 0;
	
	private String archParticipantes = "Participantes.txt";
	private String archConsumos = "Consumido.txt";
	
	
	/**
	 * Constructor que inicia el sistema y recibe el Scanner
	 * utilizado para leer las opciones y los datos ingresados por teclado.
	 * 
	 * 
	 * @param lector Scanner utilizado para leer desde teclado
	 */
	
	
	public SistemaFonda(Scanner lector)
	{
		this.lector = lector;
	}
	
	
	/*
	 * Carga los archivos iniciales de participantes y consumos.
	 */
	
	public void cargarArchivos()
	{
		cargarParticipantes();
		cargarConsumos();
		
	}
	
	
	/**
	 * Lee los nombres del archivo de participantes y los guarda en el arreglo.
	 * Evita agregar nombres vacios o participantes que ya se encuentren registrados.
	 * La carga se realiza hasta alcanzar la capacidad maxima del arreglo (200).
	 */
	private void cargarParticipantes()
	{
		try {
			File arch = new File(archParticipantes);
			Scanner lectorArch = new Scanner(arch);
			
			cantidadParticipantes = 0;
			
			while(lectorArch.hasNextLine() && cantidadParticipantes < 200 ) {
				String nombre = lectorArch.nextLine();
				
				if(!nombre.equals("") && buscarParticipante(nombre) == -1) {
					participantes[cantidadParticipantes] = nombre;
					cantidadParticipantes++;
				}
			}
			lectorArch.close();
			
			
		}catch(IOException e) {
			System.out.println("No se pudo leer "+archParticipantes);
			System.out.println("Error: " + e.toString());
		}
		
	}
	
	/**
	 * Lee los consumos almacenados en el archivo y verifica el formato
	 * y los datos de cada registro antes de guardarlo en el arreglo.
	 * Descarta los registros incorrectos y muestra un mensaje de error
	 * cuando no es posible leer o procesar datos.
	 * 
	 */
	private void cargarConsumos()
	{
		try {
			File arch = new File(archConsumos);
			Scanner lectorArch = new Scanner(arch);
			
			cantidadConsumos = 0;
			
			while(lectorArch.hasNextLine() && cantidadConsumos < 200) {
				String linea = lectorArch.nextLine();
				if(linea.equals("")) {
					continue;
				}
				
				
				String[] partes = linea.split(";");
				try {
					if(partes.length == 5 && partes[0].equalsIgnoreCase("Choripan")) {
						
						String consumidor = partes[1];
						double largo = Double.parseDouble(partes[2]);
						double ancho = Double.parseDouble(partes[3]);
						String fecha = partes[4];
						
						if(buscarParticipante(consumidor) != -1 && numeroPositivo(largo) && numeroPositivo(ancho) && fechaValida(fecha)) {
							consumos[cantidadConsumos] = new Consumo("Choripan", consumidor,largo,ancho,0,fecha);
							cantidadConsumos++;
						}
						else {
							System.out.println("Registro descartado: " + linea);
						}
			
					} else if(partes.length == 4 && partes[0].equalsIgnoreCase("Terremoto")) {
						String consumidor = partes[1];
						double litros = Double.parseDouble(partes[2]);
						String fecha = partes[3];
						
						if(buscarParticipante(consumidor) != -1 && numeroPositivo(litros) && fechaValida(fecha)) {
							consumos[cantidadConsumos] = new Consumo("Terremoto", consumidor, 0, 0, litros, fecha);
							cantidadConsumos++;
						}
						else {
							System.out.println("Registro descartado: " + linea);
						}
					} else {
						System.out.println("Linea incorrecta descartada: " + linea);
					}
					
					
					
					
				} catch(Exception e) {
					System.out.println("No se pudo procesar: "+linea);
				}
				
			}
			
			lectorArch.close();
			
		} catch(IOException e) {
			System.out.println("No es posible leer "+archConsumos);
			System.out.println("Se iniciara sin registro anteriores.");
			System.out.println("Error: " + e.getMessage());
			
		}
		
	}

	/**
	 * 
	 * Busca un participante por su nombre sin distinguir entre mayusculas y minusculas.
	 * 
	 * 
	 * @param nombre: nombre del participante que se desea buscar
	 * @return posicion del participante si existe, o -1 si no se encuentra
	 */
	
	
	private int buscarParticipante(String nombre)
	{
		for(int i=0;i<cantidadParticipantes;i++) {
			if(participantes[i].equalsIgnoreCase(nombre)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Comprueba que un numero sea mayor que cero y no sea infinito positivo.
	 * 
	 * @param numero: valor numerico que se desea validar
	 * @return true si el numero cumple las condiciones, false en caso contrario
	 */


	private boolean numeroPositivo(double numero)
	{
		return numero > 0 && numero != Double.POSITIVE_INFINITY;
	}
	
	/**
	 * Comprueba que una fecha tenga el formato YYYY-MM-DD y que el dia
	 * exista para el mes y el año indicados. Tambien considera los años bisiestos
	 * 
	 * @param fecha: fecha que se desea validar
	 * @return true si la fecha es valida, false en caso contrario
	 */
	
	

	private boolean fechaValida(String fecha)
	{
		try {
			String[] partes = fecha.split("-");
			
			if(partes.length != 3) {
				return false;
			}
			
			if(partes[0].length() != 4 || partes[1].length() != 2 || partes[2].length() != 2) {
				return false;
				
			}
			
			int año = Integer.parseInt(partes[0]);
			int mes = Integer.parseInt(partes[1]);
			int dia = Integer.parseInt(partes[2]);
			
			
			if(año < 1 || mes < 1 || mes > 12 || dia < 1) {
				return false;
			}
			
			int diasMes = 31;
			
			if(mes == 4 || mes == 6 || mes == 9 || mes == 11) {
				diasMes = 30;
			}
			
			if(mes == 2) {
				diasMes = 28;
				if(año % 400 == 0 || (año % 4 == 0 && año % 100 != 0)) {
					diasMes = 29;
				}
				
			}
			
			return dia <= diasMes;
			
	
		} catch(Exception e) {
			return false;
		}
	}

	/*
	 * Muestra el menu principal y ejecuta la opcion seleccionada hasta que el usuario elija salir
	 * 
	 */
	
	public void iniciar()
	{
		int opcion;
		
		do {
			System.out.println(".--------------------------------------------.");
			System.out.println("|                                            |");
			System.out.println("|             LA FONDA DEL PAPU              |");
			System.out.println("|                                            |");
			System.out.println("|          !Pase, coma y disfrute!           |");
			System.out.println("|                                            |");
			System.out.println("|                                            |");
			System.out.println("'--------------------------------------------'");
			System.out.println("");
			System.out.println("Registros cargados: " + cantidadConsumos);
			System.out.println("Participantes registrados: "+ cantidadParticipantes);
			System.out.println("");
			System.out.println("1. Ver registros");
			System.out.println("2. Actualizar registros");
			System.out.println("3. Calcular metricas");
			System.out.println("4. Salir");
			System.out.print("Selecciones una opción: ");
			
			opcion = LeerEntero(1,4);
			
			switch(opcion) {
			case 1:
				menuVerRegistros();
				break;
			case 2:
				menuActualizarRegistro();
				break;
			case 3:
				menuMetricas();
				break;
			case 4:
				System.out.println("Adiós...");
				break;
			}
			
			
		} while(opcion!=4);
		
		
	}

	//Muestra las opciones para calcular cantidades y metricas de consumo.
	private void menuMetricas()
	{
		int opcion;
		
		do {
			
			System.out.println("");
			System.out.println("| ===[ CALCULAR METRICAS ] ===|");
			System.out.println("");
			System.out.println("1. Cantidad de choripanes en total");
			System.out.println("2. Cantidad de terremotos en total");
			System.out.println("3. Choripanes por usuario");
			System.out.println("4. Terremotos por usuario");
			System.out.println("5. KCal consimidas por usuario");
			System.out.println("6. Dias ebrios por usuario");
			System.out.println("7. Volver al menu principal");
			System.out.println();
			
			System.out.print("Seleccione una opción: ");
			opcion = LeerEntero(1,7);
			
			switch(opcion) {
			case 1:
				totalChoripanes();
				break;
			case 2:
				totalTerremotos();
				break;
			case 3:
				choripanesPorUsuario();
				break;
				
			case 4:
				terremotosPorUsuario();
				break;
			case 5:
				kcalPorUsuario();
				break;
			case 6:
				diasEbriosPorUsuario();
				break;
				
			case 7:
				break;
			}
			
		}while(opcion != 7);
		
	}


	
	private void totalChoripanes()
	{
		// TODO Auto-generated method stub
		
	}


	private void totalTerremotos()
	{
		// TODO Auto-generated method stub
		
	}


	private void choripanesPorUsuario()
	{
		// TODO Auto-generated method stub
		
	}


	private void terremotosPorUsuario()
	{
		// TODO Auto-generated method stub
		
	}


	private void kcalPorUsuario()
	{
		// TODO Auto-generated method stub
		
	}


	private void diasEbriosPorUsuario()
	{
		// TODO Auto-generated method stub
		
	}
	
	


	//Muestra el submenu para agregar, editar o eliminar registros.
	private void menuActualizarRegistro()
	{
		int opcion;
		
		do {
			System.out.println("");
			System.out.println("| ===[ ACTUALIZAR REGISTROS ] ===|");
			System.out.println("");
			System.out.println("1. Agregar");
			System.out.println("2. Editar");
			System.out.println("3. Eliminar");
			System.out.println("4. Volver al menu principal");
			System.out.println();
			
			System.out.print("Seleccione una opción: ");
			opcion = LeerEntero(1,4);
			
			switch(opcion) {
			case 1:
				agregarRegistro();
				break;
			case 2:
				editarRegistro();
				break;
			case 3:
				eliminarRegistro();
				break;
				
			case 4:
				break;
			}
			
		}while(opcion != 4);
		
	}
	

	private void agregarRegistro()
	{
		// TODO Auto-generated method stub
		
	}


	private void editarRegistro()
	{
		// TODO Auto-generated method stub
		
	}


	private void eliminarRegistro()
	{
		// TODO Auto-generated method stub
		
	}
	
	
	


	//muestra el submenu para consultar los registros de choripanes o terremotos
	private void menuVerRegistros()
	{
		int opcion;
		
		do {
			System.out.println("");
			System.out.println("| ===[ VER REGISTROS ] ===|");
			System.out.println("");
			System.out.println("1. Choripanes");
			System.out.println("2. Terremotos");
			System.out.println("3. Volver al menu principal");
			System.out.println();
			
			System.out.print("Seleccione una opción: ");
			opcion = LeerEntero(1,3);
			
			switch(opcion) {
			case 1:
				mostrarRegistros("Choripan");
				break;
			case 2:
				mostrarRegistros("Terremoto");
				break;
			case 3:
				break;
			}
			
			
		}while(opcion != 3);
		
	}
	

	private void mostrarRegistros(String string)
	{
		// TODO Auto-generated method stub
		
	}
	
	
	


	//Solicita un numero entero hasta que el usuario ingrese uno dentro del rango indicado.
	private int LeerEntero(int minimo, int maximo)
	{
		int numero = 0;
		boolean valido = false;
		
		while(!valido) {
			try {
				String texto = lector.nextLine();
				
				if(texto.equals("")) {
					System.out.println("No ingreso ninguna opcion.");
					System.out.print("Intente nuevamente: ");
				}
				else {
					numero = Integer.parseInt(texto);
					
					if(numero < minimo || numero > maximo) {
						System.out.println("Opcion fuera de rango.");
						System.out.print("Intente nuevamente: ");
					} else {
						valido = true;
					}
				}
				
			}catch(Exception e) {
				System.out.println("Debe ingresar un numero.");
				System.out.print("Intente nuevamente: ");
			}
		}
		
		return numero;
	}
	

}
