package ru.zmeytee.skillpresentation.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.zmeytee.skillpresentation.data.repositories.UserRepositoryImpl
import ru.zmeytee.skillpresentation.data.repositories.interfaces.UserRepository

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideUserRepository(impl: UserRepositoryImpl): UserRepository
}