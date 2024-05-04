package ejercicio4;

import java.util.Random;

import messagepassing.CommunicationScheme;

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
			int caja = (Integer) csCliente[id].receive();
			char cajaCaracter;
			if (caja == 1) cajaCaracter = 'A';
			else if (caja == 2) cajaCaracter = 'B';
			else {
				System.out.println("Al cliente " + id + ", por algún motivo, le ha tocado la caja " + caja);
				cajaCaracter = 'Z';
			}
			
			int tiempoEspera = (Integer) csCliente[id].receive();
			
			if (cajaCaracter == 'A') {
				tokenA = csCajaA.receive();
			} else if (cajaCaracter == 'B') {
				tokenB = csCajaB.receive();
			}
			
			try {
				sleep(tiempoEspera * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (cajaCaracter == 'A') {
				csCajaA.send(tokenA);
			} else if (cajaCaracter == 'B') {
				csCajaB.send(tokenB);
			}
			
			System.out.println("Persona " + id + " ha usado la caja " + cajaCaracter + "\n"
					+ "Tiempo de pago = " + tiempoEspera + "\n");
		}
	}
}
