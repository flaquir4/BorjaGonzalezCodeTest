package com.flaquir4.codetest.di

import com.flaquir4.codetest.data.AuthenticationDiskDataSource
import com.flaquir4.codetest.data.AuthenticationPreferencesDiskDataSource
import com.flaquir4.codetest.data.AuthenticationApiDataSource
import com.flaquir4.codetest.data.AuthenticationDataRepository
import com.flaquir4.codetest.data.AuthenticationFakeApiDataSource
import com.flaquir4.codetest.data.AuthenticationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindsAuthenticationRepository(
        authenticationDataRepository: AuthenticationDataRepository
    ): AuthenticationRepository

    @Binds
    @Singleton
    abstract fun bindsAuthenticationApiDataSource(
        authenticateFakeApiDataSource: AuthenticationFakeApiDataSource
    ): AuthenticationApiDataSource

    @Binds
    @Singleton
    abstract fun bindsAuthenticationDiskDataSource(
        authenticationPreferencesDiskDataSource: AuthenticationPreferencesDiskDataSource
    ): AuthenticationDiskDataSource
}
