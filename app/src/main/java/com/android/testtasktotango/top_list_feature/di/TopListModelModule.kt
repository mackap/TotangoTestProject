package com.android.testtasktotango.top_list_feature.di
import com.android.testtasktotango.core_comp.di.ApiService
import com.android.testtasktotango.core_comp.di.ILocalStorage
import com.android.testtasktotango.top_list_feature.TopListModel
import dagger.Module
import dagger.Provides

/**
 *Created by Makarov on 21.05.2019
 */

@Module
class TopListModelModule {

    @Provides
    @TopListScope
    fun provideAuthModel(
        apiService: ApiService,
        localStorage: ILocalStorage
    ): TopListMVP.ITopListModel {
        return TopListModel(apiService, localStorage)
    }
}
