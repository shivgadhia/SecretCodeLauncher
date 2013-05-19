package com.shivgadhia.android.SecretCodeLauncher.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import com.shivgadhia.android.SecretCodeLauncher.R;
import com.shivgadhia.android.SecretCodeLauncher.persistance.DatabaseWriter;
import com.shivgadhia.android.SecretCodeLauncher.persistance.SecretCodesWriter;

public class AddCodeDialogFragment extends DialogFragment {
    private EditText activtiyName;
    private EditText secretCode;
    private DialogInterface.OnClickListener positiveButtonListener;
    private DialogInterface.OnClickListener negativeButtonListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_add, null);
        activtiyName = (EditText) view.findViewById(R.id.activityName);
        secretCode = (EditText) view.findViewById(R.id.secretCode);

        positiveButtonListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                SecretCodesWriter writer = new SecretCodesWriter(new DatabaseWriter(getActivity().getContentResolver()));
                writer.saveSecretCode(activtiyName.getText().toString(), secretCode.getText().toString());
            }
        };
        negativeButtonListener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AddCodeDialogFragment.this.getDialog().cancel();
            }
        };
        builder.setView(view)
                .setPositiveButton(R.string.add, positiveButtonListener)
                .setNegativeButton(R.string.cancel, negativeButtonListener);

        return builder.create();
    }

}
