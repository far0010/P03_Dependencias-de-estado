package p03.c03;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * Clase Parque que implementa las entradas/salidas y la información relacionada.
 *
 * @author Fernando Arroyo Redondo
 * @author Francisco J Arroyo Redondo
 * @version 1.0
 * Práctica 3
 */
public class Parque implements Iparque {
	/*
	 * Máximo aforo del parque
	 */
	private final int MAX_AFORO=50;
	
	/**
	 * Tiempo máximo para entrar o salir del parque.
	 */
	private final long TIEMPO_MAX = 5000;
	
	/*
	 * Variableque almacena el número de personas totales en el parque.
	 */
	private int contadorPersonasTotales;
	
	/*
	 * Almacena por puerta el número de personas que han entrado por ella.
	 */
	private Hashtable<String, Integer> contadorPersonasEntradaPuerta;
	
	/*
	 * Almacena por puerta el número de personas que han entrado por ella.
	 */
	private Hashtable<String, Integer> contadorPersonasSalidaPuerta;
	
	/*
	 * Variable que almacena el tiempo inicial.
	 */
	private Long tinicial;
	
	/*
	 * Variable que almacena el tiempo medio.
	 */
	private Double tmedio;
	
	/*
	 * Variable que almacena el tiempo en el momento que se mide.
	 */
	private Long tactual;
	
	/**
	 * Constructor de la clase.
	 */
	public Parque() {
		contadorPersonasTotales=0;
		contadorPersonasEntradaPuerta = new Hashtable<String, Integer>();
		contadorPersonasSalidaPuerta = new Hashtable<String, Integer>();
		tinicial=System.currentTimeMillis();
		tmedio = (double) 0;
		
	}
	
	@Override
	/**
	 *Método sincronizado de entar al parque. Aumenta una persona por puerta
	 * @param puerta de acceso a parque. 
	 */
	public synchronized void entrarAlParque(String puerta) {
		Integer contPu = 0;
		String tipo = "entrada";
	
		while (contadorPersonasTotales == MAX_AFORO ) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		contadorPersonasTotales++; // aumentamos aforo
		if (contadorPersonasEntradaPuerta.get(puerta) == null) {
			contadorPersonasEntradaPuerta.put(puerta, 0); // inicializamos puerta
		}
		contPu = contadorPersonasEntradaPuerta.get(puerta);
		contPu++;
		contadorPersonasEntradaPuerta.put(puerta, contPu);
		tactual = System.currentTimeMillis();
		tmedio = (tmedio + (tactual - tinicial)) / 2.0;
		
		checkInvariante(puerta, contPu);
		imprimirInfo(tipo, puerta);
		notifyAll(); // han entrado pueden salir
		if(tactual - tinicial >TIEMPO_MAX) {
			System.out.println("Tiempo límite "+ TIEMPO_MAX/1000 +" segundos alcanzado. No se puede entrar o salir del parque.");
			System.exit(0);
		}
		
	}
	
	public synchronized void salirDelParque(String puerta) {
		Integer contPu = 0;
		String tipo = "salida";
		
		while (contadorPersonasTotales == 0) {
			notifyAll();
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		if (contadorPersonasSalidaPuerta.get(puerta) == null) {
			contPu = 1;
			contadorPersonasTotales--; // disminuir aforo
			contadorPersonasSalidaPuerta.put(puerta, contPu); // inicializamos puerta

		} else {
			contadorPersonasTotales--; // disminuir aforo
			contPu = contadorPersonasSalidaPuerta.get(puerta);
			contPu++;
			contadorPersonasSalidaPuerta.put(puerta, contPu);
		}

		tactual = System.currentTimeMillis();
		tmedio = (tmedio + (tactual - tinicial)) / 2.0;

		imprimirInfo(tipo, puerta);
		
		checkInvariante(puerta, contPu);

		notifyAll(); // ya han salido del parque pueden entrar
		if(tactual - tinicial >TIEMPO_MAX) {
			System.out.println("Tiempo límite "+ TIEMPO_MAX/1000 +" segundos alcanzado. No se puede entrar o salir del parque.");
			System.exit(0);
		}
		
	}
	
	private void checkInvariante(String puerta, int contador) {
		if(puerta =="entrada") {
			assert contadorPersonasTotales<=MAX_AFORO :"Aforo completo !!";
			assert contador<=20:"No pueden entrar más personas por esa puerta"+puerta;
			
		}else {
			
			assert contadorPersonasTotales>=1 :"No hay nadie en el parque !!";
			assert contador<=20:"No pueden salir más personas por esa puerta"+puerta;
			
		}
	}

	private void imprimirSalidas() {
		System.out.println();
		Enumeration<String> s = contadorPersonasSalidaPuerta.keys();
		Object claveS;
		int valorS;
		while (s.hasMoreElements()) {
			claveS = s.nextElement();
			valorS = contadorPersonasSalidaPuerta.get(claveS);
			System.out.println("---> Salieron puerta " + claveS + " " + valorS);
			if (valorS == 20) {
				System.out.println("Finalizada salida por puerta " + claveS);
			}
		}
		
	}
		
	private void imprimirEntradas() {
		Enumeration<String> e = contadorPersonasEntradaPuerta.keys();
		Object claveE;
		int valorE;
		while (e.hasMoreElements()) {
			claveE = e.nextElement();
			valorE = contadorPersonasEntradaPuerta.get(claveE);
			System.out.println("---> Entraron puerta " + claveE + " " + valorE);
			if (valorE == 20) {
				System.out.println("Finalizada entrada por puerta " + claveE);
			}
		}
	}
	
	private void imprimirInfo(String tipo, String puerta) {
		if (tipo == "entrada") {
			System.out.println();
			System.out.println("Entrada al parque por Puerta " + puerta);
			
			imprimirEntradas();
			imprimirSalidas();
			
		}else {
			System.out.println();
			System.out.println("Salida del parque por Puerta "+ puerta);
			//System.out.println("--> Personas en el parque " + contadorPersonasTotales + " tiempo medio de estancia: "+ tmedio);
			imprimirEntradas();
			imprimirSalidas();
		}
		System.out.printf("--> Personas en el parque %d tiempo medio de estancia: %10.3f ms.%n",
				contadorPersonasTotales, tmedio);
		
	}

	
}
