package com.example.studentsmoney.database.operation;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.Calendar;

@Entity
public class Operation {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "from_name")
    public String fromName;

    @ColumnInfo(name = "to_name")
    public String toName;

    @ColumnInfo
    public float sum;

    @ColumnInfo
    public Date date;

    @ColumnInfo
    public String description;


}
