package com.shivgadhia.android.SecretCodeLauncher.persistance;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;


public class DatabaseWriter {

    private final ContentResolver contentResolver;

    public DatabaseWriter(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public void saveDataToTable(String table, ContentValues values) {
        Uri uri = createUri(table);
        contentResolver.insert(uri, values);
    }

    private Uri createUri(String tableName) {
        return Uri.parse(SecretCodesProvider.AUTHORITY + tableName);
    }

    public void delete(String tableName, String where, String[] selectionArgs) {
        Uri uri = createUri(tableName);
        contentResolver.delete(uri, where, selectionArgs);
    }
}
