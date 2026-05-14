package chinchon.deck;

/**
 * Enumera los palos disponibles de la baraja, asociando cada palo con su
 * representación visual. No hereda su comportamiento de ningún enum propio
 * del proyecto.
 *
 * @author Daniel Vázquez Arce
 * @version 1.0
 */
public enum Suit {

    ORO("🟡"),
    COPA("🏆"),
    BASTOS("🥬"),
    ESPADAS("🗡️");

    /**
     * Almacena la representación visual del palo.
     */
    private String token;

    /**
     * Construye un palo con su representación visual.
     * @param token símbolo o texto que representa el palo.
     */
    Suit(String token) {
        this.token = token;
    }

    /**
     * Devuelve la representación visual del palo.
     * @return símbolo o texto asociado al palo.
     */
    public String getSuit() {
        return token;
    }
}
