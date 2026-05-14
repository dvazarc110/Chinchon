package chinchon.table;

/**
 * Punto de entrada de la aplicación, encargado de mostrar el menú, iniciar
 * partidas y preguntar si se desea jugar de nuevo. No hereda su comportamiento
 * de ninguna otra clase específica.
 *
 * @author Daniel Vázquez Arce
 * @version 1.0
 */
public class Main {

    /**
     * Inicia la ejecución principal del programa.
     * @param args argumentos recibidos desde la línea de comandos.
     */
    public static void main(String[] args) {

    	int n;
    	ConsoleInput input = new ConsoleInput();
    	MotorGame motor;

    	do {

	        Menu.show();

	        motor = new MotorGame();

	        motor.start();

	        System.out.println("¿Otra partida? 1. Sí     2. No ");

	        n = input.readIntInRange(1, 2);

    	}while(n != 2);

    	System.out.println("¡Adios!");
    }
}
