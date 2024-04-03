import java.util.Random;

public class Dice {
    private static final int NUM_SIDES = 6;
    private static final Random random = new Random();

    public static int roll() {
        return random.nextInt(NUM_SIDES) + 1;
    }



}
