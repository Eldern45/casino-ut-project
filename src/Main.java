import java.io.Console;

public class Main {
        public static void main(String[] args) {
        ConsoleInterface consoleInterface = new ConsoleInterface();
        consoleInterface.run();


//        Console console = System.console();
//        if (console == null) {
//            System.out.println("Консоль не доступна");
//            return;
//        }
//        char[] passwordArray = console.readPassword("Введите ваш пароль: ");
//        for (char c : passwordArray) {
//            System.out.print(c);
//        }

    }
}