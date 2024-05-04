package ejercicio2;

/**
 * Modeliza un peatón, que cruzará la carretera cuando llegue su turno y no haya coches cruzando.
 * El tiempo que tarda en cruzar está determinado por la constante TIEMPO_CRUZANDO (3 segundos).
 * Tras cruzar, el peatón se mantendrá inactivo 8 segundos (valor almacenado en TIEMPO_ESPERA)
 * antes de volver a esperar para cruzar.
 * 
 * Informa por pantalla cada vez que el peatón comienza a cruzar, 
 * añadiendo el número de peatones que están cruzando simultáneamente,
 * y cuando termina.
*/
public class HiloPeaton extends Thread {
	private final static int TIEMPO_CRUZANDO = 3000;
	private final static int TIEMPO_ESPERA = 8000;
	
	@Override
	public void run() {
		for (int k = 0; k < HiloPrincipal.RONDAS; k++) {
			try {
				HiloPrincipal.mutexVar.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (VariablesGlobales.turno != Turnos.PEATONES
					|| VariablesGlobales.nccns > 0
					|| VariablesGlobales.ncceo > 0
					|| VariablesGlobales.npc == 4) {
				VariablesGlobales.npe++;
				/*
				System.out.println("Peatón quiere cruzar \n"
						+ "Número de peatones esperando para cruzar: " + VariablesGlobales.npe);
						*/
				HiloPrincipal.mutexVar.release();
				try {
					HiloPrincipal.cruzarP.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				VariablesGlobales.npe--;
			}
			VariablesGlobales.npc++;
			
			System.out.println("Peatón empieza a cruzar\n" 
					+ "Número de peatones cruzando: " + VariablesGlobales.npc);
			
			HiloPrincipal.mutexVar.release();
			
			try {
				sleep(TIEMPO_CRUZANDO);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			try {
				HiloPrincipal.mutexVar.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			VariablesGlobales.npc--;
			System.out.println("Peatón ha terminado de cruzar");
			
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
				e.printStackTrace();
			}
		}
	}
}
