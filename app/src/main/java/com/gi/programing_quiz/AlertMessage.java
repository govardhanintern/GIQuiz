package com.gi.programing_quiz;

import android.app.ProgressDialog;
import android.content.Context;

public class AlertMessage {
    public static ProgressDialog showProgressDialog(Context context) {
        ProgressDialog m_Dialog = new ProgressDialog(context);
        m_Dialog.setMessage("Loading...");
        m_Dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        m_Dialog.setCancelable(false);
        m_Dialog.show();
        return m_Dialog;
    }
}
