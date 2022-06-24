package com.example.gallery_paging

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import java.util.concurrent.Flow

class MainViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {

    val errorMessage = MutableLiveData<String>()

    fun getURLList(): LiveData< PagingData<URLs> > {
        Log.w("Inside","MainViewModel")
        return mainRepository.getAllURL().cachedIn(viewModelScope)
    }

}