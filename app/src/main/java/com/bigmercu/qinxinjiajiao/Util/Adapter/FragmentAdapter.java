package com.bigmercu.qinxinjiajiao.Util.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.bigmercu.qinxinjiajiao.UI.fragment.FindFragment;
import com.bigmercu.qinxinjiajiao.UI.fragment.HomeFragment;
import com.bigmercu.qinxinjiajiao.UI.fragment.LocationFragment;
import com.bigmercu.qinxinjiajiao.UI.fragment.MeFragment;
import com.bigmercu.qinxinjiajiao.UI.fragment.MessageFragment;

/**
 * Created by bigmercu on 16/4/15.
 */
public class FragmentAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT = 5;
    private FindFragment findFragment = null;
    private HomeFragment homeFragment = null;
    private LocationFragment locationFragment = null;
    private MessageFragment messageFragment = null;
    private MeFragment meFragment = null;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        findFragment = new FindFragment();
        homeFragment = new HomeFragment();
        locationFragment = new LocationFragment();
        messageFragment = new MessageFragment();
        meFragment = new MeFragment();
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = homeFragment;
                break;
            case 1:
                fragment = findFragment;
                break;
            case 2:
                fragment = locationFragment;
                break;
            case 3:
                fragment = messageFragment;
                break;
            case 4:
                fragment = meFragment;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }
}
