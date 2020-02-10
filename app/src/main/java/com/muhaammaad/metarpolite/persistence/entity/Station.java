package com.muhaammaad.metarpolite.persistence.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Station", indices = {@Index(value = {"stationId"}, unique = true)})
public class Station implements Serializable {
    @NonNull
    @PrimaryKey
    public String stationId;
    public String elevation;
    public String country;
    public String site;
    public String latitude;
    public String state;
    public String longitude;

    @NonNull
    public String getStationId() {
        return stationId;
    }

    public void setStationId(@NonNull String stationId) {
        this.stationId = stationId;
    }

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }

    public String getCountry() {
        return country != null ? country : "";
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSite() {
        return site != null ? site : "";
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getState() {
        return state != null ? state : "";
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "ClassPojo [elevation = " + elevation + ", country = " + country + ", site = " + site + ", station_id = " + stationId + ", latitude = " + latitude + ", state = " + state + ", longitude = " + longitude + "]";
    }
}
