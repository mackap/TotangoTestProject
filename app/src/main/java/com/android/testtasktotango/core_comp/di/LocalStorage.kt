package com.android.testtasktotango.core_comp.di
import android.content.SharedPreferences

/**
 *Created by Makarov on 21.05.2019
 */
class LocalStorage(sharedPreferences: SharedPreferences) : ILocalStorage {
    private val mSharedPreferences = sharedPreferences

}