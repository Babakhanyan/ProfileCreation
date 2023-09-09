package com.example.profilecreation.module

import android.app.Activity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.profilecreation.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object NavigationModule {

    @Provides
    @ActivityScoped
    fun provideNavigation(activity: Activity): NavController {
        return Navigation.findNavController(activity, R.id.nav_host_fragment)
    }
}
