package com.rahat_jnu.expensetracker.interfaces;

import androidx.annotation.DrawableRes;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import android.view.ActionMode;
import android.view.View;

import com.rahat_jnu.expensetracker.ui.MainActivity;

import java.util.List;

/**
 * Created by pcarrillo on 17/09/2015.
 */
public interface IMainActivityListener {

    void setMode(@MainActivity.NavigationMode int mode);
    void setTabs(List<String> tabList, TabLayout.OnTabSelectedListener onTabSelectedListener);
    void setFAB(@DrawableRes int drawableId, View.OnClickListener onClickListener);
    void setTitle(String title);
    void setPager(ViewPager vp, TabLayout.ViewPagerOnTabSelectedListener viewPagerOnTabSelectedListener);
    void setExpensesSummary(@IDateMode int dateMode);
    ActionMode setActionMode(ActionMode.Callback actionModeCallback);

}
