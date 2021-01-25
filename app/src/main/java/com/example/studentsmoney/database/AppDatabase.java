package com.example.studentsmoney.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.studentsmoney.database.hub.Hub;
import com.example.studentsmoney.database.hub.HubDao;
import com.example.studentsmoney.database.operation.Operation;
import com.example.studentsmoney.database.operation.OperationDao;

//required for room

/**
 * A simple abstract class that extends {@link RoomDatabase}.
 * <p>
 * {@link com.example.studentsmoney.controllers.DBController} is using
 * this class for managing the database.
 *
 * @author MarkYav
 * @version 1.0
 * @since 2021-01-25
 */
@Database(entities = {Hub.class, Operation.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {

    /**
     * This method allow to use {@link HubDao} for working with
     * {@link Hub}'s table.
     *
     * @return
     */
    public abstract HubDao hubDao();

    /**
     * This method allow to use {@link OperationDao} for working with
     * {@link Operation}'s table.
     *
     * @return
     */
    public abstract OperationDao operationDao();
}
