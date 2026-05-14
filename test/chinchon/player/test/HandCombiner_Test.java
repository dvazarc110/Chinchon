package chinchon.player.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Comparator;
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

class HandCombiner_Test {

	@ParameterizedTest(name = "{0}")
	@MethodSource("handsForDifferentsAnalysis")
	void differents_analysis(String testName, List<Card> hand, int expectedGroups, List<Integer> expectedGroupSizes,
			int expectedLefts) {

		HandCombiner combiner = new HandCombiner();

		CombinationResult result = combiner.analise(hand, null);

		List<Integer> actualGroupSizes = result.getCombinations().stream()
				.map(List::size)
				.sorted(Comparator.naturalOrder())
				.toList();

		List<Integer> sortedExpectedGroupSizes = expectedGroupSizes.stream()
				.sorted(Comparator.naturalOrder())
				.toList();

		assertEquals(expectedGroups, result.getCombinations().size());
		assertEquals(sortedExpectedGroupSizes, actualGroupSizes);
		assertEquals(expectedLefts, result.getLefts().size());
	}

	private static Stream<Arguments> handsForDifferentsAnalysis() {
		return Stream.of(
				Arguments.of(
						"Mano sin combinaciones debe devolver cero grupos y siete sobrantes",
						List.of(
								card(Suit.ORO, NumCard.UNO),
								card(Suit.COPA, NumCard.TRES),
								card(Suit.BASTOS, NumCard.CINCO),
								card(Suit.ESPADAS, NumCard.SIETE),
								card(Suit.ORO, NumCard.SOTA),
								card(Suit.COPA, NumCard.CABALLO),
								card(Suit.BASTOS, NumCard.REY)),
						0,
						List.of(),
						7),

				Arguments.of(
						"Trio del mismo numero debe generar un grupo de tres cartas",
						List.of(
								card(Suit.ORO, NumCard.UNO),
								card(Suit.COPA, NumCard.UNO),
								card(Suit.BASTOS, NumCard.UNO),
								card(Suit.ESPADAS, NumCard.CUATRO),
								card(Suit.ORO, NumCard.SEIS),
								card(Suit.COPA, NumCard.SOTA),
								card(Suit.BASTOS, NumCard.REY)),
						1,
						List.of(3),
						4),

				Arguments.of(
						"Cuarteto del mismo numero debe generar un grupo de cuatro cartas",
						List.of(
								card(Suit.ORO, NumCard.CINCO),
								card(Suit.COPA, NumCard.CINCO),
								card(Suit.BASTOS, NumCard.CINCO),
								card(Suit.ESPADAS, NumCard.CINCO),
								card(Suit.ORO, NumCard.UNO),
								card(Suit.COPA, NumCard.SIETE),
								card(Suit.BASTOS, NumCard.REY)),
						1,
						List.of(4),
						3),

				Arguments.of(
						"Escalera de tres cartas del mismo palo debe generar un grupo de tres cartas",
						List.of(
								card(Suit.ORO, NumCard.UNO),
								card(Suit.ORO, NumCard.DOS),
								card(Suit.ORO, NumCard.TRES),
								card(Suit.COPA, NumCard.CINCO),
								card(Suit.BASTOS, NumCard.SIETE),
								card(Suit.ESPADAS, NumCard.SOTA),
								card(Suit.COPA, NumCard.REY)),
						1,
						List.of(3),
						4),

				Arguments.of(
						"Escalera larga de cinco cartas del mismo palo debe generar un grupo de cinco cartas",
						List.of(
								card(Suit.COPA, NumCard.UNO),
								card(Suit.COPA, NumCard.DOS),
								card(Suit.COPA, NumCard.TRES),
								card(Suit.COPA, NumCard.CUATRO),
								card(Suit.COPA, NumCard.CINCO),
								card(Suit.BASTOS, NumCard.SIETE),
								card(Suit.ESPADAS, NumCard.REY)),
						1,
						List.of(5),
						2),

				Arguments.of(
						"Trio y escalera independientes deben generar dos grupos",
						List.of(
								card(Suit.ORO, NumCard.UNO),
								card(Suit.COPA, NumCard.UNO),
								card(Suit.BASTOS, NumCard.UNO),
								card(Suit.ESPADAS, NumCard.CINCO),
								card(Suit.ESPADAS, NumCard.SEIS),
								card(Suit.ESPADAS, NumCard.SIETE),
								card(Suit.COPA, NumCard.REY)),
						2,
						List.of(3, 3),
						1),

				Arguments.of(
						"Escalera de siete cartas debe generar un unico grupo de siete cartas",
						List.of(
								card(Suit.BASTOS, NumCard.UNO),
								card(Suit.BASTOS, NumCard.DOS),
								card(Suit.BASTOS, NumCard.TRES),
								card(Suit.BASTOS, NumCard.CUATRO),
								card(Suit.BASTOS, NumCard.CINCO),
								card(Suit.BASTOS, NumCard.SEIS),
								card(Suit.BASTOS, NumCard.SIETE)),
						1,
						List.of(7),
						0),

				Arguments.of(
						"Dos escaleras independientes deben generar dos grupos de tres cartas",
						List.of(
								card(Suit.ORO, NumCard.UNO),
								card(Suit.ORO, NumCard.DOS),
								card(Suit.ORO, NumCard.TRES),
								card(Suit.COPA, NumCard.CINCO),
								card(Suit.COPA, NumCard.SEIS),
								card(Suit.COPA, NumCard.SIETE),
								card(Suit.ESPADAS, NumCard.REY)),
						2,
						List.of(3, 3),
						1),

				Arguments.of(
						"Escalera cortada no debe generar grupo",
						List.of(
								card(Suit.ORO, NumCard.UNO),
								card(Suit.ORO, NumCard.DOS),
								card(Suit.ORO, NumCard.CUATRO),
								card(Suit.COPA, NumCard.SEIS),
								card(Suit.BASTOS, NumCard.SIETE),
								card(Suit.ESPADAS, NumCard.SOTA),
								card(Suit.COPA, NumCard.REY)),
						0,
						List.of(),
						7));
	}

	private static Card card(Suit suit, NumCard num) {
		return new Card(suit, num);
	}
}
