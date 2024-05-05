package ejercicio3;

/**
 * El hilo principal del ejercicio 3.
 * Inicializa el monitor y los clientes y los pone en marcha.
 */
public class HiloPrincipal {
	//creo 50 hilos Cliente	
	
	static Monitor monitor = new Monitor();
	
	public static void main(String[] args) {
		HiloCliente clientes[] = new HiloCliente[50];	
		
		for (int i = 0; i < clientes.length; i++) {
			clientes[i] = new HiloCliente(i+1,monitor);
			clientes[i].start();
		}
	}

}
