import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private List<Card> cards; // Loend kaartide pakis hoidmiseks.

    // Konstruktor teki lähtestamiseks.
    public Deck() {
        cards = new ArrayList<>(); // Uue ArrayListi loomine kaartide hoidmiseks.
        initializeDeck(); // Meetodi kutsumine teki lähtestamiseks.
    }

    // Meetod paki lähtestamiseks tavaliste mängukaartidega.
    private void initializeDeck() {
        // Standardsete mängukaartide masti ja väärtuste massiiv.
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};

        // Pesastatud silmused iga kaardi loomiseks ja pakile lisamiseks.
        for (String suit : suits) {
            for (String value : values) {
                cards.add(new Card(suit, value)); // Uue kaardiobjekti loomine praeguse masti ja väärtusega, seejärel selle lisamine pakile.
            }
        }
    }

    // Kaartide segamise meetod pakis.
    public void shuffle() {
        Collections.shuffle(cards); // Shuffle() meetodi kasutamine klassist Collections kaartide juhuslikuks segamiseks.
    }

    // Pakist kaardi jagamise meetod.
    public Card dealCard() {
        if (cards.isEmpty()) { // Kontrollimine, kas tekk on tühi.
            throw new IllegalStateException("The deck is empty"); // Erandi viskamine, kui tekk on tühi.
        }
        return cards.remove(0); // Ülemise kaardi pakist eemaldamine ja tagastamine.
    }

    // Meetod kontrollimaks, kas tekk on tühi.
    public boolean isEmpty() {
        return cards.isEmpty(); // Tagastab tõene, kui tekk on tühi, false muidu.
    }
}
