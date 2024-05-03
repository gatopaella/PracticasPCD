package ejercicio2;

public class HiloPeaton extends Thread {
	private final static int TIEMPO_CRUZANDO = 3000;
	private final static int TIEMPO_ESPERA = 7000;
	
	@Override
	public void run() {
		for (int k = 0; k < HiloPrincipal.RONDAS; k++) {
			try {
				HiloPrincipal.mutexVar.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if (VariablesGlobales.turno != Turnos.PEATONES
					|| VariablesGlobales.nccns > 0
					|| VariablesGlobales.ncceo > 0
					|| VariablesGlobales.npc == 4) {
				VariablesGlobales.npe++;
				System.out.println("Peatón quiere cruzar \n"
						+ "Número de peatones esperando para cruzar: " + VariablesGlobales.npe);
				HiloPrincipal.mutexVar.release();
				try {
					HiloPrincipal.cruzarP.acquire();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				VariablesGlobales.npe--;
			}
			VariablesGlobales.npc++;
			
			HiloPrincipal.mutexVar.release();
			
			System.out.println("Peatón cruzando\n" 
					+ "Número de peatones cruzando: " + VariablesGlobales.npc);
			try {
				sleep(TIEMPO_CRUZANDO);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				HiloPrincipal.mutexVar.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			VariablesGlobales.npc--;
			System.out.println("Peatón ha cruzado");
			
			if (VariablesGlobales.turno == Turnos.PEATONES && VariablesGlobales.npe > 0) {
				HiloPrincipal.cruzarP.release();
			} else if (VariablesGlobales.turno == Turnos.EO && VariablesGlobales.npc == 0) {
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
			} else if (VariablesGlobales.turno == Turnos.NS && VariablesGlobales.npc == 0) {
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
			} else {
				HiloPrincipal.mutexVar.release();
			}
			
			
			try {
				sleep(TIEMPO_ESPERA);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
