package ejercicio3;

import java.util.concurrent.locks.*;

public class Monitor {
	private static final int TIEMPO_MAXIMO = 99999999;
	private ReentrantLock l = new ReentrantLock();

	private boolean maquinas[] = new boolean[3];
	private Condition colaMaquina = l.newCondition();

	private boolean mesas[] = new boolean[4];
	private Condition colasMesas[] = new Condition[4];
	// private Condition colaMesa1 = l.newCondition();
	// private Condition colaMesa2 = l.newCondition();
	// private Condition colaMesa3 = l.newCondition();
	// private Condition colaMesa4 = l.newCondition();
	private int tiempoMesas[] = new int[4];
	private int tiempoMinimo;
	private int mesaMinima;

	public Monitor() {
		for (int i = 0; i < maquinas.length; i++) {
			maquinas[i] = false;
		}
		for (int i = 0; i < mesas.length; i++) {
			mesas[i] = false;
			tiempoMesas[i] = 0;
			colasMesas[i] = l.newCondition();
		}

	}

	public int pedirMaquina(int id) throws InterruptedException {
		l.lock();
		int valor = 0;
		try {
			while (maquinas[0] && maquinas[1] && maquinas[2]) {
				colaMaquina.await();
			}

			if (!maquinas[0]) {
				maquinas[0] = true;
				valor = 0;
			} else if (!maquinas[1]) {
				maquinas[1] = true;
				valor = 1;
			} else if (!maquinas[2]) {
				maquinas[2] = true;
				valor = 2;
			}
		} finally {
			l.unlock();
		}
		return valor;
	}

	public int irDeMaquinaAMesa(int id, int tiempoX, int tiempoY, int numMaquina) throws InterruptedException {
		// salir de la maquina
		l.lock();
		try {
				maquinas[numMaquina] = false;

			colaMaquina.signal();
			//ir a la mesa
			tiempoMinimo = TIEMPO_MAXIMO;
			for (int i = 0; i < tiempoMesas.length; i++) {
				if (tiempoMinimo > tiempoMesas[i]) {
					tiempoMinimo = tiempoMesas[i];
					mesaMinima = i;
				}
			}

			// TODO: Por aquí debería imprimir por pantalla todo lo que hay q imprimir
			System.out.println("Cliente " + id + " ha solicitado su servicio en la máquina: " + numMaquina);
			System.out.println("Tiempo en solicitar el servicio: " + tiempoX);
			System.out.println("Será atendido en la mesa: " + mesaMinima);
			System.out.println("Tiempo en la mesa = " + tiempoY);
			System.out.println("Tiempo de espera en la mesa0: " + tiempoMesas[0]
					+ ", mesa1: " +tiempoMesas[1]
					+ ", mesa2: " +tiempoMesas[2]	
					+ ", mesa3: " +tiempoMesas[3]);
			
			tiempoMesas[mesaMinima] += tiempoY;
			while (mesas[mesaMinima]) {
				colasMesas[mesaMinima].await();
			}
			mesas[mesaMinima] = true;
			return mesaMinima;
		} finally {
			l.unlock();
		}
	}
	public void salirMesa(int id, int mesaMinima) {
		l.lock();
		try {
			mesas[mesaMinima] = false;
			colasMesas[mesaMinima].signal();
		} finally {
			l.unlock();
		}
		
		
	}
	
	
	
	
	
	
	
	
	
}
