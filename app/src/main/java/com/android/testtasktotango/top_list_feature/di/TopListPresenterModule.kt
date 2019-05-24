package com.android.testtasktotango.top_list_feature.di


import com.android.testtasktotango.top_list_feature.TopListPresenter
import dagger.Module
import dagger.Provides
/**
 *Created by Makarov on 21.05.2019
 */

@Module
class TopListPresenterModule {
    @Provides
    @TopListScope
    fun provideTaskListPresenter(authModel: TopListMVP.ITopListModel): TopListMVP.ITopListPresenter {
        return TopListPresenter(authModel)
    }

}
