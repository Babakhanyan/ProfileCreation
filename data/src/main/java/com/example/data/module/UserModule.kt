package com.example.data.module

import com.example.data.DefaultUserRepository
import com.example.domain.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
class UserModule {

    @Provides
    @ActivityRetainedScoped
    fun provideNetworkUtils(): UserRepository {
        return DefaultUserRepository()
    }
}