package red;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.badlogic.gdx.graphics.Texture;
import juegos.Futbol;
import sun.nio.ch.Util;
import utilidades.Utiles;

public class HiloCliente extends Thread {
	
	public DatagramSocket socket;
	private InetAddress ipServidor;
	private int puertoServidor = 56293;
	private boolean fin = false;
	
	public HiloCliente() {
		try {
			ipServidor = InetAddress.getByName("255.255.255.255");
			socket = new DatagramSocket();
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		while(!fin) {
			byte[] data = new byte[1024];
			DatagramPacket dp = new DatagramPacket(data,data.length);
			try {
				socket.receive(dp);								
				procesarMensaje(dp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	private void procesarMensaje(DatagramPacket dp) {
		String msg = (new String(dp.getData())).trim();
		String[] msgCompuesto = msg.split("#");
		if(msgCompuesto[0].equals("Listo")) {
			Utiles.empieza = true;
			Futbol.cambiarMapa(msgCompuesto[1]);
		} else if(msgCompuesto[0].equals("Desconectado")) {
			Utiles.empieza = false;
		} else if(msgCompuesto[0].equals("0")){
			Utiles.id = 0;
		} else if (msgCompuesto[0].equals("1")) {
			Utiles.id = 1;
		} else if (msgCompuesto[0].equals("mover")){
			float x = Float.parseFloat(msgCompuesto[2]);
			float y = Float.parseFloat(msgCompuesto[3]);
			System.out.println("se mueve "+x+ " "+y);
			if(msgCompuesto[1].equals("0")) {
				//				Futbol.personajeRed.getBody().setTransform(x,y,0);
				Futbol.p1.setPosition(x,y);
			} else Futbol.p2.setPosition(x,y);//Futbol.personajeBlue.getBody().setTransform(x,y,0);
		}else if (msgCompuesto[0].equals("pelota")){
			float x = Float.parseFloat(msgCompuesto[1]);
			float y = Float.parseFloat(msgCompuesto[2]);
			Futbol.pelota.setPosition(x,y);
		} else if (msgCompuesto[0].equals("score")){
			Futbol.marcador.golesRed = Integer.parseInt(msgCompuesto[1]);
			Futbol.marcador.golesBlue = Integer.parseInt(msgCompuesto[2]);
		} else if(msgCompuesto[0].equals("terminar")){
			Utiles.terminar = true;
		}
		System.out.println("EVENTO: "+msg);
	}
	
	public void enviarMensaje(String mensaje) {
		byte[] data = mensaje.getBytes();
		DatagramPacket dp = new DatagramPacket(data,data.length, this.ipServidor, this.puertoServidor);
		try {
			System.out.println(mensaje);
			socket.send(dp);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("EVENTO CLIENTE "+mensaje);
	}

}
