package p03.c03;

import java.util.Enumeration;
import java.util.Hashtable; 
	/**
	 * Simulador de entradas/salidas del parque
	 *
	 * @author Fernando Arroyo Redondo
	 * @author Francisco J Arroyo Redondo
	 * @version 1.0
	 * Práctica 3
	 */
public class Parque implements Iparque{
	
	private final int MAX_AFORO=50; // aforo del parque
	private int contadorPersonasTotales; // cuenta las personas que hay actualmente
	private Hashtable<String, Integer> contadorPersonasPuerta;
	private Long tinicial;
	private Double tmedio;
	private Long tactual;
	
	/**
	 * Constructor
	 */
	public Parque() {
		contadorPersonasTotales=0;
		contadorPersonasPuerta= new Hashtable<String,Integer>();
		tinicial=System.currentTimeMillis();
		tmedio=(double) 0;	
	}
	
	/**
	 * Método sincronizado de entar al parque. Aumenta una persona por puerta
	 * @param puerta de acceso a parque.
	 */
	public synchronized void entrarAlParque(String puerta) {
		Integer contPu=0;
		//se comprueba el aforo y si está lleno espero salidas.
		while (contadorPersonasTotales >= MAX_AFORO) {
			System.out.println("Esperando salidas");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
				
		
		if(contadorPersonasPuerta.get(puerta)==null) {
			contadorPersonasPuerta.put(puerta, 0); // inicializamos puerta
		}
		contPu=contadorPersonasPuerta.get(puerta); // si hay puerta tomo valor
		if(contPu<20){
			contadorPersonasTotales++; // aumentamos aforo
			contPu++;
			contadorPersonasPuerta.put(puerta, contPu); //actualizo valor en la puerta
			tactual=System.currentTimeMillis();
			tmedio=(tmedio+(tactual-tinicial))/2.0;
			imprimirInfoEntradaPuerta(puerta);
		}
		
		// invariantes
		
		assert contPu>=20:"No pueden entrar más personas por esa puerta "+puerta;
		assert contadorPersonasTotales>=MAX_AFORO :"Aforo completo !!";
		
		notify();  // han entrado pueden salir
	}
	
	public synchronized void salirDelParque(String puerta) {
		Integer contPu=0;
		
		while (contadorPersonasTotales <= 0) {
			System.out.println("Esperando entradas");
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}	
		//
		if(contadorPersonasPuerta.get(puerta)==null) {
			contadorPersonasPuerta.put(puerta, 0); // inicializamos puerta
		}
		contPu=contadorPersonasPuerta.get(puerta);
		//comprobamos si hay personas en la puerta para salir.
		if (contPu>0){
			contadorPersonasTotales--; // disminuir aforo
			contPu--; //  disminuir personas por puerta
			contadorPersonasPuerta.put(puerta, contPu);
			tactual=System.currentTimeMillis();
			tmedio=(tmedio+(tactual-tinicial))/2.0;
			imprimirInfoSalidaPuerta(puerta);
		}
		
		// invariantes
		assert contPu<=0:"No hay personas a salir por la puerta "+puerta;
		assert contadorPersonasTotales<=0 :"No hay nadie en el parque !!";
		
		notify(); // ya han salido del parque pueden entrar
	}
	
	
	private void imprimirInfoEntradaPuerta(String puerta) {
		System.out.println();
		System.out.println("Entrada al parque por Puerta "+ puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales + " tiempo medio de estancia: "+ tmedio);
		Enumeration<String> e=contadorPersonasPuerta.keys();
		Object clave;
		int valor;
		while(e.hasMoreElements()) {
			clave=e.nextElement();
			valor=contadorPersonasPuerta.get(clave);
			System.out.println("---> Por puerta "+ clave+ " " + valor);
			if(valor==20) {
				System.out.println("Finalizada entrada por puerta "+clave);
			}
		}
	}
	
	private void imprimirInfoSalidaPuerta(String puerta) {
		System.out.println();
		System.out.println("Salida al parque por Puerta "+ puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales + " tiempo medio de estancia: "+ tmedio);
		Enumeration<String> e=contadorPersonasPuerta.keys();
		Object clave;
		int valor;
		while(e.hasMoreElements()) {
			clave=e.nextElement();
			valor=contadorPersonasPuerta.get(clave);
			System.out.println("---> Por puerta "+ clave+ " " + valor);
			if(valor==0) {
				System.out.println("Finalizada salida por puerta "+clave);
			}
		}
	}
	

}
