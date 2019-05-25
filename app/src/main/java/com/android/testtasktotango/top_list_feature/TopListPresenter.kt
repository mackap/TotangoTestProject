package com.android.testtasktotango.top_list_feature

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.Point

import com.android.testtasktotango.core_comp.ScreenState
import com.android.testtasktotango.core_comp.data.pojo.Post
import com.android.testtasktotango.top_list_feature.di.TopListMVP
import io.reactivex.Single

class TopListPresenter(topListModel: TopListMVP.ITopListModel) : TopListMVP.ITopListPresenter {

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
        mTopListModel.loadData().subscribe({
            mCurrentScreenState = ScreenState.SHOW_DATA
            mTopListView!!.showData(mTopListModel.getData())
        },{
            mCurrentErrorMessage = it.message.toString()
            mTopListView!!.showErrorMessage(mCurrentErrorMessage)
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

    override fun loadBitmap(url: String?): Single<Bitmap> {
        return mTopListModel.loadBitmap(url)
    }

    override fun processingBitmap(
        finalBitmap: Bitmap?,
        imageName: String?,
        screenSize: Point
    ): Bitmap? {
        return mTopListModel.saveImageInStorage(finalBitmap, imageName, screenSize)
    }
}