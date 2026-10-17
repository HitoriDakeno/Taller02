//David Villalobos - 21.646.173-8 - ICCI




package taller02;

import java.util.Scanner;

/**
 * 
 * Clase principal que contiene el metodo main e inicializa la ejecucion
 * del programa de la fonda
 */

public class Main
{
	//lector global estandar para la entrada por consola
	public static Scanner lector = new Scanner(System.in);
	
	
	/**
	 * Crea el sistema, carga los archivos e inicia el menu principal.
	 * Al finalizar, cierra el Scanner utilizado para leer desde el teclado.
	 * 
	 */

	public static void main(String[] args)
	{
		SistemaFonda sistem = new SistemaFonda(lector);
		
		sistem.cargarArchivos();
		sistem.iniciar();
		
		lector.close();
		

	}

}
