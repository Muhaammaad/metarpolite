
package com.muhaammaad.metarpolite.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Response {

    @SerializedName("xmlns:xsd")
    @Expose
    public String xmlnsXsd;
    @SerializedName("request")
    @Expose
    public Request request;
    @SerializedName("data")
    @Expose
    public Data data = new Data();
    @SerializedName("xsi:noNamespaceSchemaLocation")
    @Expose
    public String xsiNoNamespaceSchemaLocation;
    @SerializedName("request_index")
    @Expose
    public String requestIndex;
    @SerializedName("warnings")
    @Expose
    public String warnings;
    @SerializedName("time_taken_ms")
    @Expose
    public String timeTakenMs;
    @SerializedName("xmlns:xsi")
    @Expose
    public String xmlnsXsi;
    @SerializedName("version")
    @Expose
    public String version;
    @SerializedName("data_source")
    @Expose
    public DataSource dataSource;
    @SerializedName("errors")
    @Expose
    public String errors;

}
