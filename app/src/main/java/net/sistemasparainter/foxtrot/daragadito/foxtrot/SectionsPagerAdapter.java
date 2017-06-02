package net.sistemasparainter.foxtrot.daragadito.foxtrot;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafael.fccibim on 01/06/2017.
 */

public class SectionsPagerAdapter  extends FragmentStatePagerAdapter {
    List<Fragment> fragmentList = new ArrayList<Fragment>();

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
        //Add tabs
        //fragmentList.add(new Tab1());

    }

    @Override
    public Fragment getItem(int position) {
        return (fragmentList.get(position));
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Tab " + position;
    }
}
