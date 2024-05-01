package Ejercicio1;

public class HiloPrincipal {
	public static void main(String[] args) {
		HiloConsumidor consumidores[] = new HiloConsumidor[10];
		
		HiloGenerador generador = new HiloGenerador();
		
		HiloSumador sumador = new HiloSumador();
		generador.start();
		
		try {
			generador.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for(int i = 0; i<10; i++) {
			consumidores[i] = new HiloConsumidor(i);
			consumidores[i].start();
		}
		for(int i = 0; i<10; i++) {
			try {
				consumidores[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		sumador.start();
		
		
	}
}
