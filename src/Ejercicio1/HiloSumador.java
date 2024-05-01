package Ejercicio1;

public class HiloSumador extends Thread {
	
	@Override
	public void run() {
		for(int i = 0; i<10; i++) {
			VariablesGlobales.resultadoFinal += VariablesGlobales.arrayResultados[i];
		}
		System.out.println("Resultado Final = " + VariablesGlobales.resultadoFinal);
	}
}
