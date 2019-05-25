package com.android.testtasktotango.core_comp.di
import android.content.Context
import android.content.SharedPreferences

/**
 *Created by Makarov on 21.05.2019
 */
class LocalStorage(sharedPreferences: SharedPreferences, context:Context) : ILocalStorage {
    private val mSharedPreferences = sharedPreferences
    private val mContext = context
    override fun getContext(): Context {
        return mContext
    }

    override fun getSharedPref(): SharedPreferences {
        return mSharedPreferences
    }
}