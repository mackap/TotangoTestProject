package com.android.testtasktotango.core_comp.di

import android.content.Context
import dagger.Component
import javax.inject.Singleton


/**
 *Created by Makarov on 21.05.2019
 */

@Singleton
@Component(modules = [AppModule::class, NetModule::class, LocalStorageModule::class])
interface CoreComponent {
    fun context(): Context
    fun apiService(): ApiService
    fun localStorage(): ILocalStorage
}