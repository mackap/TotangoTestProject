package com.android.testtasktotango.top_list_feature.di
import com.android.testtasktotango.core_comp.ScreenState
import com.android.testtasktotango.core_comp.data.pojo.Post
import io.reactivex.Single

/**
 *Created by Makarov on 21.05.2019
 */
interface TopListMVP {
    interface ITopListModel{
        fun loadData(): Single<List<Post>>
        fun getData():List<Post>
    }
    interface ITopListView{

        fun showProgress(isShow: Boolean)
        fun showErrorMessage(message: String)
        fun showData(postList: List<Post>)
    }
    interface ITopListPresenter{
        fun setView(view: ITopListView?)
        fun getCurrentErrorMessage(): String
        fun getData(): List<Post>
        fun getCurrentState(): ScreenState
        fun loadData()

    }
}