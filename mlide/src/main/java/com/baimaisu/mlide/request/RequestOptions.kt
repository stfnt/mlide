package com.baimaisu.mlide.request

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.IntRange
import com.baimaisu.mlide.decoder.DecodeOptions
import com.baimaisu.mlide.diskcache.DiskCacheStrategy
import com.baimaisu.mlide.encoder.BitmapResourceEncoder
import com.baimaisu.mlide.encoder.EncodeOptions

open class RequestOptions {

    var encodeOptions: EncodeOptions = EncodeOptions()
    var decodeOptions: DecodeOptions = DecodeOptions()
    var requestPriority: RequestPriority =
        RequestPriority.MID
    var memoryCacheable = true
    var diskCacheable = true

    var errorDrawable: Drawable? = null
    var placeDrawable: Drawable? = null

    var errorDrawableId: Int? = null
    var placeDrawableId: Int? = null

    lateinit var diskCacheStrategy: DiskCacheStrategy
    lateinit var targetClass: Class<Any>
    lateinit var url: String

    var width:Int = 0
    var height:Int = 0



    fun diskCache(diskCacheStrategy: DiskCacheStrategy){
        this.diskCacheStrategy = diskCacheStrategy
    }

    fun enableMemoryCache() {
        memoryCacheable = true
    }

    fun enableDiskCache() {
        diskCacheable = true
    }

    fun disableMemoryCache() {
        memoryCacheable = false
    }

    fun disableDiskCache() {
        diskCacheable = false
    }





    //    val COMPRESSQUALITY : Option<Int> = Option.memory("com.baimaisu.mlide.encoder.BitmapResourceEncoder.COMPRESSQUALITY",90)
//    val COMPRESSFORMAT : Option<Bitmap.CompressFormat> = Option.memory("com.baimaisu.mlide.encoder.BitmapResourceEncoder.COMPRESSFORMAT",
//        Bitmap.CompressFormat.PNG)
    fun encodeQulity(@IntRange(from = 0, to = 100) qulity: Int) {
        encodeOptions.put(BitmapResourceEncoder.COMPRESSQUALITY,qulity)
    }

    fun encodeCompressFormat(compressFormat: Bitmap.CompressFormat){
        encodeOptions.put(BitmapResourceEncoder.COMPRESSFORMAT,compressFormat)
    }


    fun toResourceKey():String{
        return ""
    }

    fun toDataKey():String{
        return ""
    }

}