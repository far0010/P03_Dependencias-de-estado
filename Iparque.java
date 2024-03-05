package p03.c03;
/**
 * Interfaz del parque. Cada puerta será un hilo de ejecución.
 *
 * @author Fernando Arroyo Redondo
 * @author Francisco J Arroyo Redondo
 * @version 1.0
 * Práctica 3
 */
public interface Iparque {
	/**
	 * Aumenta en 1 las personas en el parque
	 * @param puerta por la que acceden
	 * */
	public abstract void entrarAlParque(String puerta);
	/**
	 * Disminuye en 1 las personas del parque
	 * @param puerta por la que salen
	 * */
	public abstract void salirDelParque(String puerta);
	
}
