
package com.muhaammaad.metarpolite.persistence.entity;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.io.Serializable;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "SkyCondition", foreignKeys = @ForeignKey(entity = Metar.class,
        parentColumns = "id",
        childColumns = "metarId",
        onDelete = CASCADE), indices = {@Index(value = {"id"}, unique = true), @Index(value = {"metarId"})})
public class SkyCondition implements Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;
    public long metarId;
    public String cloudBaseFtAgl;
    public String skyCover;

    public SkyCondition() {
    }

    @Ignore
    public SkyCondition(long metarId, String cloudBaseFtAgl, String skyCover) {
        this.metarId = metarId;
        this.cloudBaseFtAgl = cloudBaseFtAgl;
        this.skyCover = skyCover;
    }

    @Override
    public String toString() {
        return "SkyCondition{" +
                "cloudBaseFtAgl='" + cloudBaseFtAgl + '\'' +
                ", skyCover='" + skyCover + '\'' +
                '}';
    }
}
