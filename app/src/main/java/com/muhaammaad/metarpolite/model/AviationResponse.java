
package com.muhaammaad.metarpolite.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AviationResponse {

    @SerializedName("response")
    @Expose
    public Response response = new Response();

}
