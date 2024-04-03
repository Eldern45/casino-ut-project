import javax.swing.JOptionPane;
import java.util.Random;

public class Rulett {
    private final Account account;
    private final AccountManager accountManager;

    // Konstruktor Ruletti objekti lähtestamiseks konto ja kontohalduriga

    public Rulett(Account account, AccountManager accountManager) {
        this.account = account;
        this.accountManager = accountManager;
    }

    // Ruletimängu mängimise meetod
    public void MängiRuletti() {
        // Kuva tervitussõnum
        JOptionPane.showMessageDialog(null, "Tere tulemast mängima ruletti!", "Rulett", JOptionPane.INFORMATION_MESSAGE);

        // Lipp mängutsükli juhtimiseks
        boolean playing = true;
        while (playing) {
            // Tee panus
            int betAmount = Panustada();
            if (betAmount == -1) {
                JOptionPane.showMessageDialog(null, "Viga panuse tegemisel! Palun proovige uuesti.", "Rulett", JOptionPane.ERROR_MESSAGE);
                continue;
            } else if (betAmount == 0) {
                JOptionPane.showMessageDialog(null, "Panuse tegemine tühistatud. Naerame järgmisel korral!", "Rulett", JOptionPane.INFORMATION_MESSAGE);
                break;
            }

            // Valige arv, millele panustada
            int betOn = ValiNumber();
            if (betOn == -1) {
                JOptionPane.showMessageDialog(null, "Viga panuse valimisel! Palun proovige uuesti.", "Rulett", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            // Keerake ruletiratast
            int winningNumber = KeerutaRuletti();
            JOptionPane.showMessageDialog(null, "Õnn mänguks! Ratta number on " + winningNumber + ".", "Rulett", JOptionPane.INFORMATION_MESSAGE);

            // Kontrollige, kas panus võidab või kaotab
            if (winningNumber == betOn) {
                // Kui panus võidab, uuenda konto saldot
                double winnings = betAmount * 36;
                account.setMoney(account.getMoney() + winnings);
                JOptionPane.showMessageDialog(null, "Palju õnne! Te võitsite " + winnings + " eurot!", "Rulett", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Kui panus kaotab, teavita sellest kasutajat
                JOptionPane.showMessageDialog(null, "Kahjuks te ei võitnud. Proovige uuesti!", "Rulett", JOptionPane.INFORMATION_MESSAGE);
            }
            // Küsige kasutajalt, kas ta soovib uuesti mängida
            playing = KüsiMängidaVeelkord();
        }
    }

    private int Panustada() {
        while (true) {
            // Paluge kasutajal sisestada oma panuse summa, mis võimaldab tühistamist sisestades 0

            String betAmountString = JOptionPane.showInputDialog(null, "Palun sisestage oma panussumma (tühistamiseks sisestage 0):", "Panuse tegemine", JOptionPane.QUESTION_MESSAGE);
            // Tagastab 0, kui kasutaja tühistab panuse

            if (betAmountString == null) return 0;
            try {
                // Parsi sisendstring täisarvuks

                int betAmount = Integer.parseInt(betAmountString);
                // Kontrollige, kas panuse summa jääb kehtivasse vahemikku ega ületa saadaolevat saldot

                if (betAmount < 0 || betAmount > account.getMoney()) {
                    // Kui panuse summa on kehtetu, kuvage veateade

                    JOptionPane.showMessageDialog(null, "Vigane panussumma! Palun sisestage summa vahemikus 0 kuni " + account.getMoney() + ".", "Panuse tegemine", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Võta panuse summa kasutaja saldost maha ja tagasta panuse summa

                    account.setMoney(account.getMoney() - betAmount);
                    return betAmount;
                }
            } catch (NumberFormatException e) {
                // Kui sisendit ei saa täisarvuks sõeluda, kuvage veateade

                JOptionPane.showMessageDialog(null, "Vigane sisend! Palun sisestage ainult täisarvuline summa.", "Panuse tegemine", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private int ValiNumber() {
        while (true) {
            // Paluge kasutajal sisestada arv, millele ta soovib panustada vahemikus 0–36

            String valiNumberString = JOptionPane.showInputDialog(null, "Palun sisestage number, millele soovite panustada (0-36):", "Panuse tegemine", JOptionPane.QUESTION_MESSAGE);
            // Tagastamine -1, kui kasutaja tühistab panuse
            if (valiNumberString == null) return -1;
            try {
                // Parsi sisendstring täisarvuks

                int valitudNumber = Integer.parseInt(valiNumberString);
                // Kontrollige, kas valitud arv on kehtivas vahemikus

                if (valitudNumber < 0 || valitudNumber > 36) {
                    // Kui valitud number on kehtetu, kuvatakse veateade

                    JOptionPane.showMessageDialog(null, "Vigane number! Palun sisestage number vahemikus 0 kuni 36.", "Panuse tegemine", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Tagastab valitud numbri

                    return valitudNumber;
                }
            } catch (NumberFormatException e) {
                // Kui sisendit ei saa täisarvuks sõeluda, kuvage veateade
                JOptionPane.showMessageDialog(null, "Vigane sisend! Palun sisestage ainult täisarvuline number.", "Panuse tegemine", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private int KeerutaRuletti() {
        // Looge juhuslik arv, mis tähistab ruletiratta pöörlemist, vahemik 0-36

        Random random = new Random();
        return random.nextInt(37);
    }

    private boolean KüsiMängidaVeelkord() {
        // Küsi kasutajalt, kas ta soovib uuesti mängida, tagastades tõese jah ja false, kui ei

        int choice = JOptionPane.showConfirmDialog(null, "Kas soovite uuesti mängida?", "Rulett", JOptionPane.YES_NO_OPTION);
        return choice == JOptionPane.YES_OPTION;
    }
}
