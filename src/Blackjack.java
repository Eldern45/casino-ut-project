import javax.swing.*;
import javax.swing.JOptionPane;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;


public class Blackjack {
    // Klassimuutujate deklareerimine
    private AccountManager accountManager;
    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;
    private int currentBet;

    // Konstruktor initsialiseerib Blackjacki mängu kontohalduri eksemplariga
    public Blackjack(AccountManager accountManager) {
        this.accountManager = accountManager;
        this.deck = new Deck();
        this.playerHand = new Hand();
        this.dealerHand = new Hand();
    }

    // Blackjacki mängu mängimise meetod
    public void MängiBlackjack(Account account) {
        // Kontrollige, kas mängijal on konto
        if (account == null) {
            JOptionPane.showMessageDialog(null, "You need to have an account to play Blackjack!", "Blackjack", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Kontrollige, kas tekk on tühi
        if (deck.isEmpty()) {
            JOptionPane.showMessageDialog(null, "The card deck is empty! The game cannot be played.", "Blackjack", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Paluge mängijal panus teha
        int bet = getBetAmount();
        if (bet == 0) {
            JOptionPane.showMessageDialog(null, "Invalid bet! Game aborted.", "Blackjack", JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.currentBet = bet;

        // Initsialiseerige uus teki ja segage see
        Deck deck = new Deck();
        deck.shuffle();

        // Initsialiseerige mängija ja diileri käed
        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();

        // Jaga kaardid mängijale ja diilerile
        playerHand.addCard(deck.dealCard());
        playerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());

        // Kuva mängija ja diileri algkaardid
        JOptionPane.showMessageDialog(null, "Your cards:\n" + playerHand + "\nYour total: " + playerHand.getHandValue()
                + "\n\nDealer's cards:\n" + dealerHand.getHand().get(0), "Blackjack", JOptionPane.INFORMATION_MESSAGE);

        // Kontrollige, kas mängijal on Blackjack
        if (playerHand.isBlackjack()) {
            JOptionPane.showMessageDialog(null, "You have Blackjack! You win automatically!", "Blackjack",
                    JOptionPane.INFORMATION_MESSAGE);
            account.updateMoney(account.getMoney() + account.getBet() * 2);
            return;
        }

        // See silmus võimaldab mängijal jätkata kaartide tõmbamist seni, kuni nende käe väärtus on väiksem kui 21.
        while (playerHand.getHandValue() < 21) {
            int option = JOptionPane.showConfirmDialog(null, "Kas soovid jätkata kaartide võtmisega?", "Blackjack",
                    JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                playerHand.addCard(deck.dealCard());
                JOptionPane.showMessageDialog(null, "Sinu kaardid:\n" + playerHand + "\nSinu summa: " + playerHand.getHandValue(),
                        "Blackjack", JOptionPane.INFORMATION_MESSAGE);
            } else {
                break;
            }
        }

        // Kui mängija käe väärtus ületab 21, kaotab ta mängu.
        if (playerHand.getHandValue() > 21) {
            JOptionPane.showMessageDialog(null, "Sinu summa on üle 21! Kaotad!", "Blackjack",
                    JOptionPane.ERROR_MESSAGE);
            account.updateMoney(account.getMoney() - account.getBet());
            return;
        }

        // Diiler tõmbab kaarte, kuni nende käe väärtus jõuab 17-ni või rohkem.

        while (dealerHand.getHandValue() < 17) {
            dealerHand.addCard(deck.dealCard());
        }

        // Kuvage mängija ja diileri käed koos nende vastavate kogusummadega.
        JOptionPane.showMessageDialog(null, "Sinu kaardid:\n" + playerHand + "\nSinu summa: " + playerHand.getHandValue()
                        + "\n\nDiileri kaardid:\n" + dealerHand + "\nDiileri summa: " + dealerHand.getHandValue(), "Blackjack",
                JOptionPane.INFORMATION_MESSAGE);

        // Kui diileri käe väärtus ületab 21, võidab mängija.
        if (dealerHand.getHandValue() > 21) {
            JOptionPane.showMessageDialog(null, "Diileril on summa üle 21! Võidad!", "Blackjack",
                    JOptionPane.INFORMATION_MESSAGE);
            account.updateMoney(account.getMoney() + account.getBet() * 2);
            return;
        }


        // Võitja väljaselgitamiseks võrrelge mängija ja diileri käte väärtusi.
        if (playerHand.getHandValue() > dealerHand.getHandValue()) {
            JOptionPane.showMessageDialog(null, "Võidad!", "Blackjack", JOptionPane.INFORMATION_MESSAGE);
            account.updateMoney(account.getMoney() + account.getBet() * 2);
        } else if (playerHand.getHandValue() < dealerHand.getHandValue()) {
            JOptionPane.showMessageDialog(null, "Kaotad!", "Blackjack", JOptionPane.ERROR_MESSAGE);
            account.updateMoney(account.getMoney() - account.getBet());
        } else {
            JOptionPane.showMessageDialog(null, "Viik!", "Blackjack", JOptionPane.INFORMATION_MESSAGE);
        }

        // Küsi mängijalt, kas ta soovib mängimist jätkata.
        while (true) {
            int option = JOptionPane.showConfirmDialog(null, "Kas soovid jätkata mängimist?", "Blackjack", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                MängiBlackjack(account);
            } else {
                break;
            }
        }
    }


    public void setBet(int bet) {
        // See meetod määrab praeguse panuse summa.
        this.currentBet = bet;
    }

    private int getBetAmount() {
        int bet;
        try {
            // Palub kasutajal sisestada dialoogiboksi kaudu panuse summa.
            String betString = JOptionPane.showInputDialog(null, "Sisestage panuse summa:", "Panus", JOptionPane.QUESTION_MESSAGE);
            // Kui sisend on tühi või tühi, tagastage 0.
            if (betString == null || betString.isEmpty()) {
                return 0;
            }
            // Parsib sisendstringi täisarvuks, mis tähistab panuse summat.
            bet = Integer.parseInt(betString);
            // Kui panuse summa on väiksem või võrdne 0-ga, tagastage 0.
            if (bet <= 0) {
                return 0;
            }
        } catch (NumberFormatException e) {
            // Kui on vorminguerand (nt kasutaja sisestus ei ole kehtiv arv), tagastage 0.
            return 0;
        }
        // Tagastab kehtiva panusesumma.
        return bet;
    }


}
