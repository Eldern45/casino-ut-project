import javax.swing.JOptionPane;
import java.util.Random;


public class Rulett {
    private Account account;
    private AccountManager accountManager;

    public Rulett(Account account, AccountManager accountManager) {
        this.account = account;
        this.accountManager = accountManager;
    }

    public void MängiRuletti() {
        JOptionPane.showMessageDialog(null, "Tere tulemast mängima ruletti!", "Rulett", JOptionPane.INFORMATION_MESSAGE);

        boolean playing = true;
        while (playing) {
            int betAmount = Panustada();
            if (betAmount == -1) {
                JOptionPane.showMessageDialog(null, "Viga panuse tegemisel! Palun proovige uuesti.", "Rulett", JOptionPane.ERROR_MESSAGE);
                continue;
            } else if (betAmount == 0) {
                JOptionPane.showMessageDialog(null, "Panuse tegemine tühistatud. Naerame järgmisel korral!", "Rulett", JOptionPane.INFORMATION_MESSAGE);
                break;
            }

            int betOn = ValiNumber();
            if (betOn == -1) {
                JOptionPane.showMessageDialog(null, "Viga panuse valimisel! Palun proovige uuesti.", "Rulett", JOptionPane.ERROR_MESSAGE);
                continue;
            }

            int winningNumber = KeerutaRuletti();
            JOptionPane.showMessageDialog(null, "Õnn mänguks! Ratta number on " + winningNumber + ".", "Rulett", JOptionPane.INFORMATION_MESSAGE);

            if (winningNumber == betOn) {
                double winnings = betAmount * 36;
                account.setMoney(account.getMoney() + winnings);
                JOptionPane.showMessageDialog(null, "Palju õnne! Te võitsite " + winnings + " eurot!", "Rulett", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Kahjuks te ei võitnud. Proovige uuesti!", "Rulett", JOptionPane.INFORMATION_MESSAGE);
            }

            playing = KüsiMängidaVeelkord();
        }
    }

    private int Panustada() {
        while (true) {
            String betAmountString = JOptionPane.showInputDialog(null, "Palun sisestage oma panussumma (tühistamiseks sisestage 0):", "Panuse tegemine", JOptionPane.QUESTION_MESSAGE);
            if (betAmountString == null) return 0;
            try {
                int betAmount = Integer.parseInt(betAmountString);
                if (betAmount < 0 || betAmount > account.getMoney()) {
                    JOptionPane.showMessageDialog(null, "Vigane panussumma! Palun sisestage summa vahemikus 0 kuni " + account.getMoney() + ".", "Panuse tegemine", JOptionPane.ERROR_MESSAGE);
                } else {
                    account.setMoney(account.getMoney() - betAmount);
                    return betAmount;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Vigane sisend! Palun sisestage ainult täisarvuline summa.", "Panuse tegemine", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private int ValiNumber() {
        while (true) {
            String ValiNumberString = JOptionPane.showInputDialog(null, "Palun sisestage number, millele soovite panustada (0-36):", "Panuse tegemine", JOptionPane.QUESTION_MESSAGE);
            if (ValiNumberString == null) return -1;
            try {
                int ValitudNumber = Integer.parseInt(ValiNumberString);
                if (ValitudNumber < 0 || ValitudNumber > 36) {
                    JOptionPane.showMessageDialog(null, "Vigane number! Palun sisestage number vahemikus 0 kuni 36.", "Panuse tegemine", JOptionPane.ERROR_MESSAGE);
                } else {
                    return ValitudNumber;
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Vigane sisend! Palun sisestage ainult täisarvuline number.", "Panuse tegemine", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private int KeerutaRuletti() {
        Random random = new Random();
        return random.nextInt(37);
    }

    private boolean KüsiMängidaVeelkord() {
        int choice = JOptionPane.showConfirmDialog(null, "Kas soovite uuesti mängida?", "Rulett", JOptionPane.YES_NO_OPTION);
        return choice == JOptionPane.YES_OPTION;
    }
}
