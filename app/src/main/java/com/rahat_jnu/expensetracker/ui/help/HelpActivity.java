package com.rahat_jnu.expensetracker.ui.help;

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import com.rahat_jnu.expensetracker.R;
import com.rahat_jnu.expensetracker.ui.BaseActivity;

public class HelpActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setToolbar(toolbar);
    }

}
