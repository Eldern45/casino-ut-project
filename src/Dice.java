import java.util.Random;

public class Dice {
    private static final int NUM_SIDES = 6; // Konstant, mis tähistab täringu külgede arvu.
    private static final Random random = new Random(); // Juhuslik objekt juhuslike arvude genereerimiseks.
    // Meetod täringuveeretamise simuleerimiseks.
    public static int roll() {
        // Loob juhusliku arvu vahemikus 1 kuni NUM_SIDES (kaasa arvatud) ja tagastab selle.
        return random.nextInt(NUM_SIDES) + 1;
    }
}
