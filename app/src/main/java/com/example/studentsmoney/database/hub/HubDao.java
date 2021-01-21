package com.example.studentsmoney.database.hub;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.studentsmoney.enums.Type;

import java.util.List;
import java.util.Vector;

@Dao
public interface HubDao {
    @Query("SELECT * FROM Hub " +
            "ORDER BY `order` ASC")
    List<Hub> getAll();

    @Query("SELECT * FROM Hub " +
            "WHERE type LIKE (:type) " +
            "ORDER BY `order` ASC")
    List<Hub> getAllByType(Type type);

    @Query("SELECT * FROM Hub " +
            "WHERE area LIKE (:area) " +
            "ORDER BY `order` ASC")
    List<Hub> getAllByArea(String area);

    @Query("SELECT * FROM Hub " +
            "WHERE name LIKE (:name) " +
            "LIMIT 1")
    Hub getHubByName(String name);

    @Query("SELECT SUM(current_sum) FROM Hub " +
            "WHERE type LIKE (:type)")
    float getSumOfCurrentSums(Type type);

    @Query("SELECT SUM(planned_sum) FROM Hub " +
            "WHERE type LIKE (:type)")
    float getSumOfPlannedSums(Type type);

    @Insert
    void insert(Hub hub);
    //TODO проверять на наличие 2 одинаковых hub`ов (в DBController)

    @Delete
    void delete(Hub hub);

    @Update
    void update(Hub hub);
}
