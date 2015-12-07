package com.jaghory.mi491app;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseRecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ContactsListActivity extends AppCompatActivity {

    public HashMap<String,Double> contacts_list = new HashMap<String,Double>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        final RecyclerView contactsRecView = (RecyclerView) findViewById(R.id.contactsRecyclerView);
        final ContactsAdapter contactsA = new ContactsAdapter();
        contactsRecView.setAdapter(contactsA);
        RecyclerView.LayoutManager recLayoutMgr = new LinearLayoutManager(getApplicationContext());
        contactsRecView.setLayoutManager(recLayoutMgr);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final Firebase mFireRef = new Firebase("https://mi491app.firebaseio.com");
        mFireRef.child("users").child(mFireRef.getAuth().getUid()).child("contacts").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot contact : dataSnapshot.getChildren()) {

                            Double score = Double.parseDouble(contact.getValue().toString());


                            contacts_list.put(contact.getKey(), score);
                        }
                        contactsRecView.swapAdapter(new ContactsAdapter(contacts_list), false);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        Toast.makeText(ContactsListActivity.this, firebaseError.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_contact);
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
                startActivity(new Intent(getApplicationContext(), AddContactActivity.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_contacts_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_random_contact) {
            Toast toast = Toast.makeText(getApplicationContext(), "Add Random Person", Toast.LENGTH_SHORT);
            toast.show();
        }
        return super.onOptionsItemSelected(item);
    }

    public void populatContacts(
    ){}

    public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
        private HashMap<String,Double> contactDataset;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public View mTextView;
            public ViewHolder(View v) {
                super(v);
                mTextView = v;
            }
        }

        public  ContactsAdapter(){
            contactDataset = new HashMap<String,Double>();
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public ContactsAdapter(HashMap<String,Double> myDataset) {
            contactDataset = myDataset;
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ContactsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(android.R.layout.two_line_list_item, parent, false);


            // set the view's size, margins, paddings and layout parameters
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            int i = 0;
            for(Map.Entry<String,Double> contact : contactDataset.entrySet()){
                if(i == position){
                    contacts_list.put(contact.getKey(),(Double)contact.getValue());
                    final TextView cName = (TextView) holder.itemView.findViewById(android.R.id.text1);
                    final TextView cScore = (TextView) holder.itemView.findViewById(android.R.id.text2);
                    Firebase cFireRef = new Firebase("https://mi491app.firebaseio.com");
                    cFireRef.child("/users/"+contact.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child("displayName").getValue() == null || ((String) dataSnapshot.child("displayName").getValue()).length() <= 1){
                                cName.setText("Deleted User: " + dataSnapshot.getKey());
                            }
                            else {
                                cName.setText((String) dataSnapshot.child("displayName").getValue());
                            }
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            cName.setText("Null");
                        }
                    });

                    cScore.setText(String.valueOf(contact.getValue()));

                }
                i++;
            }


        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return contactDataset.size();
        }
    }

}
