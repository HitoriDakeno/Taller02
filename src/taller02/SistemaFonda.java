package taller02;

import java.util.Scanner;

/**
 * 
 * Gestor principal de la fonda. Maneja los arreglos de participantes y consumos.
 * 
 */

public class SistemaFonda
{
	private Scanner lector;
	
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
	 * 
	 * Carga los archivos iniciales de participantes y consumos.
	 * 
	 */
	
	public void cargarArchivos()
	{
		cargarParticipantes();
		cargarConsumos();
		
	}
	
	
	//metodos para cargar los archivos correctamente
	
	private void cargarConsumos()
	{
		// TODO Auto-generated method stub
		
	}


	private void cargarParticipantes()
	{
		// TODO Auto-generated method stub
		
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
