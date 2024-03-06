package p03.c03;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Permite la antrada al parque
 *
 * @author Fernando Arroyo Redondo
 * @author Francisco J Arroyo Redondo
 * @version 1.0
 * Práctica 3
 */
public class ActividadEntradaPuerta implements Runnable{
	
	
	private static final int NUMENTRADAS=20;
	private String idPuerta;
	private Parque parque;
	
	/**
	 * Constructor de la clase ActividadEntradaPuertas.
	 * @param idPuerta puerta por la que se realiza la actividad de entrada.
	 * @param parque donde se realiza la actividad.
	 */
	public ActividadEntradaPuerta(String idPuerta, Parque parque) {
		this.idPuerta=idPuerta;
		this.parque=parque;
	}
	
	public void run() {
		for(int i=0;i<NUMENTRADAS;i++) {
			try {
				int numero = (int)(Math.random()*1000+100);
				parque.entrarAlParque(idPuerta);
				TimeUnit.MILLISECONDS.sleep(numero);
			}catch (InterruptedException e) {
				Logger.getGlobal().log(Level.INFO, "Entrada interrumpida en puerta: " + idPuerta);
			}
		}
	}
}
