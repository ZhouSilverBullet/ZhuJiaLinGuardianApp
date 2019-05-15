package com.sdxxtop.di.module;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.sdxxtop.di.qualifier.FragmentScope;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentModule {
    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity getActivity() {
        return mFragment.getActivity();
    }
}
