package chinchon.table;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Centraliza la lectura de datos por consola, validando enteros, rangos y
 * cadenas de texto introducidas por el usuario. No hereda su comportamiento
 * de ninguna otra clase específica.
 *
 * @author Daniel Vázquez Arce
 * @version 1.0
 */
public class ConsoleInput {

    /**
     * Almacena el lector usado para recibir datos por consola.
     */
    private final Scanner keyboard;

    /**
     * Construye un lector de consola usando un Scanner recibido.
     * @param keyboard Scanner que se usará para leer la entrada.
     */
    public ConsoleInput(Scanner keyboard) {
        this.keyboard = keyboard;
    }

    /**
     * Construye un lector de consola usando la entrada estándar.
     */
    public ConsoleInput() {
        keyboard = new Scanner(System.in);
    }

    /**
     * Limpia la entrada pendiente del teclado.
     */
    public void cleanInput() {
        keyboard.nextLine();
    }

    /**
     * Lee un número entero por consola validando posibles errores.
     * @return número entero introducido.
     */
    public int readInt() {
        boolean isValid;
        int in = 0;

        do {
            isValid = true;
            try {
                in = keyboard.nextInt();
            } catch (NumberFormatException | InputMismatchException e) {
                System.err.println("Error en el INT");
                System.out.println("Introduzca un número");
                isValid = false;
            } finally {
                cleanInput();
            }
        } while (!isValid);
        return in;

    }

    /**
     * Lee un número entero dentro de un rango determinado.
     * @param lowerBound límite inferior permitido.
     * @param upperBound límite superior permitido.
     * @return número entero validado dentro del rango.
     */
    public int readIntInRange(int lowerBound, int upperBound) {
        int in;

        if (lowerBound > upperBound) {
            throw new IllegalArgumentException("\nEl upperBound es menor que el lowerBound");
        } else {
            do {
                in = readInt();
                if (upperBound < in || lowerBound > in) {
                    System.err.printf("El INT introducido no esta entre los valores %d y %d\n", lowerBound, upperBound);
                    System.out.printf("Introduzca otro número que entre dentro del rango %d - %d :\n", lowerBound, upperBound);
                }
            } while ((upperBound < in || lowerBound > in));
        }

        return (in);
    }

    /**
     * Lee una cadena de texto por consola.
     * @return texto introducido por el usuario.
     */
    public String readString() {
        return keyboard.nextLine();
    }

    /**
     * Lee una cadena de texto con una longitud máxima.
     * @param maxLength longitud máxima permitida.
     * @return texto introducido que cumple la longitud máxima.
     */
    public String readString(int maxLength) {
        String str;

        if (maxLength < 0) {
            throw new IllegalArgumentException("\nmaxLength es menor que 0");
        }
        do {
            str = readString();
            if (str.length() > maxLength) {
                System.err.println("El tamaño del STRING es mayor al especificado");
            }
        } while (str.length() > maxLength);

        return str;
    }
}