package com.shivgadhia.android.SecretCodeLauncher.persistance;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;


public class DatabaseReader {

    private final ContentResolver contentResolver;

    public DatabaseReader(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    /**
     * (1) Read - generic table support
     */
    public Cursor getAllFrom(String tableName) {
        Uri uri = createUri(tableName);
        return contentResolver.query(uri, null, null, null, null);
    }

    public Cursor getAllFrom(String tableName, String selection, String[] selectionArgs) {
        Uri uri = createUri(tableName);
        return contentResolver.query(uri, null, selection, selectionArgs, null);
    }


    /**
     * (2) Read - primary key support
     */
    public Cursor getFrom(String tableName, int primaryKey) {
        Uri uri = createUri(tableName + "/" + primaryKey);
        return contentResolver.query(uri, null, null, null, null);
    }

    /**
     * (3) Read - one to many support
     */
    public Cursor getChildren(String parentTableName, String childTableName, int primaryKey) {
        Uri uri = createUri(parentTableName + "/" + primaryKey + "/" + childTableName);
        return contentResolver.query(uri, null, null, null, null);
    }

    /**
     * (4) Read - group by & having support
     */
    public Cursor getGroupedByAndHaving(String tableName, String column, String having, String[] selection) {
        Uri uri = createUri(tableName + "?groupBy=" + column + "&having=" + having);
        return contentResolver.query(uri, selection, null, null, null);
    }

    /**
     * (5) Read - limit support
     */
    public Cursor getLimited(String tableName, int limit) {
        Uri uri = createUri(tableName + "?limit=" + limit);
        return contentResolver.query(uri, null, null, null, null);
    }

    /**
     * (6) Read - distinct support
     */
    public Cursor getDistinct(String tableName, String[] selection) {
        Uri uri = createUri(tableName + "?distinct=true");
        return contentResolver.query(uri, selection, null, null, null);
    }


    /**
     * SortBy
     */
    public Cursor getAllAndSortBy(String tableName, String sortBy) {
        Uri uri = createUri(tableName);
        return contentResolver.query(uri, null, null, null, sortBy);
    }


    private Uri createUri(String path) {
        return Uri.parse(SecretCodesProvider.AUTHORITY + path);
    }
}
