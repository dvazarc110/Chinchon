
# Proyecto Chinchón

## Índice

- [Descripción general](#descripción-general)
- [Reglas del juego](#reglas-del-juego)
  - [Baraja y reparto](#baraja-y-reparto)
  - [Jugadores](#jugadores)
  - [Objetivo principal](#objetivo-principal)
  - [Cierre de ronda](#cierre-de-ronda)
  - [Puntuación y eliminación](#puntuación-y-eliminación)
  - [Chinchón](#chinchón)
- [Documentación técnica](#documentación-técnica)
  - [Tecnologías utilizadas](#tecnologías-utilizadas)
  - [Arquitectura del proyecto](#arquitectura-del-proyecto)
  - [Estructura de directorios](#estructura-de-directorios)
  - [Descripción de paquetes](#descripción-de-paquetes)
  - [Descripción de clases](#descripción-de-clases)
- [Tests](#tests)
  - [Herramienta utilizada](#herramienta-utilizada)
  - [Enfoque de pruebas](#enfoque-de-pruebas)
  - [Estructura de los tests](#estructura-de-los-tests)
  - [Descripción de los tests](#descripción-de-los-tests)
- [Documentación adicional](#documentación-adicional)

---

# Descripción general

**Chinchón** es un proyecto desarrollado en **Java** que simula el juego de cartas tradicional del mismo nombre utilizando una **Baraja Española de 40 cartas**. El programa permite disputar partidas entre varios jugadores, que pueden ser tanto jugadores humanos como jugadores controlados por la máquina mediante una IA básica.

El objetivo principal del juego es formar combinaciones de cartas válidas, reducir la puntuación acumulada y evitar llegar a los **100 puntos**, ya que el jugador que alcanza dicha puntuación queda eliminado. La partida continúa hasta que solo queda un jugador sin eliminar, que será declarado ganador.

Además, el juego contempla la jugada especial de **Chinchón**, que permite ganar automáticamente la partida si un jugador consigue formar una única escalera completa con todas sus cartas.

---

# Reglas del juego

## Baraja y reparto

El juego utiliza una única **Baraja Española de 40 cartas**, compuesta por cuatro palos:

- Oros
- Copas
- Espadas
- Bastos

Cada palo contiene las siguientes cartas:

- 1
- 2
- 3
- 4
- 5
- 6
- 7
- Sota
- Caballo
- Rey

Al comenzar cada ronda:

1. Se crea una única baraja.
2. La baraja se mezcla.
3. Se reparten **7 cartas a cada jugador**.
4. Las cartas restantes forman el **mazo de robo**.
5. Se toma una carta del mazo de robo y se coloca visible en el **mazo de descartes** para iniciar la partida.

De esta forma, la partida siempre se juega con una sola baraja, respetando la forma tradicional del Chinchón.

---

## Jugadores

La partida admite:

- **Mínimo:** 2 jugadores.
- **Máximo:** 4 jugadores.

Los jugadores pueden ser de dos tipos:

- **Humanos**, que toman decisiones introduciendo datos por consola.
- **Máquinas (IAs)**, que toman decisiones automáticamente según la lógica implementada.

Durante la partida, todos los jugadores participan por turnos. En cada turno, un jugador puede robar carta del mazo o tomar la última carta del descarte, y posteriormente debe descartar una carta.

---

## Objetivo principal

El objetivo principal es formar agrupaciones válidas de cartas. Las combinaciones permitidas son:

### Tríos o cuartetos

Son agrupaciones de **3 o más cartas con el mismo número**, independientemente del palo.

Ejemplo:

- 5 de Oros
- 5 de Copas
- 5 de Espadas

También puede formarse un cuarteto si se reúnen las cuatro cartas del mismo valor.

---

### Escaleras

Son agrupaciones de **3 o más cartas consecutivas del mismo palo**.

Ejemplo:

- 3 de Copas
- 4 de Copas
- 5 de Copas

Para que una escalera sea válida, todas las cartas deben pertenecer al mismo palo y estar ordenadas de forma consecutiva según su valor.

---

## Cierre de ronda

Un jugador puede cerrar la ronda únicamente si cumple determinadas condiciones.

Antes de descartarse en su turno, debe comprobar cuántas cartas de su mano no forman parte de ninguna combinación válida.

### Condiciones de cierre

- Si tiene **1 carta sobrante**, puede cerrar descartándose de esa carta siempre que se cumplan las condiciones de puntuación.
- Si tiene **2 cartas sobrantes**, debe intentar descartarse de la carta con el valor más alto y quedarse con una carta cuyo valor sea **5 o inferior**.
- Si la carta sobrante con la que se queda tiene valor superior a 5, no puede cerrar.
- Si todas sus cartas combinan entre ellas, puede cerrar descartándose de una carta que no rompa las combinaciones principales.
- No se puede cerrar si quedan demasiadas cartas sin combinar.

Además, para que el cierre sea justo, **no está permitido cerrar la ronda hasta que todos los jugadores activos hayan jugado al menos su primer turno** en esa ronda.

Esto impide que un jugador pueda cerrar antes de que el resto haya tenido oportunidad de participar.

---

## Puntuación y eliminación

Cuando un jugador cierra la ronda, el resto de jugadores debe calcular la puntuación de las cartas que no forman parte de ninguna combinación válida.

Cada jugador suma a su puntuación total el valor de las cartas que le hayan quedado sin combinar.

### Reglas de puntuación

- Las cartas no combinadas suman su valor correspondiente.
- Si un jugador no tiene cartas sin combinar, es decir, todas sus cartas forman combinaciones válidas, se le restan **10 puntos** de su puntuación total.
- El primer jugador que llegue a **100 puntos** queda eliminado.
- La partida continúa con los jugadores restantes.
- El ganador será el último jugador que no haya sido eliminado.

Este sistema hace que el objetivo no sea solo cerrar rondas, sino también reducir al máximo las cartas sobrantes en cada mano.

---

## Chinchón

Existe una jugada especial llamada **Chinchón**.

Un jugador consigue Chinchón cuando todas sus cartas forman **una única escalera consecutiva del mismo palo**.

Ejemplo:

- 1 de Oros
- 2 de Oros
- 3 de Oros
- 4 de Oros
- 5 de Oros
- 6 de Oros
- 7 de Oros

Si un jugador cierra la ronda formando Chinchón, gana automáticamente la partida y elimina al resto de jugadores que sigan activos.

Al igual que ocurre con el cierre normal de ronda, esta condición solo puede aplicarse cuando ya todos los jugadores activos han jugado al menos un turno en la ronda.

---

# Documentación técnica

## Tecnologías utilizadas

El proyecto ha sido desarrollado utilizando:

- **Lenguaje:** Java
- **Versión del SDK:** Java 25
- **Entorno compatible:** Eclipse / IntelliJ IDEA
- **Sistema de pruebas:** JUnit 5 / Jupiter
- **Documentación:** Javadoc
- **Tipo de aplicación:** Aplicación de consola

La estructura del proyecto está organizada siguiendo una separación por responsabilidades, agrupando las clases en paquetes según el área funcional a la que pertenecen.

---

## Arquitectura del proyecto

El proyecto sigue una arquitectura orientada a objetos dividida principalmente en tres capas o áreas lógicas:

1. **Capa de cartas y mazos**
   - Gestiona la baraja, las cartas, los palos, los valores numéricos, el mazo de robo y el mazo de descarte.

2. **Capa de jugadores y lógica de mano**
   - Gestiona los jugadores humanos, las IAs, la evaluación de combinaciones, la selección de descartes y el cálculo de puntuaciones.

3. **Capa de mesa o flujo de partida**
   - Controla el inicio de la partida, las rondas, los turnos, el menú, la entrada por consola y la coordinación general del juego.

Esta organización permite separar claramente:

- La representación del dominio del juego.
- La lógica de decisión de los jugadores.
- La lógica de control de la partida.
- La interacción con el usuario.

---

## Estructura de directorios

La estructura principal del proyecto es la siguiente:

```text
Chinchon/
├── src/
│   └── chinchon/
│       ├── deck/
│       │   ├── Card.java
│       │   ├── Deck.java
│       │   ├── DiscardPile.java
│       │   ├── DrawPile.java
│       │   ├── NumCard.java
│       │   └── Suit.java
│       │
│       ├── player/
│       │   ├── CombinationResult.java
│       │   ├── HandAssesor.java
│       │   ├── HandCombiner.java
│       │   ├── IA.java
│       │   ├── IPlayer.java
│       │   ├── Player.java
│       │   ├── PlayerFactory.java
│       │   ├── PlayerHuman.java
│       │   ├── PosibCombinationResult.java
│       │   └── PosibHandCombiner.java
│       │
│       └── table/
│           ├── AgentGame.java
│           ├── AgentRound.java
│           ├── ConsoleInput.java
│           ├── Main.java
│           ├── Menu.java
│           ├── MotorGame.java
│           └── TurnController.java
│
├── test/
│   └── chinchon/
│       ├── deck/
│       │   └── test/
│       │       └── Deck_Test.java
│       │
│       └── player/
│           └── test/
│               ├── CombinationResult_Test.java
│               ├── HandCombiner_Test.java
│               └── IA_Test.java
│
├── doc/
│   └── Documentación generada con Javadoc
│
├── UML/
│   └── ChinchonUML.png
│
└── bin/
    └── Archivos compilados
```
---

## Descripción de paquetes

## [Paquete `chinchon.deck`](./src/chinchon/deck/)

Este paquete contiene las clases relacionadas con la baraja y los distintos montones de cartas utilizados durante la partida.

Se encarga de representar:

- Las cartas individuales.
- Los palos de la baraja.
- Los valores de las cartas.
- La baraja completa.
- El mazo de robo.
- El mazo de descarte.

Es el paquete base del modelo de cartas.

---

## [Paquete `chinchon.player`](./src/chinchon/player/)

Este paquete contiene las clases relacionadas con los jugadores y con el análisis de sus manos.

Incluye:

- La interfaz común de jugador.
- La clase base de jugador.
- Jugadores humanos.
- Jugadores IA.
- Fábrica de jugadores.
- Análisis de combinaciones.
- Resultados de combinaciones.
- Evaluación de puntuaciones.
- Cálculo de posibles combinaciones.

Es uno de los paquetes más importantes del proyecto, ya que contiene gran parte de la lógica del Chinchón.

---

## [Paquete `chinchon.table`](./src/chinchon/table/)

Este paquete contiene las clases encargadas de coordinar la partida.

Gestiona:

- El inicio del programa.
- El menú principal.
- La creación de partidas.
- El desarrollo de rondas.
- El control de turnos.
- La entrada de datos por consola.
- El estado general de la mesa.

Actúa como capa de control de la aplicación.

---

# Descripción de clases

## [Paquete `chinchon.deck`](./src/chinchon/deck/)

### [`Card`](./src/chinchon/deck/Card.java)

Representa una carta de la Baraja Española.

Cada carta está compuesta por:

- Un palo, representado mediante `Suit`.
- Un número o figura, representado mediante `NumCard`.

Métodos principales:

- `Card(Suit, NumCard)`: constructor que crea una carta con un palo y un valor.
- `getSuit()`: devuelve el palo de la carta.
- `getNum()`: devuelve el número o figura de la carta.
- `getValue()`: devuelve el valor numérico asociado a la carta.
- `toString()`: devuelve una representación textual de la carta.

---

### [`Deck`](./src/chinchon/deck/Deck.java)

Representa la baraja completa del juego.

Se encarga de crear las 40 cartas de la Baraja Española, mezclarlas y repartirlas.

Métodos principales:

- `Deck()`: constructor de la baraja.
- `initialize()`: inicializa la baraja con todas las cartas.
- `shuffleDeck()`: mezcla las cartas.
- `serve(int)`: reparte un número concreto de cartas.
- `getDeck()`: devuelve las cartas que contiene la baraja.
- `isEmpty()`: comprueba si la baraja está vacía.

---

### [`DiscardPile`](./src/chinchon/deck/DiscardPile.java)

Representa el mazo de descartes.

Permite añadir cartas descartadas, consultar la última carta, tomar la última carta y vaciar el montón conservando la carta superior.

Métodos principales:

- `discard(Card)`: añade una carta al descarte.
- `seeLast()`: consulta la última carta descartada sin retirarla.
- `takeLast()`: retira y devuelve la última carta descartada.
- `emptyExceptLast()`: vacía el descarte excepto la última carta.
- `deleteCards()`: elimina todas las cartas del descarte.
- `getDiscarded()`: devuelve una lista con las cartas descartadas.

---

### [`DrawPile`](./src/chinchon/deck/DrawPile.java)

Representa el mazo de robo.

Contiene las cartas que quedan disponibles para que los jugadores roben durante la ronda.

Métodos principales:

- `DrawPile()`: constructor del mazo de robo.
- `load(List<Card>)`: carga cartas en el mazo.
- `draw()`: roba una carta del mazo.
- `insertCards(List<Card>)`: inserta cartas en el mazo.
- `deleteCards()`: elimina las cartas del mazo.
- `isEmpty()`: comprueba si el mazo está vacío.

---

### [`NumCard`](./src/chinchon/deck/NumCard.java)

Enumeración que representa los valores de las cartas.

Valores principales:

- `UNO`
- `DOS`
- `TRES`
- `CUATRO`
- `CINCO`
- `SEIS`
- `SIETE`
- `SOTA`
- `CABALLO`
- `REY`

Métodos principales:

- `getValue()`: devuelve el valor numérico asociado a cada carta.
- `values()`: devuelve todos los valores posibles de la enumeración.
- `valueOf(String)`: devuelve un valor de la enumeración a partir de su nombre.

---

### [`Suit`](./src/chinchon/deck/Suit.java)

Enumeración que representa los palos de la Baraja Española.

Valores principales:

- `ORO`
- `COPA`
- `ESPADAS`
- `BASTOS`

Métodos principales:

- `getSuit()`: devuelve la representación del palo.
- `values()`: devuelve todos los palos posibles.
- `valueOf(String)`: devuelve un palo a partir de su nombre.

---

## [Paquete `chinchon.player`](./src/chinchon/player/)

### [`IPlayer`](./src/chinchon/player/IPlayer.java)

Interfaz que define el comportamiento común que debe tener cualquier jugador del juego.

Permite tratar de forma uniforme a jugadores humanos y jugadores controlados por IA.

Métodos principales:

- `getName()`: devuelve el nombre del jugador.
- `getHand()`: devuelve la mano del jugador.
- `getCard(Card)`: añade una carta a la mano.
- `deleteCard(Card)`: elimina una carta de la mano.
- `choiceDiscard(int, List<Card>)`: selecciona una carta para descartar.

---

### [`Player`](./src/chinchon/player/Player.java)

Clase base que implementa la interfaz `IPlayer`.

Contiene los atributos y comportamientos comunes de todos los jugadores, como el nombre, la mano y las operaciones básicas de añadir o eliminar cartas.

Métodos principales:

- `Player(String)`: constructor del jugador.
- `getName()`: devuelve el nombre del jugador.
- `getHand()`: devuelve la mano actual.
- `getCard(Card)`: añade una carta a la mano.
- `deleteCard(Card)`: elimina una carta de la mano.
- `choiceDiscard(int, List<Card>)`: define la selección de descarte.

---

### [`PlayerHuman`](./src/chinchon/player/PlayerHuman.java)

Representa a un jugador humano.

Extiende la clase `Player` y permite que el usuario interactúe con el juego mediante consola.

Métodos principales:

- `PlayerHuman(String)`: constructor del jugador humano.
- `choiceDiscard(int, List<Card>)`: permite escoger qué carta descartar.
- `showHand()`: muestra la mano del jugador.

---

### [`IA`](./src/chinchon/player/IA.java)

Representa a un jugador controlado por la máquina.

La IA toma decisiones automáticamente, evaluando si una carta del descarte puede ser útil y qué carta conviene descartar según las combinaciones de su mano.

Métodos principales:

- `IA(String)`: constructor de la IA.
- `wantDiscardLast(Card)`: decide si la IA quiere tomar la última carta del descarte.
- `choiceDiscard(int, List<Card>)`: decide qué carta descartar en su turno.

---

### [`PlayerFactory`](./src/chinchon/player/PlayerFactory.java)

Clase encargada de crear jugadores.

Aplica un patrón de tipo fábrica para centralizar la creación de jugadores humanos o jugadores IA.

Métodos principales:

- `PlayerFactory()`: constructor de la fábrica.
- `createPlayer(int, String)`: crea un jugador según el tipo indicado y el nombre recibido.

---

### [`HandCombiner`](./src/chinchon/player/HandCombiner.java)

Clase encargada de analizar la mano de un jugador y detectar combinaciones válidas.

Busca tríos, cuartetos y escaleras, separando las cartas que forman combinaciones de las cartas sobrantes.

Métodos principales:

- `HandCombiner()`: constructor.
- `analise(List<Card>, IPlayer)`: analiza una mano completa y devuelve el resultado.
- `checkCombinations(List<Card>)`: comprueba las combinaciones existentes.
- `analiseCombinations(List<Card>, Card, List<Card>)`: analiza combinaciones teniendo en cuenta cartas concretas.
- `selectCombination(List<Card>, List<Card>, Card)`: selecciona una combinación entre varias posibles.
- `removeExtraCard(List<Card>, List<Card>)`: elimina o separa cartas que no forman parte de la combinación principal.

---

### [`CombinationResult`](./src/chinchon/player/CombinationResult.java)

Representa el resultado del análisis de una mano.

Contiene:

- Las cartas que forman combinaciones.
- Las cartas sobrantes.
- La lógica para saber si se puede cerrar.
- El cálculo de puntuación.
- La comprobación de Chinchón.

Métodos principales:

- `CombinationResult(List<Card>, List<Card>)`: constructor del resultado.
- `getCombinations()`: devuelve las combinaciones encontradas.
- `getLefts()`: devuelve las cartas sobrantes.
- `canClose(int, int)`: comprueba si el jugador puede cerrar la ronda.
- `chinchon(int, int)`: comprueba si la mano forma Chinchón.
- `sumScore()`: calcula la puntuación de las cartas sobrantes.

---

### [`HandAssesor`](./src/chinchon/player/HandAssesor.java)

Clase encargada de valorar la utilidad de las cartas y calcular puntuaciones relacionadas con la mano.

Métodos principales:

- `HandAssesor()`: constructor.
- `countPunt(List<Card>, IPlayer)`: calcula la puntuación de las cartas no combinadas.
- `usefullCards(List<Card>, List<Card>, IPlayer)`: determina qué cartas pueden ser útiles para el jugador.

---

### [`PosibHandCombiner`](./src/chinchon/player/PosibHandCombiner.java)

Clase similar a `HandCombiner`, orientada a calcular posibles combinaciones futuras.

Se utiliza para analizar manos desde un punto de vista más estratégico, especialmente en decisiones de IA.

Métodos principales:

- `PosibHandCombiner()`: constructor.
- `analizar(List<Card>, IPlayer)`: analiza posibles combinaciones de una mano.

---

### [`PosibCombinationResult`](./src/chinchon/player/PosibCombinationResult.java)

Representa el resultado de un análisis de posibles combinaciones.

Permite comprobar si una mano tiene opciones de cierre o si existen combinaciones útiles en desarrollo.

Métodos principales:

- `PosibCombinationResult(List<Card>, List<Card>)`: constructor.
- `getCombinations()`: devuelve combinaciones detectadas.
- `getLefts()`: devuelve cartas sobrantes.
- `getPosibCombinations()`: devuelve posibles combinaciones.
- `canClose()`: comprueba si existe posibilidad de cierre.
- `chinchon()`: comprueba si existe posibilidad de Chinchón.
- `somScore()`: calcula la puntuación estimada.

---

## [Paquete `chinchon.table`](./src/chinchon/table/)

### [`Main`](./src/chinchon/table/Main.java)

Clase principal del programa.

Contiene el punto de entrada de la aplicación.

Métodos principales:

- `Main()`: constructor.
- `main(String[])`: método principal que inicia la ejecución del programa.

---

### [`Menu`](./src/chinchon/table/Menu.java)

Clase encargada de mostrar el menú principal del juego.

Permite iniciar la partida y gestionar las opciones básicas disponibles para el usuario.

Métodos principales:

- `Menu()`: constructor.
- `show()`: muestra el menú principal.

---

### [`MotorGame`](./src/chinchon/table/MotorGame.java)

Clase encargada de arrancar y coordinar el flujo general de la partida.

Métodos principales:

- `MotorGame()`: constructor.
- `start()`: inicia el motor del juego.

---

### [`AgentGame`](./src/chinchon/table/AgentGame.java)

Clase que representa el agente o controlador general de la partida.

Gestiona la lista de jugadores y el estado global del juego. Funciona como punto de coordinación entre los jugadores y las rondas.

Métodos principales:

- `getInstance()`: devuelve la instancia del agente de juego.
- `addPlayers(IPlayer)`: añade jugadores a la partida.
- `getPlayers()`: devuelve la lista de jugadores.

---

### [`AgentRound`](./src/chinchon/table/AgentRound.java)

Clase encargada de gestionar una ronda concreta.

Controla el reparto inicial, el mazo de robo, el mazo de descartes, el final de ronda y las operaciones de tomar o descartar cartas.

Métodos principales:

- `AgentRound()`: constructor.
- `startRound(List<IPlayer>)`: inicia una nueva ronda.
- `endRound(List<IPlayer>)`: finaliza la ronda.
- `takeCard()`: toma una carta del mazo de robo.
- `takeDiscard()`: toma la última carta del descarte.
- `toDiscard(Card)`: envía una carta al descarte.
- `seeDiscards()`: muestra o consulta los descartes.
- `getDiscards()`: devuelve las cartas descartadas.
- `getRepeat()`: devuelve el contador de repetición.
- `addRepeat()`: incrementa el contador de repetición.
- `resetRepeat()`: reinicia el contador de repetición.

---

### [`TurnController`](./src/chinchon/table/TurnController.java)

Clase encargada de controlar el turno de cada jugador.

Coordina las acciones que puede realizar un jugador durante su turno, como robar, tomar del descarte, descartar y comprobar si puede cerrar.

Métodos principales:

- `TurnController(AgentRound)`: constructor que recibe la ronda actual.
- `playTurn(IPlayer, int, int, Card)`: ejecuta el turno de un jugador.

---

### [`ConsoleInput`](./src/chinchon/table/ConsoleInput.java)

Clase auxiliar para gestionar la entrada de datos por consola.

Centraliza la lectura y validación de datos introducidos por el usuario.

Métodos principales:

- `ConsoleInput()`: constructor por defecto.
- `ConsoleInput(Scanner)`: constructor que permite usar un `Scanner` concreto.
- `readInt()`: lee un número entero.
- `readIntInRange(int, int)`: lee un entero dentro de un rango determinado.
- `readString()`: lee una cadena de texto.
- `readString(int)`: lee una cadena de texto con una longitud o condición determinada.
- `cleanInput()`: limpia la entrada.

---

# [Tests](./test/chinchon/)

## Herramienta utilizada

Los tests del proyecto se han realizado utilizando **JUnit 5**, también conocido como **JUnit Jupiter**.

JUnit 5 permite crear pruebas unitarias modernas mediante anotaciones como:

- `@Test`
- `@ParameterizedTest`
- `@MethodSource`

En este proyecto se utilizan principalmente pruebas parametrizadas, lo que permite ejecutar el mismo test con distintos conjuntos de datos.

---

## Enfoque de pruebas

En el proyecto se han aplicado principalmente dos enfoques de pruebas:

## Pruebas de caja negra

Las pruebas de caja negra se centran en comprobar el comportamiento observable de una clase o método sin depender directamente de su implementación interna.

Se aplican, por ejemplo, al comprobar:

- Si una mano puede cerrar o no.
- Si una IA acepta o rechaza una carta del descarte.
- Si una IA descarta la carta esperada.
- Si una baraja se inicializa correctamente.
- Si las combinaciones detectadas son correctas.

Estas pruebas verifican entradas y salidas esperadas según las reglas del juego.

---

## Pruebas de caja blanca

También se han aplicado pruebas de caja blanca en aquellos casos donde se conoce la lógica interna del programa y se diseñan casos concretos para recorrer ramas importantes.

Se aplican, por ejemplo, al probar:

- Casos en los que todavía no han jugado todos los jugadores.
- Casos donde se puede cerrar con una carta sobrante de valor menor o igual a 5.
- Casos donde no se puede cerrar con una carta sobrante superior a 5.
- Casos donde la IA detecta que una carta completa un trío.
- Casos donde la IA detecta que una carta completa una escalera.
- Casos donde una carta no aporta utilidad y debe rechazarse.

Este enfoque ayuda a comprobar condiciones límite y ramas específicas de la lógica del juego.

---

## Estructura de los tests

Los tests se encuentran dentro de la [carpeta `test`](./test/chinchon/), manteniendo una estructura similar a la de `src`.

```text
test/
└── chinchon/
    ├── deck/
    │   └── test/
    │       └── Deck_Test.java
    │
    └── player/
        └── test/
            ├── CombinationResult_Test.java
            ├── HandCombiner_Test.java
            └── IA_Test.java
```
Esta organización permite separar los tests según el paquete o módulo funcional que se está comprobando.

---

## Descripción de los tests

## [`Deck_Test`](./test/chinchon/deck/test/Deck_Test.java)

Este test se centra en comprobar el [correcto funcionamiento de la baraja.](./test/chinchon/deck/test/Deck_Test.png)

Entre los aspectos que se pueden verificar se encuentran:

- Que la baraja se inicialice con 40 cartas.
- Que contenga cartas de todos los palos.
- Que contenga los valores propios de la Baraja Española.
- Que el reparto de cartas reduzca correctamente el número de cartas disponibles.
- Que la baraja pueda mezclarse sin perder ni duplicar cartas.

Es un test enfocado principalmente al paquete [`chinchon.deck`](./src/chinchon/deck/).

---

## [`CombinationResult_Test`](./test/chinchon/player/test/CombinationResult_Test.java)

Este test [comprueba situaciones relacionadas con el cierre de ronda.](./test/chinchon/player/test/CombinationResult_Test.png)

Utiliza pruebas parametrizadas para validar diferentes manos y contextos de turno.

Casos destacados:

- No se puede cerrar si todavía no han jugado todos los jugadores.
- Se puede cerrar cuando todos los jugadores ya han tenido al menos un turno.
- No se puede cerrar en una partida de cuatro jugadores si aún no se ha superado la primera vuelta.
- Se puede cerrar con una carta sobrante cuyo valor sea menor o igual que 5.
- No se puede cerrar con una carta sobrante de valor mayor que 5.
- No se puede cerrar con más de una carta sobrante.

Este test valida una parte fundamental de las reglas del juego, ya que el cierre de ronda afecta directamente a la puntuación y al avance de la partida.

---

## [`HandCombiner_Test`](./test/chinchon/player/test/HandCombiner_Test.java)

Este test se centra en [comprobar la detección de combinaciones en una mano.](./test/chinchon/player/test/HandCombiner_Test.png)

Evalúa si el sistema identifica correctamente:

- Tríos.
- Cuartetos.
- Escaleras.
- Cartas sobrantes.
- Combinaciones alternativas.
- Manos completamente combinadas.
- Manos con cartas que no encajan en ningún grupo.

Su objetivo es garantizar que la lógica encargada de analizar la mano sea coherente con las reglas del Chinchón.

---

## [`IA_Test`](./test/chinchon/player/test/IA_Test.java)

Este test [comprueba el comportamiento del jugador controlado por IA.](./test/chinchon/player/test/IA_Test.png)

Se divide principalmente en dos tipos de situaciones:

### Decisión de tomar la última carta del descarte

La IA debe decidir si la carta visible del descarte le interesa.

Casos comprobados:

- Acepta una carta si completa un trío.
- Acepta una carta si completa una escalera.
- Acepta una carta cercana del mismo palo si puede ayudar a formar una escalera.
- Rechaza una carta que no coincide en número ni ayuda a formar combinaciones.

### Decisión de descarte

La IA debe elegir qué carta descartar de su mano.

Casos comprobados:

- Si toda la mano forma una escalera larga, descarta la carta de mayor valor.
- Si tiene combinaciones hechas y cartas sobrantes, descarta la sobrante de mayor valor.
- Si tiene una combinación hecha y varias cartas sobrantes, descarta la carta menos útil o de mayor penalización.

Este test permite comprobar que la IA no actúa de forma aleatoria, sino siguiendo criterios relacionados con la utilidad de las cartas y la reducción de puntos.

---

# Documentación adicional

El proyecto incluye una [carpeta `doc`](./doc/) con [documentación generada mediante **Javadoc**](./doc/index.html).

Esta documentación permite consultar:

- Paquetes del proyecto.
- Clases existentes.
- Métodos públicos.
- Jerarquía de clases.
- Índices de búsqueda.
- Relaciones entre clases e interfaces.

También se incluye una [carpeta `UML`](./UML/) con un [diagrama del proyecto](./UML/ChinchonUML.png), útil para comprender visualmente la estructura de clases y sus relaciones.

---

# Conclusión

El proyecto **Chinchón** implementa una versión en consola del juego tradicional utilizando Java y programación orientada a objetos.

La aplicación está organizada en paquetes bien diferenciados, separando la lógica de cartas, jugadores y control de partida. Además, incorpora tests unitarios con JUnit 5 para verificar las reglas principales del juego, el análisis de combinaciones y el comportamiento de la IA.

Gracias a esta estructura, el proyecto resulta mantenible, ampliable y adecuado para seguir incorporando mejoras, como una interfaz gráfica, nuevos niveles de dificultad para la IA o reglas configurables.
