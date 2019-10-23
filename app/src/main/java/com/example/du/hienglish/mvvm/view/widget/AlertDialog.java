package com.example.du.hienglish.mvvm.view.widget;

import android.content.Context;
import android.content.DialogInterface;

import com.example.du.hienglish.R;

public class AlertDialog {
    private static android.app.AlertDialog alertDialog;

    public static void show(Context context, String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("提示")
                .setMessage(message)
                .setIcon(R.drawable.img_alert)
                .setCancelable(false)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        alertDialog = builder.create();
        alertDialog.show();
    }

    public static void show(Context context, String title, String message, PositiveOnClickListener positiveOnClickListener) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setIcon(R.drawable.img_alert)
                .setCancelable(false)
                .setPositiveButton("确定", positiveOnClickListener)
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        alertDialog = builder.create();
        alertDialog.show();
    }

    public static void cancel() {
        if (alertDialog == null)
            return;
        if (alertDialog.isShowing())
            alertDialog.cancel();
    }

    public static abstract class PositiveOnClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {
        }
    }
}
