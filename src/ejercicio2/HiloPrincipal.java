package ejercicio2;

import java.util.concurrent.Semaphore;

public class HiloPrincipal {
	public static Semaphore mutexVar = new Semaphore(1);
	public static Semaphore cruzarNS = new Semaphore(0);
	public static Semaphore cruzarEO = new Semaphore(0);
	public static Semaphore cruzarP = new Semaphore(0);
	
	public final static int RONDAS = 3;

	private final static int NUM_COCHES = 50;
	private final static int NUM_PEATONES = 50;
	
	public static void main(String[] args) {
		VariablesGlobales.turno = Turnos.SIN_INICIAR;
		
		HiloCoche coches[] = new HiloCoche[NUM_COCHES];
		HiloPeaton peatones[] = new HiloPeaton[NUM_PEATONES];
		HiloControlador controlador = new HiloControlador();
		
		for (int i = 0; i < NUM_COCHES; i++) {
			coches[i] = new HiloCoche();
			coches[i].start();
		}
		
		for (int i = 0; i < NUM_PEATONES; i++) {
			peatones[i] = new HiloPeaton();
			peatones[i].start();
		}
		
		
		controlador.start();
	
	}
}
