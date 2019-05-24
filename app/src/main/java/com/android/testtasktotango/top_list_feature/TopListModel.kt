package com.android.testtasktotango.top_list_feature
import com.android.testtasktotango.core_comp.data.pojo.Post
import com.android.testtasktotango.core_comp.di.ApiService
import com.android.testtasktotango.core_comp.di.ILocalStorage
import com.android.testtasktotango.top_list_feature.di.TopListMVP
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class TopListModel(apiService: ApiService, localStorage: ILocalStorage) :
    TopListMVP.ITopListModel {
    private val POSTS_LIMIT: Int = 25

    var mApiService = apiService
    var mLocalStorage = localStorage
    var mTopList: MutableList<Post> = mutableListOf()
    var after: String? = null


    override fun loadData(): Single<List<Post>> {
        return mApiService.getTopPost(after, POSTS_LIMIT)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess {
                after = it.data.after
            }
            .flatMapObservable { Observable.fromIterable(it.data.children) }
            .map { it.data }
            .toList()
            .doOnSuccess {
                mTopList.addAll(it)
            }

    }


    override fun getData(): List<Post> {
        return mTopList
    }

}

