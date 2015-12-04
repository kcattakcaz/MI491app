package com.jaghory.mi491app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zach on 11/29/15.
 */
public class ConversationOverview extends HashMap {
    public ConversationOverview(){
        super();
    }

    public String getcTitle(){
        return (String)this.get("cTitle");
    }
    public String getcSummary(){
        return (String)this.get("cSummary");
    }

    public Object getcMembers(){
        return this.get("cMembers");
    }
   /* private String cTitle;
    private String cSummary;


    private Map<String, String> cMembers;

    public ConversationOverview(String cTitle, String cSummary, Map<String, String> cMembers) {
        this.cTitle = cTitle;
        this.cSummary = cSummary;
        this.cMembers = cMembers;
    }

    public Map<String, String> getcMembers() {
        return cMembers;
    }

    public void setcMembers(Map<String, String> cMembers) {
        this.cMembers = cMembers;
    }

    public ConversationOverview(){

    }

    public String getcTitle() {
        return cTitle;
    }

    public void setcTitle(String cTitle) {
        this.cTitle = cTitle;
    }

    public String getcSummary() {
        return cSummary;
    }

    public void setcSummary(String cSummary) {
        this.cSummary = cSummary;
    } */

}
