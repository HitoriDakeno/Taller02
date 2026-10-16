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


	
	
	
	
	
	//metodos de validaciones de datos para los archivos
	private int buscarParticipante(String consumidor)
	{
		// TODO Auto-generated method stub
		return 0;
	}


	private boolean numeroPositivo(double litros)
	{
		// TODO Auto-generated method stub
		return false;
	}


	private boolean fechaValida(String fecha)
	{
		// TODO Auto-generated method stub
		return false;
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
