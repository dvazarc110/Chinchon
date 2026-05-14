package chinchon.table;

import chinchon.player.PlayerFactory;

/**
 * Muestra y gestiona el menú inicial de configuración de jugadores, creando
 * jugadores humanos o IA mediante PlayerFactory. No hereda su comportamiento
 * de ninguna otra clase específica.
 *
 * @author Daniel Vázquez Arce
 * @version 1.0
 */

public class Menu {

    /**
     * Muestra el menú de configuración inicial y crea los jugadores.
     */
    public static void show() {

    	ConsoleInput input = new ConsoleInput();
        int kind, n;
        String name = "";
        AgentGame gp = AgentGame.getInstance();

        System.out.println("Número de jugadores (2-4): ");
        n = input.readInt();


        for (int i = 0; i < n; i++) {

            System.out.println("Jugador " + (i+1) + ", tipo humano(1) o ia(2): ");
            kind = input.readIntInRange(1, 2);

            if(kind == 1) {
            	System.out.println("Nombre: ");
            	name = input.readString();
            }else {
            	name = String.format("ia%d", i+1);
            }

            gp.addPlayers(PlayerFactory.createPlayer(kind, name));
        }
    }
}
