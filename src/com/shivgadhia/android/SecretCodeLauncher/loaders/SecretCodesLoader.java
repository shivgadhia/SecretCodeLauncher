package com.shivgadhia.android.SecretCodeLauncher.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;
import com.shivgadhia.android.SecretCodeLauncher.models.SecretCode;
import com.shivgadhia.android.SecretCodeLauncher.persistance.SecretCodesReader;

import java.util.ArrayList;

public class SecretCodesLoader extends AsyncTaskLoader<ArrayList<SecretCode>> {

    ArrayList<SecretCode> secretCodes;
    public static final int LOADER_ID = 100;

    private SecretCodesReader reader;

    public SecretCodesLoader(Context context, SecretCodesReader reader) {
        super(context);
        this.reader = reader;

    }

    @Override
    public ArrayList<SecretCode> loadInBackground() {
        return reader.getAll();
    }

    @Override
    public void deliverResult(ArrayList<SecretCode> data) {
        secretCodes = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if (secretCodes != null && secretCodes.size() > 0) {
            super.deliverResult(secretCodes);
            return;
        }

        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        secretCodes = null;
    }

}
