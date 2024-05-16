package com.mlab.nav.di

import com.mlab.nav.repo.UserRepo
import com.mlab.nav.repo.remote.RemoteRepoImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {
    @Binds
    @Singleton
    fun bindRemoteRepo(impl: RemoteRepoImpl): UserRepo
}