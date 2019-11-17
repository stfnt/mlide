package com.baimaisu.mlide.viewtarget.factory

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.baimaisu.mlide.viewtarget.BitmapImageViewTarget
import com.baimaisu.mlide.viewtarget.DrawableImageViewTarget
import com.baimaisu.mlide.viewtarget.ImageViewTarget
import com.baimaisu.mlide.viewtarget.ViewTarget
import java.lang.Exception

class ImageViewTargetFractory {
    fun <R> buildViewTarget(imageView:ImageView,clazz:Class<R>):ViewTarget<ImageView,R>{
        if(clazz == Bitmap::class.java){
            return BitmapImageViewTarget(imageView) as ViewTarget<ImageView, R>
        }else if(clazz == Drawable::class.java){
            return DrawableImageViewTarget(imageView) as ViewTarget<ImageView, R>
        }

        throw Exception("not support class: $clazz")
    }
}