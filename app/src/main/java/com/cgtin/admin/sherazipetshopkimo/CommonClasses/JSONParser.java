package com.cgtin.admin.sherazipetshopkimo.CommonClasses;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Admin on 31-03-2017.
 */

public class JSONParser {

    private StringBuilder result, result2;
    private StringBuilder sbParams;
    private URL urlObj;
    private JSONObject jObj2 = null;
    private JSONObject jObj3 = null;

    private JSONArray jObj = null;
    private HttpURLConnection conn;
    private String charset = "UTF-8";
    private String resultcode;


    public JSONObject SendHttpPosts(String URL, String method, JSONObject jsonObjSend, HashMap<String, String> params,String Authorization) {

        sbParams = new StringBuilder();
        int i = 0;

        if (params != null)
            for (String key : params.keySet()) {
                try {
                    if (i != 0) {
                        sbParams.append("&");
                    }
                    sbParams.append(key).append("=")
                            .append(URLEncoder.encode(params.get(key), charset));

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                i++;

            }


        switch (method) {
            case "POST":

                if (sbParams.length() != 0) {
                    URL += "?" + sbParams.toString();
                }

                try {

                    urlObj = new URL(URL);
                    conn = (HttpURLConnection) urlObj.openConnection();
                    conn.setReadTimeout(15000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    if(Authorization!=null) {
                        conn.setRequestProperty("Authorization", "Bearer " + Authorization);
                    }
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonObjSend.toString());

                    writer.flush();
                    writer.close();
                    os.close();


                } catch (Exception e) {
                    e.printStackTrace();

                }
                break;

            case "PUT":

                if (sbParams.length() != 0) {
                    URL += "?" + sbParams.toString();
                }

                try {

                    urlObj = new URL(URL);
                    conn = (HttpURLConnection) urlObj.openConnection();
                    conn.setReadTimeout(15000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("PUT");
                    conn.setRequestProperty("Content-Type", "application/json");
                    if(Authorization!=null) {
                        conn.setRequestProperty("Authorization", "Bearer " + Authorization);
                    }
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonObjSend.toString());

                    writer.flush();
                    writer.close();
                    os.close();


                } catch (Exception e) {
                    e.printStackTrace();

                }
                break;
            case "GET":

                if (sbParams.length() != 0) {
                    URL += "?" + sbParams.toString();
                }

                try {
                    urlObj = new URL(URL);
                    conn = (HttpURLConnection) urlObj.openConnection();
                    conn.setReadTimeout(15000 /* milliseconds */);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("GET");
                    conn.setRequestProperty("Content-Type", "application/json");
                    if(Authorization!=null) {
                        conn.setRequestProperty("Authorization", "Bearer " + Authorization);
                    }
                    conn.setRequestProperty("Accept-Charset", charset);
                    conn.setDoInput(true);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case "DELETE":

                if (sbParams.length() != 0) {
                    URL += "?" + sbParams.toString();
                }

                try {
                    urlObj = new URL(URL);
                    conn = (HttpURLConnection) urlObj.openConnection();
                    conn.setReadTimeout(15000 /* milliseconds */);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("DELETE");
                    if(Authorization!=null) {
                        conn.setRequestProperty("Authorization", "Bearer " + Authorization);
                    }
                    conn.setRequestProperty("Accept-Charset", charset);
                    conn.setDoOutput(true);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
        }

        try {

            int responseCode = conn.getResponseCode();

            BufferedReader in;

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                in = new BufferedReader(new
                        InputStreamReader(
                        conn.getInputStream()));

                result = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {

                    result.append(line);
                    break;
                }


            }
            else if(responseCode==HttpsURLConnection.HTTP_UNAUTHORIZED) {


                in = new BufferedReader(new
                        InputStreamReader(
                        conn.getErrorStream()));

                result = new StringBuilder();
                String line;


                while ((line = in.readLine()) != null) {

                    result.append(line);
                    break;
                }
            }
            else {


                try {
                    jObj3 = new JSONObject("{\"Status\":\"failed\",\"Message\":\"Error Code " + responseCode + "\"}");
                    resultcode = String.valueOf(jObj3);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                InputStream ins = new ByteArrayInputStream(resultcode.getBytes("UTF-8"));

                in = new BufferedReader(new
                        InputStreamReader(
                        ins));

                result = new StringBuilder();
                String line2;

                while ((line2 = in.readLine()) != null) {

                    result.append(line2);
                    break;
                }

            }
        } catch (IOException e) {

            e.printStackTrace();

        }

        try {
            jObj2 = new JSONObject(result.toString());

        } catch (JSONException e) {

            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }
        // return JSON Object
        return jObj2;

    }
}