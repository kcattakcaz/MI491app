package com.jaghory.mi491app;

import java.util.List;

/**
 * Created by zach on 11/29/15.
 */
public class ConversationOverview {
    private String cTitle;
    private String cSummary;

    public List getcMembers() {
        return cMembers;
    }

    public void setcMembers(List cMembers) {
        this.cMembers = cMembers;
    }

    private List cMembers;

    public ConversationOverview(String cTitle, String cSummary,List cMembers) {
        this.cTitle = cTitle;
        this.cSummary = cSummary;
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
    }

}
