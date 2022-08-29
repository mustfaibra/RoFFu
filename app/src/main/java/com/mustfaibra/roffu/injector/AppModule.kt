package com.mustfaibra.roffu.injector

import android.content.Context
import androidx.room.Room
import com.mustfaibra.roffu.data.local.RoomDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

// 3/20/2022

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
            "ShoesStoreDatabase",
        )
        .fallbackToDestructiveMigration()
        .addCallback(populateDataCallback)
        .build()

    /** A function to provide a single instance of the local database DAO throughout our app */
    @Provides
    @Singleton
    fun provideDaoInstance(db: RoomDb) = db.getDao()


    /** provide our api client instance */
    @Singleton
    @Provides
    fun provideClient() = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        engine {
            connectTimeout = 5000
            socketTimeout = 5000
        }
    }
}