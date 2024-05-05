package ejercicio3;

import java.util.concurrent.locks.*;

/**
 * Monitor encargado de proteger la exclusión mutua de las máquinas y las mesas y coordinar los hilos
 * para que funcionen concurrentemente.
 */
public class Monitor {
	private static final int TIEMPO_MAXIMO = 99999999;
	private ReentrantLock l = new ReentrantLock();

	private boolean maquinas[] = new boolean[3];
	private Condition colaMaquina = l.newCondition();

	private boolean mesas[] = new boolean[4];
	private Condition colasMesas[] = new Condition[4];
	private int tiempoMesas[] = new int[4];
	private int tiempoMinimo;
	private int mesaMinima;

	/**
	 * Constructor del monitor.
	 */
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

	/**
	 * Cuando un hilo cliente llama esta función, o bien se le asigna una máquina o bien se le deja esperando en una cola
	 * hasta que haya una máquina libre, cuando será despertado para volver a intentarlo.
	 * @return El número de la máquina a la que se dirige el cliente.
	 * @throws InterruptedException
	 */
	public int pedirMaquina() throws InterruptedException {
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

	/**
	 * Esta función consta de dos partes.
	 * Primero, deja libre la máquina en la que ha estado el cliente.
	 * Después, el cliente accederá a la mesa que le corresponda.
	 * @param id El identificador del cliente.
	 * @param tiempoX El tiempo que el cliente ha permanecido en la máquina.
	 * @param tiempoY El tiempo que el cliente va a permanecer en la mesa.
	 * @param numMaquina El número asociado a la máquina que ha utilizado el cliente.
	 * @return El número de la mesa asignada al cliente.
	 * @throws InterruptedException
	 */
	public int irDeMaquinaAMesa(int id, int tiempoX, int tiempoY, int numMaquina) throws InterruptedException {
		// salir de la maquina
		l.lock();
		try {
				maquinas[numMaquina] = false;

			colaMaquina.signalAll();
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
	
	/**
	 * Desbloquea la mesa ocupada por el cliente tras su uso y avisa para que otro cliente en la cola pueda acceder
	 * a ella.
	 * @param mesa El número de la mesa utilizada por el cliente. 
	 */
	public void salirMesa(int mesa) {
		l.lock();
		try {
			mesas[mesa] = false;
			colasMesas[mesa].signalAll();
		} finally {
			l.unlock();
		}
		
		
	}
	
	
	
	
	
	
	
	
	
}
