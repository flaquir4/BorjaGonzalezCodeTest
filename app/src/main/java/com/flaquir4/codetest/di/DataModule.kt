package com.flaquir4.codetest.di

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
    abstract fun providesAuthenticationRepository(
        authenticationDataRepository: AuthenticationDataRepository
    ): AuthenticationRepository

    @Binds
    @Singleton
    abstract fun providesAuthenticationApiDataSource(
        authenticateFakeApiDataSource: AuthenticationFakeApiDataSource
    ): AuthenticationApiDataSource
}
