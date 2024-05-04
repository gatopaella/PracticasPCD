package ejercicio4;

import java.util.Random;

import messagepassing.CommunicationScheme;

/**
 * Modeliza un cliente.
 * 
 * Primero comprará, lo que se traduce en una espera de como mucho un segundo.
 * Después preguntará al controlador en qué caja ponerse, pasándole un mensaje a través de un canal asíncrono.
 * Cuando reciba la respuesta del controlador, solicitará el acceso a la caja.
 * Cuando acceda, la ocupará un cierto tiempo (que el controlador habrá determinado aleatoriamente) antes de liberarla.
 * Entonces imprimirá por pantalla la caja a la que ha accedido y el tiempo que ha permanecido en ella.
 * 
 * Repetirá este proceso 5 veces.
 */
public class HiloCliente extends Thread {
	public static int NUM_REPETICIONES = 5;
	
	private CommunicationScheme csControlador;
	private CommunicationScheme csCliente[];
	private CommunicationScheme csCajaA;
	private CommunicationScheme csCajaB;
	
	private Object tokenA;
	private Object tokenB;
	
	private int id;
	
	private Random random;
	
	/**
	 * Constructor del hilo cliente.
	 * @param id El número que identifica al cliente
	 * @param csControlador Interfaz genérica. El canal para el paso de mensajes al hilo controlador.
	 * @param csCliente Interfaz genérica. El canal por el que los clientes recibirán mensajes del controlador
	 * @param csCajaA Interfaz genérica. EL buzón utilizado para garantizar la exclusión mutua de la caja A.
	 * @param csCajaB Interfaz genérica. EL buzón utilizado para garantizar la exclusión mutua de la caja B.
	 */
	public HiloCliente(int id, CommunicationScheme csControlador, CommunicationScheme csCliente[],
			CommunicationScheme csCajaA, CommunicationScheme csCajaB) {
		this.csControlador = csControlador;
		this.csCliente = new CommunicationScheme[HiloPrincipal.NUM_CLIENTES];
		for (int i = 0; i < HiloPrincipal.NUM_CLIENTES; i++) {
			this.csCliente[i] = csCliente[i];
		}
		this.csCajaA = csCajaA;
		this.csCajaB = csCajaB;
		this.id = id;
		random = new Random();
	}
	
	@Override
	public void run() {
		for (int i = 0; i < NUM_REPETICIONES; i++) {
			try {
				sleep(random.nextInt(1000)+1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			csControlador.send(id);
			//System.out.println("El cliente " + id + " pregunta a qué caja ir");
			int caja = (Integer) csCliente[id].receive();
			char cajaCaracter;
			if (caja == 0) cajaCaracter = 'A';
			else if (caja == 1) cajaCaracter = 'B';
			else {
				cajaCaracter = 'Z';
			}
			//System.out.println("Al cliente " + id + " le ha tocado la caja " + cajaCaracter);
			
			int tiempoEspera = (Integer) csCliente[id].receive();
			//System.out.println("El cliente " + id + " espera " + tiempoEspera + " seg");
			if (cajaCaracter == 'A') {
				tokenA = csCajaA.receive();
			} else if (cajaCaracter == 'B') {
				tokenB = csCajaB.receive();
			}
			//System.out.println("Al cliente " + id + " accede a la caja " + cajaCaracter);
			
			try {
				sleep(tiempoEspera * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//System.out.println("El cliente " + id + " devuelve la caja " + caja);
			if (cajaCaracter == 'A') {
				csCajaA.send(tokenA);
			} else if (cajaCaracter == 'B') {
				csCajaB.send(tokenB);
			}
			
			System.out.println("Persona " + id + " ha usado la caja " + cajaCaracter + "\n"
					+ "Tiempo de pago = " + tiempoEspera + "\n"
					+ "Thread.sleep(" + tiempoEspera * 1000 + "ms)\n"
					+ "Persona " + id + " liberando la caja " + cajaCaracter);
					
		}
	}
}
