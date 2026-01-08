package red;

public class ProgramaCliente{
	public ProgramaCliente() {
		HiloCliente server = new HiloCliente();
		server.start();
	}	
}

