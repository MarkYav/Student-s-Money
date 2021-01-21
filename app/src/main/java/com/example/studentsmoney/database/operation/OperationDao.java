package com.example.studentsmoney.database.operation;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.studentsmoney.database.hub.Hub;

import java.util.Date;
import java.util.List;

@Dao
public interface OperationDao {
    @Query("SELECT * FROM operation " +
            "ORDER BY date DESC")
    List<Operation> getAll();

    @Query("SELECT * FROM operation " +
            "WHERE from_name LIKE (:name) OR to_name LIKE (:name) " +
            "ORDER BY date DESC")
    List<Operation> getAllByName(String name);

    @Query("SELECT SUM(sum) FROM operation " +
            "WHERE date BETWEEN (:from) AND (:to) " +
            "ORDER BY date DESC")
    float getSumSpentBetweenDates(Date from, Date to);

    @Insert
    void insert(Operation operation);

    @Delete
    void delete(Operation operation);
}
