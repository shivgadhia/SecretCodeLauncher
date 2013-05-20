package com.shivgadhia.android.SecretCodeLauncher.models;

public class SecretCode {
    private final int id;
    private final String activityName;
    private final String secretCode;

    public SecretCode(int id, String activityName, String secretCode) {
        this.id = id;
        this.activityName = activityName;
        this.secretCode = secretCode;
    }

    @Override
    public String toString() {
        return activityName + " (" + secretCode + ")";
    }

    public int getId() {
        return id;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getSecretCode() {
        return secretCode;
    }
}
