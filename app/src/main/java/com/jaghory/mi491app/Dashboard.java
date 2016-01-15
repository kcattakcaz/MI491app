package com.jaghory.mi491app;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;

public class Dashboard extends Fragment{
    ConversationManagementInterface parentActivityConversationManager;
    private RecyclerView conversationsRecyclerView;
    private RecyclerView.Adapter conversationsAdapter;
    private RecyclerView.LayoutManager conversationsLayoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_conversations_inbox,container,false);

        //Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle();
        // use a linear layout manager
        conversationsLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        conversationsRecyclerView = (RecyclerView) view.findViewById(R.id.conversationsRecView);
        conversationsRecyclerView.setLayoutManager(conversationsLayoutManager);

        final Firebase mFireRef = new Firebase("https://mi491app.firebaseio.com/conversations");
        final User currentUser = new User();

        final Query queryRef = mFireRef.orderByChild(mFireRef.getAuth().getUid()).equalTo(true);

        System.out.println("Query " + queryRef);
        mFireRef.getRoot().child("users/" + mFireRef.getAuth().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                System.out.println(dataSnapshot);

                currentUser.setDisplayName((String) dataSnapshot.child("displayName").getValue());
                currentUser.setPhoneNumber(dataSnapshot.child("phoneNumber").getValue().toString());

                Snackbar.make(getActivity().findViewById(R.id.smartphone_conversations_fragment_primary_container), "Welcome back, " + currentUser.getDisplayName(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



        conversationsRecyclerView.setHasFixedSize(true);


        final FirebaseRecyclerAdapter mAdapter = new FirebaseRecyclerAdapter<ConversationOverview,ConversationViewHolder>(ConversationOverview.class, R.layout.content_conversations_overview, ConversationViewHolder.class, queryRef) {
            //@Override
            public void populateViewHolder(ConversationViewHolder chatMessageViewHolder, ConversationOverview chatMessage) {
                chatMessageViewHolder.nameText.setText(chatMessage.getcTitle());
                chatMessageViewHolder.messageText.setText(chatMessage.getcSummary());
            }
        };
        conversationsRecyclerView.setAdapter(mAdapter);

        ItemClickSupport.addTo(conversationsRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                /*Intent clickedConv = new Intent(getActivity().getApplicationContext(), ConversationActivity.class);
                clickedConv.putExtra("conversation_firebase_ref", mAdapter.getRef(position).toString());

                startActivity(clickedConv);
                */
                System.out.println(mAdapter.getRef(position).toString());
                parentActivityConversationManager.onSelectConversation(mAdapter.getRef(position).toString());


            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Message sent", Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                ConversationOverview nC = new ConversationOverview();
                nC.setcSummary("New Message");
                nC.setcTitle("Empty Title");
                mFireRef.push().setValue(nC);
                */
                startActivity(new Intent(getActivity().getApplicationContext(), ComposeActivity.class));
            }
        });


        return view;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
           this.parentActivityConversationManager = (ConversationManagementInterface) activity;
        }
        catch(ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement ConversationManagementInterface");
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_conversations_inbox, menu);
        return true;
    }

    @Override
    public void onBackPressed()
    {
        //super.onBackPressed();
        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "You are already signed-in", Toast.LENGTH_SHORT);
        toast.show();

    }*/

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
        else if(id == R.id.action_friends_list){
            startActivity(new Intent(getActivity().getApplicationContext(),ContactsListActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
    public static class ConversationViewHolder extends RecyclerView.ViewHolder {
        TextView messageText;
        TextView nameText;

        public ConversationViewHolder(View itemView) {
            super(itemView);
            nameText = (TextView)itemView.findViewById(R.id.mConversationTitle);
            messageText = (TextView) itemView.findViewById(R.id.mConversationSummary);
        }
    }

    public interface ConversationManagementInterface{
        public void onCreateConversation();
        public void onSelectConversation(String conversation_firebase_ref);
        public void onDeleteConversation(String conversation_firebase_ref);
    }
}

