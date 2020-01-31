
package com.muhaammaad.metarpolite.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QualityControlFlags {

    @SerializedName("auto")
    @Expose
    public String auto;
    @SerializedName("no_signal")
    @Expose
    public String noSignal;

}
