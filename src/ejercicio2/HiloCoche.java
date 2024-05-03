package ejercicio2;

public class HiloCoche extends Thread {

	private final static int TIEMPO_CRUZANDO = 500;
	private final static int TIEMPO_CAMBIO_DIR = 7000;
	
	@Override
	public void run() {
		for (int k = 0; k < HiloPrincipal.RONDAS; k++) {
				// NORTE - SUR
			try { // wait(mutexVar)
				HiloPrincipal.mutexVar.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (VariablesGlobales.turno != Turnos.NS 
					|| VariablesGlobales.ncceo > 0 
					|| VariablesGlobales.npc > 0 
					|| VariablesGlobales.nccns == 4) {
				VariablesGlobales.ncens++;
				System.out.println("Coche quiere cruzar de norte a sur\n"
						+ "Número de coches esperando para cruzar de N a S: " + VariablesGlobales.ncens);
				HiloPrincipal.mutexVar.release();
				try { // wait(cruzarNS)
					HiloPrincipal.cruzarNS.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				VariablesGlobales.ncens--;
			}
			VariablesGlobales.nccns++;
			
			HiloPrincipal.mutexVar.release();
			
			System.out.println("Coche empieza a cruzar de norte a sur\n"
					+ "Número de coches cruzando de norte a sur: " + VariablesGlobales.nccns);
			try { // CRUZAN
				sleep(TIEMPO_CRUZANDO);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {	// wait(mutexVar)
				HiloPrincipal.mutexVar.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			VariablesGlobales.nccns--;
			System.out.println("Coche termina de cruzar de norte a sur");
			
			if (VariablesGlobales.turno == Turnos.NS && VariablesGlobales.ncens > 0) {
				
				HiloPrincipal.cruzarNS.release();
				
			} else if (VariablesGlobales.turno == Turnos.PEATONES && VariablesGlobales.nccns == 0) {
				//HiloPrincipal.mutexVar.release();
				int peatones_a_liberar = 10;
				if (VariablesGlobales.npe < peatones_a_liberar) peatones_a_liberar = VariablesGlobales.npe;
				for (int i = 0; i < peatones_a_liberar; i++) {
					HiloPrincipal.cruzarP.release();
					try {
						HiloPrincipal.mutexVar.acquire();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				HiloPrincipal.mutexVar.release();
			} else if (VariablesGlobales.turno == Turnos.EO && VariablesGlobales.nccns == 0) {
				//HiloPrincipal.mutexVar.release();
				int coches_a_liberar = 4;
				if (VariablesGlobales.nceeo < coches_a_liberar) coches_a_liberar = VariablesGlobales.nceeo;
				for (int i = 0; i < coches_a_liberar; i++) {
					HiloPrincipal.cruzarEO.release();
					try {
						HiloPrincipal.mutexVar.acquire();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				HiloPrincipal.mutexVar.release();
			} else {
				HiloPrincipal.mutexVar.release();
			}
			
			try {
				sleep(TIEMPO_CAMBIO_DIR);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				// ESTE - OESTE
			
			try { // wait(mutexVar)
				HiloPrincipal.mutexVar.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (VariablesGlobales.turno != Turnos.EO
					|| VariablesGlobales.npc > 0
					|| VariablesGlobales.nccns > 0
					|| VariablesGlobales.ncceo == 4) {
				System.out.println("Coche quiere cruzar de este a oeste\n"
						+ "Número de coches esperando para cruzar de este a oeste: " + VariablesGlobales.nceeo);
				VariablesGlobales.nceeo++;
				HiloPrincipal.mutexVar.release();
				try { // wait(cruzarEO)
					HiloPrincipal.cruzarEO.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				VariablesGlobales.nceeo--;
			}
			VariablesGlobales.ncceo++;
			HiloPrincipal.mutexVar.release();
			
			System.out.println("Coche empieza a cruzar de este a oeste\n"
					+ "Número de coches cruzando de E a O: " + VariablesGlobales.ncceo);
			try { // CRUZAN
				sleep(TIEMPO_CRUZANDO);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try { // wait(mutexVar)
				HiloPrincipal.mutexVar.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			VariablesGlobales.ncceo--;
			System.out.println("Coche termina de cruzar de este a oeste");
			
			if (VariablesGlobales.turno == Turnos.EO && VariablesGlobales.nceeo > 0) {
				HiloPrincipal.cruzarEO.release();
			} else if (VariablesGlobales.turno == Turnos.NS && VariablesGlobales.ncceo == 0) {
				//HiloPrincipal.mutexVar.release();
				int coches_a_liberar = 4;
				if (VariablesGlobales.ncens < coches_a_liberar) coches_a_liberar = VariablesGlobales.ncens;
				for (int i = 0; i < coches_a_liberar; i++) {
					HiloPrincipal.cruzarNS.release();
					try {
						HiloPrincipal.mutexVar.acquire();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				HiloPrincipal.mutexVar.release();
			} else if (VariablesGlobales.turno == Turnos.PEATONES && VariablesGlobales.npc == 0) {
				//HiloPrincipal.mutexVar.release();
				int peatones_a_liberar = 10;
				if (VariablesGlobales.npe < peatones_a_liberar) peatones_a_liberar = VariablesGlobales.npe;
				for (int i = 0; i < peatones_a_liberar; i++) {
					HiloPrincipal.cruzarP.release();
					try {
						HiloPrincipal.mutexVar.acquire();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				HiloPrincipal.mutexVar.release();
			} else {
				HiloPrincipal.mutexVar.release();
			}
			
			try {
				sleep(TIEMPO_CAMBIO_DIR);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
