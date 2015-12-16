package com.jaghory.mi491app;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;

public class ConversationActivity extends Activity {
    private RecyclerView messagesRecyclerView;
    private RecyclerView.Adapter conversationsAdapter;
    private RecyclerView.LayoutManager messagesLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
       // getActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();

        String firebaseRefStr = i.getStringExtra("conversation_firebase_ref");

       final Firebase firebaseRef = new Firebase(firebaseRefStr);

        Button sendButton = (Button) findViewById(R.id.sendMessage);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                

            }
        });

        // use a linear layout manager
        messagesLayoutManager = new LinearLayoutManager(getApplicationContext());
        messagesRecyclerView = (RecyclerView) findViewById(R.id.messagesRecView);
        messagesRecyclerView.setLayoutManager(messagesLayoutManager);

        final FirebaseRecyclerAdapter mAdapter = new FirebaseRecyclerAdapter<Message,MessagesViewHolder>(Message.class, R.layout.content_conversations_overview, MessagesViewHolder.class, firebaseRef.child("messages")) {
            //@Override
            public void populateViewHolder(MessagesViewHolder chatMessageViewHolder, Message chatMessage) {
                chatMessageViewHolder.nameText.setText(chatMessage.getSender());
                chatMessageViewHolder.messageText.setText(chatMessage.getContent());
            }
        };
        messagesRecyclerView.setAdapter(mAdapter);

    }

    private static class MessagesViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView nameText;

        public MessagesViewHolder(View itemView) {
            super(itemView);
            nameText = (TextView)itemView.findViewById(R.id.mConversationTitle);
            messageText = (TextView) itemView.findViewById(R.id.mConversationSummary);
        }
    }

}
