package timetracker.com.timetracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.ImageView;

import timetracker.com.timetracker.fragments.AbstractFragment;
import timetracker.com.timetracker.fragments.ActiveFragment;
import timetracker.com.timetracker.fragments.AllFragment;

/**
 * Created by User on 05/01/2017.
 */

public class PageAdapterHome  extends FragmentStatePagerAdapter {

    int mNumOfTabs;

    public PageAdapterHome(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }


    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("projectName", "PRaiz" );
        switch (position) {
            case 0:
                AllFragment tab2 = new AllFragment();
                tab2.setArguments(bundle);
                return tab2;
            case 1:
                ActiveFragment tab3 = new ActiveFragment();
                tab3.setArguments(bundle);
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}