package com.jaghory.mi491app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;

public class ConversationActivity extends Fragment {
    private RecyclerView messagesRecyclerView;
    private RecyclerView.Adapter conversationsAdapter;
    private RecyclerView.LayoutManager messagesLayoutManager;
    private Firebase conversationRef;
    private User currentUser;
    EditText userText;
    public String getCurrentConversationFirebaseRef() {
        return currentConversationFirebaseRef;
    }

    public void setCurrentConversationFirebaseRef(String currentConversationFirebaseRef) {
        this.currentConversationFirebaseRef = currentConversationFirebaseRef;
    }

    private String currentConversationFirebaseRef = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.fragment_conversation);

       // getActionBar().setDisplayHomeAsUpEnabled(true);

  //      Intent i = getIntent();
        if(currentConversationFirebaseRef != null) {
            View view = inflater.inflate(R.layout.fragment_conversation,container,false);

            //String firebaseRefStr = i.getStringExtra("conversation_firebase_ref");

            String firebaseRefStr = this.currentConversationFirebaseRef;

            this.userText = (EditText) view.findViewById(R.id.inputText);

            this.conversationRef = new Firebase(firebaseRefStr);
            final Firebase firebaseRef = this.conversationRef;
            final Firebase sFireRef = new Firebase("https://mi491app.firebaseio.com/users");

            final User user = new User();
            this.currentUser = user;

            sFireRef.child(sFireRef.getAuth().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    user.setDisplayName(dataSnapshot.child("displayName").getValue().toString());
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

            // use a linear layout manager
            messagesLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            messagesRecyclerView = (RecyclerView) view.findViewById(R.id.messagesRecView);
            messagesRecyclerView.setLayoutManager(messagesLayoutManager);

            final FirebaseRecyclerAdapter mAdapter = new FirebaseRecyclerAdapter<Message, MessagesViewHolder>(Message.class, R.layout.content_messages, MessagesViewHolder.class, firebaseRef.child("messages")) {
                //@Override
                public void populateViewHolder(MessagesViewHolder chatMessageViewHolder, Message chatMessage) {
                    chatMessageViewHolder.nameText.setText(chatMessage.getSender());
                    chatMessageViewHolder.messageText.setText(chatMessage.getContent());
                    messagesRecyclerView.scrollToPosition(messagesRecyclerView.getAdapter().getItemCount());
                }
            };
            messagesRecyclerView.setAdapter(mAdapter);
            return view;
        }
        else {
            View view = inflater.inflate(R.layout.fragment_smartphone_conversations,container,false);
            return view;
        }

    }

    public void sendTextMessage(){
        Message sMessage = new Message("text", currentUser.getDisplayName(), 0, userText.getText().toString());
        conversationRef.child("messages").push().setValue(sMessage);
        userText.setText("");
        userText.clearFocus();
        messagesRecyclerView.scrollToPosition(messagesRecyclerView.getAdapter().getItemCount());
    }

    public static class MessagesViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView nameText;

        public MessagesViewHolder(View itemView) {
            super(itemView);
            nameText = (TextView)itemView.findViewById(R.id.messageTitle);
            messageText = (TextView) itemView.findViewById(R.id.messageContent);
        }
    }

}
