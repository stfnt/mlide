package com.baimaisu.mlide.engine.generator

import com.baimaisu.mlide.*
import com.baimaisu.mlide.diskcache.DataSource
import com.baimaisu.mlide.engine.DecodeHelper
import com.baimaisu.mlide.engine.generator.iinterface.Generator
import com.baimaisu.mlide.request.RequestOptions
import java.io.InputStream
import java.lang.Exception

class SourceGenerator(var decodeHelper: DecodeHelper) : Generator, DataFetcher.DataCallBack<Any>{
    lateinit var requestOptions: RequestOptions
    lateinit var fetchReadyCallBack: Generator.FetchReadyCallBack

    var indexOfModelLoader = 0



    var data:Any? = null



    var generator:Generator? = null
    var fetcher:DataFetcher<Any>? = null
    var isCanceled = false
    override fun start(): Boolean {
        if(isCanceled){
            return false
        }
        if(data != null){
            cacheData()
        }
        if(generator != null && (generator as Generator).start()){
            return true
        }
        generator = null


        val list = decodeHelper.getAllModelLoaderPaths()
        while (indexOfModelLoader < list.size){
            fetcher = list[indexOfModelLoader].fetcher
            val dataClass = fetcher!!.getDataClass()
            if(decodeHelper.getAllDecodePaths(dataClass).isNotEmpty()){
                list[indexOfModelLoader].fetcher.fetchData(this)
                return true
            }else{
                indexOfModelLoader++
            }
        }

        return false
    }



    fun cacheData(){
        DiskLruCacheUtil.saveFile(requestOptions.toDataKey(),data as InputStream)
        generator = DataGenerator().apply {
            this.requestOptions = this@SourceGenerator.requestOptions
            this.fetchReadyCallBack = this@SourceGenerator.fetchReadyCallBack
        }
    }

    override fun cancel() {
        isCanceled = true
        fetcher?.cancel()
    }

    override fun onFail(e: Exception) {
        fetchReadyCallBack.onDataFetchFailed(e)
    }

    override fun onReady(data: Any) {
        if(requestOptions.diskCacheStrategy.isDataCacheable(DataSource.REMOTE)){
            /**
             *  save with diskLru
             *  call DataGenerator next
             */
            fetchReadyCallBack.reschedule()
        }else{
            fetchReadyCallBack.onDataFetchReady(data,DataSource.REMOTE)
        }
    }
}

