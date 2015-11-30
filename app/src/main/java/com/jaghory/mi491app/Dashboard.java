package com.jaghory.mi491app;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    private RecyclerView conversationsRecyclerView;
    private RecyclerView.Adapter conversationsAdapter;
    private RecyclerView.LayoutManager conversationsLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(this.getApplicationContext());
        setContentView(R.layout.activity_conversations_inbox);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // use a linear layout manager
        conversationsLayoutManager = new LinearLayoutManager(getApplicationContext());
        conversationsRecyclerView = (RecyclerView) findViewById(R.id.conversationsRecView);
        conversationsRecyclerView.setLayoutManager(conversationsLayoutManager);

        Firebase mFireRef = new Firebase("https://mi491app.firebaseio.com/conversations");
        conversationsRecyclerView.setHasFixedSize(true);
        

        FirebaseRecyclerAdapter mAdapter = new FirebaseRecyclerAdapter<ConversationOverview,ConversationViewHolder>(ConversationOverview.class, android.R.layout.two_line_list_item, ConversationViewHolder.class, mFireRef) {
            //@Override
            public void populateViewHolder(ConversationViewHolder chatMessageViewHolder, ConversationOverview chatMessage) {
                chatMessageViewHolder.nameText.setText(chatMessage.getcTitle());
                chatMessageViewHolder.messageText.setText(chatMessage.getcSummary());
                System.out.println("HELLLOOOOO "+chatMessage.getcTitle());
            }
        };
        conversationsRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hello", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

            }});



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_conversations_inbox, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private static class ConversationViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView nameText;

        public ConversationViewHolder(View itemView) {
            super(itemView);
            nameText = (TextView)itemView.findViewById(android.R.id.text1);
            messageText = (TextView) itemView.findViewById(android.R.id.text2);
        }
    }
}

