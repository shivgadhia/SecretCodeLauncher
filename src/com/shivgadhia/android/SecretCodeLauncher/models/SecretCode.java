package com.shivgadhia.android.SecretCodeLauncher.models;

public class SecretCode {
    public String getActivityName() {
        return activityName;
    }

    public String getSecretCode() {
        return secretCode;
    }

    private final String activityName;
    private final String secretCode;

    public SecretCode(String activityName, String secretCode) {
        this.activityName = activityName;
        this.secretCode = secretCode;
    }

    @Override
    public String toString() {
        return activityName + " ("+secretCode+")";
    }
}
