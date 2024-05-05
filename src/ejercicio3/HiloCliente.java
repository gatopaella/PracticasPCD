package ejercicio3;

import java.util.Random;

/**
 * Modeliza un cliente.
 * Utilizará el monitor dos veces. La primera, para acceder a la cola de las máquinas. La segunda, para ser dirigido a una
 * de las 4 colas de las mesas, permaneciendo inactivo un cierto tiempo tras cada uso del monitor.
 */
public class HiloCliente extends Thread{
	private int id;
	private Monitor monitor;
	private int tiempoX;
	private int tiempoY;
	private int numMaquina;
	private int numMesa;
	Random random = new Random();
	
	/**
	 * Constructor del hilo cliente.
	 * @param id Número entero que identifica el hilo.
	 * @param m El monitor al que accederá el hilo.
	 */
	public HiloCliente (int id, Monitor m) {
		this.id = id;
		monitor = m;
		tiempoX = random.nextInt(500);
		tiempoY = random.nextInt(500);
	}
	
	@Override
	public void run() {


		try {
			numMaquina = monitor.pedirMaquina();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			sleep(tiempoX);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			numMesa = monitor.irDeMaquinaAMesa(id, tiempoX, tiempoY, numMaquina);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			sleep(tiempoY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			monitor.salirMesa(numMesa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
