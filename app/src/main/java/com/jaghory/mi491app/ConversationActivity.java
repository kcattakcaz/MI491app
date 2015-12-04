package com.jaghory.mi491app;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Firebase;

public class ConversationActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
       // getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();

        String firebaseRefStr = i.getStringExtra("conversation_firebase_ref");

        Firebase firebaseRef = new Firebase(firebaseRefStr);

        TextView tv = (TextView) findViewById(R.id.textView4);
        tv.setText(firebaseRef.toString());

    }

}
