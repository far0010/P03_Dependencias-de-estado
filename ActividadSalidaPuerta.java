package p03.c03;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActividadSalidaPuerta implements Runnable{
	/**
	 * Permite la salida del parque
	 *
	 * @author Fernando Arroyo Redondo
	 * @author Francisco J Arroyo Redondo
	 * @version 1.0
	 * Práctica 3
	 */
	private static final int NUMSALIDAS=20;
	private String idPuerta;
	private Parque parque;
	
	public ActividadSalidaPuerta(String idPuerta, Parque parque) {
		this.idPuerta=idPuerta;
		this.parque=parque;
	}
	public void run() {
		for(int i=0;i<NUMSALIDAS;i++) {
			try {
				int numero = (int)(Math.random()*1000+500);
				parque.salirDelParque(idPuerta);
				TimeUnit.MILLISECONDS.sleep(numero);
			}catch (InterruptedException e) {
				Logger.getGlobal().log(Level.INFO, "Salida interrumpida en puerta: " + idPuerta);
			}
		}
	}
	
}
