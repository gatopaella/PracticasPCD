package Ejercicio1;

import java.util.concurrent.locks.ReentrantLock;

public class VariablesCompartidas {
	private int[] arrayDatos;
	private int[] arrayResultados;
	
	private ReentrantLock lockNumOp = new ReentrantLock();
	private ReentrantLock lockResults = new ReentrantLock();
	
	public VariablesCompartidas() {
		numOpArr = new int[110];
		resultsArr = new int[10];
	}
	
	public int getDato(int i) {
		return numOpArr[i];
	}
	
	public int getResultados(int i) {
		return resultsArr[i];
	}
	
	public int 
}
