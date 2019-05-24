package com.android.testtasktotango.core_comp.di
/**
 *Created by Makarov on 21.05.2019
 */
import android.content.Context
import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocalStorageModule {
    private val sharedPrefName:String = "shared_pref_key"

    @Provides
    @Singleton
    fun provideSharedPref(context: Context): SharedPreferences {
        return context.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideLocalData(sharedPreferences: SharedPreferences):ILocalStorage{
        return LocalStorage(sharedPreferences)
    }
}
