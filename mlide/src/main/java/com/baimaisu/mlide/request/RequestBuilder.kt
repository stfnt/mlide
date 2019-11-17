package com.baimaisu.mlide.request

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.IntRange
import com.baimaisu.mlide.decoder.DecodeOptions
import com.baimaisu.mlide.diskcache.DiskCacheStrategy
import com.baimaisu.mlide.encoder.BitmapResourceEncoder
import com.baimaisu.mlide.encoder.EncodeOptions
import com.baimaisu.mlide.mlide
import com.baimaisu.mlide.request.manager.RequestManager
import com.baimaisu.mlide.viewtarget.ImageViewTarget
import com.baimaisu.mlide.viewtarget.ViewTarget

/***********************************************************
 ** Copyright (C), 2008-2016, OPPO Mobile Comm Corp., Ltd.
 ** VENDOR_EDIT
 ** File:
 ** Description:
 ** Version: 1.0
 ** Date : 2019/10/15
 ** Author: liangweibin@myoas.com
 **
 ** ---------------------Revision History: ---------------------
 **  <author>	             <data> 	 <version >	    <desc>
 ** liangweibin@myoas.com    2019/10/15    1.0
 ****************************************************************/




class RequestBuilder<R>(var requestManager: RequestManager,var transcodeClass:Class<R>) : RequestOptions() {

    fun load(url:String): RequestBuilder<R> {
        this.url = url
        return this
    }

    fun error(errorDrawableId:Int): RequestBuilder<R> {
        this.errorDrawableId = errorDrawableId
        return this
    }

    fun error(errorDrawable:Drawable): RequestBuilder<R> {
        this.errorDrawable = errorDrawable
        return this
    }


    fun place(placeDrawableId:Int): RequestBuilder<R> {
        this.placeDrawableId = placeDrawableId
        return this
    }
    fun place(placeDrawable: Drawable): RequestBuilder<R> {
        this.placeDrawable = placeDrawable
        return this
    }


    fun into(imageView: ImageView):ViewTarget<ImageView,R>{
        val request = SingleRequest<R>()
        request.requestOptions = this
        val target = mlide.imageViewTargetFractory.buildViewTarget(imageView,transcodeClass)
        request.target = target

        request.begin()

        requestManager.runRequest(request)
        return target

    }

    fun override(width:Int,height:Int):RequestBuilder<R>{
        this.width = width
        this.height = height
        return this
    }


}