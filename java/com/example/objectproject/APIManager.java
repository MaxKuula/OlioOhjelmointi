package com.example.objectproject;

import android.provider.DocumentsContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class APIManager {

    /* gets info from json string */
    public void readJSON(String url) {
        String json = getJSON(url);
        System.out.println("JSON: " + json);

        if (json != null) {
            try {
                JSONObject jObject = new JSONObject(json);
                    double dairy = jObject.getDouble("Dairy");
                    double meat = jObject.getDouble("Meat");
                    double vegetable = jObject.getDouble("Plant");
                    double restaurant = jObject.getDouble("Restaurant");
                    double total = jObject.getDouble("Total");

                    UserProfile.getInstance().setCarbonFPInfo(dairy, meat, vegetable, restaurant, total);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /* gets JSON file from internet and converts it to string */
    public String getJSON(String urlString) {
        String response = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(conn.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }
            response = sb.toString();
            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return response;
    }

}
