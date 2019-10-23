package com.example.du.hienglish.mvvm.view.widget;

import android.content.Context;
import android.content.DialogInterface;

public class ProgressDialog {
    private static android.app.ProgressDialog progressDialog;

    public static void show(Context context, boolean cancelable, String str) {
        try {
            progressDialog = new android.app.ProgressDialog(context);
            progressDialog.setCancelable(cancelable);
            progressDialog.setMessage(str);
            progressDialog.show();
            progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    progressDialog.cancel();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cancel() {
        if (progressDialog == null)
            return;
        if (progressDialog.isShowing())
            progressDialog.cancel();
    }
}
