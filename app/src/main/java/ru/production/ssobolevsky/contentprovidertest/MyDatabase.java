package ru.production.ssobolevsky.contentprovidertest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pro on 07.07.2018.
 */

public class MyDatabase {

    private DbHelper mDbHelper;

    public MyDatabase(Context context) {
        mDbHelper = new DbHelper(context);
    }

    public void addNote(MyNote note) {
        SQLiteDatabase database = null;
        try {
            database = mDbHelper.getWritableDatabase();
            ContentValues contentValues = getContentValues(note);
            database.beginTransaction();
            addNoteInternal(database, contentValues);
            database.setTransactionSuccessful();
        } catch (SQLiteException e) {
            Log.wtf("SQL", "SQLiteException");
        } finally {
            if (database != null) {
                if (database.inTransaction()) {
                    database.endTransaction();
                }
                database.close();
            }
        }
    }

    private ContentValues getContentValues(MyNote note) {
        ContentValues values = new ContentValues();
        values.put(MyNote.TITLE, note.getTitle());
        values.put(MyNote.SUBTITLE, note.getSubtitle());
        return values;
    }

    private void addNoteInternal(SQLiteDatabase database, ContentValues contentValues) {
        long a = database.insert(MyNote.NOTE_TABLE, null, contentValues);
    }

    public List<MyNote> getNotes() {
        SQLiteDatabase database = null;
        List<MyNote> list = new ArrayList<>();
        try {
            database = mDbHelper.getReadableDatabase();
            database.beginTransaction();
            Cursor cursor = database.query(MyNote.NOTE_TABLE, null, null, null, null, null, null);
            list = parseAllNotes(cursor);
            database.setTransactionSuccessful();
        } catch (SQLiteException e){
            Log.wtf("SQL", "SQLiteException");
        } finally {
            if (database != null) {
                if (database.inTransaction()) {
                    database.endTransaction();
                }
                database.close();
            }
        }

        return list;
    }

    private List<MyNote> parseAllNotes(Cursor cursor) {
        List<MyNote> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            int title = cursor.getColumnIndex(MyNote.TITLE);
            int subtitle = cursor.getColumnIndex(MyNote.SUBTITLE);
            int id = cursor.getColumnIndex(MyNote.ID);
            do {
                MyNote note = new MyNote(cursor.getInt(id), cursor.getString(title), cursor.getString(subtitle));
                list.add(note);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public MyNote getNoteById(int id) {
        Log.wtf("TAG", "ID = " + id);
        SQLiteDatabase database = null;
        MyNote myNote = null;
        try {
            database = mDbHelper.getReadableDatabase();
            database.beginTransaction();
            Cursor cursor = database.query(MyNote.NOTE_TABLE, null, "id = " + id, null, null, null, null);
            myNote = parseNote(cursor);
            cursor.close();
            database.setTransactionSuccessful();
        } catch (SQLiteException e) {
            Log.wtf("SQL", "SQLiteException");
        } finally {
            if (database != null) {
                if (database.inTransaction()) {
                    database.endTransaction();
                }
                database.close();
            }
        }
        return myNote;
    }

    private MyNote parseNote(Cursor cursor) {
        if (cursor.moveToFirst()) {
            return  new MyNote(cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("subtitle")));
        }
        return null;
    }

    public void updateNote(MyNote note) {
        SQLiteDatabase database = null;
        try {
            database = mDbHelper.getWritableDatabase();
            database.beginTransaction();
            ContentValues newValues = new ContentValues();
            newValues.put(MyNote.TITLE, note.getTitle());
            newValues.put(MyNote.SUBTITLE, note.getSubtitle());
            database.update(MyNote.NOTE_TABLE, newValues, "id = " + note.getId(), null);
            database.setTransactionSuccessful();
        } catch (SQLiteException e) {
            Log.wtf("SQL", "SQLiteException");
        } finally {
            if (database != null) {
                if (database.inTransaction()) {
                    database.endTransaction();
                }
                database.close();
            }
        }
    }

    public DbHelper getDbHelper() {
        return mDbHelper;
    }
}
