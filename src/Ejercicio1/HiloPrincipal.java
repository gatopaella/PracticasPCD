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
		//TODO el compi dice en la práctica que hay que resolver esto usando reentrantLock
		//El contador tendría sentido si queremos que los hilos muestren su TID tras haber mostrado los resultados, quizás, fuf
	}
}
