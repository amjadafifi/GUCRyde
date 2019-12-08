package com.example.gucryde;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private Button logoutButton;
    private String email;
    private TextView selectRider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        logoutButton = findViewById(R.id.logOutButton);
        selectRider = findViewById(R.id.nowRider);

        if(SaveSharedPreference.getUserName(MainActivity.this).length() == 0)
        {
            selectRider.setVisibility(View.GONE);
            System.out.println(SaveSharedPreference.getUserName(getApplicationContext()));
            Intent i = new Intent(getApplicationContext(),loginActivity.class);
            startActivity(i);
        }
        else {
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
