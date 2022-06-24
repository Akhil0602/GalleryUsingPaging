package com.example.gallery_paging

import android.text.style.URLSpan

class MainResp(status:Int, message:String, data:List<URLs>, pages:Int ) {

    var status=status
    var message=message
    var data=data
    var pages=pages

    fun getID(): Int
    {
        return this.status
    }

    fun getURL():List<URLs>
    {
        return data
    }


}