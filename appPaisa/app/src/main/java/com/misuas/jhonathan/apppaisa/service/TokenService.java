package com.misuas.jhonathan.apppaisa.service;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by USUARIO on 13/05/2018.
 */

public class TokenService extends AsyncTask<String,String,String> {


    private final AppCompatActivity activida;
    private final ProgressDialog dialog;
    private String datos;


    public TokenService(AppCompatActivity activida, ProgressDialog dialog) {
        this.activida = activida;
        this.dialog = dialog;
    }
    @Override
    protected String doInBackground(String... params) {
        String stream = null;
        String link = "https://u20161149029.000webhostapp.com/comunicacion/mensajes.php";
        String token=params[0];
        String info = "";
        try {

            info = URLEncoder.encode("token", "UTF-8") + "=" + URLEncoder.encode(token, "UTF-8");
            info.replace("@", "%40");
            URL url = new URL(link);
            System.out.print(token);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(info);
            wr.flush();
            wr.close();
            if (conn.getResponseCode() == 200) {
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder sb = new StringBuilder();
                String line;

                while ((line = r.readLine()) != null) {
                    sb.append(line);
                }
                r.close();
                in.close();
                stream = sb.toString();

            } else {
                conn.disconnect();
            }
            this.datos = stream;

            conn.disconnect();
            return stream;
        } catch(Exception e){

            return new String("Exception: " + e.getMessage());
        }

    }

    @Override
    protected void onPostExecute(String s) {
        dialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
