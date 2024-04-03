import java.util.List;
import java.util.ArrayList;

public class Hand {
    private List<Card> cards; // Kaartide list käes.

    public Hand() {
        cards = new ArrayList<>(); // Konstruktor lisab uue ArrayList-i kaartide hoidmiseks.
    }

    public void addCard(Card card) {
        cards.add(card); // Meetod kaardi lisamiseks kätte.
    }

    public int getHandValue() {
        int value = 0; // Käe väärtus.
        int numAces = 0; // Ässade arv käes.

        for (Card card : cards) { // Kõikide kaartide läbimine käsitsi.
            String cardValue = card.getValue(); // Kaardi väärtus.
            if (cardValue.equals("Poiss") || cardValue.equals("Emand") || cardValue.equals("Kuningas")) {
                value += 10; // Lisame 10 väärtuse, kui kaart on poiss, emand või kuningas.
            } else if (cardValue.equals("Äss")) {
                numAces++; // Suurendame ässade arvu.
            } else {
                value += Integer.parseInt(cardValue); // Lisame kaardi väärtuse kui number.
            }
        }

        for (int i = 0; i < numAces; i++) { // Iga ässa jaoks käime läbi.
            if (value + 11 <= 21) {
                value += 11; // Kui saame lisada 11, siis teeme seda.
            } else {
                value += 1; // Muul juhul lisame väärtuseks 1.
            }
        }

        return value; // Tagastame käe väärtuse.
    }

    public List<Card> getHand() {
        return cards; // Tagastame kõik kaardid käes.
    }

    public boolean isBlackjack() {
        return cards.size() == 2 && getHandValue() == 21; // Kontrollime, kas käsi on Blackjack.
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Card card : cards) {
            builder.append(card).append("\n"); // Loome stringi kõigi kaartide jaoks käes.
        }
        return builder.toString();
    }
}
