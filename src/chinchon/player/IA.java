package chinchon.player;

import java.util.ArrayList;
import java.util.List;

import chinchon.deck.Card;

/**
 * Representa a un jugador controlado por inteligencia artificial, encargado de
 * decidir qué carta robar y cuál descartar mediante análisis de combinaciones.
 * Hereda su comportamiento base de la clase Player.
 *
 * @author Daniel Vázquez Arce
 * @version 1.0
 */
public class IA extends Player {

    /**
     * Almacena el evaluador usado para valorar manos y cartas útiles.
     */
    private final HandAssesor assesor = new HandAssesor();

    /**
     * Construye un jugador controlado por inteligencia artificial.
     * @param name nombre asignado al jugador IA.
     */
    public IA(String name) {
        super(name);
    }

    /**
     * Elige la mejor carta que debe descartar la inteligencia artificial.
     * @param sel posición seleccionada, no usada directamente por la IA.
     * @param discard cartas disponibles en el montón de descartes.
     * @return carta elegida para descartar.
     */
    @Override
    public Card choiceDiscard(int sel, List<Card> discard) {

        Card best = null;
        int bestPunt = 0, bestGroup = 0, bestCards, actPunt, actGroup, actCards;
        int count = 0;
        double bestValue = 0, actValue;
        List<Card> simulation = new ArrayList<>(hand);
        List<Card> simulation2;
        List<Card> simulation3;
        CombinationResult r;
        PosibCombinationResult pr, aux;
        
        r = new HandCombiner().analise(simulation, this);
        
        if((r.getCombinations().size() == 1) && (r.getLefts().size() == 0)) {
        	for(Card c : hand) {
        		if(best == null) {
        			best = c;
        		}else if(best.getValue() < c.getValue()){
        			best = c;
        		}
        	}
        }else if((r.getCombinations().size() == 2) && (r.getLefts().size() == 0)) {
        	for(List<Card> group : r.getCombinations()) {
        		if(group.size() >= 4) {
        			for(Card c : hand) {
                		if(best == null) {
                			best = c;
                		}else if(best.getValue() < c.getValue()){
                			best = c;
                		}
                	}
        		}
        	}
        }else if(r.getLefts().size() >= 1) {
        	pr = new PosibHandCombiner().analizar(r.getLefts(), this);
        	if(pr.getLefts().size() >= 1) {
        		for(Card c : pr.getLefts()) {
        			if(best == null) {
        				best = c;
            		}else if(best.getValue() < c.getValue()){
            			best = c;
            		}
        		}
        	}else {
        		for(Card c : r.getLefts()) {
        			simulation2 = new ArrayList<>(r.getLefts());
        			simulation2.remove(c);
        			aux = new PosibHandCombiner().analizar(simulation2, this);
        			actPunt = assesor.countPunt(simulation2, this);
        			actGroup = aux.getCombinations().size();
        			if(actGroup == 0) {
        				actGroup = 1;
        			}
        			actValue = actPunt/actGroup;
        			if(count == 0) {
        				bestPunt = actPunt;
            			bestGroup = actGroup;
            			bestValue = actValue;
            			best = c;
        			}else {
        				if(actValue < bestValue) {
        					bestPunt = actPunt;
                			bestGroup = actGroup;
                			bestValue = actValue;
                			best = c;
        				}else if(actValue == bestValue) {
        					if(actGroup > bestGroup) {
        						bestPunt = actPunt;
                    			bestGroup = actGroup;
                    			bestValue = actValue;
                    			best = c;
        					}else if(actGroup == bestGroup) {
        						if(c.getValue() > best.getValue()) {
									bestPunt = actPunt;
		                			bestGroup = actGroup;
		                			bestValue = actValue;
		                			best = c;
								}else if(c.getValue() == best.getValue()) {
		                			simulation3 = new ArrayList<>(r.getLefts());
		                			simulation3.remove(best);
		                			
		                			bestCards = assesor.usefullCards(simulation3, discard, this);
		                			actCards = assesor.usefullCards(simulation2, discard, this);
        						
		                			if(actCards > bestCards) {
		                				bestPunt = actPunt;
		                				bestGroup = actGroup;
		                				bestValue = actValue;
		                				best = c;
		                			}
								}
        						
        					}
        				}
        			}
        			
        			count++;
        		}
        	}
        }

        return best;
    }

    /**
     * Decide si la IA quiere robar la última carta del montón de descartes.
     * @param lastDiscard última carta visible del montón de descartes.
     * @return true si la IA quiere tomar la carta descartada, false en caso contrario.
     */
    public boolean wantDiscardLast(Card lastDiscard) {

        List<Card> simulation = new ArrayList<>(getHand());
        List<Card> aux;
        List<Card> aux2;
        List<Card> aux3;
        Card worst = null;
        CombinationResult r;
        boolean result = false;
        
        simulation.add(lastDiscard);
        r = new HandCombiner().analise(simulation, this);
        
        if(r.getLefts().size() <= 5) {
        	for(List<Card> combi : r.getCombinations()) {
        		if(combi.contains(lastDiscard)) {
        			result = true;
        		}
        	}
        }
        
        if(result == false) {
        	aux = new ArrayList<>(r.getLefts());
        	aux.remove(lastDiscard);
        	for(Card c : aux) {
        		if(c.getValue() == lastDiscard.getValue()) {
        			result = true;
        		}else if(((c.getNum().ordinal())-1 == lastDiscard.getNum().ordinal()) || ((c.getNum().ordinal())-2 == lastDiscard.getNum().ordinal())) {
        			if(c.getSuit().equals(lastDiscard.getSuit())) {
        				result = true;
        			}
        		}else if(((c.getNum().ordinal())+1 == lastDiscard.getNum().ordinal()) || ((c.getNum().ordinal())+2 == lastDiscard.getNum().ordinal())) {
        			if(c.getSuit().equals(lastDiscard.getSuit())) {
        				result = true;
        			}
        		}
        	}
        }
        
        return result;
    }
}
