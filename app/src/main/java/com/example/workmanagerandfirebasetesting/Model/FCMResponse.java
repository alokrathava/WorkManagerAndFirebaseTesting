package com.example.workmanagerandfirebasetesting.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FCMResponse {
    @SerializedName("multicast_id")
    @Expose
    private String multicastId;
    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("failure")
    @Expose
    private String failure;
    @SerializedName("canonical_ids")
    @Expose
    private String canonicalIds;
    @SerializedName("results")
    @Expose
    private List<MessageResult> results = null;

    public String getMulticastId() {
        return multicastId;
    }

    public void setMulticastId(String multicastId) {
        this.multicastId = multicastId;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getFailure() {
        return failure;
    }

    public void setFailure(String failure) {
        this.failure = failure;
    }

    public String getCanonicalIds() {
        return canonicalIds;
    }

    public void setCanonicalIds(String canonicalIds) {
        this.canonicalIds = canonicalIds;
    }

    public List<MessageResult> getResults() {
        return results;
    }

    public void setResults(List<MessageResult> results) {
        this.results = results;
    }
}
