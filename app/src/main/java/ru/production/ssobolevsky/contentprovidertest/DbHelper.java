package ru.production.ssobolevsky.contentprovidertest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by pro on 07.07.2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final int VERSION_DB = 1;
    private static final String DB_NAME = "notes.db";

    public DbHelper(Context context) {
        this(context, DB_NAME, null, VERSION_DB);
    }

    private DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        createEmptyTable(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        deleteTable(sqLiteDatabase);
        createEmptyTable(sqLiteDatabase);
    }

    private void createEmptyTable(SQLiteDatabase database) {
        database.execSQL("create table NOTE(id integer primary key, title text, subtitle text)");
    }

    private void deleteTable(SQLiteDatabase database) {
        database.execSQL("drop table if exists NOTE");
    }
}

