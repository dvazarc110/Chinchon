package chinchon.deck;


/**
 * Representa una carta de la baraja de Chinchón, formada por un palo y un número.
 * Permite consultar su valor, palo y número, y mostrarla como texto. No hereda su
 * comportamiento de ninguna otra clase específica.
 *
 * @author Daniel Vázquez Arce
 * @version 1.0
 */
public class Card {

    /**
     * Almacena el palo al que pertenece la carta.
     */
    private Suit suit;

    /**
     * Almacena el número o figura de la carta.
     */
    private NumCard num;

    /**
     * Construye una carta con un palo y un número determinados.
     * @param token palo que tendrá la carta.
     * @param num número o figura que tendrá la carta.
     */
    public Card(Suit token, NumCard num) {
        this.suit = token;
        this.num = num;
    }

    /**
     * Devuelve el valor numérico de la carta.
     * @return valor asociado al número o figura de la carta.
     */
    public int getValue() {
        return num.getValue();
    }

    /**
     * Devuelve el palo de la carta.
     * @return palo al que pertenece la carta.
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Devuelve el número o figura de la carta.
     * @return número o figura de la carta.
     */
    public NumCard getNum(){
    	return num;
    }

    /**
     * Convierte la carta a una representación en texto.
     * @return texto con el valor y el palo de la carta.
     */
    public String toString() {
        return num.getValue() + " " + suit.getSuit();
    }
}
