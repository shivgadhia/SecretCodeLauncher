package com.shivgadhia.android.SecretCodeLauncher.activities;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.shivgadhia.android.SecretCodeLauncher.R;
import com.shivgadhia.android.SecretCodeLauncher.fragments.AddCodeDialogFragment;
import com.shivgadhia.android.SecretCodeLauncher.persistance.DatabaseWriter;
import com.shivgadhia.android.SecretCodeLauncher.persistance.SecretCodesWriter;

public class SavedCodesActivity extends Activity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        SecretCodesWriter writer = new SecretCodesWriter(new DatabaseWriter(getContentResolver()));
        writer.saveSecretCode("test1", "4636");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:

                DialogFragment newFragment = new AddCodeDialogFragment();
                newFragment.show(getFragmentManager(), "addCode");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
