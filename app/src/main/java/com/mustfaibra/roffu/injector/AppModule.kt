package com.mustfaibra.roffu.injector

import android.content.Context
import androidx.room.Room
import com.mustfaibra.roffu.data.local.RoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    /** A function to provide a single Context's instance of the application throughout our app */
    @Provides
    @Singleton
    fun provideContextInstance(@ApplicationContext cxt: Context) = cxt

    /** Provide Coroutine scope */
    @Singleton
    @Provides
    fun provideCoroutineScope() = CoroutineScope(SupervisorJob())

    /** A function to provide a single instance of the local database throughout our app */
    @Provides
    @Singleton
    fun provideRoomInstance(
        @ApplicationContext context: Context,
        populateDataCallback: RoomDb.PopulateDataClass,
    ) = Room
        .databaseBuilder(
            context.applicationContext,
            RoomDb::class.java,
            "RoFFuDatabase",
        )
        .fallbackToDestructiveMigration()
        .addCallback(populateDataCallback)
        .build()

    /** A function to provide a single instance of the local database Dao throughout our app */
    @Provides
    @Singleton
    fun provideDaoInstance(db: RoomDb) = db.getDao()

}