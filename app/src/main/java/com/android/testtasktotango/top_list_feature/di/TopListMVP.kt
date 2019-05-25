package com.android.testtasktotango.top_list_feature.di
import android.graphics.Bitmap
import android.graphics.Point
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
        fun loadBitmap(url: String?): Single<Bitmap>
        fun saveImageInStorage(
            finalBitmap: Bitmap?,
            imageName: String?,
            screenSize: Point
        ): Bitmap?
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
        fun loadBitmap(url: String?): Single<Bitmap>
        fun processingBitmap(
            finalBitmap: Bitmap?,
            imageName: String?,
            screenSize: Point
        ): Bitmap?
    }
}