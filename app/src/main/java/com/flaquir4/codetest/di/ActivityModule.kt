package com.flaquir4.codetest.di

import android.app.Activity
import com.flaquir4.codetest.presentation.login.LoginActivity
import com.flaquir4.codetest.presentation.login.LoginView
import com.flaquir4.codetest.presentation.main.MainActivity
import com.flaquir4.codetest.presentation.main.MainView
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class ActivityModule {

    @Provides
    @ActivityScoped
    fun provideLoginActivityView(loginActivity: Activity): LoginView =
        loginActivity as LoginActivity

    @Provides
    @ActivityScoped
    fun provideMainActivityView(mainActivity: Activity): MainView =
        mainActivity as MainActivity
}
