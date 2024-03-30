import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AccountManager {
    List<Account> accounts;
    private File file = new File("src/accounts.txt");

    public AccountManager() throws FileNotFoundException {
        accounts = new ArrayList<>();
        loadAccounts();
    }

    public void createUser(String email, String username, String password, int money) throws FileNotFoundException, UnsupportedEncodingException {
        Account account = new Account(email, username, password, money);
        accounts.add(account);
        saveAccounts();
    }

    public void saveAccounts() throws FileNotFoundException, UnsupportedEncodingException {
        try (PrintWriter pw = new PrintWriter(file, "UTF-8")) {
            for (Account account : accounts) {
                pw.println(account.getEmail() + ";" + account.getUsername() + ";" + account.getPassword() + ";" +
                        account.getMoney());
            }
        }
    }

    public void loadAccounts() throws FileNotFoundException {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return;
        }
        try (Scanner sc = new Scanner(file, "UTF-8")) {
            while (sc.hasNextLine()) {
                String[] data = sc.nextLine().split(";");
                accounts.add(new Account(data[0], data[1], data[2], Double.parseDouble(data[3])));
            }
        }
    }

    public Account findAccount(String email, String password) {
        for (Account account : accounts) {
            if (account.getEmail().equals(email) && account.getPassword().equals(password)) {
                return account;
            }
        }
        return null;
    }
}