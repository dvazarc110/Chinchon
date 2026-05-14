package chinchon.deck;

/**
 * Enumera los valores numéricos disponibles para las cartas de la baraja
 * española usada en el juego. Cada constante almacena su puntuación asociada.
 * No hereda su comportamiento de ningún enum propio del proyecto.
 *
 * @author Daniel Vázquez Arce
 * @version 1.0
 */

public enum NumCard {

    UNO(1),
    DOS(2),
    TRES(3),
    CUATRO(4),
    CINCO(5),
    SEIS(6),
    SIETE(7),
    SOTA(10),
    CABALLO(11),
    REY(12);

    /**
     * Almacena el valor numérico asociado a la carta.
     */
    private int value;

    /**
     * Construye un valor de carta con su puntuación asociada.
     * @param value valor numérico de la carta.
     */
    NumCard(int value) {
        this.value = value;
    }

    /**
     * Devuelve el valor numérico de la carta.
     * @return valor asociado a la carta.
     */
    public int getValue() {
        return value;
    }
}
