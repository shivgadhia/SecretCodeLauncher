package com.shivgadhia.android.SecretCodeLauncher.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.*;
import android.widget.*;
import com.shivgadhia.android.SecretCodeLauncher.R;
import com.shivgadhia.android.SecretCodeLauncher.loaders.SecretCodesLoader;
import com.shivgadhia.android.SecretCodeLauncher.models.SecretCode;
import com.shivgadhia.android.SecretCodeLauncher.persistance.*;

import java.util.ArrayList;

import static android.widget.AdapterView.OnItemClickListener;

public class SecretCodesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private ListView listView;
    private SimpleCursorAdapter simpleAdapter;
    private ArrayList<SecretCode> secretCodes;
    private AbsListView.MultiChoiceModeListener multiChoiceModeListener = new AbsListView.MultiChoiceModeListener() {
        @Override
        public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        }

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.cab_delete:
                    deleteSelectedItems();
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
        }
    };


    private void deleteSelectedItems() {
        SparseBooleanArray checkedItemPositions = listView.getCheckedItemPositions();
        boolean checked;
        for(int i=0; i < secretCodes.size(); i++){
            checked = checkedItemPositions.get(i);
            if(checked){
                new SecretCodesWriter(new DatabaseWriter(getActivity().getContentResolver())).setToDelete(secretCodes.get(i));
            }
        }

    }

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
        simpleAdapter = new SimpleCursorAdapter(
                getActivity(),R.layout.simple_checkable_list_item_2, null,
                uiBindFrom, uiBindTo, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.secret_codes_fragment_layout, container, false);
        listView = (ListView) v.findViewById(R.id.secretCodesList);
        listView.setAdapter(simpleAdapter);
        listView.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(multiChoiceModeListener);
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
        simpleAdapter.swapCursor(data);
        secretCodes = populateListWith(data);
    }

    private void fireSecretCodeIntent(String code) {
        Intent secretCodeIntent = new Intent("android.provider.Telephony.SECRET_CODE", Uri.parse("android_secret_code://" + code));
        getActivity().sendBroadcast(secretCodeIntent);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        simpleAdapter.swapCursor(null);
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

    @Override
    public void onDestroy() {
        new SecretCodesWriter(new DatabaseWriter(getActivity().getContentResolver())).prune();
        super.onDestroy();
    }
}
