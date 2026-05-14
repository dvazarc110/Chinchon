package chinchon.player.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import chinchon.deck.Card;
import chinchon.deck.NumCard;
import chinchon.deck.Suit;
import chinchon.player.IA;

class IA_Test {

	@ParameterizedTest(name = "{0}")
	@MethodSource("handsForTakeSituations")
	void Take_Situations(String testName, List<Card> hand, Card lastDiscard, boolean expectedDecision) {

		IA ia = new IA("ia_test");

		for (Card card : hand) {
			ia.getCard(card);
		}

		assertEquals(expectedDecision, ia.wantDiscardLast(lastDiscard));
	}

	@ParameterizedTest(name = "{0}")
	@MethodSource("handsForRightChoices")
	void Right_Choices(String testName, List<Card> hand, List<Card> discards, Card expectedDiscard) {

		IA ia = new IA("ia_test");

		for (Card card : hand) {
			ia.getCard(card);
		}

		Card actualDiscard = ia.choiceDiscard(0, discards);

		assertSame(expectedDiscard, actualDiscard);
	}

	private static Stream<Arguments> handsForTakeSituations() {
		return Stream.of(
				Arguments.of(
						"Acepta una carta del descarte si completa un trio",
						List.of(
								card(Suit.ORO, NumCard.CINCO),
								card(Suit.COPA, NumCard.CINCO),
								card(Suit.BASTOS, NumCard.UNO),
								card(Suit.COPA, NumCard.TRES),
								card(Suit.ESPADAS, NumCard.SIETE),
								card(Suit.ORO, NumCard.SOTA),
								card(Suit.BASTOS, NumCard.REY)),
						card(Suit.ESPADAS, NumCard.CINCO),
						true),

				Arguments.of(
						"Acepta una carta del descarte si completa una escalera",
						List.of(
								card(Suit.ORO, NumCard.UNO),
								card(Suit.ORO, NumCard.DOS),
								card(Suit.COPA, NumCard.CINCO),
								card(Suit.BASTOS, NumCard.SIETE),
								card(Suit.ESPADAS, NumCard.SOTA),
								card(Suit.COPA, NumCard.CABALLO),
								card(Suit.BASTOS, NumCard.REY)),
						card(Suit.ORO, NumCard.TRES),
						true),

				Arguments.of(
						"Acepta una carta cercana del mismo palo que puede ayudar a formar escalera",
						List.of(
								card(Suit.COPA, NumCard.CUATRO),
								card(Suit.ORO, NumCard.UNO),
								card(Suit.BASTOS, NumCard.TRES),
								card(Suit.ESPADAS, NumCard.SIETE),
								card(Suit.ORO, NumCard.SOTA),
								card(Suit.BASTOS, NumCard.CABALLO),
								card(Suit.COPA, NumCard.REY)),
						card(Suit.COPA, NumCard.DOS),
						true),

				Arguments.of(
						"No acepta una carta que no coincide en numero ni ayuda por palo",
						List.of(
								card(Suit.ORO, NumCard.UNO),
								card(Suit.COPA, NumCard.CUATRO),
								card(Suit.BASTOS, NumCard.SEIS),
								card(Suit.ESPADAS, NumCard.SOTA),
								card(Suit.ORO, NumCard.CABALLO),
								card(Suit.COPA, NumCard.REY),
								card(Suit.BASTOS, NumCard.DOS)),
						card(Suit.ESPADAS, NumCard.CINCO),
						false));
	}

	private static Stream<Arguments> handsForRightChoices() {
		Card oroUno = card(Suit.ORO, NumCard.UNO);
		Card oroDos = card(Suit.ORO, NumCard.DOS);
		Card oroTres = card(Suit.ORO, NumCard.TRES);
		Card oroCuatro = card(Suit.ORO, NumCard.CUATRO);
		Card oroCinco = card(Suit.ORO, NumCard.CINCO);
		Card oroSeis = card(Suit.ORO, NumCard.SEIS);
		Card oroSiete = card(Suit.ORO, NumCard.SIETE);
		Card oroSota = card(Suit.ORO, NumCard.SOTA);

		Card copaUno = card(Suit.COPA, NumCard.UNO);
		Card copaDos = card(Suit.COPA, NumCard.DOS);
		Card copaTres = card(Suit.COPA, NumCard.TRES);
		Card espadasCinco = card(Suit.ESPADAS, NumCard.CINCO);
		Card espadasSeis = card(Suit.ESPADAS, NumCard.SEIS);
		Card espadasSiete = card(Suit.ESPADAS, NumCard.SIETE);
		Card bastosRey = card(Suit.BASTOS, NumCard.REY);
		Card espadasCuatro = card(Suit.ESPADAS, NumCard.CUATRO);

		Card bastosUno = card(Suit.BASTOS, NumCard.UNO);
		Card bastosDos = card(Suit.BASTOS, NumCard.DOS);
		Card bastosTres = card(Suit.BASTOS, NumCard.TRES);
		Card copaSiete = card(Suit.COPA, NumCard.SIETE);
		Card espadasSota = card(Suit.ESPADAS, NumCard.SOTA);
		Card bastosCaballo = card(Suit.BASTOS, NumCard.CABALLO);
		Card copaRey = card(Suit.COPA, NumCard.REY);
		Card oroRey = card(Suit.ORO, NumCard.REY);

		return Stream.of(
				Arguments.of(
						"Si toda la mano forma una escalera larga descarta la carta de mayor valor",
						List.of(
								oroUno,
								oroDos,
								oroTres,
								oroCuatro,
								oroCinco,
								oroSeis,
								oroSiete,
								oroSota),
						List.of(
								card(Suit.COPA, NumCard.REY),
								card(Suit.BASTOS, NumCard.CABALLO),
								card(Suit.ESPADAS, NumCard.SOTA)),
						oroSota),

				Arguments.of(
						"Con dos escaleras hechas y un par de cartas sobrantes, descarta la carta sobrante de mayor valor",
						List.of(
								copaUno,
								copaDos,
								copaTres,
								espadasCinco,
								espadasSeis,
								espadasSiete,
								bastosRey,
								espadasCuatro),
						List.of(
								card(Suit.ORO, NumCard.SIETE),
								card(Suit.BASTOS, NumCard.SOTA),
								card(Suit.COPA, NumCard.CABALLO)),
						bastosRey),

				Arguments.of(
						"Con una combinacion hecha y varias sobrantes sin combinacion posible descarta la sobrante mas alta que no forme un semigrupo",
						List.of(
								bastosUno,
								bastosDos,
								bastosTres,
								copaSiete,
								espadasSota,
								bastosCaballo,
								copaRey,
								oroRey),
						List.of(
								card(Suit.COPA, NumCard.CINCO),
								card(Suit.ORO, NumCard.SEIS),
								card(Suit.ESPADAS, NumCard.DOS)),
						bastosCaballo));
	}

	private static Card card(Suit suit, NumCard num) {
		return new Card(suit, num);
	}
}