package timetracker.com.timetracker;

/**
 * Created by User on 29/12/2016.
 */
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import timetracker.com.timetracker.fragments.AbstractFragment;
import timetracker.com.timetracker.fragments.ActiveFragment;
import timetracker.com.timetracker.fragments.AllFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    String project;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.project = "PRaiz";
    }
    public PagerAdapter(FragmentManager fm, int NumOfTabs, String project) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.project = project;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("projectName", project );
        System.out.println("Entra en el PageAdapter de "+ project);
        switch (position) {
            case 0:
                AbstractFragment tab1 = new AbstractFragment();
                tab1.setArguments(bundle);
                return tab1;
            case 1:
                AllFragment tab2 = new AllFragment();
                tab2.setArguments(bundle);
                return tab2;
            case 2:
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