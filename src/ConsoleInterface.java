import javax.swing.*;

import java.awt.Window;
import java.awt.Toolkit;
import java.awt.AWTEvent;
import java.awt.event.WindowEvent;
import java.awt.event.AWTEventListener;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class ConsoleInterface {
    AccountManager accountManager= new AccountManager();
    public ConsoleInterface() throws FileNotFoundException {
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            public void eventDispatched(AWTEvent event) {
                if (event instanceof WindowEvent) {
                    WindowEvent we = (WindowEvent) event;
                    if (we.getID() == WindowEvent.WINDOW_CLOSING) {
                        ((Window) event.getSource()).dispose();
                        try {
                            accountManager.saveAccounts();
                        } catch (UnsupportedEncodingException | FileNotFoundException e) {
                            throw new RuntimeException(e);
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
            int trueFalse = JOptionPane.showConfirmDialog(null, jPanel, "Konto loomine",
                    JOptionPane.OK_CANCEL_OPTION);
            if (trueFalse == JOptionPane.CANCEL_OPTION) break;
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
            int trueFalse = JOptionPane.showConfirmDialog(null, jPanel, "Logimine", JOptionPane.OK_CANCEL_OPTION);
            if (trueFalse == JOptionPane.CANCEL_OPTION) break;
            String password = new String(jPasswordField.getPassword());

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

    private void showMenu(Account account) {
        boolean running = true;
        while (running) {
            String[] options = {"Näita jääki", "Muuda kasutajanime", "Muuda parooli", "Logi välja"};
            int choice = JOptionPane.showOptionDialog(null, "Valige toiming:", "Menüü",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            switch (choice) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Teie jääk: " + account.getMoney()
                                    + " eurot" , "Kasiino", JOptionPane.INFORMATION_MESSAGE);
                    break;
                case 1:
//                TODO: changeLogin(account);
                    break;
                case 2:
//                TODO: changePassword(account);
                    break;
                default:
                    running = false;
            }
        }
    }
}
