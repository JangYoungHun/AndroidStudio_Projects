package com.example.contentprovider_practice;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.time.temporal.ValueRange;

public class PersonProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.contentprovider_practice";
    public static final String BASE_PATH = "person";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" +BASE_PATH);

    private static final int PERSONS = 1;
    private static final int PERSON_ID = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI(AUTHORITY,BASE_PATH,PERSONS);
        uriMatcher.addURI(AUTHORITY,BASE_PATH + "/#",PERSON_ID);
    }

    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {

        DataBaseHelper helper = new DataBaseHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch(uriMatcher.match(uri)){
            case PERSONS:
                return "vnd.android.cursor.dir/persons";
            default:
                throw new IllegalArgumentException("알 수 없는 URI : " + uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor cursor;
        switch (uriMatcher.match(uri)){
            case PERSONS:
                cursor = database.query(DataBaseHelper.TABLE_NAME,DataBaseHelper.COLUMNS,selection,selectionArgs, null,null, DataBaseHelper.PERSON_NAME+" ASC");
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 URI : " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor;

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        long id = database.insert(DataBaseHelper.TABLE_NAME, null, values);
        if(id >0){
            Uri _uri = ContentUris.withAppendedId(CONTENT_URI, id);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }

        throw new SQLException("추가 실패 -> URI : " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case PERSONS:
                count = database.delete(DataBaseHelper.TABLE_NAME,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 URI : " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;

    }

    @Override

    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int count = 0;
        switch (uriMatcher.match(uri)){
            case PERSONS:
                count = database.update(DataBaseHelper.TABLE_NAME,values,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 URI : " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;

    }
}
