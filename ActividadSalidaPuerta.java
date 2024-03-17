package p03.c03;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Permite la salida del parque
 *
 * @author Fernando Arroyo Redondo
 * @author Francisco J Arroyo Redondo
 * @version 1.0
 * Práctica 3
 */
public class ActividadSalidaPuerta implements Runnable{
	
	/*
	 * Número máximo de salidas en cad puerta.
	 */
	private static final int NUMSALIDAS=20;

	/*
	 * Identificación de la puerta utilizada.
	 */
	private String idPuerta;

	/*
	 * Parque que se usa.
	 */
	private Iparque parque;
	
	public ActividadSalidaPuerta(String idPuerta, Iparque parque) {
		this.idPuerta=idPuerta;
		this.parque=parque;
	}

	/**
	 * Método run que lanza la actividad.
	 */
	public void run() {
		for(int i=0;i<NUMSALIDAS;i++) {
			try {
				int numero = (int)(Math.random()*1000+100);
				parque.salirDelParque(idPuerta);
				TimeUnit.MILLISECONDS.sleep(numero);
			}catch (InterruptedException e) {
				Logger.getGlobal().log(Level.INFO, "Salida interrumpida en puerta: " + idPuerta);
			}
		}
	}
	
}
