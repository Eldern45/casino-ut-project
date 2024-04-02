import javax.swing.*;
import javax.swing.JOptionPane;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;


public class Blackjack {
    private AccountManager accountManager;
    private Deck deck;
    private Hand playerHand;
    private Hand dealerHand;

    public Blackjack(AccountManager accountManager) {
        this.accountManager = accountManager;
        this.deck = new Deck();
        this.playerHand = new Hand();
        this.dealerHand = new Hand();
    }

    public void MängiBlackjack(Account account) {
        if (account == null) {
            JOptionPane.showMessageDialog(null, "Teil peab olema konto, et mängida blackjacki!", "Blackjack", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (deck.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Kaardipakk on tühi! Mäng ei ole võimalik.", "Blackjack", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Deck deck = new Deck();
        deck.shuffle();

        Hand playerHand = new Hand();
        Hand dealerHand = new Hand();

        playerHand.addCard(deck.dealCard());
        playerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());

        JOptionPane.showMessageDialog(null, "Sinu kaardid:\n" + playerHand + "\nSinu summa: " + playerHand.getHandValue()
                + "\n\nDiileri kaardid:\n" + dealerHand.getHand().get(0), "Blackjack", JOptionPane.INFORMATION_MESSAGE);

        if (playerHand.isBlackjack()) {
            JOptionPane.showMessageDialog(null, "Sinul on Blackjack! Võidad automaatselt!", "Blackjack",
                    JOptionPane.INFORMATION_MESSAGE);
            account.updateMoney(account.getMoney() + account.getBet() * 2.5);
            return;
        }


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

        if (playerHand.getHandValue() > 21) {
            JOptionPane.showMessageDialog(null, "Sinu summa on üle 21! Kaotad!", "Blackjack",
                    JOptionPane.ERROR_MESSAGE);
            account.updateMoney(account.getMoney() - account.getBet());
            return;
        }


        while (dealerHand.getHandValue() < 17) {
            dealerHand.addCard(deck.dealCard());
        }

        JOptionPane.showMessageDialog(null, "Sinu kaardid:\n" + playerHand + "\nSinu summa: " + playerHand.getHandValue()
                        + "\n\nDiileri kaardid:\n" + dealerHand + "\nDiileri summa: " + dealerHand.getHandValue(), "Blackjack",
                JOptionPane.INFORMATION_MESSAGE);

        if (dealerHand.getHandValue() > 21) {
            JOptionPane.showMessageDialog(null, "Diileril on summa üle 21! Võidad!", "Blackjack",
                    JOptionPane.INFORMATION_MESSAGE);
            account.updateMoney(account.getMoney() + account.getBet() * 2);
            return;
        }

        if (playerHand.getHandValue() > dealerHand.getHandValue()) {
            JOptionPane.showMessageDialog(null, "Võidad!", "Blackjack", JOptionPane.INFORMATION_MESSAGE);
            account.updateMoney(account.getMoney() + account.getBet() * 2);
        } else if (playerHand.getHandValue() < dealerHand.getHandValue()) {
            JOptionPane.showMessageDialog(null, "Kaotad!", "Blackjack", JOptionPane.ERROR_MESSAGE);
            account.updateMoney(account.getMoney() - account.getBet());
        } else {
            JOptionPane.showMessageDialog(null, "Viik!", "Blackjack", JOptionPane.INFORMATION_MESSAGE);
        }
    }


}
