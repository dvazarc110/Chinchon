package chinchon.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import chinchon.player.HandCombiner;
import chinchon.player.IPlayer;
import chinchon.player.CombinationResult;

/**
 * Coordina el flujo principal de la partida, gestionando rondas, turnos,
 * puntuaciones, eliminaciones y declaración del ganador. No hereda su
 * comportamiento de ninguna otra clase específica.
 *
 * @author Daniel Vázquez Arce
 * @version 1.0
 */
public class MotorGame {

    /**
     * Almacena el gestor general de la partida.
     */
    private final AgentGame agent = AgentGame.getInstance();

    /**
     * Almacena el gestor de la ronda actual.
     */
    private final AgentRound agentRound = new AgentRound();

    /**
     * Almacena el controlador encargado de ejecutar los turnos.
     */
    private final TurnController turnController;

    /**
     * Almacena el contador de turnos de la ronda actual.
     */
    private int turncount;

    /**
     * Almacena la puntuación acumulada de cada jugador.
     */
    private final Map<IPlayer, Integer> score = new HashMap<>();

    /**
     * Construye el motor principal de juego inicializando el controlador de turnos.
     */
    public MotorGame() {
        this.turnController = new TurnController(agentRound);
    }

    /**
     * Inicia y coordina el desarrollo completo de la partida.
     */
    public void start() {

    	ConsoleInput input = new ConsoleInput();
    	String next = "";
    	boolean roundEnded, hasClosed;

        initiateScoring();

        while (!endGame()) {

            System.out.println("\n🔵 NUEVA RONDA");

            agentRound.startRound(agent.getPlayers());
            agentRound.resetRepeat();

            roundEnded = false;

            this.turncount = 0;

            while (!roundEnded) {

                for (IPlayer player : agent.getPlayers()) {

                    System.out.println("\nTurno de: " + player.getName());

                    this.turncount++;

                    hasClosed = turnController.playTurn(player, turncount, (agent.getPlayers()).size(), agentRound.seeDiscards());

                    if (hasClosed) {

                        System.out.println("🏁 RONDA CERRADA por " + player.getName());

                        processEndRound(player);

                        roundEnded = true;
                        break;
                    }
                }
            }

            deletePlayers();
            showScores();

            if(!endGame()) {
            	System.out.println("Introduzca cualquier tecla para pasar a la siguiente ronda: ");
            	next = input.readString();
            }
            agentRound.endRound(agent.getPlayers());


        }

        stateWinner();
    }

    /**
     * Inicializa la puntuación de todos los jugadores a cero.
     */
    private void initiateScoring() {
        for (IPlayer p : agent.getPlayers()) {
            score.put(p, 0);
        }
    }

    /**
     * Procesa el final de una ronda sumando puntuaciones y comprobando Chinchón.
     * @param ganador jugador que ha cerrado la ronda.
     */
    private void processEndRound(IPlayer ganador) {

        HandCombiner combiner = new HandCombiner();
        CombinationResult r, rWinner;
        int points;

        for (IPlayer p : agent.getPlayers()) {

            r = combiner.analise(p.getHand(), p);

            points = r.sumScore();

            score.put(p, score.get(p) + points);

            System.out.println(p.getName() + " suma " + points + " puntos");
        }

        // bonus chinchón
        rWinner = combiner.analise(ganador.getHand(), ganador);

        if (rWinner.chinchon(turncount, (agent.getPlayers()).size())) {
            System.out.println("🔥 CHINCHÓN! Victoria directa de " + ganador.getName());
            score.put(ganador, 0);
            deleteEveryoneExcept(ganador);
        }
    }

    /**
     * Elimina de la partida a los jugadores que han alcanzado o superado la puntuación límite.
     */
    private void deletePlayers() {

        Iterator<IPlayer> it = agent.getPlayers().iterator();
        IPlayer p;

        while (it.hasNext()) {

            p = it.next();

            if (score.get(p) >= 100) {

                System.out.println("❌ Eliminado: " + p.getName());

                it.remove();
                score.remove(p);
            }
        }
    }

    /**
     * Comprueba si la partida ha terminado.
     * @return true si queda uno o ningún jugador, false en caso contrario.
     */
    private boolean endGame() {
        return agent.getPlayers().size() <= 1;
    }

    /**
     * Muestra por consola el ganador final de la partida.
     */
    private void stateWinner() {

        IPlayer winner = agent.getPlayers().get(0);

        System.out.println("\n🏆 GANADOR FINAL: " + winner.getName());
    }

    /**
     * Muestra por consola las puntuaciones actuales de todos los jugadores.
     */
    private void showScores() {

        System.out.println("\n📊 PUNTUACIONES:");

        for (Map.Entry<IPlayer, Integer> e : score.entrySet()) {
            System.out.println(e.getKey().getName() + ": " + e.getValue());
        }
    }

    /**
     * Elimina de la partida a todos los jugadores excepto al ganador indicado.
     * @param ganador jugador que debe permanecer en la partida.
     */
    private void deleteEveryoneExcept(IPlayer ganador) {

        List<IPlayer> copy = new ArrayList<>(agent.getPlayers());

        for (IPlayer p : copy) {
            if (!p.equals(ganador)) {
                agent.getPlayers().remove(p);
            }
        }
    }
}
