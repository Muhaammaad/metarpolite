
package com.muhaammaad.metarpolite.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SkyCondition implements Serializable {

    @SerializedName("cloud_base_ft_agl")
    @Expose
    public String cloudBaseFtAgl;
    @SerializedName("sky_cover")
    @Expose
    public String skyCover;

    @Override
    public String toString() {
        return "SkyCondition{" +
                "cloudBaseFtAgl='" + cloudBaseFtAgl + '\'' +
                ", skyCover='" + skyCover + '\'' +
                '}';
    }
}
