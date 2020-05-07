package com.rahat_jnu.expensetracker.ui.income;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.rahat_jnu.expensetracker.ExpenseTrackerApp;
import com.rahat_jnu.expensetracker.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class IncomeAdapter extends RecyclerView.Adapter<IncomeAdapter.ViewHolder> {
        private LayoutInflater inflater;
        private List<Income> incomes;
        IncomeAdapter(Context context){
            this.inflater = LayoutInflater.from(context);
            notifyDataSetChanged();
        }

        IncomeAdapter(Context context, List<Income> incomes){
            this.inflater = LayoutInflater.from(context);
            this.incomes = incomes;
        }


    @NonNull
        @Override
        public com.rahat_jnu.expensetracker.ui.income.IncomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = inflater.inflate(R.layout.income_item_view,viewGroup,false);
            return new com.rahat_jnu.expensetracker.ui.income.IncomeAdapter.ViewHolder(view);
        }

    @Override
        public void onBindViewHolder(@NonNull com.rahat_jnu.expensetracker.ui.income.IncomeAdapter.ViewHolder viewHolder, int i) {

            int  amount    = (int) incomes.get(i).getAmountIncome();
            String  title     = incomes.get(i).getDescriptionIncome();
            String  date     = incomes.get(i).getDateIncome();

            viewHolder.nTitle.setText(title);
            viewHolder.nDate.setText(date);
            viewHolder.nAmount.setText(getFormatted(amount));

        }

        @Override
        public int getItemCount() {
            return incomes.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            TextView nTitle,nDate,nAmount,nID;

            public ViewHolder(@NonNull final View itemView) {
                super(itemView);
                nTitle  = itemView.findViewById(R.id.tv_description);
                nDate   = itemView.findViewById(R.id.tv_date);
                nAmount   = itemView.findViewById(R.id.tv_amount);
                //nID     = itemView.findViewById(R.id.listId);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(final View view) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(view.getContext());
                        builder1.setTitle("Delete Income?");
                        builder1.setMessage("Are you sure want to Delete this income?");
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Database db = new Database(view.getContext());
                                        db.deleteIncome(incomes.get(getAdapterPosition()).getIdIncome());
                                        dialog.cancel();
                                        notifyDataSetChanged();
                                    }
                                });

                        builder1.setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        Button buttonbackground = alert11.getButton(DialogInterface.BUTTON_NEGATIVE);
                        buttonbackground.setTextColor(Color.BLACK);

                        Button buttonbackground1 = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
                        buttonbackground1.setTextColor(Color.BLACK);
                        return false;
                    }
                });
            }
        }

    public String getFormatted(int number) {
        String countryCode = PreferenceManager.getDefaultSharedPreferences(ExpenseTrackerApp.getContext()).getString(ExpenseTrackerApp.getContext().getString(R.string.pref_country_key), ExpenseTrackerApp.getContext().getString(R.string.default_country));
        Locale locale = new Locale("BN", countryCode);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        String formattedNumber = numberFormat.format(number);
        String symbol = numberFormat.getCurrency().getSymbol(locale);
        return formattedNumber.replace(symbol, "৳");
    }


}
