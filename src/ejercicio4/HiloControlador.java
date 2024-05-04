package ejercicio4;

import java.util.Random;

import messagepassing.CommunicationScheme;
import messagepassing.MailBox;

public class HiloControlador extends Thread {
	private CommunicationScheme csControlador;
	private CommunicationScheme csCliente[];
	private Random random;
	
	public HiloControlador(CommunicationScheme csControlador, CommunicationScheme csCliente[]) {
		this.csControlador = csControlador;
		this.csCliente = new CommunicationScheme[HiloPrincipal.NUM_CLIENTES];
		for (int i = 0; i < HiloPrincipal.NUM_CLIENTES; i++) {
			this.csCliente[i] = csCliente[i];
		}
		random = new Random();
	}
	
	@Override
	public void run() {
		while(true) {
			// La clase Integer implementa serializable
			int idCliente = (Integer) csControlador.receive(); // Confiamos en que recibamos un Integer con el ID del hilo cliente
			int tiempoEspera = random.nextInt(10)+1;
			if (tiempoEspera >= 5) {
				tiempoEspera = tiempoEspera / 2;
				csCliente[idCliente].send(0); // La caja 0 es la A
			} else {
				csCliente[idCliente].send(1); // La caja 1 es la B
			}
			csCliente[idCliente].send(tiempoEspera);
		}
	}
}
