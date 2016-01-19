package com.jaghory.mi491app;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class SmartphoneConversationsActivity extends AppCompatActivity implements Dashboard.ConversationManagementInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);
        if(savedInstanceState != null){
            return;
        }

        //ConversationsOverviewFragment conv_overview_frag = new ConversationsOverviewFragment();
        Dashboard conv_overview_frag = new Dashboard();
        conv_overview_frag.setArguments(getIntent().getExtras());
        ConversationActivity conv_viewer_frag = new ConversationActivity();

        getSupportFragmentManager().beginTransaction().add(R.id.smartphone_conversations_fragment_primary_container, conv_overview_frag).commit();
        if(findViewById(R.id.smartphone_conversations_fragment_secondary_container) != null) {
            getSupportFragmentManager().beginTransaction().add(R.id.smartphone_conversations_fragment_secondary_container,conv_viewer_frag).commit();
        }

    }

    @Override
    public void onCreateConversation(){

    }
    public void onSelectConversation(String conversation_firebase_ref){
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(fab.getVisibility() != View.VISIBLE) {
            fab.setVisibility(View.VISIBLE);
            fab.animate().translationY(fab.getHeight())
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                        }
                    });
        }


        final ConversationActivity conv_viewer_frag = new ConversationActivity();
        conv_viewer_frag.setCurrentConversationFirebaseRef(conversation_firebase_ref);
        if(findViewById(R.id.smartphone_conversations_fragment_secondary_container) != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.smartphone_conversations_fragment_secondary_container, conv_viewer_frag).commit();
        }
        else{
            getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right,android.R.anim.slide_in_left,android.R.anim.slide_out_right).replace(R.id.smartphone_conversations_fragment_primary_container, conv_viewer_frag).commit();

        }


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conv_viewer_frag.sendMessage();
            }
        });
    }
    public void onDeleteConversation(String conversation_firebase_ref){

    }

}
