package com.rahat_jnu.expensetracker.ui.income;

import java.util.Date;

public class Income {
    private long idIncome;
    private long amountIncome;
    private String dateIncome;
    private String descriptionIncome;

    public Income(long idIncome, long amountIncome, String dateIncome, String descriptionIncome) {
        this.idIncome = idIncome;
        this.amountIncome = amountIncome;
        this.dateIncome= dateIncome;
        this.descriptionIncome = descriptionIncome;
    }

    public Income() {
    }

    public long getIdIncome() {
        return idIncome;
    }

    public void setIdIncome(long idIncome) {
        this.idIncome = idIncome;
    }


    public String getDescriptionIncome() {
        return descriptionIncome;
    }

    public void setDescriptionIncome(String descriptionIncome) {
        this.descriptionIncome = descriptionIncome;
    }

    public String getDateIncome() {
        return dateIncome;
    }

    public void setDateIncome(String dateIncome) {
        this.dateIncome = dateIncome;
    }

    public long getAmountIncome() {
        return amountIncome;
    }

    public void setAmountIncome(long amountIncome) {
        this.amountIncome = amountIncome;
    }
}
