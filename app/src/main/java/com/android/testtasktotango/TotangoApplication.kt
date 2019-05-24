package com.android.testtasktotango

import android.app.Application

import com.android.testtasktotango.core_comp.di.AppModule
import com.android.testtasktotango.core_comp.di.CoreComponent
import com.android.testtasktotango.core_comp.di.DaggerCoreComponent
import com.android.testtasktotango.top_list_feature.di.DaggerTopListFeatureComponent
import com.android.testtasktotango.top_list_feature.di.TopListFeatureComponent

/**
 *Created by Makarov on 21.05.2019
 */

class  TotangoApplication : Application(){
    lateinit var mCoreComponent: CoreComponent
    var mTopListComponent: TopListFeatureComponent? = null
    override fun onCreate() {
        super.onCreate()
        mCoreComponent = DaggerCoreComponent.builder().appModule(AppModule(this)).build()
    }

    fun getTopListComponent(): TopListFeatureComponent? {
        if (mTopListComponent == null) {
            mTopListComponent = DaggerTopListFeatureComponent
                .builder()
                .coreComponent(mCoreComponent).build()
        }
        return mTopListComponent
    }

}