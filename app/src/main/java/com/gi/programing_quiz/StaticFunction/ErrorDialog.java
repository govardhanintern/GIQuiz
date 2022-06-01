package com.gi.programing_quiz.StaticFunction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ErrorDialog {
    public static AlertDialog.Builder showBuilder(Context context) {
        AlertDialog.Builder ownBuilder = new AlertDialog.Builder(context);
        ownBuilder.setMessage("Something wants to wrong with server please try again letter");
        ownBuilder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return ownBuilder;
    }
}
