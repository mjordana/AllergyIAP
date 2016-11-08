package com.allergyiap.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Globalia-5 on 04/07/2016.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList;
    private final List<String> mFragmentTitleList;

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
        mFragmentList = new ArrayList<>();
        mFragmentTitleList = new ArrayList<>();

        List<Fragment> fragments = manager.getFragments();
        if(fragments != null){
            fragments.clear();
            /*FragmentTransaction ft = manager.beginTransaction();
            for(Fragment f : fragments) {
                if(f instanceof NoticeListFragment || f instanceof ChatListFragment || f instanceof ChatIndividualListFragment)
                    ft.remove(f);
            }
            ft.commitAllowingStateLoss();*/
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFrag(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    public void clear(){
        mFragmentList.clear();
        mFragmentTitleList.clear();
    }

}