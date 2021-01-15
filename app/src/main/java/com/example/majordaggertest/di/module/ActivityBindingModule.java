package com.example.majordaggertest.di.module;

import com.example.majordaggertest.ui.main.MainActivity;
import com.example.majordaggertest.ui.main.MainFragmentBindingModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = {MainFragmentBindingModule.class})
    abstract MainActivity bindMainActivity();
}
