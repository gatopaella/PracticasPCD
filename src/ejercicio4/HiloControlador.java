package ejercicio4;

import java.util.Random;

import messagepassing.CommunicationScheme;

/**
 * El hilo controlador.
 * 
 * Cuando un cliente solicita acceder a una caja, el controlador genera un tiempo de espera aleatorio y, dependiendo del
 * resultado, asocia al cliente la caja A (más rápida) o la B. Entonces manda al cliente un mensaje asíncrono para
 * informarle de qué caja le corresponde.
 */
public class HiloControlador extends Thread {
	private CommunicationScheme csControlador;
	private CommunicationScheme csCliente[];
	private Random random;
	
	/**
	 * Constructor del hilo controlador.
	 * @param csControlador El canal por el que recibirá solicitudes de los clientes.
	 * @param csCliente El canal para el paso de mensajes a los clientes.
	 */
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
			//System.out.println("Controlador a la espera...");
			int idCliente = (Integer) csControlador.receive(); // Confiamos en que recibamos un Integer con el ID del hilo cliente
			int tiempoEspera = random.nextInt(10) + 1;
			if (tiempoEspera > 5) {
				tiempoEspera = (tiempoEspera+1) / 2;
				csCliente[idCliente].send(0); // La caja 0 es la A
				//System.out.println("El controlador da al cliente " + idCliente + " la caja A " + tiempoEspera + " seg");
			} else {
				csCliente[idCliente].send(1); // La caja 1 es la B
				//System.out.println("El controlador da al cliente " + idCliente + " la caja B " + tiempoEspera + " seg");
			}
			csCliente[idCliente].send(tiempoEspera);
		}
	}
}
