package ejercicio1;

/**
 * Calcula el resultado final sumando todo el contenido del array de resultados.
 */
public class HiloSumador extends Thread {
	
	@Override
	public void run() {
		for(int i = 0; i<10; i++) {
			VariablesGlobales.resultadoFinal += VariablesGlobales.arrayResultados[i];
		}
		System.out.println("Resultado Final = " + VariablesGlobales.resultadoFinal);
	}
}
