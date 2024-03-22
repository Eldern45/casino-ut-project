import java.util.Scanner;

public class ConsoleInterface {
    public void run() {
        System.out.println("Tere tulemast kasiinosse!");
        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("1. Logige sisse oma olemasolevale kontole");
            System.out.println("2. Looge uus konto");
            System.out.println("3. Väljuge programmist");
            System.out.print("Valige toiming: ");
            try (Scanner console = new Scanner(System.in)) {
                String choice = console.nextLine();
                switch (choice) {
                    case "1":
                        // TODO Logimine
                        break;
                    case "2":
                        // TODO Konto loomine
                        break;
                    case "3":
                        System.out.println("Head päevajätku!");
                        running = false;
                        break;
                    default:
                        System.out.println("Vale valik. Proovige uuesti.");
                }
            }
        }
    }
}
