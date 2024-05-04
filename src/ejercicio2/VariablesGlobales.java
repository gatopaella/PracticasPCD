package ejercicio2;

/**
 * Las variables globales que los hilos leerán y escribirán durante su ejecución.
 * npc contiene el número de peatones cruzando,
 * npe el número de peatones esperando,
 * nccns el número de coches cruzando de norte a sur,
 * ncens el número de coches esperando para cruzar de norte a sur,
 * ncceo el número de coches cruzando de este a oeste
 * y nceeo el número de coches esperando para cruzar de este a oeste.
 */
public class VariablesGlobales {
	public static int npc = 0;
	public static int npe = 0;
	public static int nccns = 0;
	public static int ncens = 0;
	public static int ncceo = 0;
	public static int nceeo = 0;
	
	public static Turnos turno = Turnos.SIN_INICIAR;
}
