package com.truongsinh.projectrtos.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.truongsinh.projectrtos.DataBase.CreateSQLite;
import com.truongsinh.projectrtos.ThongTinUser.User;

import java.util.ArrayList;

public class TruyVanDataBase {
    CreateSQLite dataBase;
    public  SQLiteDatabase sqLiteDatabase;
    public TruyVanDataBase(Context context)
    {
        dataBase = new CreateSQLite(context);
    }

    public void open_db()
    {
        sqLiteDatabase = dataBase.getWritableDatabase();
    }
    public void close_db()
    {
        dataBase.close();
    }
    public boolean inSertTKMK(User user)
    {

            ContentValues contentValues = new ContentValues();
            contentValues.put(CreateSQLite.TK, user.getTk());
            contentValues.put(CreateSQLite.MK, user.getMk());
            long x = sqLiteDatabase.insert(CreateSQLite.TABLELOGIN, null, contentValues);
            if(x == -1)
                return false;
            return true;

    }
    public void deleteTKMK(String tk)
    {
        sqLiteDatabase.delete(CreateSQLite.TABLELOGIN,CreateSQLite.TK+"="+tk,null);
    }

    public User getTTUser()
    {
        Cursor cursor = sqLiteDatabase.query(CreateSQLite.TABLELOGIN,new String[]{CreateSQLite.TK,CreateSQLite.MK},null
                                            ,null,null,null,null);
        cursor.moveToFirst();
        String tk = cursor.getString(cursor.getColumnIndex(CreateSQLite.TK));
        String mk = cursor.getString(cursor.getColumnIndex(CreateSQLite.MK));
        User user = new User(tk,mk);
        return user;
    }
    public boolean updateMK(String mk)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateSQLite.MK,mk);
        int row = sqLiteDatabase.update(CreateSQLite.TABLELOGIN,contentValues,CreateSQLite.ID_TK+"=1",null);
        if(row != 0)
            return true;
        return false;
    }
    public void updateViTriData(int x)
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateSQLite.POSITION,x);
        sqLiteDatabase.update(CreateSQLite.TABLEPOSITION,contentValues,CreateSQLite.ID_POSITION+"=1",null);
    }
    public void insertData(float nhietdo,float doam)
    {
        // Nhiệt độ
        Cursor cursor = sqLiteDatabase.query(CreateSQLite.TABLEPOSITION,new String[]{CreateSQLite.POSITION},null,null,
                null,null,null);
        cursor.moveToFirst();
        int position = cursor.getInt(0);
        Log.d("tag","cursor + "+position);
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateSQLite.NHIETDO,nhietdo);
        sqLiteDatabase.update(CreateSQLite.TABLETEMPERATURE,contentValues,CreateSQLite.ID_TEMPERATURE+"="+position,null);
        // Độ ẩm
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(CreateSQLite.DOAM,doam);
        sqLiteDatabase.update(CreateSQLite.TABLEDOAM,contentValues1,CreateSQLite.ID_DOAM+"="+position,null);
        position = position+1;
        if(position > 7)
            position = 1;
        updateViTriData(position);

    }
    public ArrayList<Float> getAllDataTemp()
    {
        ArrayList<Float> arr= new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(CreateSQLite.TABLETEMPERATURE,new String[]{CreateSQLite.NHIETDO},null,
                null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            arr.add(cursor.getFloat(0));
            cursor.moveToNext();
        }
        return arr;
    }
    public ArrayList<Float> getAllDataDoAm()
    {
        ArrayList<Float> arr= new ArrayList<>();
        Cursor cursor = sqLiteDatabase.query(CreateSQLite.TABLEDOAM,new String[]{CreateSQLite.DOAM},null,
                null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            arr.add(cursor.getFloat(0));
            cursor.moveToNext();
        }
        return arr;
    }
    public void setAllDataTo0()
    {
        Cursor cursor = sqLiteDatabase.query(CreateSQLite.TABLETEMPERATURE,new String[]{CreateSQLite.NHIETDO},null,null,
                null,null,null);
        cursor.moveToFirst();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CreateSQLite.NHIETDO,1);
        sqLiteDatabase.update(CreateSQLite.TABLETEMPERATURE,contentValues,CreateSQLite.ID_TEMPERATURE+"="+0,null);
        cursor.moveToNext();
        ContentValues contentValues1 = new ContentValues();
        contentValues1.put(CreateSQLite.NHIETDO,0);
        for(int i = 1; i<=7;i++)
        {
            sqLiteDatabase.update(CreateSQLite.TABLETEMPERATURE,contentValues1,CreateSQLite.ID_TEMPERATURE+"="+i,null);
            cursor.moveToNext();
        }
    }

}
