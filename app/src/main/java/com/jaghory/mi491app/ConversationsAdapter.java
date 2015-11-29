package com.jaghory.mi491app;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by zach on 11/27/15.
 */

public class ConversationsAdapter extends RecyclerView.Adapter<ConversationsAdapter.ViewHolder> {
    private ArrayList<Conversation> convos;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mConversationTitle;
        public TextView mConversationSummary;
        public ViewHolder(View listItemView) {
            super(listItemView);
            mConversationTitle = (TextView) listItemView.findViewById(R.id.mConversationTitle);
            mConversationSummary = (TextView) listItemView.findViewById(R.id.mConversationSummary);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ConversationsAdapter(String firebase_base_url, String user) {
        convos = new ArrayList<Conversation>();
        User user1 = new User("jsmith","John Smith");
        User user2 = new User("jappleseed","Johnny Appleseed");
        Conversation c1 = new Conversation();
        ArrayList<User> user0 = new ArrayList<>();
        user0.add(user1);
        user0.add(user2);
        c1.setUsers(user0);
        convos.add(c1);
        convos.add(c1);
        convos.add(c1);
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ConversationsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_conversations_overview, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Conversation cv = convos.get(position);
        holder.mConversationTitle.setText(cv.getTitle());
        System.out.println(cv.getTitle());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return convos.size();
    }
}