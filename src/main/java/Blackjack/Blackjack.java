package Blackjack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;


public class Blackjack {

    public static void main(String[] args) {
        Kortstokk playingDeck = new Kortstokk();
        playingDeck.hentKortStokk();
        playingDeck.parseKortStokk();

        //boolsk verdi for å avslutte runde
        boolean endGame = false;

        //velkommen mld
        System.out.println("Velkommen til blackjack \n");

        //lager en kortstokk for spiller
        Kortstokk playerDeck = new Kortstokk();
        //lager en kortstokk for marit
        Kortstokk dealerDeck = new Kortstokk();


        //spiller får 2 kort
        playerDeck.draw(playingDeck);
        playerDeck.draw(playingDeck);

        //Marit får 2 kort
        dealerDeck.draw(playingDeck);
        dealerDeck.draw(playingDeck);

        //While loop for spillet
        while (true) {
            System.out.println("kort i din hånd");
            System.out.println(playerDeck.toString());
            System.out.println("kort i din hånd er verdt: " + playerDeck.cardValue());

            //vis marit sin hånd
            System.out.println("marit sine kort: ");
            System.out.println(dealerDeck.toString());
            System.out.println("kort i hennes hånd er verdt: " + dealerDeck.cardValue());


            //hva vil spilleren gjøre
            System.out.println("Vil du (1)trekke kort eller (2)gi deg: ");
            Scanner userInput = new Scanner(System.in);
            int respons = userInput.nextInt();

            //om spilleren vil trekke kort
            if (respons == 1) {
                playerDeck.draw(playingDeck);
                System.out.println("du trakk ett: " + playerDeck.getCard(playerDeck.deckSize() - 1).toString());
                //bust hvis > 21
                if (playerDeck.cardValue() > 21) {
                    System.out.println("Vinner: " + "Marit \n" + "Marit | " + dealerDeck.cardValue() +" | " + dealerDeck.toString() +
                            "\n" + "Spiller | " + playerDeck.cardValue() + " | " + playerDeck.toString());
                    endGame = true;
                    break;
                }
            }

            //trekker når 16 men stopper når 17
            while ((playerDeck.cardValue() <= 17) && endGame == false) {
                playerDeck.draw(playingDeck);
                System.out.println("spiller trekker: " + playerDeck.getCard(playerDeck.deckSize() - 1).toString());
            }
            //om spiller vil gi seg
            if (respons == 2) {
                break;
            }
        }
        //vis marit sine kort
        System.out.println("Marit sine kort: " + dealerDeck.toString());
        //sjekk om marit har høyere poeng enn spiller
        if ((dealerDeck.cardValue() > playerDeck.cardValue()) && endGame == false) {
            System.out.println("Marit vinner! \n");
            endGame = true;
        }
        //vis marit sin kort verdi
        System.out.println("Marit har så mange poeng: " + dealerDeck.cardValue());
        //sjekk om marit tapte
        if ((dealerDeck.cardValue() >= 21) && endGame == false) {
            System.out.println("Marit tapte, spiller vinner \n");
            endGame = true;
        }

        //sjekker om det er uavgjort
        if ((playerDeck.cardValue() == dealerDeck.cardValue()) && endGame == false) {
            System.out.println("uavgjort");
            endGame = true;
        }

        if ((playerDeck.cardValue() > dealerDeck.cardValue()) && endGame == false) {
            System.out.println("Spiller vinner" + "\n" + "Marit | " + dealerDeck.cardValue() +" | " + dealerDeck.toString() +
                    "\n" + "Spiller | " + playerDeck.cardValue() + " | " + playerDeck.toString());
            endGame = true;
        }

        System.out.println("slutt på spillet. Ha en fin dag videre");
    }
}




