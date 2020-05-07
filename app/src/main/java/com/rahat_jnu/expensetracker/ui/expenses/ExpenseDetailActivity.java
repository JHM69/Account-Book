package com.rahat_jnu.expensetracker.ui.expenses;

import android.os.Bundle;

import com.rahat_jnu.expensetracker.R;
import com.rahat_jnu.expensetracker.ui.BaseActivity;

public class   ExpenseDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        String expenseId = getIntent().getStringExtra(ExpenseDetailFragment.EXPENSE_ID_KEY);
        replaceFragment(ExpenseDetailFragment.newInstance(expenseId), false);
    }

}
