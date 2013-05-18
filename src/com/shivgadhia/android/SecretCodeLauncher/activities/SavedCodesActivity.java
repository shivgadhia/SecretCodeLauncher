package com.shivgadhia.android.SecretCodeLauncher.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.shivgadhia.android.SecretCodeLauncher.R;

public class SavedCodesActivity extends Activity
{
    private View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            fireSecretCodeIntent();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(buttonOnClickListener);
    }

    private void fireSecretCodeIntent() {
        Intent secretCodeIntent = new Intent("android.provider.Telephony.SECRET_CODE", Uri.parse("android_secret_code://4636"));
        sendBroadcast(secretCodeIntent);
    }
}
