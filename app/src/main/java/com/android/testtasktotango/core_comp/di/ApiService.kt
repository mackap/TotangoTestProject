package com.android.testtasktotango.core_comp.di

import com.android.testtasktotango.core_comp.data.pojo.RedditTopPostResp
import io.reactivex.Single
import retrofit2.http.*

interface ApiService {

    @GET("top.json")
    fun getTopPost(@Query ("after") after: String?,
                   @Query("limit") limit:Int): Single<RedditTopPostResp>
}
