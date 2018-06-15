package com.qinlei.tokephoto.delegate;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class DelegateFragmentFinder {
    private static final String DELEGATE_FRAGMENT_TAG = DelegateFragmentFinder.class.getSimpleName() + "Tag";

    private static class Singleton {
        private static final DelegateFragmentFinder instance = new DelegateFragmentFinder();
    }

    public static DelegateFragmentFinder getInstance() {
        return Singleton.instance;
    }

    public DelegateFragment find(FragmentActivity activity) {
        DelegateFragment fragment = null;
        if (activity != null && !activity.isFinishing()) {
            FragmentManager fm = activity.getSupportFragmentManager();
            fragment = (DelegateFragment) fm.findFragmentByTag(DELEGATE_FRAGMENT_TAG);
            if (fragment == null) {
                fragment = DelegateFragment.newInstance();
                fm.beginTransaction()
                        .add(fragment, DELEGATE_FRAGMENT_TAG)
                        .commitAllowingStateLoss();
            }
        }
        return fragment;
    }
}
