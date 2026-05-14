package chinchon.deck.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import chinchon.deck.Card;
import chinchon.deck.Deck;
import chinchon.deck.NumCard;
import chinchon.deck.Suit;

class Deck_Test {

	@Test
	void Create_Deck() {

		Deck deck = new Deck();

		deck.initialize();

		List<Card> cards = deck.getDeck();

		assertEquals(40, cards.size());

		int index = 0;
		for (Suit expectedSuit : Suit.values()) {
			for (NumCard expectedNum : NumCard.values()) {
				Card actualCard = cards.get(index);

				assertEquals(expectedSuit, actualCard.getSuit());
				assertEquals(expectedNum, actualCard.getNum());
				assertEquals(expectedNum.getValue(), actualCard.getValue());

				index++;
			}
		}
	}

	@ParameterizedTest(name = "Sirve manos completas para {0} jugadores")
	@ValueSource(ints = { 2, 3, 4 })
	void BeforeStart_Serve(int playerAmount) {

		Deck deck = new Deck();
		List<List<Card>> hands = new ArrayList<>();

		deck.shuffleDeck();

		for (int i = 0; i < playerAmount; i++) {
			hands.add(deck.serve(7));
		}

		assertEquals(playerAmount, hands.size());

		for (List<Card> hand : hands) {
			assertEquals(7, hand.size());
			assertFalse(hand.contains(null));
		}

		assertEquals(40 - (playerAmount * 7), deck.getDeck().size());
	}
}