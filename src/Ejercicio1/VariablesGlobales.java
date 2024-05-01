package Ejercicio1;

import java.util.concurrent.locks.ReentrantLock;

public class VariablesGlobales {
	public static int arrayDatos[] = new int[110];
	public static int arrayResultados[] = new int[10];
	public static int resultadoFinal = 0;
	public static ReentrantLock lContador = new ReentrantLock();
	
}
