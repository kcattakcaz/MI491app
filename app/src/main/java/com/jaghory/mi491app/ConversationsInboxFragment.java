package com.jaghory.mi491app;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;
import com.firebase.ui.FirebaseRecyclerAdapter;

/**
 * A placeholder fragment containing a simple view.
 */
public class ConversationsInboxFragment extends Fragment {

    private RecyclerView conversationsRecyclerView;
    private RecyclerView.Adapter conversationsAdapter;
    private RecyclerView.LayoutManager conversationsLayoutManager;

    public ConversationsInboxFragment() {
    }

    public void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);


    }

    private static class ConversationViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView nameText;

        public ConversationViewHolder(View itemView) {
            super(itemView);
            nameText = (TextView)itemView.findViewById(R.id.mConversationTitle);
            messageText = (TextView) itemView.findViewById(R.id.mConversationSummary);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        //conversationsRecyclerView = (RecyclerView) getActivity().findViewById(R.id.conversationsRecView);

        View fragView = inflater.inflate(R.layout.fragment_conversations_inbox, container, false);
        conversationsRecyclerView = (RecyclerView) fragView.findViewById(R.id.conversationsRecView) ;

        // use a linear layout manager
       conversationsLayoutManager = new LinearLayoutManager(this.getContext());
        conversationsRecyclerView.setLayoutManager(conversationsLayoutManager);

        Firebase mFireRef = new Firebase("https://mi491app.firebaseio.com/conversations");
        conversationsRecyclerView.setHasFixedSize(true);

        FirebaseRecyclerAdapter mAdapter = new FirebaseRecyclerAdapter(ConversationOverview.class, android.R.layout.two_line_list_item, ConversationViewHolder.class, mFireRef) {
            //@Override
            public void populateViewHolder(ConversationViewHolder chatMessageViewHolder, ConversationOverview chatMessage) {
                chatMessageViewHolder.nameText.setText(chatMessage.getcTitle());
                chatMessageViewHolder.messageText.setText(chatMessage.getcTitle());
            }
        };
        conversationsRecyclerView.setAdapter(mAdapter);
        conversationsRecyclerView.setAdapter(mAdapter);


        return fragView;
    }
}


