package Ejercicio1;

import java.util.Random;

public class HiloGenerador extends Thread {
	private Random random = new Random();
	
	public void run() {
		for(int i = 0; i<10; i++) {
			for(int j = 0; j<5; j++) {
				VariablesGlobales.arrayDatos[i*11+j*2] = random.nextInt(11);
				VariablesGlobales.arrayDatos[i*11+j*2+1] = random.nextInt(3);
			}
			VariablesGlobales.arrayDatos[i*11+10] = random.nextInt(11);
		}
	}
}
