package com.apoorv.dubey.android.model;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ImportantIssue {
    private String id = "";
    private String url = "";
    private String issueDescription = "";
    private boolean isPending = true;

    public boolean isPending() {
        return isPending;
    }

    public void setPending(boolean pending) {
        isPending = pending;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
