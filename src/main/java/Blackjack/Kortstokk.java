package Blackjack;

import Blackjack.Kort;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


public class Kortstokk {
    private static HttpURLConnection connection;
    StringBuffer EndpointResponse = new StringBuffer();
    ArrayList<Kort> kortstokk;

    public Kortstokk(){
        this.kortstokk = new ArrayList<Kort>();
    }

    public void hentKortStokk() {
        String line;
        BufferedReader reader;
        try {
            URL url = new URL("http://nav-deckofcards.herokuapp.com/shuffle");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(6000);
            int status = connection.getResponseCode();

            if (status != 200) {
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while ((line = reader.readLine()) != null) {
                    EndpointResponse.append(line);
                }
                reader.close();
            } else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    EndpointResponse.append(line);
                }
                reader.close();
            }
            //System.out.println(EndpointResponse.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseKortStokk(){
        JSONArray jsonarray = new JSONArray(EndpointResponse.toString());

        for(int i=0; i<jsonarray.length(); i++){
            JSONObject obj = jsonarray.getJSONObject(i);

            String suit = obj.getString("suit");
            String value = obj.getString("value");

            Kort kortet = new Kort(suit,value);
            kortstokk.add(kortet);
        }
    }
    public int deckSize(){
        return this.kortstokk.size();
    }

    public Kort getCard(int i){
        return this.kortstokk.get(i);
    }

    public void removeCard(int i){
        this.kortstokk.remove(i);
    }

    //henter kort fra kortstokken
    public void draw(Kortstokk comingFrom){
        this.kortstokk.add(comingFrom.getCard(0));
        comingFrom.removeCard(0);
    }

    public int cardValue(){
        int totalValue = 0;
        int aces = 0;

        for (Kort aCard : this.kortstokk){
            switch (aCard.getValue()){
                case "2": totalValue +=2; break;
                case "3": totalValue +=3; break;
                case "4": totalValue +=4; break;
                case "5": totalValue +=5; break;
                case "6": totalValue +=6; break;
                case "7": totalValue +=7; break;
                case "8": totalValue +=8; break;
                case "9": totalValue +=9; break;
                case "10":totalValue +=10; break;
                case "J": totalValue +=10; break;
                case "Q": totalValue +=10; break;
                case "K": totalValue +=10; break;
                case "A": aces +=1; break;
            }
        }
        for (int i = 0; i < aces; i++){
            if (totalValue > 10){
                totalValue += 1;
            }
            else {
                totalValue += 11;
            }
        }
        return totalValue;
    }

    @Override
    public String toString() {
        return kortstokk.toString();
    }
}