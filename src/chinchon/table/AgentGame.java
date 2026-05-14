package chinchon.table;

import java.util.*;

import chinchon.player.IPlayer;

/**
 * Gestiona el estado general de la partida, manteniendo la lista de jugadores
 * y sus puntuaciones iniciales mediante una instancia única. No hereda su
 * comportamiento de ninguna otra clase específica.
 *
 * @author Daniel Vázquez Arce
 * @version 1.0
 */
public class AgentGame {

    /**
     * Almacena la única instancia de AgentGame.
     */
    private static AgentGame instance;

    /**
     * Almacena los jugadores que participan en la partida.
     */
    private List<IPlayer> players = new ArrayList<>();

    /**
     * Almacena la puntuación asociada a cada jugador.
     */
    private Map<IPlayer, Integer> scoring = new HashMap<>();

    /**
     * Construye el gestor de partida de forma privada para aplicar Singleton.
     */
    private AgentGame() {}

    /**
     * Devuelve la instancia única del gestor de partida.
     * @return instancia única de AgentGame.
     */
    public static AgentGame getInstance() {
        if (instance == null) {
            instance = new AgentGame();
        }
        return instance;
    }

    /**
     * Añade un jugador a la partida e inicializa su puntuación.
     * @param p jugador que se va a añadir.
     */
    public void addPlayers(IPlayer p) {
        players.add(p);
        scoring.put(p, 0);
    }

    /**
     * Devuelve la lista de jugadores de la partida.
     * @return jugadores actuales de la partida.
     */
    public List<IPlayer> getPlayers() {
        return players;
    }
}
