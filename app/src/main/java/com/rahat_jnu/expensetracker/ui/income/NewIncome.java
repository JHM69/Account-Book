package com.rahat_jnu.expensetracker.ui.income;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.rahat_jnu.expensetracker.R;
import com.rahat_jnu.expensetracker.adapters.CategoriesSpinnerAdapter;
import com.rahat_jnu.expensetracker.interfaces.IUserActionsMode;
import com.rahat_jnu.expensetracker.utils.DateUtils;
import com.rahat_jnu.expensetracker.utils.DialogManager;
import com.rahat_jnu.expensetracker.utils.Util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by pcarrillo on 21/09/2015.
 */
public class NewIncome extends DialogFragment implements View.OnClickListener{

    private TextView tvTitle;
    private Button btnDate;
    private EditText etDescription;
    private EditText etTotal;
    private TextView total;
    private CategoriesSpinnerAdapter mCategoriesSpinnerAdapter;
    private Date selectedDate;
    private Income mIncome;
    IncomeAdapter ad ;
    private @IUserActionsMode int mUserActionMode;

    static NewIncome newInstance() {
        NewIncome  newExpenseFragment = new NewIncome();
        return newExpenseFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.dialog_add_income, container, false);
        tvTitle = (TextView)rootView.findViewById(R.id.tv_title);
        btnDate = (Button)rootView.findViewById(R.id.btn_date);
        etDescription = (EditText)rootView.findViewById(R.id.et_description);
        etTotal = (EditText)rootView.findViewById(R.id.et_total);
        return rootView;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            mUserActionMode = getArguments().getInt(IUserActionsMode.MODE_TAG) == IUserActionsMode.MODE_CREATE ? IUserActionsMode.MODE_CREATE : IUserActionsMode.MODE_UPDATE;
        }
        selectedDate = new Date();
        updateDate();
        btnDate.setOnClickListener(this);
        (getView().findViewById(R.id.btn_cancel)).setOnClickListener(this);
        (getView().findViewById(R.id.btn_save)).setOnClickListener(this);
    }


    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btn_date) {
            showDateDialog();
        } else if (view.getId() == R.id.btn_cancel) {
            dismiss();
        } else if (view.getId() == R.id.btn_save) {
            onSaveExpense();
        }
    }

    private void onSaveExpense() {
            if (!Util.isEmptyField(etTotal)) {
                String total = etTotal.getText().toString();
                String description = etDescription.getText().toString();
                  {
                    Income editIncome = new Income();
                    editIncome.setAmountIncome(Long.parseLong(total));
                    editIncome.setDescriptionIncome(description);
                    editIncome.setDateIncome(Util.formatDateToString(selectedDate, Util.getCurrentDateFormat()));
                    Database db = new Database(this.getContext());
                    db.addIncome(editIncome);

                  }
                // update widget if the expense is created today
                if (DateUtils.isToday(selectedDate)) {
                }
                dismiss();
            } else {
                DialogManager.getInstance().showShortToast(getString(R.string.error_total));
            }

    }

    private void showDateDialog() {
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(selectedDate);
        DialogManager.getInstance().showDatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(year, month, day);
                selectedDate = calendar.getTime();
                updateDate();
            }
        }, calendar);
    }

    private void updateDate() {
        btnDate.setText(Util.formatDateToString(selectedDate, Util.getCurrentDateFormat()));
    }

}
