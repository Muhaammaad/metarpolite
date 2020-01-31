
package com.muhaammaad.metarpolite.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Data {

    @SerializedName("METAR")
    @Expose
    public List<METAR> metars = new ArrayList<>();
    @SerializedName("Station")
    @Expose
    public HashMap<String,Station> stations = new HashMap<>();
    @SerializedName("num_results")
    @Expose
    public String numResults;

}
