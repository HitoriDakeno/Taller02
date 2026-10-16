//David Villalobos - 21.646.173-8 - ICCI

package taller02;

public class Consumo
{
	/**
	 * Representa un registro de consumo de la fonda (Choripan o terremoto).
	 * Agrupa los atributos necesarios y maneja el calculo de kCal
	 * y la conversion a texto para el archivo de datos
	 */
	private String tipo; //"choripan" o "terremoto"
	private String consumidor; //nombre del participante
	private double largo; //dimension del largo en cm del choripan
	private double ancho; //dimension del ancho en cm del choripan
	private double litros; //cant litros del terremoto
	private String fecha; //fecha en formato YYYY-MM-DD
	
	
	
	/**
	 * Constructor principal para inicializar un registro de consumo.
	 * 
	 * @param tipo          Tipo de consumo (choripan o terremoto)
	 * @param consumidor    Nombre de la persona que consumio
	 * @param largo         Largo del choripan (0 si es terremoto)
	 * @param ancho         Ancho del choripan (0 si es terremto)
	 * @param litros        Litros del terremoto (0 si es choripan)
	 * @param fecha         Fecha del consumo (YYYY-MM-DD)
	 */
	
	public Consumo(String tipo, String consumidor, double largo, double ancho, double litros, String fecha)
	{
		this.tipo = tipo;
		this.consumidor = consumidor;
		this.largo = largo;
		this.ancho = ancho;
		this.litros = litros;
		this.fecha = fecha;
	}
	
	
	//Obtiene el tipo de consumo
	public String getTipo()
	{
		return tipo;
	}
	
	//Obtiene el nombre del consumidor
	public String getConsumidor()
	{
		return consumidor;
	}
	
	//obtiene el largo en cm
	public double getLargo()
	{
		return largo;
	}
	
	//obtiene el ancho en cm
	public double getAncho()
	{
		return ancho;
	}
	
	//obtiene la cantidad de litros
	public double getLitros()
	{
		return litros;
	}
	
	//obtiene la fecha del registro
	public String getFecha()
	{
		return fecha;
	}
	
	
	//modifica el nombre del consumidor
	public void setConsumidor(String consumidor)
	{
		this.consumidor = consumidor;
	}
	
	//modifica el largo en cm
	public void setLargo(double largo)
	{
		this.largo = largo;
	}
	
	//modifica el ancho en cm
	public void setAncho(double ancho)
	{
		this.ancho = ancho;
	}
	
	//modifica la cantidad de litros
	public void setLitros(double litros)
	{
		this.litros = litros;
	}
	
	//modifica la fecha del registro
	public void setFecha(String fecha)
	{
		this.fecha = fecha;
	}
	
	
	//logica de negocio:
	
	/**
	 * Calcula las kilocalorias consumidas segun el tipo de producto.
	 * Formulas:
	 * -Choripan: (largo*ancho) / 3
	 * -Terremoto: (largo*10) / 3
	 * 
	 * @return Cantidad de KCal del registro
	 */
	
	public double calcularKCal()
	{
		if(tipo.equalsIgnoreCase("Choripan")) {
			return (largo*ancho) / 3;
		}
		return (litros*10) / 3;
	}
	
	
	
	/**
	 * Genera la linea formateada separada por punto y coma (;)
	 * para escribir y actualizar el archivo Consumido.txt con persistencia
	 * 
	 * @return Cadena con el formato exacto requerido por el archivo de texto
	 * 
	 */
	
	public String aFormatoArchivo()
	{
		if(tipo.equalsIgnoreCase("Choripan")) {
			return "Choripan;" + consumidor + ";" + largo + ";" + ancho + ";" + fecha;
		}
		return "Terremoto;" + consumidor + ";" + litros + ";" + fecha;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
