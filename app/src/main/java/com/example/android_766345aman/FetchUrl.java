package com.example.android_766345aman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchUrl {

    public String ReadUrl(String url) throws IOException {
        String data = "";

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;


        try {
            URL url1 = new URL(url);

            urlConnection = (HttpURLConnection)  url1.openConnection();
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer sb = new StringBuffer();

            String jsonLine = "";

            while ((jsonLine = bufferedReader.readLine()) != null){
                sb.append(jsonLine);
            }
            data = sb.toString();
            bufferedReader.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            inputStream.close();
            urlConnection.disconnect();
        }

        return  data;


    }





}
