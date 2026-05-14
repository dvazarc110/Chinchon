package chinchon.table;

import chinchon.deck.Card;
import chinchon.player.HandCombiner;
import chinchon.player.IA;
import chinchon.player.IPlayer;
import chinchon.player.CombinationResult;

/**
 * Controla el turno de cada jugador, gestionando la decisión de robo, descarte
 * y comprobación de cierre o Chinchón. No hereda su comportamiento de ninguna
 * otra clase específica.
 *
 * @author Daniel Vázquez Arce
 * @version 1.0
 */
public class TurnController {

    /**
     * Almacena la ronda sobre la que se ejecutan los turnos.
     */
    private final AgentRound round;

    /**
     * Almacena el combinador usado para analizar manos.
     */
    private final HandCombiner combiner = new HandCombiner();

    /**
     * Construye un controlador de turnos asociado a una ronda.
     * @param round ronda que será controlada.
     */
    public TurnController(AgentRound round) {
        this.round = round;
    }

    /**
     * Ejecuta el turno completo de un jugador.
     * @param player jugador que realiza el turno.
     * @param turncont contador del turno actual.
     * @param playeramount cantidad de jugadores de la partida.
     * @param lastDesc última carta descartada anteriormente.
     * @return true si el jugador puede cerrar o tiene Chinchón, false en caso contrario.
     */
    public boolean playTurn(IPlayer player, int turncont, int playeramount, Card lastDesc) {

    	ConsoleInput input = new ConsoleInput();
    	int choice;
    	int turn = turncont;
    	String next = "";
        Card takenCard, lastDiscarded, discarded;
        CombinationResult r;

        lastDiscarded = round.seeDiscards();

        System.out.println("Carta de mazo de descartes: " + lastDiscarded);

        if (player instanceof IA ia) {
        	System.out.println("Mano actual: " + player.getHand());
            if (ia.wantDiscardLast(lastDiscarded)) {
            	if(round.getRepeat() > 1) {
            		if((playeramount / (round.getRepeat() - 1)) < 1) {
            			takenCard = round.takeDiscard();
            		}else {
            			takenCard = round.takeCard();
            			round.resetRepeat();
            		}
            	}else {
            		takenCard = round.takeDiscard();
            	}

            } else {
                takenCard = round.takeCard();
                round.resetRepeat();
            }

        } else {
        	System.out.println("Mano actual: " + player.getHand());

        	System.out.println("¿Quiere robar del mazo de descartes (1) o de la baraja (2)?");
        	choice = input.readIntInRange(1, 2);
        	if(choice == 1) {
        		takenCard = round.takeDiscard();
        	} else {
        		takenCard = round.takeCard(); // simplificado
        	}
        }

        player.getCard(takenCard);

        System.out.println("Mano: " + player.getHand());

        if (player instanceof IA ia) {
        	discarded = player.choiceDiscard(0, round.getDiscards());
        	if(lastDesc == discarded) {
        		round.addRepeat();
        	}
        } else {
        	System.out.println("¿Que carta de su mano quiere descartar (1 - 8)?");
        	choice = input.readIntInRange(1, 8);
        	discarded = player.choiceDiscard(choice-1, round.getDiscards());
        }

        player.deleteCard(discarded);
        round.toDiscard(discarded);

        System.out.println("Tras descarte: " + player.getHand());

        r = new HandCombiner().analise(player.getHand(), player);

        System.out.println("Introduzca cualquier tecla para terminar su turno: ");
    	input.cleanInput();
        return r.canClose(turncont, playeramount) || r.chinchon(turncont, playeramount);
    }

}
