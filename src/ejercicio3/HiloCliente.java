package ejercicio3;

import java.util.Random;

public class HiloCliente extends Thread{
	private int id;
	private Monitor monitor;
	private int tiempoX;
	private int tiempoY;
	private int numMaquina;
	private int numMesa;
	Random random = new Random();
	

	public HiloCliente (int id, Monitor m) {
		this.id = id;
		monitor = m;
		tiempoX = random.nextInt(100);
		tiempoY = random.nextInt(1000);
	}
	
	@Override
	public void run() {


		try {
			numMaquina = monitor.pedirMaquina(id);
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
			monitor.salirMesa(id, numMesa);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
