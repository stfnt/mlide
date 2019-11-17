package com.baimaisu.mlide

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks2
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import com.baimaisu.mlide.decoder.getBitmapPool
import com.baimaisu.mlide.decoder.impl.StreamBitmapDecoder
import com.baimaisu.mlide.lifecycle.MLifecycleObserver
import com.baimaisu.mlide.registry.Registry
import com.baimaisu.mlide.request.manager.RequestManager
import com.baimaisu.mlide.viewtarget.factory.ImageViewTargetFractory
import com.jakewharton.disklrucache.DiskLruCache
import java.io.InputStream
import java.util.concurrent.ThreadPoolExecutor

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
object RequestConfig {
    var placeDrawable: Drawable?= null
    var placeDrawableId:Int? = null

    var errDrawable:Drawable? = null
    var errDrawableId:Int? = null
}

object ApplicationHolder{
    lateinit var application: Application
}

/***
 *  1.有生命周期监听
 *  2。注册了释放内存回调，可以在内存低的使用，释放静态的对象池
 *  3。resource和data分别对应不同的key
 *
 */
class Mlide : ComponentCallbacks2{

    var diskLruCache:DiskLruCache
    var keyGenerator:KeyGenerator
    var threadPoolExecutor:ThreadPoolExecutor
    lateinit var registry:Registry

    val imageViewTargetFractory = ImageViewTargetFractory()

    class Builder{
        lateinit var diskLruCacheFactory: DiskLruCacheFactory
        lateinit var keyGeneratorFactory: KeyGeneratorFactory
        lateinit var threadPoolFactory: ExecutorFactory

        fun build():Mlide{
            return Mlide(builder = this)
        }
    }

    constructor(builder:Builder){
        this.diskLruCache = builder.diskLruCacheFactory.create()
        this.keyGenerator = builder.keyGeneratorFactory.create()
        this.threadPoolExecutor = builder.threadPoolFactory.create()
        registerDefaultCompent()
    }

    private fun registerDefaultCompent(){
        registry.decoderRegistry.append(InputStream::class.java,Bitmap::class.java,StreamBitmapDecoder())
    }

    fun with(lifecycleOwner:LifecycleOwner):RequestManager{
        if(MLifecycleObserver.registry.containsKey(lifecycleOwner)){
            return MLifecycleObserver.registry[lifecycleOwner] as RequestManager
        }
        val requestManager = RequestManager()
        MLifecycleObserver.registry[lifecycleOwner] = requestManager
        lifecycleOwner.lifecycle.addObserver(MLifecycleObserver(lifecycleOwner))
        return requestManager

    }

    override fun onLowMemory() {

    }

    override fun onConfigurationChanged(newConfig: Configuration) {

    }

    override fun onTrimMemory(level: Int) {
        getBitmapPool().trimMemory(level)
    }



}

class MlideConfig{
    fun setErrorDrawable(error:Drawable){
        RequestConfig.errDrawable =error
    }
    fun setErrorDrawableId(errorId:Int){
        RequestConfig.errDrawableId = errorId
    }

    fun setPlaceDrawable(place:Drawable){
        RequestConfig.placeDrawable = place
    }

    fun setPlaceDrawableId(placeId:Int){
        RequestConfig.placeDrawableId = placeId
    }
}
lateinit var mlide:Mlide
fun Application.initMlide(){
    mlide = Mlide.Builder().apply {
        diskLruCacheFactory = DiskLruCacheFactoryImpl().apply {
            maxSize = 100 * 1024 * 1024
        }

        keyGeneratorFactory = KeyGeneratorFactoryImpl()

        threadPoolFactory = ExecutorFactoryImpl()
    }.build()
}

