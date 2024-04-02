import java.util.List;
import java.util.ArrayList;

public class Hand {
    private List<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public int getHandValue() {
        int value = 0;
        int numAces = 0;

        for (Card card : cards) {
            String cardValue = card.getValue();
            if (cardValue.equals("Jack") || cardValue.equals("Queen") || cardValue.equals("King")) {
                value += 10;
            } else if (cardValue.equals("Ace")) {
                numAces++;
            } else {
                value += Integer.parseInt(cardValue);
            }
        }

        for (int i = 0; i < numAces; i++) {
            if (value + 11 <= 21) {
                value += 11;
            } else {
                value += 1;
            }
        }

        return value;
    }

    public List<Card> getHand() {
        return cards;
    }

    public boolean isBlackjack() {
        return cards.size() == 2 && getHandValue() == 21;
    }
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Card card : cards) {
            builder.append(card).append("\n");
        }
        return builder.toString();
    }
}