package com.example.majordaggertest.ui.main;

import com.example.majordaggertest.ui.detail.DetailsFragment;
import com.example.majordaggertest.ui.list.ListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class MainFragmentBindingModule {

    @ContributesAndroidInjector
    abstract ListFragment provideListFragment();

    @ContributesAndroidInjector
    abstract DetailsFragment provideDetailsFragment();
}
