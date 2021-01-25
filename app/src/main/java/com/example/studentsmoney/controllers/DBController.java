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

/**
 * This is a layer between room and View-layer.
 * <p>
 * This class contains methods which address to room.
 * It is called by View-layer classes.
 * <p>
 * Use the {@link DBController#DBController(Context)} method to
 * create an instance of this layer.
 *
 * Use the {@link }
 *
 * @author MarkYav
 * @version 1.0
 * @since 2021-01-25
 */
public class DBController {

    /**
     * This is the name of app's database.
     */
    private static final String DB_NAME = "my-database";

    /**
     * An instance of {@link AppDatabase} for creation
     * {@link HubDao} and {@link OperationDao}.
     */
    private AppDatabase appDatabase;

    /**
     * An instance of {@link HubDao} for working with the {@link Hub}
     * records in the database.
     */
    private HubDao hubDao;

    /**
     * An instance of {@link OperationDao} for working with the {@link Operation}
     * records in the database.
     */
    private OperationDao operationDao;

    /**
     * Use this constructor to create a new instance of
     * this class using the provided parameters.
     *
     * @param context is the context. It is required for
     *                getting axes to {@link HubDao} and {@link OperationDao}.
     */
    public DBController(Context context) {
        appDatabase = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, DB_NAME).allowMainThreadQueries().build(); //без allowMainThreadQueries() не работало

        hubDao = appDatabase.hubDao();
        operationDao = appDatabase.operationDao();
    }

    //All methods for Hub

    /**
     * Use this method to take a list of {@link Hub}s of a certain {@link Type}.
     *
     * @param type The {@link Type} of {@link Hub}s that is wanted
     *             to be returned.
     * @return A list of {@link Hub}s of a certain {@link Type}.
     */
    public List<Hub> getAllHubsByType(Type type) {
        return hubDao.getAllByType(type);
    }

    /**
     * Use this method to take a {@link Hub} of a certain name.
     *
     * @param name The name of {@link Hub} what is wanted.
     * @return A {@link Hub} with this name.
     */
    public Hub getHubByName(String name) {
        return hubDao.getHubByName(name);
    }

    /**
     * Use this method to take the sum of money in the all {@link Hub}s.
     *
     * @return The sum of money.
     */
    public float getSumOfCurrentSums() {
        return hubDao.getSumOfCurrentSums(Type.asset);
    }

    /**
     * Use this method to take the sum of money that is planned
     * to spent (for all {@link Hub}s).
     *
     * @return The sum of money.
     */
    public float getSumOfPlannedSums() {
        return hubDao.getSumOfPlannedSums(Type.spend);
    }

    /**
     * Use this method to get the number of {@link Hub}s of
     * a certain {@link Type}.
     *
     * @param type The {@link Type} of {@link Hub}s which we want to count.
     * @return the number of {@link Hub}s of a certain {@link Type}.
     */
    public int getNumberOfHubsByType(Type type) {
        List<Hub> list = hubDao.getAllByType(type);
        return list.size();
    }

    /**
     * Use this method to add a new {@link Hub} to the database.
     *
     * @param hub The {@link Hub} wanted to be added.
     */
    public void insertHub(Hub hub) {
        hubDao.insert(hub);
    }

    /**
     * Use this method to delete a {@link Hub} from the database.
     *
     * @param hub The {@link Hub} wanted to be deleted.
     */
    public void deleteHub(Hub hub) {
        hubDao.delete(hub);
    }

    /**
     * Use this method to update a {@link Hub} in the database.
     *
     * @param hub The {@link Hub} wanted to be updated.
     */
    public void update(Hub hub) {
        hubDao.update(hub);
    }

    //All methods for Operation

    /**
     * Use this method to take a {@link Operation} of a certain name.
     *
     * @param name The name of {@link Operation} what is wanted.
     * @return A {@link Operation} with this name.
     */
    public List<Operation> getOperationByName(String name) {
        return operationDao.getAllByName(name);
    }

    /**
     * Use this method to take a sum of money that is spent
     * in last month.
     *
     * @return The sum of money.
     */
    public float getSumSpentOnMonth() {
        Calendar calendar = Calendar.getInstance();
        Date dateTo = calendarToDate(calendar);
        calendar.add(Calendar.MONTH, -1);
        Date dateFrom = calendarToDate(calendar);

        return operationDao.getSumSpentBetweenDates(dateFrom, dateTo);
    }

    /**
     * Use this method to add a new {@link Operation} to the database.
     *
     * @param operation The {@link Operation} wanted to be added.
     */
    public void insertOperation(Operation operation) {
        operationDao.insert(operation);
    }

    //Convert Date to Calendar

    /**
     * Use this method to convert a {@link Date} to a {@link Calendar}.
     *
     * @param date The {@link Date} wanted to be converted.
     * @return The {@link Calendar} converted from the {@link Date}.
     */
    private Calendar dateToCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }

    //Convert Calendar to Date

    /**
     * Use this method to convert a {@link Calendar} to a {@link Date}.
     *
     * @param calendar The {@link Calendar} wanted to be converted.
     * @return The {@link Date} converted from the {@link Calendar}
     */
    private Date calendarToDate(Calendar calendar) {
        return calendar.getTime();
    }
}
