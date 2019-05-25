package com.android.testtasktotango.top_list_feature
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.net.Uri
import android.provider.MediaStore
import com.android.testtasktotango.core_comp.data.pojo.Post
import com.android.testtasktotango.core_comp.di.ApiService
import com.android.testtasktotango.core_comp.di.ILocalStorage
import com.android.testtasktotango.top_list_feature.di.TopListMVP
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.net.URL


class TopListModel(apiService: ApiService, localStorage: ILocalStorage) :
    TopListMVP.ITopListModel {
    private val POSTS_LIMIT: Int = 25

    var mApiService = apiService
    var mLocalStorage = localStorage
    val mContext = localStorage.getContext()
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

    override fun saveImageInStorage(
        finalBitmap: Bitmap?,
        imageName: String?,
        screenSize: Point
    ): Bitmap? {
       var imagePath = MediaStore.Images.Media.insertImage(mContext.contentResolver, finalBitmap, imageName, null)
    return getOptimizedBitmap(imagePath, screenSize)
    }

   override fun loadBitmap(url: String?): Single<Bitmap> {
     return   Single.fromCallable {
         BitmapFactory.decodeStream(URL(url).openConnection().getInputStream()) }
    }

    override fun getData(): List<Post> {
        return mTopList
    }
    //// get scaled bitmap from storage
    fun getOptimizedBitmap(loadedImageUri: String?, screenSize: Point): Bitmap? {

        val imageUri = Uri.parse(loadedImageUri)
        val bitmapOptions: BitmapFactory.Options = BitmapFactory.Options()
        bitmapOptions.inJustDecodeBounds = true

        BitmapFactory.decodeStream(mContext.contentResolver.openInputStream(imageUri), null, bitmapOptions)

        bitmapOptions.inSampleSize = calculateInSampleSize(bitmapOptions, screenSize.x, screenSize.y)
        bitmapOptions.inJustDecodeBounds = false

        return BitmapFactory.decodeStream(mContext.contentResolver.openInputStream(imageUri), null, bitmapOptions)
    }
    //// calculate scale factor for show big images to prevent java.lang.RuntimeException: Canvas: trying to draw too large
    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        // Raw height and width of image
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

}

