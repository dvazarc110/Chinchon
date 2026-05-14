package chinchon.player;

import java.util.List;

import chinchon.deck.Card;

/**
 * Almacena el resultado del análisis de posibles combinaciones futuras,
 * incluyendo combinaciones, combinaciones potenciales y cartas sobrantes.
 * No hereda su comportamiento de ninguna otra clase específica.
 *
 * @author Daniel Vázquez Arce
 * @version 1.0
 */
public class PosibCombinationResult {

    /**
     * Almacena las combinaciones encontradas.
     */
    private List<List<Card>> combinations;

    /**
     * Almacena las posibles combinaciones futuras.
     */
    private List<List<Card>> posibCombinations;

    /**
     * Almacena las cartas que no pertenecen a ninguna combinación.
     */
    private List<Card> lefts;

    /**
     * Construye un resultado de posibles combinaciones.
     * @param combinations combinaciones encontradas.
     * @param lefts cartas sobrantes.
     */
    public PosibCombinationResult(List<List<Card>> combinations, List<Card> lefts) {
        this.combinations = combinations;
        this.lefts = lefts;
    }

    /**
     * Comprueba si el jugador puede cerrar con las cartas sobrantes actuales.
     * @return true si puede cerrar, false en caso contrario.
     */
    public boolean canClose() {
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

    /**
     * Comprueba si existe una combinación de Chinchón.
     * @return true si hay una combinación de siete cartas, false en caso contrario.
     */
    public boolean chinchon() {
        return combinations.stream()
                .anyMatch(g -> g.size() == 7);
    }

    /**
     * Calcula la puntuación de las cartas sobrantes.
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
     * Devuelve las posibles combinaciones futuras.
     * @return lista de posibles combinaciones.
     */
	public List<List<Card>> getPosibCombinations() {
		return posibCombinations;
	}

    /**
     * Devuelve las cartas sobrantes.
     * @return lista de cartas que no forman combinación.
     */
	public List<Card> getLefts(){
		return lefts;
	}
}
