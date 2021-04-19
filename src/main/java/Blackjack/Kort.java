package Blackjack;

public class Kort {

    String suit;
    String value;

    public Kort() {

    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    Kort (String suit, String value){
        this.suit = suit;
        this.value = value;

    }

    @Override
    public String toString() {
        return suit + " " + value;
    }
}
