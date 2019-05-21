package com.truongsinh.projectrtos.DataBase;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.truongsinh.projectrtos.DAO.TruyVanDataBase;
import com.truongsinh.projectrtos.MainActivity;
import com.truongsinh.projectrtos.ThongTinUser.User;

import androidx.annotation.Nullable;

public class CreateSQLite extends SQLiteOpenHelper {
    public static final String DATABASE = "DataBase";
    public static final String TABLELOGIN ="Account";
    public static final String ID_TK = "_id";
    public static final String TK = "TK";
    public static final String MK = "MK";
    //
    public static final String TABLEPOSITION ="position";
    public static final String ID_POSITION = "_id";
    public static final String POSITION = "PInsertData";
    public static final String TABLETEMPERATURE ="Temperature";
    public static final String ID_TEMPERATURE = "_id";
    public static final String NHIETDO = "DATA";
    public static final String TABLEDOAM ="DoAm_TB";
    public static final String DOAM = "DATA_TB";
    public static final String ID_DOAM = "_id";
    //
    public static final int version = 1;

    public CreateSQLite(@Nullable Context context) {
        super(context, DATABASE, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableLogin = "CREATE TABLE " + TABLELOGIN + "("+ID_TK
                +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ TK+" TEXT UNIQUE," + MK+" TEXT)";
        db.execSQL(createTableLogin);
        String createTableTem = "CREATE TABLE " + TABLETEMPERATURE + "("+ID_TEMPERATURE
                +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ NHIETDO+" REAL)";
        db.execSQL(createTableTem);
        Log.d("tag","tao bang nhiet do");
        String createTableDoAm = "CREATE TABLE " + TABLEDOAM + "("+ID_DOAM
                +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ DOAM+" REAL)";
        db.execSQL(createTableDoAm);
        Log.d("tag","tao bang do am");
        String createTablePostion = "CREATE TABLE " + TABLEPOSITION + "("+ID_POSITION
                +" INTEGER PRIMARY KEY AUTOINCREMENT,"+ POSITION+" INTEGER)";
        db.execSQL(createTablePostion);
        Log.d("tag","tao bang position");
        initData(db);
    }
    public void initData(SQLiteDatabase db)
    {
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(CreateSQLite.NHIETDO,0);
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(CreateSQLite.DOAM,0);
        for(int i = 1;i<=7;i++)
        {
            db.insert(CreateSQLite.TABLETEMPERATURE,null,contentValues1);
            db.insert(CreateSQLite.TABLEDOAM,null,contentValues2);
        }
        Log.d("tag","insert data");
        ContentValues contentValues3 = new ContentValues();
        contentValues3.put(CreateSQLite.TK, "nhom2");
        contentValues3.put(CreateSQLite.MK, "sinh");
        db.insert(CreateSQLite.TABLELOGIN, null, contentValues3);
        Log.d("tag","insert tk thanh cong");
        ContentValues contentValues4 = new ContentValues();
        contentValues4.put(CreateSQLite.POSITION, 1);
        db.insert(CreateSQLite.TABLEPOSITION,null,contentValues4);
        Log.d("tag","insert position tc");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
