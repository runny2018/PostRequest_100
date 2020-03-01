package com.example.postrequest_100;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    TextView outputView;
    String output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new MyTask().execute();




    }

    private class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {

                //ask string parameters to Atif, if not working

                outputView= findViewById(R.id.postOutput);

                URL url=new URL("http://localhost:3000/login");
                HttpURLConnection connection= (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("POST");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5" );

                connection.setDoOutput(true);
                DataOutputStream dStream=new DataOutputStream(connection.getOutputStream());

                dStream.flush();
                dStream.close();

                int responseCode=connection.getResponseCode();
                output="Request URL "+url;
                output+=System.getProperty("line.separator")+"Request Parameters ";
                output+=System.getProperty("line.separator")+"Response Code "+responseCode;

                BufferedReader br=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line="";
                StringBuilder responseOutput=new StringBuilder();

                while((line=br.readLine())!=null){
                    responseOutput.append(line);
                }

                br.close();

                output+=System.getProperty("line.separator")+responseOutput.toString();



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            outputView.setText(output);
            super.onPostExecute(aVoid);
        }
    }

}
