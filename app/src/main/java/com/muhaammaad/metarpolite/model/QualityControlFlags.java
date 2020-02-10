
package com.muhaammaad.metarpolite.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class QualityControlFlags implements Serializable {

    public String auto;
    public String noSignal;
    public String autoStation;
    public String corrected;
    public String maintenanceIndicator;
    public String lightningSensorOff;
    public String freezingRainSensorOff;
    public String presentWeatherSensorOff;
}
