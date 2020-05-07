package com.rahat_jnu.expensetracker.ui.note;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

public class MyEditText extends TextView {
    private boolean mEnabled; // is this edittext enabled

    public MyEditText(Context context) {
        super(context);
    }

    public MyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            if (!mEnabled) return;
            super.setEnabled(false);
            super.setEnabled(mEnabled);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.mEnabled = enabled;
        super.setEnabled(enabled);
    }}
