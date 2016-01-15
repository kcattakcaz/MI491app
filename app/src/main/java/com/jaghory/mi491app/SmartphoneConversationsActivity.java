package com.jaghory.mi491app;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SmartphoneConversationsActivity extends AppCompatActivity implements Dashboard.ConversationManagementInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smartphone_conversations);
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
        System.out.println("I WAS CALLED");
        ConversationActivity conv_viewer_frag = new ConversationActivity();
        conv_viewer_frag.setCurrentConversationFirebaseRef(conversation_firebase_ref);
        if(findViewById(R.id.smartphone_conversations_fragment_secondary_container) != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.smartphone_conversations_fragment_secondary_container, conv_viewer_frag).commit();
        }
        else{
            getSupportFragmentManager().beginTransaction().replace(R.id.smartphone_conversations_fragment_primary_container, conv_viewer_frag).commit();
        }
    }
    public void onDeleteConversation(String conversation_firebase_ref){

    }

}
