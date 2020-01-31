package com.muhaammaad.metarpolite.model;

public class Station {
    public String elevation_m;
    public String country;
    public String site;
    public String station_id;
    public String latitude;
    public String state;
    public String longitude;

    @Override
    public String toString() {
        return "ClassPojo [elevation_m = " + elevation_m + ", country = " + country + ", site = " + site + ", station_id = " + station_id + ", latitude = " + latitude + ", state = " + state + ", longitude = " + longitude + "]";
    }
}
