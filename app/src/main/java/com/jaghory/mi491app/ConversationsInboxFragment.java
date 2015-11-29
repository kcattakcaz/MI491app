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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        //conversationsRecyclerView = (RecyclerView) getActivity().findViewById(R.id.conversationsRecView);

        View fragView = inflater.inflate(R.layout.fragment_conversations_inbox, container, false);
        conversationsRecyclerView = (RecyclerView) fragView.findViewById(R.id.conversationsRecView) ;

        // use a linear layout manager
       conversationsLayoutManager = new LinearLayoutManager(this.getContext());
        conversationsRecyclerView.setLayoutManager(conversationsLayoutManager);

        // specify an adapter (see also next example)
        //conversationsAdapter = new MyAdapter(myDataset);
        //onversationsRecyclerView.setAdapter(conversationsAdapter);
        conversationsAdapter = new ConversationsAdapter("blah","blah");
        conversationsRecyclerView.setAdapter(conversationsAdapter);

        return fragView;
    }
}
