package ru.production.ssobolevsky.contentprovidertest;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pro on 07.07.2018.
 */

public class ConvertUtils {

    public static final String ID = "id";
    public static final String TITLE = "title";
    public static final String SUBTITLE = "subtitle";

    public static MyNote convertValuesToNote(ContentValues values) {
        MyNote note = new MyNote(values.getAsInteger(ID), values.getAsString(TITLE), values.getAsString(SUBTITLE));
        return note;
    }

    public static ContentValues convertMyNotetoValues(MyNote note) {
        ContentValues values = new ContentValues();
        values.put(ID, note.getId());
        values.put(TITLE, note.getTitle());
        values.put(SUBTITLE, note.getSubtitle());
        return values;
    }

    public static List<MyNote> convertCursorToNotes(Cursor cursor) {
        List<MyNote> notes = new ArrayList<>();

        while (cursor.moveToNext()) {
            MyNote note = new MyNote(cursor.getInt(cursor.getInt(cursor.getColumnIndex(MyNote.ID))),
                    cursor.getString(cursor.getColumnIndex(MyNote.TITLE)),
                    cursor.getString(cursor.getColumnIndex(MyNote.SUBTITLE)));
            notes.add(note);
        }
        cursor.close();
        return notes;
    }

}
