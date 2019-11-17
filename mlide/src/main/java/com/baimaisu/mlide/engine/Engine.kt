package com.baimaisu.mlide.engine


import com.baimaisu.mlide.request.RequestOptions
import com.baimaisu.mlide.decoder.Resource
import com.baimaisu.mlide.diskcache.DataSource
import com.baimaisu.mlide.mlide
import com.baimaisu.mlide.momorycache.memoryCache
import java.lang.Exception



interface ResourceCallBack<R>{
    fun onReschedule(decodeJob: DecodeJob<R>)
    fun onResourceCallBack(resource:Resource<R>,dataSource: DataSource)
    fun onLoadFail(e:Exception)
}

class Engine<R> : ResourceCallBack<R>{
    override fun onResourceCallBack(
        resource: com.baimaisu.mlide.decoder.Resource<R>,
        dataSource: DataSource
    ) {

    }

    override fun onReschedule(decodeJob: DecodeJob<R>) {
        mlide.threadPoolExecutor.execute(decodeJob)
    }



    override fun onLoadFail(e: Exception) {
    }


    fun load(requestOptions: RequestOptions, cb:ResourceCallBack<R>){

        /**
         *  fetch from memory is fast
         *  and only do it in main thread is enough
         */
        val key = requestOptions.url
        if(memoryCache.get(key) != null && memoryCache.get(key).get() != null
            && (memoryCache.get(key).get() as Any).javaClass == requestOptions.targetClass){
            cb.onResourceCallBack(memoryCache.get(key).get() as Resource<R>,DataSource.MEMORY_CACHE)
            return
        }

        val decodeJob = DecodeJob<R>().apply { init(requestOptions,cb) }
        mlide.threadPoolExecutor.execute(decodeJob)
    }





}