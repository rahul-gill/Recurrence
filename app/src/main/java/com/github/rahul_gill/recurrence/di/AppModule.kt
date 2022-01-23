package com.github.rahul_gill.recurrence.di

import android.content.Context
import androidx.room.Room
import com.github.rahul_gill.recurrence.data.ReminderDatabase
import com.github.rahul_gill.recurrence.data.ReminderDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun getEventDatabaseInstance(@ApplicationContext context: Context): ReminderDatabase{
        return Room.databaseBuilder(
            context,
            ReminderDatabase::class.java,
            "REMINDER_DATABASE"
        ).build()
    }

    @Singleton
    @Provides
    fun getEventDatabaseDao(database: ReminderDatabase) : ReminderDatabaseDao{
        return database.reminderDatabaseDao
    }
}