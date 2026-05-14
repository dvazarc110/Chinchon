package chinchon.player.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import chinchon.deck.Card;
import chinchon.deck.NumCard;
import chinchon.deck.Suit;
import chinchon.player.CombinationResult;
import chinchon.player.HandCombiner;

class CombinationResult_Test {

	@ParameterizedTest(name = "{0}")
	@MethodSource("handsForEndTurnSituations")
	void endTurn_Situations(String testName, List<Card> hand, int turncont, int playeramount, boolean expectedCanClose) {

		HandCombiner combiner = new HandCombiner();

		CombinationResult result = combiner.analise(hand, null);

		assertEquals(expectedCanClose, result.canClose(turncont, playeramount));
	}

	private static Stream<Arguments> handsForEndTurnSituations() {
		return Stream.of(
				Arguments.of(
						"No puede cerrar si aun no han jugado todos los jugadores aunque no tenga sobrantes",
						List.of(
								card(Suit.ORO, NumCard.UNO),
								card(Suit.ORO, NumCard.DOS),
								card(Suit.ORO, NumCard.TRES),
								card(Suit.ORO, NumCard.CUATRO),
								card(Suit.ORO, NumCard.CINCO),
								card(Suit.ORO, NumCard.SEIS),
								card(Suit.ORO, NumCard.SIETE)),
						2,
						2,
						false),

				Arguments.of(
						"Puede cerrar cuando todos han jugado al menos un turno y no tiene sobrantes",
						List.of(
								card(Suit.COPA, NumCard.UNO),
								card(Suit.COPA, NumCard.DOS),
								card(Suit.COPA, NumCard.TRES),
								card(Suit.COPA, NumCard.CUATRO),
								card(Suit.COPA, NumCard.CINCO),
								card(Suit.COPA, NumCard.SEIS),
								card(Suit.COPA, NumCard.SIETE)),
						3,
						2,
						true),

				Arguments.of(
						"No puede cerrar con cuatro jugadores si el turno no supera la cantidad de jugadores",
						List.of(
								card(Suit.BASTOS, NumCard.UNO),
								card(Suit.BASTOS, NumCard.DOS),
								card(Suit.BASTOS, NumCard.TRES),
								card(Suit.BASTOS, NumCard.CUATRO),
								card(Suit.BASTOS, NumCard.CINCO),
								card(Suit.BASTOS, NumCard.SEIS),
								card(Suit.BASTOS, NumCard.SIETE)),
						4,
						4,
						false),

				Arguments.of(
						"Puede cerrar con cuatro jugadores cuando ya se ha superado la primera vuelta y no tiene sobrantes",
						List.of(
								card(Suit.ESPADAS, NumCard.UNO),
								card(Suit.ESPADAS, NumCard.DOS),
								card(Suit.ESPADAS, NumCard.TRES),
								card(Suit.ESPADAS, NumCard.CUATRO),
								card(Suit.ESPADAS, NumCard.CINCO),
								card(Suit.ESPADAS, NumCard.SEIS),
								card(Suit.ESPADAS, NumCard.SIETE)),
						5,
						4,
						true),

				Arguments.of(
						"Puede cerrar despues de la primera vuelta con una carta sobrante de valor menor o igual que cinco",
						List.of(
								card(Suit.ORO, NumCard.UNO),
								card(Suit.ORO, NumCard.DOS),
								card(Suit.ORO, NumCard.TRES),
								card(Suit.COPA, NumCard.CINCO),
								card(Suit.COPA, NumCard.SEIS),
								card(Suit.COPA, NumCard.SIETE),
								card(Suit.BASTOS, NumCard.CINCO)),
						3,
						2,
						true),

				Arguments.of(
						"No puede cerrar con una carta sobrante de valor mayor que cinco",
						List.of(
								card(Suit.ORO, NumCard.UNO),
								card(Suit.ORO, NumCard.DOS),
								card(Suit.ORO, NumCard.TRES),
								card(Suit.COPA, NumCard.CINCO),
								card(Suit.COPA, NumCard.SEIS),
								card(Suit.COPA, NumCard.SIETE),
								card(Suit.BASTOS, NumCard.REY)),
						3,
						2,
						false),

				Arguments.of(
						"No puede cerrar con mas de una carta sobrante",
						List.of(
								card(Suit.ORO, NumCard.UNO),
								card(Suit.ORO, NumCard.DOS),
								card(Suit.ORO, NumCard.TRES),
								card(Suit.COPA, NumCard.SEIS),
								card(Suit.BASTOS, NumCard.SOTA),
								card(Suit.ESPADAS, NumCard.REY),
								card(Suit.COPA, NumCard.CUATRO)),
						3,
						2,
						false));
	}

	private static Card card(Suit suit, NumCard num) {
		return new Card(suit, num);
	}
}