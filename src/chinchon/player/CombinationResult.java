package chinchon.player;

import java.util.List;

import chinchon.deck.Card;

/**
 * Almacena el resultado del análisis de una mano, incluyendo las combinaciones
 * encontradas y las cartas sobrantes. Permite determinar si un jugador puede
 * cerrar, si tiene Chinchón y calcular su puntuación. No hereda su comportamiento
 * de ninguna otra clase específica.
 *
 * @author Daniel Vázquez Arce
 * @version 1.0
 */
public class CombinationResult {

    /**
     * Almacena las combinaciones válidas encontradas en la mano.
     */
    private List<List<Card>> combinations;

    /**
     * Almacena las cartas que no pertenecen a ninguna combinación.
     */
    private List<Card> lefts;

    /**
     * Construye un resultado de combinaciones con sus cartas sobrantes.
     * @param combinations combinaciones encontradas.
     * @param lefts cartas sobrantes.
     */
    public CombinationResult(List<List<Card>> combinations, List<Card> lefts) {
        this.combinations = combinations;
        this.lefts = lefts;
    }

    /**
     * Comprueba si el jugador puede cerrar la ronda.
     * @param turncont contador del turno actual.
     * @param playeramount cantidad de jugadores de la partida.
     * @return true si puede cerrar, false en caso contrario.
     */
    public boolean canClose(int turncont, int playeramount) {
    	if(turncont <= playeramount) {
    		return false;
    	}else {
	    	if(lefts.size() == 0) {
	    		return true;
	    	}else if(lefts.size() == 1) {
	    		if(lefts.get(0).getValue() > 5) {
	    			return false;
	    		}else {
	    			return true;
	    		}
	    	}else {
	    		return false;
	    	}
    	}
    }

    /**
     * Comprueba si el resultado contiene una combinación de Chinchón.
     * @param turncont contador del turno actual.
     * @param playeramount cantidad de jugadores de la partida.
     * @return true si hay Chinchón, false en caso contrario.
     */
    public boolean chinchon(int turncont, int playeramount) {
    	if(turncont <= playeramount) {
    		return false;
    	}else {
    		return combinations.stream()
    							.anyMatch(g -> g.size() == 7);
    	}
    }

    /**
     * Calcula la puntuación total de las cartas sobrantes.
     * @return suma de puntos de las cartas sobrantes, o -10 si no hay sobrantes.
     */
    public int sumScore() {
    	if(lefts.size() > 0) {
    		return lefts.stream()
    						.mapToInt(Card::getValue)
    						.sum();
    	}else {
    		return (-10);
    	}
    }

    /**
     * Devuelve las combinaciones encontradas.
     * @return lista de combinaciones de cartas.
     */
	public List<List<Card>> getCombinations() {
		return combinations;
	}

    /**
     * Devuelve las cartas sobrantes.
     * @return lista de cartas que no forman combinación.
     */
	public List<Card> getLefts(){
		return lefts;
	}
}
