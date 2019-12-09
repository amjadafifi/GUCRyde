package com.example.gucryde;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

public class pickUpActivity extends AppCompatActivity {
    private Button logoutButton;
    private Button pickUpButton;
    private String email;
    private String name;
    private TextView selectRider;
    private TextView userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_up);
        logoutButton = findViewById(R.id.logOutButton);
        pickUpButton = findViewById(R.id.pickUpButton);
        selectRider = findViewById(R.id.nowRider);
        userName = findViewById(R.id.userNameDisplay);

        if (SaveSharedPreference.getUserName(pickUpActivity.this).length() == 0) {
            selectRider.setVisibility(View.GONE);
            System.out.println(SaveSharedPreference.getUserName(getApplicationContext()) + " halaa ");
            Intent i = new Intent(getApplicationContext(), loginActivity.class);
            startActivity(i);
    }
        logoutButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FacebookSdk.sdkInitialize(getApplicationContext());
                if (AccessToken.getCurrentAccessToken() != null) {
                    LoginManager.getInstance().logOut();
                }
                Intent i = new Intent(getApplicationContext(), loginActivity.class);
                startActivity(i);
            }
        });
}
}
