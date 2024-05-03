package ejercicio3;

import java.util.Random;

public class HiloCliente extends Thread{
	private Monitor monitor;
	private int tiempoX;
	private int tiempoY;
	private int numMaquina;
	private int numMesa;
	Random random = new Random();
	

	public HiloCliente (Monitor m) {
		monitor = m;
		tiempoX = random.nextInt(1000);
		tiempoY = random.nextInt(1000);
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
			numMesa = monitor.irDeMaquinaAMesa(tiempoY, numMaquina);
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