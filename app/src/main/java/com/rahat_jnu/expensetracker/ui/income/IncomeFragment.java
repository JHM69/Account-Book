package com.rahat_jnu.expensetracker.ui.income;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.preference.PreferenceManager;
import androidx.annotation.DrawableRes;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rahat_jnu.expensetracker.ExpenseTrackerApp;
import com.rahat_jnu.expensetracker.R;

import com.rahat_jnu.expensetracker.ui.MainActivity;
import com.rahat_jnu.expensetracker.ui.MainFragment;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class IncomeFragment extends MainFragment {

    private View rootView;
    private TextView total;
    private FloatingActionButton mFloatingActionButton;
    RecyclerView recyclerView;
    IncomeAdapter adapter;
    Database db;
    private List<Income> allIncomes;

    public static com.rahat_jnu.expensetracker.ui.income.IncomeFragment newInstance() {
        return new com.rahat_jnu.expensetracker.ui.income.IncomeFragment();
    }

    @Override
    public void setTitle(String title) {
        super.setTitle("Income");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //adapter.notifyDataSetChanged();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.tool_bar_test2, container, false);
        return rootView;


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMainActivityListener.setMode(MainActivity.NAVIGATION_MODE_STANDARD);
        total = rootView.findViewById(R.id.tv_total_incomem);
        mFloatingActionButton = new FloatingActionButton(getContext());
        mFloatingActionButton = rootView.findViewById(R.id.fabnew);
        mFloatingActionButton.show();
        db = new Database(rootView.getContext());
        allIncomes = db.getAllIncomes();
        recyclerView = rootView.findViewById(R.id.allIncomesList);
        db = new Database(this.getContext());
        total.setText(getFormatted(db.getTotalIncome()));
        ImageView refreshD = rootView.findViewById(R.id.refresh);
        if (allIncomes.isEmpty()) {
            Toast.makeText(getActivity(), "Tap on + icon to add new Income", Toast.LENGTH_SHORT).show();
        } else {
            displayList(allIncomes);
            adapter.notifyDataSetChanged();

        }
        refreshD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allIncomes = db.getAllIncomes();
                total.setText(getFormatted(db.getTotalIncome()));
                displayList(allIncomes);
                adapter.notifyDataSetChanged();

            }
        });
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewIncome newIncome = NewIncome.newInstance();
                newIncome.show(getFragmentManager(), "");

            }
        });
    }


    private void displayList(List<Income> allIncomes) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new IncomeAdapter(getActivity(), allIncomes);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public String getFormatted(float number) {
        String countryCode = PreferenceManager.getDefaultSharedPreferences(ExpenseTrackerApp.getContext()).getString(ExpenseTrackerApp.getContext().getString(R.string.pref_country_key), ExpenseTrackerApp.getContext().getString(R.string.default_country));
        Locale locale = new Locale("BN", countryCode);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        String formattedNumber = numberFormat.format(number);
        String symbol = numberFormat.getCurrency().getSymbol(locale);
        return formattedNumber.replace(symbol, "৳");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        allIncomes = db.getAllIncomes();
        total.setText(getFormatted(db.getTotalIncome()));
        displayList(allIncomes);
        adapter.notifyDataSetChanged();
    }

}