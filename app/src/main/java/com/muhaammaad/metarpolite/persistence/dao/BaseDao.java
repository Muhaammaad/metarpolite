package com.muhaammaad.metarpolite.persistence.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

import java.util.List;

public interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insertId(T t);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertId(List<T> t);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(T t);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<T> t);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(T t);

    @Delete
    void delete(T t);
}
