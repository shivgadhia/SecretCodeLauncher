package com.shivgadhia.android.SecretCodeLauncher.persistance;

import android.content.ContentValues;

public class SecretCodesWriter {
    private final DatabaseWriter databaseWriter;

    public SecretCodesWriter(DatabaseWriter databaseWriter) {
        this.databaseWriter = databaseWriter;
    }

    public void saveSecretCode(String activityName, String secretCode) {

        ContentValues values = new ContentValues();
        values.put(Tables.SecretCodes.COL_ACTIVITY_NAME, activityName);
        values.put(Tables.SecretCodes.COL_SECRET_CODE, secretCode);

        databaseWriter.saveDataToTable(Tables.TBL_SECRET_CODES, values);
    }

}
