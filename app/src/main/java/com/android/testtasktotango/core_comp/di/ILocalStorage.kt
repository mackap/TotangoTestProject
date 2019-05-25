package com.android.testtasktotango.core_comp.di

import android.content.Context
import android.content.SharedPreferences


/**
 *Created by Makarov on 21.05.2019
 */
interface ILocalStorage {
    fun getContext(): Context
    fun getSharedPref(): SharedPreferences
}