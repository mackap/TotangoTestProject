package com.android.testtasktotango.top_list_feature

import android.annotation.SuppressLint
import android.util.Log
import com.android.testtasktotango.core_comp.ScreenState
import com.android.testtasktotango.core_comp.data.pojo.Post
import com.android.testtasktotango.top_list_feature.di.TopListMVP

class TopListPresenter(topListModel: TopListMVP.ITopListModel) : TopListMVP.ITopListPresenter {

var TAG = javaClass.simpleName
    private var mTopListModel = topListModel
    private var mTopListView: TopListMVP.ITopListView? = null
    var mCurrentScreenState: ScreenState = ScreenState.FIRST_OPEN
    var mCurrentErrorMessage = ""

    override fun setView(view: TopListMVP.ITopListView?) {
        mTopListView = view
    }


    @SuppressLint("CheckResult")
    override fun loadData() {
        mTopListView!!.showProgress(true)
        print("-------------- TopListPresenter, loadData()")
        mTopListModel.loadData().subscribe({
            mCurrentScreenState = ScreenState.SHOW_DATA
            mTopListView!!.showData(mTopListModel.getData())
        },{
            mCurrentErrorMessage = it.message.toString()
            mTopListView!!.showErrorMessage(mCurrentErrorMessage)
            Log.d(TAG, "------------- error, $mCurrentErrorMessage")
        })
    }

    override fun getData(): List<Post> {
        mTopListView!!.showProgress(true)
        return mTopListModel.getData()

    }

    override fun getCurrentErrorMessage(): String {
        return mCurrentErrorMessage
    }

    override fun getCurrentState(): ScreenState {
        return mCurrentScreenState
    }


}