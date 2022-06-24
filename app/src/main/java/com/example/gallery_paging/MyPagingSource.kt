package com.example.gallery_paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.net.URL

class MyPagingSource(private val apiService: Api): PagingSource<Int, URLs>() {

    var total_page=3

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, URLs> {

         try {
            val position = params.key ?: 1

             Log.w("Position",position.toString())

            val requestBody: RequestBody = MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("type", "1")
                .addFormDataPart("page", position.toString())
                .addFormDataPart("per_page", "10")
                .addFormDataPart("added_by", "2")
                .build()

            class OAuthInterceptor(private val tokenType: String, private val accessToken: String) :
                Interceptor {

                override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
                    var request = chain.request()
                    request =
                        request.newBuilder().post(requestBody).addHeader("token", accessToken).build()

                    return chain.proceed(request)
                }
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(
                    OAuthInterceptor("token",MainActivity.token
                    )
                )
                .build()


            val gson = GsonBuilder()
                .setLenient()
                .create()

            var rf = Retrofit.Builder().baseUrl("http://emapp.brandmydream.com/evobee/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()

            var res = rf.create(Api::class.java)


            var c1=res.createPost()//enqueue(object : Callback<MainResp> {
              /*  override fun onResponse(call: Call<MainResp>, response: Response<MainResp>) {

                    Log.w("E", response.body()?.getID().toString())
                    Log.w("Call", call.request().toString())
                    Log.w("Tag", response.isSuccessful.toString())
                    Log.w("Data", call.toString())
                    Log.w("Data", response.body().toString())
                    // var x = response.body()

                    Log.w("Yes", "Working")


                    var x = response.body()!!.getURL()
                    Log.w("x",x.size.toString())
                  //  MainActivity.list=x
                  //  MainActivity.z1=1
                  /*  LoadResult.Page(data = x , prevKey = if (position == 1) null else position - 1,
                        nextKey = position + 1)*/
                    Log.w("Called","Yes")
                    aaa=1

                }

                override fun onFailure(call: Call<MainResp>, t: Throwable) {
                    Log.w("EE", call.toString())
                    Log.w("Data", call.request().toString())
                    Log.w("bool", call.isExecuted.toString())
                    // Log.w("Exec",call.execute().toString())
                    Log.w("Cause", t.cause.toString())
                    Log.w("Message", t.message.toString())
                }

            })*/
             var list1=c1.getURL() as MutableList<URLs>
//             list1.addAll(list1)
//             list1.addAll(list1)
//             list1.addAll(list1)
//             list1.addAll(list1)
//             list1.addAll(list1)
//             list1.addAll(list1)



             var list2=list1 as List<URLs>
             Log.w("LS",params.loadSize.toString())
            // total_page=((list2.size+7)/8)
             Log.w("Size",list2.size.toString())
            return LoadResult.Page(data = list2 , prevKey = if (position == 1) null else position - 1,
                nextKey =if(position==total_page)null else position + 1)
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, URLs>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }


}