package com.jaghory.mi491app;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this.getApplicationContext());
        setContentView(R.layout.activity_login);



        Button login_btn = (Button) findViewById(R.id.login_btn);
        Button signup_btn = (Button) findViewById(R.id.signup_btn);
        final EditText username = (EditText) findViewById(R.id.login_username);
        final EditText password = (EditText) findViewById(R.id.login_password);

        final Firebase fRef = new Firebase("https://mi491app.firebaseio.com/users/");

        final Firebase.AuthResultHandler authResultHandler = new Firebase.AuthResultHandler(){
            @Override
            public void onAuthenticated(AuthData authData){
                startActivity(new Intent(getApplicationContext(),Dashboard.class));
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError){
                Snackbar.make(password, "Incorrect username or password.", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        };

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fRef.authWithPassword(username.getText().toString(),password.getText().toString(),authResultHandler);
            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                fRef.createUser(username.getText().toString(), password.getText().toString(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> result) {
                        Snackbar.make(v, "Created!  Now sign in!", Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        String err = new String();
                        String usr_name = username.getText().toString();
                        switch (firebaseError.getCode()){
                            case FirebaseError.EMAIL_TAKEN: err = usr_name+" is taken, try again.";
                                break;
                            case FirebaseError.INVALID_EMAIL: err = usr_name+" is not a valid e-mail, try again.";
                                break;
                            case FirebaseError.INVALID_PASSWORD: err = "Not a valid password, try again.";
                                break;
                            default: err = "Unknown error creating account.";
                                break;
                        }
                        Snackbar.make(v, err, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
            }
        });



    }

}
