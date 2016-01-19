package com.jaghory.mi491app;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.FirebaseRecyclerAdapter;

public class RegistrationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final Firebase mFireRef = new Firebase("https://mi491app.firebaseio.com");

        Intent userInfo = getIntent();

        final String username = userInfo.getStringExtra("username");
        final String password = userInfo.getStringExtra("password");

        final Button getStarted = (Button) findViewById(R.id.registration_getStarted);
        final EditText displayName = (EditText) findViewById(R.id.register_displayName);
        final EditText phoneNumber = (EditText) findViewById(R.id.register_phoneNumber);

        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFireRef.authWithPassword(username,password, new Firebase.AuthResultHandler(){
                    @Override
                    public void onAuthenticated(AuthData authData){
                        Snackbar.make(getStarted, "Please wait...", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                        User authed_user = new User(username,displayName.getText().toString(),phoneNumber.getText().toString());
                        mFireRef.child("users").child(authData.getUid()).setValue(authed_user);
                        mFireRef.child("users_email").child(authed_user.getPhoneNumber()).setValue(mFireRef.getAuth().getUid());
                        //startActivity(new Intent(getApplicationContext(), Dashboard.class));
                        startActivity(new Intent(getApplicationContext(),SmartphoneConversationsActivity.class));
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError){
                        Snackbar.make(getStarted, firebaseError.toString(), Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed(){
        Toast toast = Toast.makeText(getApplicationContext(), "Wait! You aren't finished!", Toast.LENGTH_SHORT);
        toast.show();
    }

}
