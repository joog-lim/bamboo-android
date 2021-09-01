package com.study.bamboo.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

//    @Singleton
//    @Provides
//    fun providesDatabase(
//        @ApplicationContext context: Context
//    ) = Room.databaseBuilder(
//        context,
//        AdminPostDataBase::class.java,
//        DATABASE_NAME
//
//    ).build()
//
//
//    @Singleton
//    @Provides
//    fun provideDao(database: AdminPostDataBase) = database.adminDao()


}