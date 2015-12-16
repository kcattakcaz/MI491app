package com.jaghory.mi491app;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
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
        final Firebase sFireRef = new Firebase("https://mi491app.firebaseio.com/users");

        final User user = new User();

        sFireRef.child(sFireRef.getAuth().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user.setDisplayName(dataSnapshot.child("displayName").getValue().toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        Button sendButton = (Button) findViewById(R.id.sendMessage);
        final EditText userText = (EditText) findViewById(R.id.inputText);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message sMessage = new Message("text",user.getDisplayName(),0,userText.getText().toString());
                firebaseRef.child("messages").push().setValue(sMessage);
                userText.setText("");
                userText.clearFocus();
                messagesRecyclerView.scrollToPosition(messagesRecyclerView.getAdapter().getItemCount());
            }
        });

        // use a linear layout manager
        messagesLayoutManager = new LinearLayoutManager(getApplicationContext());
        messagesRecyclerView = (RecyclerView) findViewById(R.id.messagesRecView);
        messagesRecyclerView.setLayoutManager(messagesLayoutManager);

        final FirebaseRecyclerAdapter mAdapter = new FirebaseRecyclerAdapter<Message,MessagesViewHolder>(Message.class, R.layout.content_messages, MessagesViewHolder.class, firebaseRef.child("messages")) {
            //@Override
            public void populateViewHolder(MessagesViewHolder chatMessageViewHolder, Message chatMessage) {
                chatMessageViewHolder.nameText.setText(chatMessage.getSender());
                chatMessageViewHolder.messageText.setText(chatMessage.getContent());
                messagesRecyclerView.scrollToPosition(messagesRecyclerView.getAdapter().getItemCount());
            }
        };
        messagesRecyclerView.setAdapter(mAdapter);

    }

    private static class MessagesViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView nameText;

        public MessagesViewHolder(View itemView) {
            super(itemView);
            nameText = (TextView)itemView.findViewById(R.id.messageTitle);
            messageText = (TextView) itemView.findViewById(R.id.messageContent);
        }
    }

}
