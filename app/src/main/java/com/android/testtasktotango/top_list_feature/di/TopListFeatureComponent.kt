package com.android.testtasktotango.top_list_feature.di

import com.android.testtasktotango.core_comp.di.CoreComponent
import com.android.testtasktotango.top_list_feature.TopListActivity
import dagger.Component

/**
 *Created by Makarov on 21.05.2019
 */
@Component(dependencies = [CoreComponent::class],
           modules = [TopListModelModule::class,
               TopListPresenterModule::class]
)
@TopListScope
interface TopListFeatureComponent {
    fun injectIntoActivity(activity: TopListActivity)
}