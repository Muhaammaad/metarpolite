
package com.muhaammaad.metarpolite.persistence.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "QualityControlFlags", foreignKeys = @ForeignKey(entity = Metar.class,
        parentColumns = "id",
        childColumns = "metarId",
        onDelete = CASCADE), indices = {@Index(value = {"id"}, unique = true), @Index(value = {"metarId"})})
public class QualityControlFlags implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;
    public long metarId;
    public String auto;
    public String noSignal;
    public String autoStation;
    public String corrected;
    public String maintenanceIndicator;
    public String lightningSensorOff;
    public String freezingRainSensorOff;
    public String presentWeatherSensorOff;

    @Override
    public String toString() {
        return "QualityControlFlags{" +
                ", metarId=" + metarId +
                ", auto='" + auto + '\'' +
                ", noSignal='" + noSignal + '\'' +
                ", autoStation='" + autoStation + '\'' +
                ", corrected='" + corrected + '\'' +
                ", maintenanceIndicator='" + maintenanceIndicator + '\'' +
                ", lightningSensorOff='" + lightningSensorOff + '\'' +
                ", freezingRainSensorOff='" + freezingRainSensorOff + '\'' +
                ", presentWeatherSensorOff='" + presentWeatherSensorOff + '\'' +
                '}';
    }
}
