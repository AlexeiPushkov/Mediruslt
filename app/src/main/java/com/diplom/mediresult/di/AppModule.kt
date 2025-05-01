package com.diplom.mediresult.di

import android.app.Application
import android.content.Context
import com.diplom.mediresult.data.manager.LocalUserManagerImp
import com.diplom.mediresult.domain.manager.LocalUserManager
import com.diplom.mediresult.domain.usecases.AppEntryUseCases
import com.diplom.mediresult.domain.usecases.ReadAppEntry
import com.diplom.mediresult.domain.usecases.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideLocalUserManger(
        application: Application
    ): LocalUserManager = LocalUserManagerImp(application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    ) = AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )
}