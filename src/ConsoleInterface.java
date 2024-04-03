import javax.swing.*;
import java.awt.Window;
import java.awt.Toolkit;
import java.awt.AWTEvent;
import java.awt.event.WindowEvent;
import java.awt.event.AWTEventListener;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class ConsoleInterface {
    private AccountManager accountManager;
    private Blackjack blackjack;
    private Account currentAccount;

    public ConsoleInterface() throws FileNotFoundException {
        accountManager = new AccountManager();
        blackjack = new Blackjack(accountManager);
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            public void eventDispatched(AWTEvent event) {
                if (event instanceof WindowEvent we) {
                    if (we.getID() == WindowEvent.WINDOW_CLOSING) {
                        ((Window) event.getSource()).dispose();
                        try {
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

    private void createUser() throws FileNotFoundException, UnsupportedEncodingException {
        while (true) {
            String email = JOptionPane.showInputDialog(null, "Sisestage e-post: ", "Konto loomine",
                    JOptionPane.QUESTION_MESSAGE);
            if (email == null) break;

            String login = JOptionPane.showInputDialog(null, "Looge kasutajanimi: ", "Konto loomine",
                    JOptionPane.QUESTION_MESSAGE);
            if (login == null) break;

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
            if (choice == 1) continue;
            if (choice == 2) break;

            String[] options2 = {"Super!"};
            JOptionPane.showOptionDialog(null, "Olete loonud uue konto! Selle auks on meil " +
                            "Sulle üllatus tasuta 50 euro näol Sinu virtuaalkontole!", "Konto loomine",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options2, options2[0]);

            accountManager.createUser(email, login, password, 50);
            break;
        }
    }

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

            Account account = accountManager.findAccount(email, password);
            if (account != null) {
                accountManager.setCurrentAccount(account);
                JOptionPane.showMessageDialog(null, "Tere tulemast, " + account.getUsername() + "!", "Logimine", JOptionPane.INFORMATION_MESSAGE);
                return account;
            } else {
                JOptionPane.showMessageDialog(null, "Vale kasutajanimi või parool!", "Logimine", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    private void showMenu(Account account) {
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
                    showGameMenu(account);
                    break;
                default:
                    running = false;
            }
        }
    }

    public void showGameMenu(Account account) {
        boolean running = true;
        while (running) {
            String[] options = {"Tagasi menüüsse", "Rulett", "Blackjack", "Täringud"};
            int choice = JOptionPane.showOptionDialog(null, "Valige mäng:", "Mängimine",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 1:
                    MängiRuletti(account);
                    break;
                case 2:
                    blackjack.MängiBlackjack(account);
                    break;
                case 3:
                    playDice(accountManager);
                default:
                    running = false;
            }
        }
    }


    private void changeLogin(Account account) {
        while (true) {
            String newLogin = JOptionPane.showInputDialog(null, "Sisestage uus kasutajanimi:", "Kasutajanime muutmine", JOptionPane.QUESTION_MESSAGE);
            if (newLogin == null) break;

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

            if (account.getPassword().equals(oldPassword)) {
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

    private void MängiRuletti(Account account){

        // Kontrollime, kas kasutajakontot on olemas
        if (account == null) {
            JOptionPane.showMessageDialog(null, "Teil peab olema konto, et mängida ruletti!", "Rulett", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Loome Rulett objekti, kasutades antud kasutajat ja konto haldurit
        Rulett  rulett =  new Rulett(account, accountManager);

        // Käivitame Ruleti mängu
        rulett.MängiRuletti();
    }


    private int getBetAmount() {
        int bet;
        try {
            // Kasutaja sisestab panuse summa dialoogiboksi abil.
            String betString = JOptionPane.showInputDialog(null, "Sisestage panussumma:", "Panus", JOptionPane.QUESTION_MESSAGE);
            bet = Integer.parseInt(betString);
            // Kontrollitakse, kas sisestatud panus on null või negatiivne. Kui on, siis tagastatakse 0.
            if (bet <= 0) {
                return 0;
            }
        } catch (NumberFormatException e) {
            // Kui sisestatakse midagi muud peale numbri, siis tagastatakse 0.
            return 0;
        }
        // Tagastatakse sisestatud panuse summa.
        return bet;
    }


    private void playDice(AccountManager accountManager) {
        currentAccount = accountManager.getCurrentAccount();
        if (currentAccount == null) {
            // Kontrollime, kas praegune konto on sisse logitud, vastasel juhul väljastame veateate ja lõpetame meetodi.
            JOptionPane.showMessageDialog(null, "Te pole sisse loginud!", "Viga", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean continuePlaying = true;
        while (continuePlaying) {
            int bet = getBetAmount();
            if (bet == 0) {
                // Kontrollime, kas panus on korrektne, vastasel juhul väljastame veateate ja lõpetame meetodi.
                JOptionPane.showMessageDialog(null, "Vigane panus! Mäng lõpetatakse.", "Täringud", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int userBetNumber = getUserBetNumber();

            int playerRoll = Dice.roll();
            int dealerRoll = Dice.roll();

            // Väljastame mängija ja diileri veeretuse tulemuse.
            JOptionPane.showMessageDialog(null, "Teie veeretus: " + playerRoll + "\nDiileri veeretus: " + dealerRoll, "Täringud", JOptionPane.INFORMATION_MESSAGE);

            if (userBetNumber == dealerRoll) {
                // Kui mängija arvas numbri õigesti, väljastame võiduteate ja värskendame mängija kontot.
                JOptionPane.showMessageDialog(null, "Võitsid! Palju õnne!", "Täringud", JOptionPane.INFORMATION_MESSAGE);
                try {
                    accountManager.updateMoney(currentAccount.getEmail(), bet);
                } catch (FileNotFoundException | UnsupportedEncodingException e) {
                    JOptionPane.showMessageDialog(null, "Konto saldo värskendamise viga!", "Täringud", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Kui mängija ei arvanud numbri õigesti, väljastame kaotusteate ja värskendame mängija kontot.
                JOptionPane.showMessageDialog(null, "Kaotasid! Proovi uuesti!", "Täringud", JOptionPane.ERROR_MESSAGE);
                try {
                    accountManager.updateMoney(currentAccount.getEmail(), -bet);
                } catch (FileNotFoundException | UnsupportedEncodingException e) {
                    JOptionPane.showMessageDialog(null, "Konto saldo värskendamise viga!", "Täringud", JOptionPane.ERROR_MESSAGE);
                }
            }

            // Küsime kasutajalt, kas nad soovivad mängu jätkata.
            int choice = JOptionPane.showConfirmDialog(null, "Kas soovite jätkata mängu?", "Täringud", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.NO_OPTION) {
                // Kui kasutaja valib "Ei", peatame mängu.
                continuePlaying = false;
            }
        }
        // Pärast mängu lõppu kuvame peamenüü praegusele kasutajale.
        showGameMenu(currentAccount);
    }

    private int getUserBetNumber() {
        int betNumber;
        while (true) {
            try {
                String betNumberString = JOptionPane.showInputDialog(null, "Sisestage oma ennustatav number (1-6):", "Panus", JOptionPane.QUESTION_MESSAGE);

                // Kontrollime, kas kasutaja katkestas tegevuse või tühjendas sisestusakna
                if (betNumberString == null) {
                    return 0;
                }

                // Teisendame sisestatud stringi arvuks
                betNumber = Integer.parseInt(betNumberString);

                // Kontrollime, kas sisestatud number on sobivas vahemikus
                if (betNumber < 1 || betNumber > 6) {
                    JOptionPane.showMessageDialog(null, "Palun sisestage number vahemikus 1 kuni 6!", "Vigane sisend", JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                // Kui sisestatud number on sobivas vahemikus, lõpetame tsükli
                break;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Vigane sisend! Palun sisestage ainult numbreid.", "Vigane sisend", JOptionPane.ERROR_MESSAGE);
            }
        }
        return betNumber;
    }






}
