package utilidades;

import red.HiloServidor;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class Utiles {
		public static boolean empieza = false;
		public static boolean terminar = false;
		public static boolean patear = false;
		public static Random r = new Random();
		public static Scanner s = new Scanner(System.in);
		public static int jugador = 0;

		public static int V_WIDTH = 1280, V_HEIGHT = 720;

		public static HiloServidor servidor;

		public static MODO modoActual;

		public enum MODO {
			BASKET,
			FUTBOL, BOWLING;
		}
		
		public static int ingresarEntero(int min, int max) {
				
				int opc=0;
				boolean error = false;
				
				do {
					error = false;
					try {
						opc = s.nextInt();
						if((opc<min)||(opc>max)) {
							System.out.println("Error. Debe ingresar un numero del " + min + " al " + max);
							error = true;
						}
						s.nextLine();
					} catch (InputMismatchException e) {
						System.out.println("Error. Tipo de dato mal ingresado");
						error = true;
						s.nextLine();
					}
				}while(error);
				
				return opc;			
		}
		
		public static void esperar(int milis) {
			try {
				Thread.sleep(milis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		 public static int obtenerNumeroEntre(int min, int max) {
		        return r.nextInt(max - min + 1) + min;
		  }
		 
		 
	}
