package com.example.studentsmoney.controllers;

import android.content.Context;

import androidx.room.Room;

import com.example.studentsmoney.database.AppDatabase;
import com.example.studentsmoney.database.hub.Hub;
import com.example.studentsmoney.database.hub.HubDao;
import com.example.studentsmoney.database.operation.Operation;
import com.example.studentsmoney.database.operation.OperationDao;
import com.example.studentsmoney.enums.Type;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/*
This is a layer between room and View-layer.

This class contains methods which address to room.
It is called by View-layer classes.
 */
public class DBController {

    private static final String DB_NAME = "my-database";

    private AppDatabase appDatabase;
    private HubDao hubDao;
    private OperationDao operationDao;

    //constructor
    public DBController(Context context) {
        appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, DB_NAME).allowMainThreadQueries().build(); //без allowMainThreadQueries() не работало

        hubDao = appDatabase.hubDao();
        operationDao = appDatabase.operationDao();
    }

    //All methods for Hub
    public List<Hub> getAllHubsByType(Type type){
        return hubDao.getAllByType(type);
    }

    public Hub getHubByName(String name){
        return hubDao.getHubByName(name);
    }

    public float getSumOfCurrentSums(){
        return hubDao.getSumOfCurrentSums(Type.asset);
    }

    public float getSumOfPlannedSums(){
        return hubDao.getSumOfPlannedSums(Type.spend);
    }

    public int getNumberOfHubsByType(Type type){
        List<Hub> list = hubDao.getAllByType(type);
        return list.size();
    }

    public void insertHub(Hub hub){
        hubDao.insert(hub);
    }

    public void deleteHub(Hub hub){
        hubDao.delete(hub);
    }

    public void update(Hub hub){
        hubDao.update(hub);
    }

    //All methods for Operation
    public List<Operation> getOperationByName(String name){
        return operationDao.getAllByName(name);
    }

    public float getSumSpentOnMonth(){
        Calendar calendar = Calendar.getInstance();
        Date dateTo = calendarToDate(calendar);
        calendar.add(Calendar.MONTH, -1);
        Date dateFrom = calendarToDate(calendar);

        return operationDao.getSumSpentBetweenDates(dateFrom, dateTo);
    }

    public void insertOperation(Operation operation){
        operationDao.insert(operation);
    }

    //Convert Date to Calendar
    private Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    //Convert Calendar to Date
    private Date calendarToDate(Calendar calendar) {
        return calendar.getTime();
    }
}
