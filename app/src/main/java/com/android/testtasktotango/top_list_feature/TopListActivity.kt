package com.android.testtasktotango.top_list_feature

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.android.testtasktotango.R
import com.android.testtasktotango.TotangoApplication
import com.android.testtasktotango.core_comp.IRecItemClickListener
import com.android.testtasktotango.core_comp.ScreenState
import com.android.testtasktotango.core_comp.data.pojo.Post
import com.android.testtasktotango.top_list_feature.di.TopListMVP
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.top_post_list_activity.*
import java.net.URL
import javax.inject.Inject


class TopListActivity : AppCompatActivity(), TopListMVP.ITopListView, IRecItemClickListener {

    private var screenHeight: Int =0
    private var screenWidth: Int = 0
    private var mSelectedImageThumb: String? = null
    @Inject
    lateinit var mTopListPresenter: TopListMVP.ITopListPresenter
    var mRecAdapter: TopListAdapter? = null
    lateinit var mLayoutManager: LinearLayoutManager
    var compositDisposable: CompositeDisposable = CompositeDisposable()
    val WRITE_PERMISSION = 12345
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as TotangoApplication).getTopListComponent()?.injectIntoActivity(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.top_post_list_activity)
        mLayoutManager = LinearLayoutManager(this)
        rec_activity_post_list.layoutManager = mLayoutManager

        setRecyclerScrollListener()
    }


    override fun onResume() {
        super.onResume()
        getScreenSize()
        mTopListPresenter.setView(this)
        showCurrentState(mTopListPresenter.getCurrentState())
    }

    private fun showCurrentState(currenScreenState: ScreenState) {
        when (currenScreenState) {
            ScreenState.FIRST_OPEN -> mTopListPresenter.loadData()
            ScreenState.SHOW_DATA -> showData(mTopListPresenter.getData())
            ScreenState.SHOW_PROGRESS -> showProgress(true)
            ScreenState.SHOW_ERROR -> showErrorMessage(mTopListPresenter.getCurrentErrorMessage())
        }
    }

    override fun showData(postList: List<Post>) {
        showProgress(false)
        if (mRecAdapter == null) {
            mRecAdapter = TopListAdapter(postList!!, this)
        } else {
            mRecAdapter?.updateData(postList)
        }

        rec_activity_post_list.adapter = mRecAdapter
    }


    override fun showErrorMessage(message: String) {
        showProgress(false)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun showProgress(isShow: Boolean) {
        if (isShow) {
            progress_circular.visibility = View.VISIBLE
        } else {
            progress_circular.visibility = View.GONE
        }
    }

    override fun onPause() {
        super.onPause()
        mTopListPresenter.setView(null)
        compositDisposable.clear()
    }

    private fun setRecyclerScrollListener() {
        rec_activity_post_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                if (mTopListPresenter.getCurrentState() == ScreenState.SHOW_DATA
                    && totalItemCount == mLayoutManager.findLastVisibleItemPosition() + 1
                ) {
                    mTopListPresenter.loadData()
                }
            }
        })
    }

    override fun onItemClick(imageUrl: String?) {
        imageUrlProcessing(imageUrl)
    }

    private fun imageUrlProcessing(imageUrl: String?) {
        mSelectedImageThumb = imageUrl
        if (checkRuntimePermission())
            compositDisposable.add(
                Single.fromCallable {
                    loadBitmap(imageUrl)
                }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        var optimizedBitmpap = saveImageInStorage(it, imageUrl)
                        showImage(optimizedBitmpap)
                    }, {
                        Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                    })
            )
    }

    private fun showImage(optimizedBitmap: String) {
        val iv = ImageView(this)
        iv.setImageBitmap(getOptimizedBitmap(optimizedBitmap))
        val dialog: AlertDialog = AlertDialog.Builder(this).setView(iv).create()
        dialog.show()
    }

    private fun saveImageInStorage(finalBitmap: Bitmap?, imageName: String?): String {
        return MediaStore.Images.Media.insertImage(contentResolver, finalBitmap, imageName, null)
    }

    fun loadBitmap(url: String?): Bitmap? {
        val newurl = URL(url)
        return BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
    }

    //// get scaled bitmap from storage
    fun getOptimizedBitmap(loadedImageUri: String?): Bitmap? {



        val imageUri = Uri.parse(loadedImageUri)
        val bitmapOptions: BitmapFactory.Options = BitmapFactory.Options()
        bitmapOptions.inJustDecodeBounds = true

        BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri), null, bitmapOptions)

        bitmapOptions.inSampleSize = calculateInSampleSize(bitmapOptions, screenWidth, screenHeight)
        bitmapOptions.inJustDecodeBounds = false

        return BitmapFactory.decodeStream(contentResolver.openInputStream(imageUri), null, bitmapOptions)
    }

    private fun getScreenSize() {
        val display = getWindowManager().getDefaultDisplay();
        val size = Point()
        display.getSize(size)
        screenWidth = size.x
        screenHeight = size.y
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

    private fun checkRuntimePermission(): Boolean {
        return if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            == PackageManager.PERMISSION_GRANTED
        ) {
            true
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                , WRITE_PERMISSION
            )
            false
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == WRITE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                imageUrlProcessing(mSelectedImageThumb)
            }
        }
    }
}