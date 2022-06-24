package com.example.gallery_paging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity() {

    companion object {
     //   lateinit var list: List<URLs>
     //   var z1=0
        var base = "http://emapp.brandmydream.com/evobee/"
        var token =
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjoiMTQ4MCIsInVzZXJfdHlwZSI6IjIiLCJkZXZpY2VfdG9rZW4iOiJkdXJnZXNoIiwiZGV2aWNlX3R5cGUiOiJhbmRyb2lkIiwidGltZSI6MTY1NTg5NTA0MCwiaWQiOiIxNDgwIn0.7wGXIf-aKdrmikBBcaescupKvtHYD9wccVHh9nXJ9gw"
    }

    lateinit var viewModel: MainViewModel
    lateinit var requestBody:RequestBody
    private val adapter = MyPagingAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    //    list=res.createPost().execute().body()!!.getURL()

        val mainRepository = MainRepository(Api.getInstance())

        var binding=findViewById<RecyclerView>(R.id.recyclerView)

        binding.adapter = adapter
        binding.layoutManager=GridLayoutManager(this,2)

        viewModel = ViewModelProvider(
            this,
            MyModelFactory(mainRepository)
        ).get(MainViewModel::class.java)

        viewModel.errorMessage.observe(this) {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        adapter.addLoadStateListener { loadState ->
            // show empty list
            if (loadState.refresh is LoadState.Loading ||
                loadState.append is LoadState.Loading)
              //  binding.progressDialog.isVisible = true
            else {
             //   binding.progressDialog.isVisible = false
                // If we have an error, show a toast
                val errorState = when {
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.prepend is LoadState.Error ->  loadState.prepend as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                errorState?.let {
                    Log.w("Error1",it.error.toString())
                    Toast.makeText(this, it.error.toString(), Toast.LENGTH_LONG).show()
                }

            }
        }

        lifecycleScope.launch {
            viewModel.getURLList().observe(this@MainActivity) {
                it?.let {
                    adapter.submitData(lifecycle, it)
                }
            }
        }

    }
}
