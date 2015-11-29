package com.jaghory.mi491app;

/**
 * Created by zach on 11/29/15.
 */
public class ConversationOverview {
    private String cTitle;
    private String cSummary;

    public ConversationOverview(String cTitle, String cSummary) {
        this.cTitle = cTitle;
        this.cSummary = cSummary;
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
