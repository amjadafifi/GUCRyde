package com.example.gucryde;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class choice extends AppCompatActivity {

    private Button riderButton;
    private Button pickUpButton;
    private Button logoutButton;
    private String email;
    private TextView selectRider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        logoutButton = findViewById(R.id.logOutButton);
        riderButton = findViewById(R.id.riderButton);
        pickUpButton = findViewById(R.id.pickUpButton);

        if(SaveSharedPreference.getUserName(choice.this).length() == 0)
        {
            riderButton.setVisibility(View.GONE);
            pickUpButton.setVisibility(View.GONE);
            selectRider.setVisibility(View.GONE);
            System.out.println(SaveSharedPreference.getUserName(getApplicationContext()));
            Intent i = new Intent(getApplicationContext(),loginActivity.class);
            startActivity(i);
        }
        else {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                email = extras.getString("email");
            }

            riderButton.setVisibility(View.VISIBLE);
            pickUpButton.setVisibility(View.VISIBLE);
            logoutButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    FacebookSdk.sdkInitialize(getApplicationContext());
                    if(AccessToken.getCurrentAccessToken() != null) {
                        LoginManager.getInstance().logOut();
                    }
                    Intent i = new Intent(getApplicationContext(),loginActivity.class);
                    startActivity(i);
                }
            });
            riderButton.setOnClickListener(new View.OnClickListener(){
                public void onClick(View V){
                    new GetUrlContentTask().execute("http://192.168.0.3/rider.php?rider=1&email=" + email);
                    finish();
                    Intent i = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(i);
                }
            });

        }
    }

    private class GetUrlContentTask extends AsyncTask<String, Integer, String> {
        protected String doInBackground(String... urls) {
            String content = "", line;
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setDoOutput(true);
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.connect();
                BufferedReader rd = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                while ((line = rd.readLine()) != null) {
                    content += line + "\n";
                }


            } catch (IOException e) {
                e.printStackTrace();
            }
            return content;
        }
        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(String result) {

        }
    }
}
