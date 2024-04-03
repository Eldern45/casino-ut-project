public class Card {
    private String suit;
    private String value;

    // Konstruktor kaardiobjekti lähtestamiseks konkreetse masti ja väärtusega.
    public Card(String suit, String value) {
        this.suit = suit; // Määrab kaardile antud masti.
        this.value = value; // Määrab kaardile antud väärtuse.
    }

    // Getteri meetod kaardi masti hankimiseks.
    public String getSuit() {
        return suit;
    }

    // Getteri meetod kaardi väärtuse hankimiseks.
    public String getValue() {
        return value;
    }

    // Alistab meetodi toString(), et esitada kaart stringina
    @Override
    public String toString() {
        return value + " of " + suit; // Tagastab kaardi väärtuse ja masti stringi.
    }

}
