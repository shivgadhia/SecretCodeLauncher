package com.shivgadhia.android.SecretCodeLauncher.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import com.shivgadhia.android.SecretCodeLauncher.R;
import com.shivgadhia.android.SecretCodeLauncher.loaders.SecretCodesLoader;
import com.shivgadhia.android.SecretCodeLauncher.models.SecretCode;
import com.shivgadhia.android.SecretCodeLauncher.persistance.DatabaseReader;
import com.shivgadhia.android.SecretCodeLauncher.persistance.SecretCodesReader;
import com.shivgadhia.android.SecretCodeLauncher.persistance.Tables;

import java.util.ArrayList;

import static android.widget.AdapterView.OnItemClickListener;

public class SecretCodesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private ListView listView;
    private SimpleCursorAdapter listAdapter;
    private ArrayList<SecretCode> secretCodes;

    private OnItemClickListener onItemClickListener = new OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            fireSecretCodeIntent(secretCodes.get(position).getSecretCode());
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String[] uiBindFrom = new String[]{Tables.SecretCodes.COL_ACTIVITY_NAME, Tables.SecretCodes.COL_SECRET_CODE};
        int[] uiBindTo = new int[]{android.R.id.text1, android.R.id.text2};
        listAdapter = new SimpleCursorAdapter(
                getActivity(), android.R.layout.simple_list_item_2, null,
                uiBindFrom, uiBindTo, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.secret_codes_fragment_layout, container, false);
        listView = (ListView) v.findViewById(R.id.secretCodesList);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(onItemClickListener);
        initLoader();
        return v;
    }

    public void initLoader() {
        LoaderManager lm = getLoaderManager();
        lm.destroyLoader(SecretCodesLoader.LOADER_ID);
        lm.initLoader(SecretCodesLoader.LOADER_ID, null, this);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        SecretCodesReader reader = new SecretCodesReader(new DatabaseReader(getActivity().getContentResolver()));
        return reader.getCursorLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        listAdapter.swapCursor(data);
        secretCodes = populateListWith(data);
    }

    private void fireSecretCodeIntent(String code) {
        Intent secretCodeIntent = new Intent("android.provider.Telephony.SECRET_CODE", Uri.parse("android_secret_code://" + code));
        getActivity().sendBroadcast(secretCodeIntent);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        listAdapter.swapCursor(null);
        secretCodes.clear();
    }


    private ArrayList<SecretCode> populateListWith(Cursor cursor) {
        ArrayList<SecretCode> data = new ArrayList<SecretCode>();
        if (cursor.moveToFirst()) {
            do {
                SecretCode SecretCode = getPost(cursor);
                data.add(SecretCode);
            } while (cursor.moveToNext());
        } else {
            Log.e("PostReader", "No data in the cursor.");
        }
        return data;
    }

    private SecretCode getPost(Cursor cursor) {
        String activityName = cursor.getString(cursor.getColumnIndexOrThrow(Tables.SecretCodes.COL_ACTIVITY_NAME));
        String secretCode = cursor.getString(cursor.getColumnIndexOrThrow(Tables.SecretCodes.COL_SECRET_CODE));
        return new SecretCode(activityName, secretCode);
    }
}
