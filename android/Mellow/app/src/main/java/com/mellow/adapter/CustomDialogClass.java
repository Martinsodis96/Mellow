package com.mellow.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.mellow.mellow.R;

public class CustomDialogClass extends Dialog {

    public Activity callingActivity;
    Button discardButton;
    Button keepButton;
    Dialog customDialog;

    public CustomDialogClass(Activity callingActivity) {
        super(callingActivity);
        this.callingActivity = callingActivity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.discard_post_alert_dialog);
        this.setCanceledOnTouchOutside(false);
        this.discardButton = (Button) findViewById(R.id.discard_button);
        this.keepButton = (Button) findViewById(R.id.keep_button);
        this.customDialog = this;

        keepButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
        discardButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                NavUtils.navigateUpFromSameTask(callingActivity);
            }
        });
    }
}