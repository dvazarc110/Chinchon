package chinchon.player;

/**
 * Crea instancias de jugadores humanos o controlados por IA según el tipo
 * solicitado. No hereda su comportamiento de ninguna otra clase específica.
 *
 * @author Daniel Vázquez Arce
 * @version 1.0
 */
public class PlayerFactory {

    /**
     * Crea un jugador según el tipo indicado.
     * @param kind tipo de jugador que se quiere crear.
     * @param name nombre asignado al jugador.
     * @return jugador creado.
     */
    public static IPlayer createPlayer(int kind, String name) {

        if (kind == 1) {
            return new PlayerHuman(name);
        } else {
        	return new IA(name);
        }
    }
}
