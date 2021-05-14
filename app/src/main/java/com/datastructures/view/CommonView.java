package com.datastructures.view;

import android.view.View;

import androidx.appcompat.app.AlertDialog;

public interface CommonView {
    public AlertDialog.Builder getAlertDialog(String title, String message);

    public View getViewForDialog(int resource);

    public void showSnackBarForDialog(String message, int length);

    public void restartCurrentActivity();
}
