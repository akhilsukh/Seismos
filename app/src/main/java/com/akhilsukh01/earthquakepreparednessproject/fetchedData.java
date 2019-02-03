package com.akhilsukh01.earthquakepreparednessproject;

import android.graphics.Color;
import android.os.AsyncTask;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by akhil on 3/25/2018.
 */

public class fetchedData extends AsyncTask<Void, Void, Void> {

    String data = "";
    private String singlePlace = "";
    private String singleCoordinates = "";
    String time;
    private double singleMag = 0.0;
    private long singleTime = 0;
    public static int counter = 0;
    private String realTime = "";



    static ArrayList<String> allPlaces = new ArrayList<>();
    static ArrayList<String> allPlacesF = new ArrayList<>();

    static ArrayList<Double> allMag = new ArrayList<>();
    static ArrayList<Double> allMagF = new ArrayList<>();

    static ArrayList<Long> allDate = new ArrayList<>();
    static ArrayList<String> allDateS = new ArrayList<>();

    private ArrayList<String> allCoordinates = new ArrayList<>();
    static ArrayList<String> allCoordinatesF = new ArrayList<>();

    static ArrayList<Double> allLat = new ArrayList<>();
    static ArrayList<Double> allLong = new ArrayList<>();


    @Override
    protected Void doInBackground(Void... voids) {

        try {
            URL url = new URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/4.5_day.geojson");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";

            while (line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }

            allPlaces.clear();
            allMag.clear();
            allMagF.clear();
            allDate.clear();
            allDateS.clear();
            allPlacesF.clear();
            allCoordinates.clear();
            allCoordinatesF.clear();
            allLat.clear();
            allLong.clear();

            //PARSING JSON
            JSONObject root = new JSONObject(data);
            JSONArray features = root.getJSONArray("features");
            for (int i = 0; i < features.length(); i++) {

                JSONObject properties = features.getJSONObject(i).getJSONObject("properties");
                double mag = properties.getDouble("mag");
                String place = properties.getString("place");
                long time = properties.getLong("time");
//                singlePlace = place;
//                singleMag = mag;
//                singleTime = time;
                allPlaces.add(place);
                allMag.add(mag);
                allDate.add(time);

                JSONObject mFeatures = features.getJSONObject(i).getJSONObject("geometry");
                String coordinates = mFeatures.getString("coordinates");
                singleCoordinates = coordinates;
                allCoordinates.add(coordinates);
            }
            counter = allDate.size();
            //END OF PARSING


            //Formatting the title to remove words before "of"
            for (int n = 0; n < counter; n++) {
                String sentence = String.valueOf(fetchedData.allPlaces.get(n));
                String search  = "of";
                if ( sentence.toLowerCase().indexOf(search.toLowerCase()) != -1 ) {
                    String sFormatted = sentence.substring(sentence.indexOf("of") + 3);
                    sFormatted.trim();
                    allPlacesF.add(sFormatted);
                } else {
                    allPlacesF.add(allPlaces.get(n));
                }
            }

            for (int n = 0; n < counter; n++) {
                String test = allCoordinates.get(n);
                String lastWord = test.substring(0, test.lastIndexOf(","));
                String firstWord = lastWord.substring(1);
                String[] latLong = firstWord.split(",");
                allLat.add(Double.valueOf(latLong[1]));
                allLong.add(Double.valueOf(latLong[0]));
                allCoordinatesF.add((latLong[0]) + "," + Double.valueOf(latLong[1]));
            }
            //END OF FORMATTING


            long milliNow = System.currentTimeMillis();
            SimpleDateFormat fNow = new SimpleDateFormat("M/d");
            Date now = new Date();
            String strDate = fNow.format(now);
            for (int n =0; n < counter; n++){
                if (milliNow - allDate.get(n) < 7200000){
                    long under = milliNow - allDate.get(n);
                    long finalLeft = under/60000;
                    allDateS.add(finalLeft + " minutes ago");
                }
                else {
                    Date dateTime = new Date(allDate.get(n));
                    SimpleDateFormat ifNotToday = new SimpleDateFormat("M/d h:mm a");
                    SimpleDateFormat ifToday = new SimpleDateFormat("h:mm a");
                    SimpleDateFormat checkIfToday = new SimpleDateFormat("M/d");
                    realTime = ifNotToday.format(dateTime);
                    String todaysDate = ifToday.format(dateTime);
                    String nowDate = checkIfToday.format(dateTime);
                    if (strDate.equals(nowDate)){
                        allDateS.add("Today at " + todaysDate);
                    }
                    else {
                        allDateS.add(realTime);
                    }
                }
            }


            for (int n =0; n < counter; n++){
//                Double preRound = allMag.get(n);
//                DecimalFormat df = new DecimalFormat("#.#");
//                System.out.println(df.format(preRound));
//                Double postRound =  Double.valueOf(new DecimalFormat("0.0").format(preRound));
//                allMagF.add(Double.valueOf(df.format(preRound)));
                allMagF.add(allMag.get(n));
            }


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);



        if (counter >= 6){
            IntroHome.locat1.setText(allPlaces.get(0));
            IntroHome.locat2.setText(allPlaces.get(1));
            IntroHome.locat3.setText(allPlaces.get(2));
            IntroHome.locat4.setText(allPlaces.get(3));
            IntroHome.locat5.setText(allPlaces.get(4));
            IntroHome.locat6.setText(allPlaces.get(5));

            IntroHome.mag1.setText(allMag.get(0).toString());
            IntroHome.mag2.setText(allMag.get(1).toString());
            IntroHome.mag3.setText(allMag.get(2).toString());
            IntroHome.mag4.setText(allMag.get(3).toString());
            IntroHome.mag5.setText(allMag.get(4).toString());
            IntroHome.mag6.setText(allMag.get(5).toString());
        }
        else if (counter == 5){
            IntroHome.locat1.setText(allPlaces.get(0));
            IntroHome.locat2.setText(allPlaces.get(1));
            IntroHome.locat3.setText(allPlaces.get(2));
            IntroHome.locat4.setText(allPlaces.get(3));
            IntroHome.locat5.setText(allPlaces.get(4));

            IntroHome.mag1.setText(allMag.get(0).toString());
            IntroHome.mag2.setText(allMag.get(1).toString());
            IntroHome.mag3.setText(allMag.get(2).toString());
            IntroHome.mag4.setText(allMag.get(3).toString());
            IntroHome.mag5.setText(allMag.get(4).toString());

            IntroHome.locat6.setVisibility(View.GONE);
            IntroHome.mag6.setVisibility(View.GONE);

        }
        else if (counter == 4){
            IntroHome.locat1.setText(allPlaces.get(0));
            IntroHome.locat2.setText(allPlaces.get(1));
            IntroHome.locat3.setText(allPlaces.get(2));
            IntroHome.locat4.setText(allPlaces.get(3));

            IntroHome.mag1.setText(allMag.get(0).toString());
            IntroHome.mag2.setText(allMag.get(1).toString());
            IntroHome.mag3.setText(allMag.get(2).toString());
            IntroHome.mag4.setText(allMag.get(3).toString());

            IntroHome.locat5.setVisibility(View.GONE);
            IntroHome.mag5.setVisibility(View.GONE);
            IntroHome.locat6.setVisibility(View.GONE);
            IntroHome.mag6.setVisibility(View.GONE);
        }
        else if (counter == 3){
            IntroHome.locat1.setText(allPlaces.get(0));
            IntroHome.locat2.setText(allPlaces.get(1));
            IntroHome.locat3.setText(allPlaces.get(2));

            IntroHome.mag1.setText(allMag.get(0).toString());
            IntroHome.mag2.setText(allMag.get(1).toString());
            IntroHome.mag3.setText(allMag.get(2).toString());

            IntroHome.locat4.setVisibility(View.GONE);
            IntroHome.mag4.setVisibility(View.GONE);
            IntroHome.locat5.setVisibility(View.GONE);
            IntroHome.mag5.setVisibility(View.GONE);
            IntroHome.locat6.setVisibility(View.GONE);
            IntroHome.mag6.setVisibility(View.GONE);
        }
        else if (counter == 2){
            IntroHome.locat1.setText(allPlaces.get(0));
            IntroHome.locat2.setText(allPlaces.get(1));

            IntroHome.mag1.setText(allMag.get(0).toString());
            IntroHome.mag2.setText(allMag.get(1).toString());

            IntroHome.locat3.setVisibility(View.GONE);
            IntroHome.mag3.setVisibility(View.GONE);
            IntroHome.locat4.setVisibility(View.GONE);
            IntroHome.mag4.setVisibility(View.GONE);
            IntroHome.locat5.setVisibility(View.GONE);
            IntroHome.mag5.setVisibility(View.GONE);
            IntroHome.locat6.setVisibility(View.GONE);
            IntroHome.mag6.setVisibility(View.GONE);
        }



        double dMag1 = Double.parseDouble(IntroHome.mag1.getText().toString());
        double dMag2 = Double.parseDouble(IntroHome.mag2.getText().toString());
        double dMag3 = Double.parseDouble(IntroHome.mag3.getText().toString());
        double dMag4 = Double.parseDouble(IntroHome.mag4.getText().toString());
        double dMag5 = Double.parseDouble(IntroHome.mag5.getText().toString());
        double dMag6 = Double.parseDouble(IntroHome.mag6.getText().toString());


        String parse1 = ("#" + "DBB9B9");
        String parse2 = ("#" + "DE8F8F");
        String parse3 = ("#" + "CB8787");
        String parse4 = ("#" + "D54D4D");
        String parse5 = ("#" + "D13E3E");
        String parse6 = ("#" + "ED0808");



//        if (dMag1 >= 2.5 && dMag1 < 4.0){
//            IntroHome.mag1.setTextColor(Color.parseColor(parse1));
//        }
//        else if (dMag1 >= 4.0 && dMag1 < 4.4){
//            IntroHome.mag1.setTextColor(Color.parseColor(parse2));
//        }
//        else if (dMag1 >= 4.4 && dMag1 < 4.8){
//            IntroHome.mag1.setTextColor(Color.parseColor(parse3));
//        }
//        else if (dMag1 >= 4.8 && dMag1 < 5.2){
//            IntroHome.mag1.setTextColor(Color.parseColor(parse4));
//        }
//        else if (dMag1 >= 5.2 && dMag1 < 5.6){
//            IntroHome.mag1.setTextColor(Color.parseColor(parse5));
//        }
//        else if (dMag1 >= 5.6 && dMag1 < 7.0){
//            IntroHome.mag1.setTextColor(Color.parseColor(parse6));
//        }




        if (dMag1 >= 2.5 && dMag1 < 4.0){
            IntroHome.mag1.setTextColor(Color.parseColor(parse1));
        }
        else if (dMag1 >= 4.0 && dMag1 < 4.4){
            IntroHome.mag1.setTextColor(Color.parseColor(parse2));
        }
        else if (dMag1 >= 4.4 && dMag1 < 4.8){
            IntroHome.mag1.setTextColor(Color.parseColor(parse3));
        }
        else if (dMag1 >= 4.8 && dMag1 < 5.2){
            IntroHome.mag1.setTextColor(Color.parseColor(parse4));
        }
        else if (dMag1 >= 5.2 && dMag1 < 5.6){
            IntroHome.mag1.setTextColor(Color.parseColor(parse5));
        }
        else if (dMag1 >= 5.6 && dMag1 < 7.0){
            IntroHome.mag1.setTextColor(Color.parseColor(parse6));
        }




        if (dMag2 >= 2.5 && dMag2 < 4.0){
            IntroHome.mag2.setTextColor(Color.parseColor(parse1));
        }
        else if (dMag2 >= 4.0 && dMag2 < 4.4){
            IntroHome.mag2.setTextColor(Color.parseColor(parse2));
        }
        else if (dMag2 >= 4.4 && dMag2 < 4.8){
            IntroHome.mag2.setTextColor(Color.parseColor(parse3));
        }
        else if (dMag2 >= 4.8 && dMag2 < 5.2){
            IntroHome.mag2.setTextColor(Color.parseColor(parse4));
        }
        else if (dMag2 >= 5.2 && dMag2 < 5.6){
            IntroHome.mag2.setTextColor(Color.parseColor(parse5));
        }
        else if (dMag2 >= 5.6 && dMag2 < 7.0){
            IntroHome.mag2.setTextColor(Color.parseColor(parse6));
        }




        if (dMag3 >= 2.5 && dMag3 < 4.0){
            IntroHome.mag3.setTextColor(Color.parseColor(parse1));
        }
        else if (dMag3 >= 4.0 && dMag3 < 4.4){
            IntroHome.mag3.setTextColor(Color.parseColor(parse2));
        }
        else if (dMag3 >= 4.4 && dMag3 < 4.8){
            IntroHome.mag3.setTextColor(Color.parseColor(parse3));
        }
        else if (dMag3 >= 4.8 && dMag3 < 5.2){
            IntroHome.mag3.setTextColor(Color.parseColor(parse4));
        }
        else if (dMag3 >= 5.2 && dMag3 < 5.6){
            IntroHome.mag3.setTextColor(Color.parseColor(parse5));
        }
        else if (dMag3 >= 5.6 && dMag3 < 7.0){
            IntroHome.mag3.setTextColor(Color.parseColor(parse6));
        }




        if (dMag4 >= 2.5 && dMag4 < 4.0){
            IntroHome.mag4.setTextColor(Color.parseColor(parse1));
        }
        else if (dMag4 >= 4.0 && dMag4 < 4.4){
            IntroHome.mag4.setTextColor(Color.parseColor(parse2));
        }
        else if (dMag4 >= 4.4 && dMag4 < 4.8){
            IntroHome.mag4.setTextColor(Color.parseColor(parse3));
        }
        else if (dMag4 >= 4.8 && dMag4 < 5.2){
            IntroHome.mag4.setTextColor(Color.parseColor(parse4));
        }
        else if (dMag4 >= 5.2 && dMag4 < 5.6){
            IntroHome.mag4.setTextColor(Color.parseColor(parse5));
        }
        else if (dMag4 >= 5.6 && dMag4 < 7.0){
            IntroHome.mag4.setTextColor(Color.parseColor(parse6));
        }




        if (dMag5 >= 2.5 && dMag5 < 4.0){
            IntroHome.mag5.setTextColor(Color.parseColor(parse1));
        }
        else if (dMag5 >= 4.0 && dMag5 < 4.4){
            IntroHome.mag5.setTextColor(Color.parseColor(parse2));
        }
        else if (dMag5 >= 4.4 && dMag5 < 4.8){
            IntroHome.mag5.setTextColor(Color.parseColor(parse3));
        }
        else if (dMag5 >= 4.8 && dMag5 < 5.2){
            IntroHome.mag5.setTextColor(Color.parseColor(parse4));
        }
        else if (dMag5 >= 5.2 && dMag5 < 5.6){
            IntroHome.mag5.setTextColor(Color.parseColor(parse5));
        }
        else if (dMag5 >= 5.6 && dMag5 < 7.0){
            IntroHome.mag5.setTextColor(Color.parseColor(parse6));
        }




        if (dMag6 >= 2.5 && dMag6 < 4.0){
            IntroHome.mag6.setTextColor(Color.parseColor(parse1));
        }
        else if (dMag6 >= 4.0 && dMag6 < 4.4){
            IntroHome.mag6.setTextColor(Color.parseColor(parse2));
        }
        else if (dMag6 >= 4.4 && dMag6 < 4.8){
            IntroHome.mag6.setTextColor(Color.parseColor(parse3));
        }
        else if (dMag6 >= 4.8 && dMag6 < 5.2){
            IntroHome.mag6.setTextColor(Color.parseColor(parse4));
        }
        else if (dMag6 >= 5.2 && dMag6 < 5.6){
            IntroHome.mag6.setTextColor(Color.parseColor(parse5));
        }
        else if (dMag6 >= 5.6 && dMag6 < 7.0){
            IntroHome.mag6.setTextColor(Color.parseColor(parse6));
        }


    }

}
