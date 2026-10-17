//David Villalobos - 21.646.173-8 - ICCI
package taller02;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
			
			opcion = leerEntero(1,4);
			
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
			opcion = leerEntero(1,7);
			
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


	//Cuenta y muestra la cantidad total de registros de choripanes
	private void totalChoripanes()
	{
		int total = 0;
		for(int i=0;i<cantidadConsumos;i++) {
			if(consumos[i].getTipo().equalsIgnoreCase("Choripan")) {
				total++;
			}
		}
		System.out.println("Total de choripanes comidos: "+total);
		
	}

	//Cuenta los terremotos registrados y suma la cantidad total de litros consumidos
	private void totalTerremotos()
	{
		int total = 0;
		double litrosTotales = 0;
		
		for(int i=0;i<cantidadConsumos;i++) {
			if(consumos[i].getTipo().equalsIgnoreCase("Terremoto")) {
				total++;
				litrosTotales += consumos[i].getLitros();
			}
		}
		
		System.out.println("total de terremotos tomados: "+total+"("+litrosTotales+" litros)");
		
		
	}

	//cuenta y muestra cuantos choripanes tiene registrado cada participante.
	private void choripanesPorUsuario()
	{
		System.out.println("| ===[ CHORIPANES POR USUARIO ] ===|");
		
		for(int p=0;p<cantidadParticipantes;p++) {
			int total = 0;
			for(int i=0;i<cantidadConsumos;i++) {
				if(consumos[i].getTipo().equalsIgnoreCase("Choripan") && consumos[i].getConsumidor().equalsIgnoreCase(participantes[p])) {
					total++;
				}
			}
			
			System.out.println(participantes[p] + ":" + total);
		}
		
	}

	//cuenta los terremotos y suma sus litros para cada participante.
	private void terremotosPorUsuario()
	{
		System.out.println("| ===[ TERREMOTO POR USUARIO ] ===|");
		
		for(int p=0;p<cantidadParticipantes;p++) {
			int total = 0;
			double litros = 0;
			for(int i=0;i<cantidadConsumos;i++) {
				if(consumos[i].getTipo().equalsIgnoreCase("Terremoto") && consumos[i].getConsumidor().equalsIgnoreCase(participantes[p])) {
					total++;
					litros += consumos[i].getLitros();
				}
			}
			
			System.out.println("- "+participantes[p] + " "+total+" terremotos"+" "+litros+" L");
		}
		
	}

	//calcula y muestra las kilocalorias de choripanes, terremotos y el total de cada participante
	//ademas del total general.
	private void kcalPorUsuario()
	{
		double totalFonda = 0;
		
		System.out.println("| ===[ KCAL POR USUARIO ] ===|");
		System.out.printf("%-12s %12s %12s %12s%n", "Usuario", "Choripan", "Terremoto", "Total");
		
		for(int p = 0;p<cantidadParticipantes;p++) {
			double kcalChoripan = 0;
			double kcalTerremoto = 0;
			
			for(int i=0;i<cantidadConsumos;i++) {
				if(consumos[i].getConsumidor().equalsIgnoreCase(participantes[p])) {
					if(consumos[i].getTipo().equalsIgnoreCase("Choripan")) {
						kcalChoripan += consumos[i].calcularKCal();
						
					}
					else {
						kcalTerremoto += consumos[i].calcularKCal();
					}
				}
			}
			double total = kcalChoripan + kcalTerremoto;
			totalFonda += total;
			
			System.out.printf("%-12s %12.2f %12.2f %12.2f%n", participantes[p], kcalChoripan, kcalTerremoto,total);
			
			
		}
		System.out.println("Consumo total de la fonda: "+totalFonda);
		
		
	}
	
	
	

	/*
	 * cuenta para cada participante las fechas en que acumulo dos litros o mas de terremoto,
	 * sumando los registros de una misma fecha.
	 */
	private void diasEbriosPorUsuario()
	{
		System.out.println("| ===[ DIAS EBRIOS POR USUARIO ] ===|");
		System.out.println("(Se cuentan 2 litros o mas en una misma fecha)");
		
		for(int p=0;p<cantidadParticipantes;p++) {
			int diasEbrios = 0;
			for(int i=0;i<cantidadConsumos;i++) {
				if(!consumos[i].getTipo().equalsIgnoreCase("Terremoto") || !consumos[i].getConsumidor().equalsIgnoreCase(participantes[p])) {
					continue;
				}
				
				String fecha = consumos[i].getFecha();
				boolean fechaRevisada = false;
				
				
				for(int j=0;j<i;j++) {
					if(consumos[j].getTipo().equalsIgnoreCase("Terremoto") && consumos[j].getConsumidor().equalsIgnoreCase(participantes[p]) 
							&& consumos[j].getFecha().equals(fecha)) {
						fechaRevisada = true;
					}
					
				}
				
				if(!fechaRevisada) {
					double litrosDia = 0;
					
					for(int j=0;j<cantidadConsumos;j++) {
						if(consumos[j].getTipo().equalsIgnoreCase("Terremoto") && consumos[j].getConsumidor().equalsIgnoreCase(participantes[p]) 
								&& consumos[j].getFecha().equals(fecha)) {
							litrosDia += consumos[j].getLitros();
							
							
						}
					}
					
					if(litrosDia >= 2) {
						diasEbrios++;
						System.out.println(participantes[p] + ": "+fecha+ " ("+litrosDia+" L)");
					}
				}
			}
			
			System.out.println("Dias ebrio de "+participantes[p] + ":" + diasEbrios);
		}
		
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
			opcion = leerEntero(1,4);
			
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
	
	//Solicita los datos de un nuevo consumo, lo agrega al arreglo y guarda los cambios en el archivo
	private void agregarRegistro()
	{
		if(cantidadConsumos >= 200) {
			System.out.println("Se alcanzo la capacidad maxima de 200 registros.");
			return;
		}
		
		if(cantidadParticipantes == 0) {
			System.out.println("No hay participantes disponibles.");
			return;
		}
		
		System.out.println();
		System.out.println("1. Choripan");
		System.out.println("2. Terremoto");
		System.out.println("3. Cancelar");
		System.out.print("Seleccione el tipo: ");
		int tipo = leerEntero(1,3);
		
		if(tipo == 3) {
			return;
		}
		
		String consumidor = elegirParticipante();
		
		if(consumidor.equals("")) {
			return;
		}
		
		Consumo nuevo;
		
		if(tipo == 1) {
			double largo = leerPositivo("Largo del choripan (cm): ");
			double ancho = leerPositivo("Ancho del choripan (cm): ");
			String fecha = leerFecha();
			
			nuevo = new Consumo("Choripan",consumidor,largo,ancho,0,fecha);
			
		}
		else {
			double litros = leerPositivo("Litros del terremoto: ");
			String fecha = leerFecha();
			
			nuevo = new Consumo("Terremoto", consumidor,0,0,litros,fecha);
			
		}
		
		consumos[cantidadConsumos] = nuevo;
		cantidadConsumos++;
		
		if(guardarConsumos()) {
			System.out.println("Registro agregado y guardado correctamente");
			System.out.println("Kcal el registro: "+nuevo.calcularKCal());
			
		}

		
		
		
	}

	//muestra los participantes disponibles y devuelve el nobre del que el usuario seleccione.
	private String elegirParticipante()
	{
		if(cantidadParticipantes == 0) {
			System.out.println("No hay participantes cargados.");
			return "";
		}
		
		System.out.println("Participantes disponibles: ");
		
		for(int i=0; i<cantidadParticipantes; i++) {
			System.out.println("("+(i+1) + ") " + participantes[i]);
		}
		
		System.out.print("Seleccione el consumidor: ");
		int opcion = leerEntero(1,cantidadParticipantes);
		return participantes[opcion-1];
	
	}

	//solicita un numero mayor que cero y repite la lectura mientras el dato ingresado no sea valido.
	private double leerPositivo(String mensaje)
	{
		double numero = 0;
		boolean valido = false;
		
		while(!valido) {
			System.out.println("mensaje");
			
			try {
				numero = Double.parseDouble(lector.nextLine());
				if(numeroPositivo(numero)) {
					valido = true;
				} else {
					System.out.println("Debe ingresar un numero mayor a cero.");
				}
				
			}catch(Exception e) {
				System.out.println("Debe ingresar un numero valido");
			}
			
			
		}
		return numero;
	}

	//solicita una fecha hasta que el usuario ingrese una fecha valida em formato YYYY-MM-DD
	private String leerFecha()
	{
		String fecha = "";
		boolean valido = false;
		
		while(!valido) {
			System.out.print("Fecha (YYYY-MM-DD): ");
			fecha = lector.nextLine();
			
			if(fechaValida(fecha)) {
				valido = true;
			} else {
				System.out.println("Fecha invalida");
			}
		}
		return fecha;
	}
	
	
	/*
	 * permite seleccionar un registro y modificar sus datos
	 * los campos vacios conservan sus valores actuales.
	 * 
	 */
	private void editarRegistro()
	{
		System.out.println("1. Choripan");
		System.out.println("2. Terremoto");
		System.out.println("3. Cancelar");
		System.out.print("Tipo de registro a editar: ");
		int tipo = leerEntero(1,3);
		
		if(tipo == 3) {
			return;
		}
		
		String nombreTipo = "Choripan";
		if(tipo == 2) {
			nombreTipo = "Terremoto";
		}
		
		int cant = mostrarRegistros(nombreTipo);
		
		if(cant == 0) {
			System.out.println("No hay registros de ese tipo.");
			return;
		}
		
		System.out.println("Numero del registro a editar (0 para cancelar): ");
		int numero = leerEntero(0,cant);
		
		if(numero == 0) {
			return;
		}
		
		int pos = buscarPosTipo(nombreTipo,numero);
		
		if(pos == -1) {
			System.out.println("No existe ese registro");
			return;
		}
		
		Consumo consumo = consumos[pos];
		
		System.out.println("Deje el campo vacio para conservar el valor actual");
		System.out.print("Consumidor {"+consumo.getConsumidor()+"}: ");
		String nuevoConsumidor = lector.nextLine();
		
		if(!nuevoConsumidor.equals("")) {
			if(buscarParticipante(nuevoConsumidor) != -1) {
				consumo.setConsumidor(nuevoConsumidor);
			}
			else {
				System.out.println("Participante inexistente. Se conserva el anterior");
			}
		}
		
		if(nombreTipo.equals("Choripan")) {
			System.out.print("Largo [" +consumo.getLargo()+"]: ");
			String textoLargo = lector.nextLine();
			
			if(!textoLargo.equals("")) {
				try {
					double largo = Double.parseDouble(textoLargo);
					
					if(numeroPositivo(largo)) {
						consumo.setLargo(largo);
					}
					else {
						System.out.println("Largo invalido. Se conserva el anterior");
					}
					
					
				}catch(Exception e) {
					System.out.println("Numero invalido. Se conserva el anterior.");
				}
			}
			
			System.out.print("Ancho [" +consumo.getAncho() + "]: ");
			String textoAncho = lector.nextLine();
			
			if(!textoAncho.equals("")) {
				
				try {
					double ancho = Double.parseDouble(textoAncho);
					
					if(numeroPositivo(ancho)) {
						consumo.setAncho(ancho);
					}
					else {
						System.out.println("Ancho invalido. Se conserva el anterior");
					}
					
					
				}catch(Exception e) {
					System.out.println("Numero invalido. Se conserva el anterior.");
				}
				
			}
			
		}
		else {
			System.out.print("Litros ["+ consumo.getLitros() + "]: ");
			String textoLitros = lector.nextLine();
			
			if(!textoLitros.equals("")) {
				try {
					double litros = Double.parseDouble(textoLitros);
					if(numeroPositivo(litros)) {
						consumo.setLitros(litros);
					} else {
						System.out.println("Litros invalidos. Se conservan los anteriores");
					}
					
				} catch(Exception e) {
					System.out.println("Numero invalido. Se conserva los anteriores");
				}
			}
		}
		
		System.out.print("Fecha ["+consumo.getFecha()+"]: ");
		String nuevaFecha = lector.nextLine();
		
		if(!nuevaFecha.equals("")) {
			if(fechaValida(nuevaFecha)) {
				consumo.setFecha(nuevaFecha);
				
			}
			else {
				System.out.println("Fecha invalida. Se conserva la anterior");
			}
		}
		
		if(guardarConsumos()) {
			System.out.println("Registro actualizado y guardado correctamente.");
		}
	}


	
	
	//Permite seleccionar un registro, confirmar su eliminacion y guardar los cambios en el archivo
	private void eliminarRegistro()
	{
		System.out.println("1. Choripan");
		System.out.println("2. Terremoto");
		System.out.println("3. Cancelar");
		System.out.print("Tipo de registro a eliminar: ");
		
		int tipo = leerEntero(1,3);
		
		
		if(tipo == 3) {
			return;
		}
		
		String nombreTipo = "Choripan";
		
		if(tipo == 2) {
			nombreTipo = "Terremoto";
			
		}
		
		int cant = mostrarRegistros(nombreTipo);
		
		if(cant == 0) {
			System.out.println("No hay registros para eliminar.");
			return;
		}
		
		System.out.print("Numero del registro a eliminar (0 para cancelar): ");
		int numero = leerEntero(0,cant);
		
		if(numero == 0) {
			return;
		}
		
		int pos = buscarPosTipo(nombreTipo, numero);
		
		if(pos == -1) {
			System.out.println("No existe ese registro");
			return;
		}
		
		Consumo consumo = consumos[pos];
		
		System.out.println("Registro: "+consumo.getTipo() + " | " + consumo.getConsumidor() + " | " + consumo.getFecha());
		System.out.print("Confirma la eliminacion (s/n): ");
		String res = lector.nextLine();
		
		if(res.equalsIgnoreCase("s")) {
			for(int i= pos;i<cantidadConsumos-1;i++) {
				consumos[i] = consumos[i+1];
			}
			
			cantidadConsumos--;
			
			if(guardarConsumos()) {
				System.out.println("Registro eliminado y cambios guardados.");
			}
		} else {
			System.out.println("Eliminación cancelada");
		}
	}
	
	
	
	
	//busca la posicion del registro numero indicado dentro de los consumos de un tipo determinado
	private int buscarPosTipo(String tipo, int numero)
	{
		int cont = 0;
		
		for(int i=0;i<cantidadConsumos;i++) {
			if(consumos[i].getTipo().equalsIgnoreCase(tipo)) {
				cont++;
				if(cont == numero) {
					return i;
				}
				
			}
		}
		
		return -1;
	}
	
	

	// guarda todos los consumos actuales en el archivo y devuelve true si la escritura se completa correctamente
	private boolean guardarConsumos()
	{
		try {
			BufferedWriter escritor = new BufferedWriter(new FileWriter(archConsumos));
			for(int i=0;i<cantidadConsumos;i++) {
				escritor.write(consumos[i].aFormatoArchivo());
				escritor.newLine();
			}
			
			escritor.close();
			return true;
			
			
		}catch(IOException e) {
			System.out.println("Error al guardar los consumos: "+ e.toString());
			return false;
		}
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
			opcion = leerEntero(1,3);
			
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
	
	//muestra los registros del tipo indicado y devuelve la cantidad de registros mostrados
	private int mostrarRegistros(String tipo)
	{
		int cont = 0;
		double litrosTotales = 0;
		
		System.out.println("");
		
		if(tipo.equalsIgnoreCase("Choripan")) {
			System.out.println("                                        CHORIPANES");
			System.out.println();
			
			System.out.printf("%-4s %-12s %8s %8s  %-12s %8s%n", "#", "Consumidor", "Largo", "Ancho", "Fecha", "KCal");
			
		} else {
			System.out.println("                                        TERREMOTOS");
			System.out.printf("%-4s %-12s %8s %-12s %8s%n", "#", "Consumidor", "Litros", "Fecha", "KCal");
		}
		
		for(int i=0; i<cantidadConsumos;i++) {
			Consumo consumo = consumos[i];
			if(consumo.getTipo().equalsIgnoreCase(tipo)) {
				cont++;
				
				if(tipo.equalsIgnoreCase("Choripan")) {
					System.out.printf("%-4d %-12s %7.2f cm %7.2f cm  %-12s %8.2f%n",cont, 
							consumo.getConsumidor(), consumo.getLargo(), consumo.getAncho(), 
							consumo.getFecha(), consumo.calcularKCal());
				} else {
					System.out.printf("%-4d %-12s %7.2f L  %-12s %8.2f%n", cont,consumo.getConsumidor(),
							consumo.getLitros(), consumo.getFecha(), consumo.calcularKCal());
					litrosTotales += consumo.getLitros();
				}
			}
		}
		
		System.out.println("-------------------------------------------------------------");
		if(tipo.equalsIgnoreCase("Choripan")) {
			System.out.println("Total => "+cont + " choripanes registrados");
			
		} else {
			System.out.println("Total => "+cont+" terremotos registrados ("+litrosTotales+" L)");
			
		}
		
		System.out.println("-------------------------------------------------------------");
		return cont;
	}
	
	
	


	//Solicita un numero entero hasta que el usuario ingrese uno dentro del rango indicado.
	private int leerEntero(int minimo, int maximo)
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
