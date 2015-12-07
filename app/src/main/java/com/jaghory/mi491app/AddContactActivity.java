package com.jaghory.mi491app;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import org.w3c.dom.Text;

import java.util.HashMap;

public class AddContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Button searchButton = (Button) findViewById(R.id.searchButton);
        final EditText searchString = (EditText) findViewById(R.id.editText_phone_search);
        final Firebase tFireRef = new Firebase("https://mi491app.firebaseio.com");

        final Button acceptBtn = (Button) findViewById(R.id.result_accept);
        final TextView usrName = (TextView) findViewById(R.id.result_username);
        final TextView usrEmail = (TextView) findViewById(R.id.result_email);
        final LinearLayout sResults = (LinearLayout) findViewById(R.id.searchResults);



        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sResults.setVisibility(View.INVISIBLE);
                if(searchString.getText().toString().length() <= 1){
                    Toast t = Toast.makeText(getApplicationContext(),"That doesn't appear to be a valid phone number",Toast.LENGTH_SHORT);
                    t.show();
                    return;
                }
                Query queryRef = tFireRef.child("users_email").child(searchString.getText().toString());
                queryRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            Toast t = Toast.makeText(getApplicationContext(), dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT);
                            t.show();
                            final String contactUid = dataSnapshot.getValue().toString();
                            tFireRef.child("users").child(contactUid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    usrName.setText(dataSnapshot.child("displayName").getValue().toString());
                                    usrEmail.setText(dataSnapshot.child("username").getValue().toString());
                                    sResults.setVisibility(View.VISIBLE);
                                    acceptBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            System.out.println("Hello world: " + contactUid);
                                            HashMap userData = new HashMap();
                                            userData.put(contactUid, 0);
                                            HashMap userData2 = new HashMap();
                                            userData2.put(tFireRef.getAuth().getUid(),0);
                                            //tFireRef.child("users").child(tFireRef.getAuth().getUid()).child("contacts").setValue(userData);
                                            tFireRef.child("users").child(tFireRef.getAuth().getUid()).child("contacts").updateChildren(userData);
                                            tFireRef.child("users").child(contactUid).child("contacts").updateChildren(userData2);
                                            finish();
                                        }
                                    });


                                }

                                @Override
                                public void onCancelled(FirebaseError firebaseError) {

                                }
                            });

                        } else {
                            Toast t = Toast.makeText(getApplicationContext(), "No results", Toast.LENGTH_SHORT);
                            t.show();
                        }

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


            }
        });

    }

}
