package com.example.gallery_paging

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import retrofit2.Retrofit

class MainRepository constructor(private val retrofitService: Api) {

    fun getAllURL(): LiveData<PagingData<URLs>> {

        Log.w("Inside","MainRepository")

        return Pager(
            config = PagingConfig(
                pageSize = 8,
                enablePlaceholders = false,
                initialLoadSize = 25
            ),
            pagingSourceFactory = {
                MyPagingSource(retrofitService)
            }
            , initialKey = 1
        ).liveData
    }

}