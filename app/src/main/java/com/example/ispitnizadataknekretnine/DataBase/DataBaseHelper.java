package com.example.ispitnizadataknekretnine.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DataBaseHelper extends OrmLiteSqliteOpenHelper {


    private static String DATABASE_NAME= "agencijaZaNekretnine";
    private static int DATABASE_VERSION = 1;

    Dao<Nekretnina, Integer> mNekretninaDao = null;


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {

        try {
            TableUtils.createTable(connectionSource, Nekretnina.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

        try {
            TableUtils.dropTable(connectionSource, Nekretnina.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Dao<Nekretnina, Integer> getmNekretninaDao () throws SQLException{
        if (mNekretninaDao == null){
            mNekretninaDao =getDao(Nekretnina.class);
        }
        return mNekretninaDao;
    }

    @Override
    public void close() {
        mNekretninaDao=null;
        super.close();
    }
}
