package ru.production.ssobolevsky.contentprovidertest;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.net.URI;

public class MyContentProvider extends ContentProvider {

    private DAO mDAO;
    private MyDatabase mMyDatabase;
    private static final String AUTHORITY = "ru.production.ssobolevsky.contentprovidertest";
    private static final String NOTES_TABLE = "notes.db";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + NOTES_TABLE);

    public static final int NOTES = 1;
    public static final int NOTE_ID = 2;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHORITY, NOTES_TABLE, NOTES);
        sUriMatcher.addURI(AUTHORITY, NOTES_TABLE + "/#", NOTE_ID);
    }

    public MyContentProvider() {

    }

    @Override
    public boolean onCreate() {
        mMyDatabase = new MyDatabase(getContext());
        mDAO = new DaoImpl(mMyDatabase);
        return mMyDatabase != null;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(NOTES_TABLE);

        int uriType = sUriMatcher.match(uri);

        switch (uriType) {
            case NOTE_ID :
                queryBuilder.appendWhere(MyNote.ID + " = " + uri.getLastPathSegment());
                break;
            case NOTES :
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri");
        }
        Cursor cursor = queryBuilder.query(mMyDatabase.getDbHelper().getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sUriMatcher.match(uri);
        int id = 0;
        switch (uriType) {
            case NOTE_ID :
                break;
            case NOTES :
                id = ConvertUtils.convertValuesToNote(values).getId();
                mDAO.addNote(ConvertUtils.convertValuesToNote(values));
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(NOTES_TABLE + "/" + id);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        int uriType = sUriMatcher.match(uri);

        switch (uriType) {
            case NOTE_ID :
                mDAO.updateNote(ConvertUtils.convertValuesToNote(values));
                break;
            case NOTES :
                break;
            default:
                throw new IllegalArgumentException("Unknown Uri");
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return 0;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
