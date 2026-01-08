package red;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Iterator;

import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.xpath.internal.operations.Bool;
import elementos.Personaje;
import juegos.Futbol;
import red.Conexion;
import utilidades.Utiles;

import static juegos.Futbol.*;
import static utilidades.Utiles.modoActual;

public class HiloServidor extends Thread{
	private DatagramSocket socket;
	private int puerto = 56293;
	private boolean fin = false;
	private int cantClientes = 0;
	private Conexion conexiones[] = new Conexion[2];



	public HiloServidor() {
		try {
			this.socket = new DatagramSocket(puerto);
		} catch (SocketException e) {
			e.printStackTrace();
		  System.err.println("Error al crear el socket del servidor UDP");
		}
	}
	
	@Override
	public void run() {
		while(!this.fin) {
			byte[] data = new byte[1024];
			DatagramPacket dp = new DatagramPacket(data,data.length);
			try {
				this.socket.receive(dp);			
				procesarMensaje(dp);
			} catch (IOException e) {
				e.printStackTrace();
				
			} 
		}
	}

	private void procesarMensaje(DatagramPacket dp) {
		String msg = (new String(dp.getData())).trim();
		String[] msgCompuesto = msg.split("#");
		System.out.println(msg);
			if(msgCompuesto[0].equals("Conexion")) {
				if(cantClientes >= 2) {
					return;
				}
				if(cantClientes < 1 && msgCompuesto.length > 1) {
					if(msgCompuesto[1] != null) modoActual = msgCompuesto[1].equals("futbol") ? Utiles.MODO.FUTBOL : msgCompuesto[1].equals("basket") ? Utiles.MODO.BASKET : Utiles.MODO.BOWLING;
				}
					if (cantClientes < 2) {
		                conexiones[cantClientes] = new Conexion(dp.getAddress(), dp.getPort());		                
		                cantClientes++;		                
		            }				 
					if(cantClientes == 2) {
						enviarMensaje("0", conexiones[0].getIp(), conexiones[0].getPuerto());
						enviarMensaje("1", conexiones[1].getIp(), conexiones[1].getPuerto());
						broadcastCasero("Listo#"+ modoActual);
						Utiles.empieza = true;
						System.out.println("executed");
						broadcastCasero("mover#"+0+"#"+(personajeRed.getBody().getPosition().x-115) +"#"+(personajeRed.getBody().getPosition().y-115));
						broadcastCasero("mover#"+1+"#"+(personajeBlue.getBody().getPosition().x-115) +"#"+(personajeBlue.getBody().getPosition().y-115));
						broadcastCasero("pelota#"+(pelota.getBody().getPosition().x-115) +"#"+(pelota.getBody().getPosition().y-115));
					}
			} else {

				int jugadorId = verificarExisteCliente(dp);
				if(jugadorId < 0) return;
				System.out.println(jugadorId);
				// Eventos de jugador
				if(msgCompuesto[0].equals("Desconectar")) {
					enviarMensaje("Desconectado", dp.getAddress(),dp.getPort());
					conexiones[jugadorId] = null;
					cantClientes--;
				} else if(msgCompuesto[0].equals("mover")) {
					// mover # arriba # abajo # izq # derecha
					Vector2 newPosition;
					if(jugadorId == 0){
						personajeRed.moverJugador(
								Boolean.parseBoolean(msgCompuesto[1]),
								Boolean.parseBoolean(msgCompuesto[2]),
								Boolean.parseBoolean(msgCompuesto[3]),
								Boolean.parseBoolean(msgCompuesto[4])
						);
						newPosition = personajeRed.getBody().getPosition();
					}else {
								Futbol.personajeBlue.moverJugador(Boolean.parseBoolean(msgCompuesto[1]),
								Boolean.parseBoolean(msgCompuesto[2]),
								Boolean.parseBoolean(msgCompuesto[3]),
								Boolean.parseBoolean(msgCompuesto[4]));
						newPosition = personajeBlue.getBody().getPosition();
					}
					//					broadcastCasero("mover#"+jugadorId+"#"+(newPosition.x-115) +"#"+(newPosition.y-115));
				} else if (msgCompuesto[0].equals("patear")) {
					Personaje p;
					if(jugadorId == 0) {
						p = personajeRed;
					}else p= personajeBlue;
					System.out.println("D "+p.getBody().getPosition().dst(pelota.getBody().getPosition()));
					if(p.getBody().getPosition().dst(pelota.getBody().getPosition()) < 40){
						long fuerzaPateo = 100000;
						Vector2 direccion = pelota.getBody().getLinearVelocity();
						Vector2 fuerza = direccion.scl(fuerzaPateo * pelota.getBody().getMass());
						pelota.getBody().applyForceToCenter(fuerza, true);
					}

				}
			}
	}
	
	private int verificarExisteCliente(DatagramPacket dp) {
		int indice = -1;
		int i = 0;
		
		do {
			int puertoConex = conexiones[i].getPuerto();
			InetAddress ipConex = conexiones[i].getIp();
			if(ipConex.equals(dp.getAddress()) && puertoConex== dp.getPort()) {
				indice = i;
			}
			i++;
		}while(i<conexiones.length && indice==-1);
		return indice;
	}

	public void enviarMensaje(String mensaje, InetAddress ipDestino, int puerto) {
		byte[] data = mensaje.getBytes();
		DatagramPacket dp = new DatagramPacket(data,data.length,ipDestino, puerto);
		try {
			System.out.println(dp);
			socket.send(dp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void cerrarSocket() {
	    if (socket != null && !socket.isClosed()) {
	        socket.close();
	        this.fin = true;
	        System.out.println("Servidor cerrado");
	    }
	}

	public void broadcastCasero(String mensaje)
	{
		System.out.println("BROADCAST "+mensaje);
		for(int i = 0; i < cantClientes; i++){
			if(this.conexiones[i] != null){
				enviarMensaje(mensaje,conexiones[i].getIp(),conexiones[i].getPuerto());
			}
		}
		/*enviarMensaje(mensaje,conexiones[0].getIp(),conexiones[0].getPuerto());
		enviarMensaje(mensaje,conexiones[1].getIp(),conexiones[1].getPuerto());*/
	}

	public void terminar(){
		broadcastCasero("terminar");
		cantClientes = 0;
		conexiones = new Conexion[2];
	}
}
