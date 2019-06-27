package timetracker.com.timetracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import timetracker.com.timetracker.fragments.ActiveFragment;
import timetracker.com.timetracker.fragments.AllFragment;
import timetracker.com.timetracker.fragments.MenuFragment;

/**
 * Created by User on 05/01/2017.
 */

public class PageAdapterMenu extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PageAdapterMenu(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }


    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                MenuFragment tab2 = new MenuFragment();
                return tab2;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}