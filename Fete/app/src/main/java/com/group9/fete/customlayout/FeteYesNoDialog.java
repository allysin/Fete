package com.group9.fete.customlayout;

import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.view.View;

import com.group9.fete.R;

/**
 * Created by Anubhav on 30-10-2014.
 */
public class FeteYesNoDialog extends DialogPreference {
    public FeteYesNoDialog(Context context, AttributeSet attr){
        super(context, attr);
        setDialogLayoutResource(R.layout.fete_yesno_dialog_layout);
    }

    @Override
    public void onBindDialogView(View view){
        super.onBindDialogView(view);
    }
}
