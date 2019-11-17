package com.baimaisu.mlide.engine.generator

import com.baimaisu.mlide.*
import com.baimaisu.mlide.diskcache.DataSource
import com.baimaisu.mlide.engine.generator.iinterface.Generator
import com.baimaisu.mlide.request.RequestOptions
import java.lang.Exception

class DataGenerator : Generator,DataFetcher.DataCallBack<Any>{

    lateinit var requestOptions: RequestOptions

    var loadData: ModelLoader.LoadData<Any>?= null

    private var isCanceled = false

    lateinit var fetchReadyCallBack: Generator.FetchReadyCallBack
    var indexOfModelLoader = 0
    override fun start():Boolean {
        if(isCanceled){
            return false
        }

        val key = requestOptions.toDataKey()
        val file = mlide.diskLruCache.getFile(key)
        if(!file.exists()){
            return false
        }

        val modelLoaders = mlide.registry.modelLoaderRegistry.getModelLoadPath(file.javaClass)
        if(modelLoaders.isEmpty()){
            return false
        }

        while (indexOfModelLoader < modelLoaders.size){
            if(modelLoaders[indexOfModelLoader].handles(file)){
                loadData = modelLoaders[indexOfModelLoader].build(file)
                if(loadData == null){
                    continue
                }

                val streamClass = loadData!!.fetcher.getDataClass()
                if(mlide.registry.decoderRegistry.getDecodePath(streamClass,requestOptions.targetClass).isNotEmpty()){
                    loadData!!.fetcher.fetchData(this)
                    return true
                }

            }

            indexOfModelLoader++
        }

        return false
    }

    override fun cancel() {
        isCanceled = true
        loadData?.fetcher?.cancel()
    }

    override fun onFail(e: Exception) {
        fetchReadyCallBack.onDataFetchFailed(e)
    }

    override fun onReady(data: Any) {
        fetchReadyCallBack.onDataFetchReady(data,DataSource.DISK_DATA_CACHE)
    }



}