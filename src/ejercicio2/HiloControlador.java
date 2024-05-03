package ejercicio2;

public class HiloControlador extends Thread {
	private final static int TIEMPO_ESPERA = 5000;
	@Override
	public void run() {
		for (int k = 0; k < HiloPrincipal.RONDAS; k++) {
			try {
				HiloPrincipal.mutexVar.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			VariablesGlobales.turno = Turnos.NS;
			System.out.println("TURNO NORTE-SUR");
			
			if (VariablesGlobales.nccns == 0 && VariablesGlobales.ncceo == 0 && VariablesGlobales.npc == 0) {
				int cochesNS_a_liberar = 4;
				// Por aquí hay en el pseudocódigo un wait que sobra 
				if (VariablesGlobales.ncens < cochesNS_a_liberar) cochesNS_a_liberar = VariablesGlobales.ncens;
				for (int i = 0; i < cochesNS_a_liberar; i++) {
					HiloPrincipal.cruzarNS.release();
					try { // Confiando en la magia de la exclusión mutua
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				HiloPrincipal.mutexVar.acquire();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			VariablesGlobales.turno = Turnos.PEATONES;
			System.out.println("TURNO DEL PROLETARIADO (PEATONES)");
			
			if(VariablesGlobales.nccns == 0 && VariablesGlobales.npc == 0 && VariablesGlobales.ncceo == 0) {
				int peatones_a_liberar = 10;
				if (VariablesGlobales.npe < peatones_a_liberar) peatones_a_liberar = VariablesGlobales.npe;
				for (int i = 0; i < peatones_a_liberar; i++) {
					HiloPrincipal.cruzarP.release();
					try { // Confiando en la magia de la exclusión mutua
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				HiloPrincipal.mutexVar.acquire();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			VariablesGlobales.turno = Turnos.EO;
			System.out.println("TURNO ESTE-OESTE");
			
			if (VariablesGlobales.nccns == 0 && VariablesGlobales.npc == 0 && VariablesGlobales.ncceo == 0) {
				int cochesEO_a_liberar = 4;
				if (VariablesGlobales.nceeo < cochesEO_a_liberar) cochesEO_a_liberar = VariablesGlobales.nceeo;
				for (int i = 0; i < cochesEO_a_liberar; i++) {
					HiloPrincipal.cruzarEO.release();
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
