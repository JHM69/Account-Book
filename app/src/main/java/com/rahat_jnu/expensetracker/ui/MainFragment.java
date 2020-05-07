package com.rahat_jnu.expensetracker.ui;

import android.content.Context;

import com.rahat_jnu.expensetracker.interfaces.IMainActivityListener;

/**
 * Created by pcarrillo on 17/09/2015.
 */
public class MainFragment extends BaseFragment {

    protected IMainActivityListener mMainActivityListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mMainActivityListener = (IMainActivityListener)context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mMainActivityListener = null;
    }

}
