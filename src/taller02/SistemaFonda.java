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
	 * inicia el menu iteractivo principal del programa.
	 * 
	 */
	
	public void iniciar()
	{
		//se implementa la logica proximamente
		
	}
	

}
