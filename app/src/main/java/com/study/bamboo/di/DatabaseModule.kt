package com.study.bamboo.di

import android.content.Context
import androidx.room.Room
import com.study.bamboo.data.database.AdminPostDataBase
import com.study.bamboo.utils.Util.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AdminPostDataBase::class.java,
        DATABASE_NAME

    ).build()


    @Singleton
    @Provides
    fun provideDao(database: AdminPostDataBase) = database.adminDao()


}