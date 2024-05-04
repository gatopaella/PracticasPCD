package ejercicio1;

/**
 * Cada hilo consumidor i procesará las i-ésimas posiciones del array de datos y depositará el resultado
 * en la i-ésima posición del array de resultados.
 */
public class HiloConsumidor extends Thread {
	private int op;
	private int i;

	/**
	 * Constructor del hilo consumidor.
	 * @param i Número que identifica al consumidor, determinando las posiciones del array de datos que le corresponden.
	 */
	public HiloConsumidor(int i) {
		this.i = i;
	}
	
	public void run() {
		VariablesGlobales.arrayResultados[i] = VariablesGlobales.arrayDatos[i * 11];
		for (int j = 1; j <= 5; j++) {
			op = VariablesGlobales.arrayDatos[i * 11 + 2 * j - 1];
			switch (op) {
			case 0: {
				VariablesGlobales.arrayResultados[i] += VariablesGlobales.arrayDatos[i * 11 + 2 * j];
				break;
			}
			case 1: {
				VariablesGlobales.arrayResultados[i] -= VariablesGlobales.arrayDatos[i * 11 + 2 * j];
				break;
			}
			case 2: {
				VariablesGlobales.arrayResultados[i] *= VariablesGlobales.arrayDatos[i * 11 + 2 * j];
				break;
			}
			}
		}

		System.out.println("Resultado " + i + ": " + VariablesGlobales.arrayResultados[i]);
		System.out.println("TID: " + getId());

	}
}
