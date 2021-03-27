package ru.zmeytee.skillpresentation.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(ViewModelComponent::class)
class CoroutineModule {

    @Provides
    @ViewModelScoped
    fun provideDefaultDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }
}