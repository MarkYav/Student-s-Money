package com.example.studentsmoney.database.hub;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.studentsmoney.enums.Currency;
import com.example.studentsmoney.enums.Type;

@Entity
public class Hub {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo
    public String name;

    @ColumnInfo
    public Type type;

    @ColumnInfo
    public Currency currency;

    @ColumnInfo(name = "planned_sum")
    public float plannedSum;

    @ColumnInfo(name = "current_sum")
    public float currentSum;

    @ColumnInfo
    public String area;

    @ColumnInfo
    public String icon;

    @ColumnInfo
    public int order;
}
