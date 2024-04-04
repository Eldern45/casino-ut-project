import javax.swing.*;
import java.awt.Window;
import java.awt.Toolkit;
import java.awt.AWTEvent;
import java.awt.event.WindowEvent;
import java.awt.event.AWTEventListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Random;

//See on klass, kus on rakendatud kogu programmi liides.
public class ConsoleInterface {
    protected AccountManager accountManager;
    //Klassi konstruktor
    public ConsoleInterface() throws FileNotFoundException {
        accountManager = new AccountManager();

        //See on akna kuulaja. Kui aken sulgub, siis ta salvestab andmed ja lõpetab programmi.
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            public void eventDispatched(AWTEvent event) {
                if (event instanceof WindowEvent we) {
                    if (we.getID() == WindowEvent.WINDOW_CLOSING) {
                        ((Window) event.getSource()).dispose();
                        try {
                            //Kontode salvestamine
                            accountManager.saveAccounts();
                        } catch (UnsupportedEncodingException | FileNotFoundException e) {
                            JOptionPane.showMessageDialog(null, "Viga kontode salvestamisel!", "Kasiino", JOptionPane.ERROR_MESSAGE);
                        }
                        System.exit(0);
                    }
                }
            }
        }, AWTEvent.WINDOW_EVENT_MASK);
    }

    //Peamine meetod, mis köivotab programmi
    public void run() throws FileNotFoundException, UnsupportedEncodingException {
        JOptionPane.showMessageDialog(null, "Tere tulemast kasiinosse!", "Kasiino",
                JOptionPane.INFORMATION_MESSAGE);

        boolean running = true;
        while (running) {
            String[] options = {"Looge uus konto", "Logige sisse oma olemasolevale kontole", "Väljuge programmist"};
            int choice = JOptionPane.showOptionDialog(null, "Valige üks:", "Kasiino",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    createUser();
                    break;
                case 1:
                    Account account = login();
                    if (account != null)
                        showMenu(account);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Head päevajätku!");
                    running = false;
            }
        }
    }


    //Loome uut kasutajat
    private void createUser() throws FileNotFoundException, UnsupportedEncodingException {
        while (true) {
            String email = JOptionPane.showInputDialog(null, "Sisestage e-post: ", "Konto loomine",
                    JOptionPane.QUESTION_MESSAGE);

            //Kui vajutatakse cancel
            if (email == null) break;

            String login = JOptionPane.showInputDialog(null, "Looge kasutajanimi: ", "Konto loomine",
                    JOptionPane.QUESTION_MESSAGE);
            //Kui vajutatakse cancel
            if (login == null) break;

            //Parooli sisestamine
            JPanel jPanel = new JPanel();
            JPasswordField jPasswordField = new JPasswordField(10);
            jPanel.add(new JLabel("Looge parooli: "));
            jPanel.add(jPasswordField);
            int result = JOptionPane.showConfirmDialog(null, jPanel, "Konto loomine",
                    JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.CANCEL_OPTION) break;
            String password = new String(jPasswordField.getPassword());

            String[] options1 = {"Jah", "Ei, Sisesta uuesti", "Tagasi algekraanisse"};
            int choice = JOptionPane.showOptionDialog(null, "Teie email ja kasutajanimi: " + email +"/" +
                    login + ". Kas olete kindel, et sisestasite need õigesti?", "Konto loomine",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options1, options1[0]);

            //Kui vajutatakse Ei, Sisesta uuesti
            if (choice == 1) continue;
            //Kui vajutatakse Tagasi algekraanisse
            if (choice == 2) break;

            String[] options2 = {"Super!"};
            JOptionPane.showOptionDialog(null, "Olete loonud uue konto! Selle auks on meil " +
                            "Sulle üllatus tasuta 50 euro näol Sinu virtuaalkontole!", "Konto loomine",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options2, options2[0]);

            //free 50 bucks
            accountManager.createUser(email, login, password, 50);
            break;
        }
    }

    //Logimine kontosse
    private Account login() {
        while (true) {
            String email = JOptionPane.showInputDialog(null, "Sisestage e-post:", "Logimine", JOptionPane.QUESTION_MESSAGE);
            if (email == null) break;

            JPanel jPanel = new JPanel();
            JPasswordField jPasswordField = new JPasswordField(10);
            jPanel.add(new JLabel("Sisestage parool:"));
            jPanel.add(jPasswordField);
            int result = JOptionPane.showConfirmDialog(null, jPanel, "Logimine", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.CANCEL_OPTION) break;
            String password = new String(jPasswordField.getPassword());


            //Kontrollime, kas on meil selline konto
            Account account = accountManager.findAccount(email, password);
            if (account != null) {
                JOptionPane.showMessageDialog(null, "Tere tulemast, " + account.getUsername() + "!", "Logimine", JOptionPane.INFORMATION_MESSAGE);
                return account;
            } else {
                JOptionPane.showMessageDialog(null, "Vale kasutajanimi või parool!", "Logimine", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    //Peamine menüü, kus saab teha selliseid tegevusi "Näita jääki", "Muuda kasutajanime", "Muuda parooli","Mine mängima", "Logi välja"
    private void showMenu(Account account) throws FileNotFoundException, UnsupportedEncodingException {
        boolean running = true;
        while (running) {
            String[] options = {"Näita jääki", "Muuda kasutajanime", "Muuda parooli","Mine mängima", "Logi välja"};
            int choice = JOptionPane.showOptionDialog(null, "Valige toiming:", "Menüü",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Teie jääk: " + account.getMoney()
                                    + " eurot" , "Kasiino", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 1:
                    changeLogin(account);
                    break;
                case 2:
                    changePassword(account);
                    break;
                case 3:
                    //showGameMenu tagastab account, sest pärast Blackjacki on vaja uuendada infot konto kohta
                    account = showGameMenu(account);
                    break;
                default:
                    running = false;
            }
        }
    }

    //Kasutajanimi muutmine, saab teha ilma parooli teadmata
    private void changeLogin(Account account) {
        while (true) {
            String newLogin = JOptionPane.showInputDialog(null, "Sisestage uus kasutajanimi:", "Kasutajanime muutmine", JOptionPane.QUESTION_MESSAGE);
            if (newLogin == null) break;

            //Kui kasutajanimi pole võetud
            if (accountManager.isLoginAvailable(newLogin)) {
                account.setUsername(newLogin);
                try {
                    accountManager.saveAccounts();
                    JOptionPane.showMessageDialog(null, "Kasutajanimi edukalt muudetud!", "Kasutajanime muutmine", JOptionPane.INFORMATION_MESSAGE);
                    break;
                } catch (FileNotFoundException | UnsupportedEncodingException e) {
                    JOptionPane.showMessageDialog(null, "Viga kasutajanime muutmisel!", "Kasutajanime muutmine", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Kasutajanimi on juba võetud!", "Kasutajanime muutmine", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //Paroolimuutmine
    private void changePassword(Account account) {
        while (true) {
            JPanel panel = new JPanel();
            JPasswordField oldPasswordField = new JPasswordField(10);
            JPasswordField newPasswordField = new JPasswordField(10);
            JPasswordField confirmPasswordField = new JPasswordField(10);
            panel.add(new JLabel("Vana parool:"));
            panel.add(oldPasswordField);
            panel.add(new JLabel("Uus parool:"));
            panel.add(newPasswordField);
            panel.add(new JLabel("Kinnita uus parool:"));
            panel.add(confirmPasswordField);

            int result = JOptionPane.showConfirmDialog(null, panel, "Parooli muutmine", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.CANCEL_OPTION) break;

            String oldPassword = new String(oldPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            //Kui vana ja uus paroolid ühtivad
            if (account.getPassword().equals(oldPassword)) {
                //Kui uued paroolid ühtivad
                if (newPassword.equals(confirmPassword)) {
                    account.setPassword(newPassword);
                    try {
                        accountManager.saveAccounts();
                        JOptionPane.showMessageDialog(null, "Parool edukalt muudetud!", "Parooli muutmine", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    } catch (FileNotFoundException | UnsupportedEncodingException e) {
                        JOptionPane.showMessageDialog(null, "Viga parooli muutmisel!", "Parooli muutmine", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Uued paroolid ei ühti!", "Parooli muutmine", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Vale vana parool!", "Parooli muutmine", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    //Mängumenüü
    public Account showGameMenu(Account account) throws FileNotFoundException, UnsupportedEncodingException {
        boolean running = true;
        while (running) {
            //On vaja uuendada kontode kohta infot, sest kasutaja võib olla tahab mängida Blackjack-i, mis on teine klass
            account = accountManager.findAccount(account.getEmail(), account.getPassword());

            String[] options = {"Blackjack", "Täringud", "Tagasi menüüsse"};
            int choice = JOptionPane.showOptionDialog(null, "Valige mäng:", "Mängimine",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    //Blackjacki mängimine
                    new Blackjack().MängiBlackjack(account);
                    //On vaja uuendada kontode kohta infot, sest tegemist oli klassiga
                    accountManager.loadAccounts();
                    break;
                case 1:
                    playDice(account);
                    break;
                default:
                    running = false;
            }
        }
        return account;
    }

    public double getBetAmount(Account account) {
        while (true) {
            // Kasutaja sisestab panuse summa dialoogiboksi abil.
            String betString = JOptionPane.showInputDialog(null, "Sisestage panussumma:", "Panus", JOptionPane.QUESTION_MESSAGE);
            if (betString == null) break;
            try {
                // Kontrollitakse, kas sisestatud panus on null või negatiivne.
                double bet = Double.parseDouble(betString);
                if (bet <= 0) {
                    JOptionPane.showMessageDialog(null, "Sisestage midagi suuem, kui null!", "Vigane sisend", JOptionPane.ERROR_MESSAGE);
                }
                // Kontrollime, kas on piisavalt raha kontos
                else if (account.getMoney() < bet) {
                    JOptionPane.showMessageDialog(null, "Kontol pole piisavalt raha! (" + account.getMoney() + " eurot)", "Vigane sisend", JOptionPane.ERROR_MESSAGE);
                }
                else
                    return bet;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Sisestage number!", "Vigane sisend", JOptionPane.ERROR_MESSAGE);
            }
        }
        // -1 == cancel
        return -1;
    }

    private int getUserBetNumber() {
        while (true) {
            String betNumberString = JOptionPane.showInputDialog(null,
                    "Sisestage oma ennustatav number (1-6):", "Panus", JOptionPane.QUESTION_MESSAGE);
            try {
                // Teisendame sisestatud stringi arvuks
                int betNumber = Integer.parseInt(betNumberString);
                // Kontrollime, kas sisestatud number on sobivas vahemikus
                if (betNumber < 1 || betNumber > 6) {
                    JOptionPane.showMessageDialog(null, "Palun sisestage number vahemikus 1 kuni 6!", "Vigane sisend", JOptionPane.ERROR_MESSAGE);
                }
                else
                    return betNumber;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Sisestage number!", "Vigane sisend", JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    private void playDice(Account account) throws FileNotFoundException, UnsupportedEncodingException {
        boolean continuePlaying = true;
        while (continuePlaying) {
            double bet = getBetAmount(account);
            if (bet == -1) {
                break;
            }
            int userBetNumber = getUserBetNumber();
            int dealerRoll = roll();

            // Väljastame mängija ja diileri veeretuse tulemuse.
            JOptionPane.showMessageDialog(null, "Teie veeretus: " + userBetNumber + "\nDiileri veeretus: " + dealerRoll, "Täringud", JOptionPane.INFORMATION_MESSAGE);

            if (userBetNumber == dealerRoll) {
                // Kui mängija arvas numbri õigesti, väljastame võiduteate ja värskendame mängija kontot.
                JOptionPane.showMessageDialog(null, "Võitsid " + bet * 6 + " eurot. Palju õnne!", "Täringud", JOptionPane.INFORMATION_MESSAGE);
                account.setMoney(account.getMoney() + bet * 5);
                accountManager.saveAccounts();
            } else {
                // Kui mängija ei arvanud numbri õigesti, väljastame kaotusteate ja värskendame mängija kontot.
                JOptionPane.showMessageDialog(null, "Kaotasid! Järgmine kord läheb vedama!", "Täringud", JOptionPane.ERROR_MESSAGE);
                account.setMoney(account.getMoney() - bet);
                accountManager.saveAccounts();
            }

            // Küsime kasutajalt, kas nad soovivad mängu jätkata.
            int choice = JOptionPane.showConfirmDialog(null, "Kas soovite jätkata mängu?", "Täringud", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.NO_OPTION)
                // Kui kasutaja valib "Ei", peatame mängu.
                continuePlaying = false;
        }
    }

    private static int roll() {
        int numSides = 6; // Arv, mis tähistab täringu külgede arvu.
        Random random = new Random(); // Juhuslik objekt juhuslike arvude genereerimiseks.
        // Meetod täringuveeretamise simuleerimiseks.

        // Loob juhusliku arvu vahemikus 1 kuni numSides (kaasa arvatud) ja tagastab selle.
        return random.nextInt(numSides) + 1;
    }
}
