package com.rahat_jnu.expensetracker.ui.reminders;

import android.os.Bundle;

import com.rahat_jnu.expensetracker.R;
import com.rahat_jnu.expensetracker.interfaces.IUserActionsMode;
import com.rahat_jnu.expensetracker.ui.BaseActivity;

/**
 * Created by Pedro on 9/26/2015.
 */
public class NewReminderActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        @IUserActionsMode int mode = getIntent().getIntExtra(IUserActionsMode.MODE_TAG, IUserActionsMode.MODE_CREATE);
        String reminderId = getIntent().getStringExtra(NewReminderFragment.REMINDER_ID_KEY);
        replaceFragment(NewReminderFragment.newInstance(mode, reminderId), false);
    }

}
