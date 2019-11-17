package com.baimaisu.mlide.decoder

import android.graphics.Bitmap
import com.baimaisu.mlide.Mlide

interface BitmapPool {
    fun recycleBitmap(bitmap: Bitmap)

    fun getBitmap(width:Int,height:Int,bitmapConfig: Bitmap.Config):Bitmap

    fun trimMemory(level:Int)
}

val defaultBitmapPool = object :BitmapPool{

    override fun recycleBitmap(bitmap: Bitmap) {

    }

    override fun getBitmap(width: Int, height: Int,bitmapConfig: Bitmap.Config): Bitmap {
        return Bitmap.createBitmap(width,height,bitmapConfig)
    }

    override fun trimMemory(level: Int) {

    }

}

val queueBitmapPool = QueueBitmapPool()
fun Mlide.getBitmapPool():BitmapPool{
    return queueBitmapPool
}

