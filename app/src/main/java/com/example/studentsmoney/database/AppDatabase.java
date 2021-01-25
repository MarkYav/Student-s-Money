package com.example.studentsmoney.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.studentsmoney.database.hub.Hub;
import com.example.studentsmoney.database.hub.HubDao;
import com.example.studentsmoney.database.operation.Operation;
import com.example.studentsmoney.database.operation.OperationDao;

//required for room
@Database(entities = {Hub.class, Operation.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract HubDao hubDao();

    public abstract OperationDao operationDao();
}
