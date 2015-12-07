package com.jaghory.mi491app;

import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.ArrayRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class ComposeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);
        this.setTitle("New Conversation");
        final Firebase sFireRef = new Firebase("https://mi491app.firebaseio.com");
        final MultiAutoCompleteTextView contacts = (MultiAutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        contacts.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1));

        sFireRef.child("users").child(sFireRef.getAuth().getUid()).child("contacts").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot.hasChildren());
                if (!dataSnapshot.hasChildren()) {
                    AlertDialog.Builder warning = new AlertDialog.Builder(ComposeActivity.this);
                    warning.setMessage("You have no contacts to message!").setTitle("Awww....");
                    warning.setPositiveButton("Sigh.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    warning.create();
                    warning.show();
                }
                final ArrayList<User> users = new ArrayList<User>();
                final ArrayList<String> user_names = new ArrayList<String>();
                for(DataSnapshot contact : dataSnapshot.getChildren()){

                    sFireRef.child("users").child(contact.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            User user = new User();
                            System.out.println(dataSnapshot.child("displayName").getValue().toString());

                            user.setDisplayName(dataSnapshot.child("displayName").getValue().toString());
                            user.setUsername(dataSnapshot.child("username").getValue().toString());
                            user.setPhoneNumber(dataSnapshot.child("phoneNumber").getValue().toString());
                            users.add(user);
                            user_names.add(dataSnapshot.child("displayName").getValue().toString());
                            ArrayAdapter AutoCompleteAdapter = new ArrayAdapter(getApplicationContext(),android.R.layout.simple_list_item_1,user_names.toArray());
                            System.out.println(user_names.toString());
                            contacts.setAdapter(AutoCompleteAdapter);
                            contacts.setThreshold(1);
                            contacts.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });

                }



            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                AlertDialog.Builder warning = new AlertDialog.Builder(ComposeActivity.this);
                warning.setMessage("We're having problems contacting the server").setTitle("Awww....");
                warning.setPositiveButton("Sigh.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                warning.create();
                warning.show();
            }
        });


    }

}
