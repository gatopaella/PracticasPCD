package ejercicio4;

import messagepassing.MailBox;

public class HiloPrincipal {
	public final static int NUM_CLIENTES = 30;
	
	public static void main(String[] args) {
		MailBox buzonControlador = new MailBox();
		MailBox buzonPantalla = new MailBox();
		MailBox buzonCliente[] = new MailBox[NUM_CLIENTES];
		for (int i = 0; i < NUM_CLIENTES; i++) {
			buzonCliente[i] = new MailBox();
		}
		MailBox buzonCajaA = new MailBox();
		MailBox buzonCajaB = new MailBox();
		
		
	}
}
