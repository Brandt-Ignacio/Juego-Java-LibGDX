package utilidades;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Utiles {
		// TAMAÑOS
		public static int WIDTH = 1280;
		public static int HEIGHT = 720;

		public static boolean empieza = false;
		public static boolean terminar = false;
		public static boolean patear = false;
		public static boolean confirma = false;


		public static String modo;
		public static int id = -1;
		public static SpriteBatch batch = new SpriteBatch();
		public static Random r = new Random();
		public static Scanner s = new Scanner(System.in);


		
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
