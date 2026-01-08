package red;

import java.net.InetAddress;

public class Conexion {
	private InetAddress ip;
	private int puerto;
	
	public Conexion(InetAddress ip, int puerto) {
		this.ip = ip;
		this.puerto = puerto;
	}

	public InetAddress getIp() {
		return this.ip;
	}

	public int getPuerto() {
		return this.puerto;
	}

}
