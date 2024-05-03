package ejercicio3;

public class HiloPrincipal {
	//creo 50 hilos Cliente	
	
	static Monitor monitor = new Monitor();
	
	public static void main(String[] args) {
		HiloCliente clientes[] = new HiloCliente[10];	
		
		for (int i = 0; i < clientes.length; i++) {
			clientes[i] = new HiloCliente(monitor);
			clientes[i].start();
		}
	}

}