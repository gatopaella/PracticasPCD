package ejercicio4;

import messagepassing.MailBox;

/**
 * El hilo principal del ejercicio 4.
 * Al ejecutarse, inicializa los hilos clientes, el controlador y los buzones y pone todo en marcha.
 */
public class HiloPrincipal {
	public final static int NUM_CLIENTES = 5;
	
	public static void main(String[] args) {
		MailBox buzonControlador = new MailBox();
		MailBox buzonCliente[] = new MailBox[NUM_CLIENTES];
		for (int i = 0; i < NUM_CLIENTES; i++) {
			buzonCliente[i] = new MailBox();
		}
		MailBox buzonCajaA = new MailBox();
		MailBox buzonCajaB = new MailBox();
		
		buzonCajaA.send("tokenA");
		buzonCajaB.send("tokenB");
		
		HiloControlador controlador = new HiloControlador(buzonControlador, buzonCliente);
		HiloCliente clientes[] = new HiloCliente[NUM_CLIENTES];
		for (int i = 0; i < NUM_CLIENTES; i++) {
			clientes[i] = new HiloCliente(i, buzonControlador, buzonCliente, buzonCajaA, buzonCajaB);
		}
		controlador.start();
		for (int i = 0; i < NUM_CLIENTES; i++) {
			clientes[i].start();
		}
	}
}
