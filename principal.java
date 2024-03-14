package p03.c03;

/**
 * clase principal main()
 *
 * @author Fernando Arroyo Redondo
 * @author Francisco J Arroyo Redondo
 * @version 1.0
 * Práctica 3
 */
public class principal {
	/**
	 * Modulo principal lanza el programa.
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final int PUERTAS=5;
		
		Iparque parque = new Parque();
		char letra_puerta = 'A'; //primera puerta
		
		for (int i = 0; i < PUERTAS; i++) { // numero de puertas definido por parámetro
			
				String puerta = "" + ((char) (letra_puerta++));
				// hilos de entrada
				Thread entrada = new Thread(new ActividadEntradaPuerta(puerta, parque));
				
				// hilos de salida
				Thread salida = new Thread(new ActividadSalidaPuerta(puerta, parque));
				entrada.start();
				salida.start();
			
			

		}
	}
}