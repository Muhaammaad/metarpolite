
package com.muhaammaad.metarpolite.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SkyCondition {

    @SerializedName("cloud_base_ft_agl")
    @Expose
    public String cloudBaseFtAgl;
    @SerializedName("sky_cover")
    @Expose
    public String skyCover;

}
