package com.baimaisu.mlide.engine.generator

import com.baimaisu.mlide.*
import com.baimaisu.mlide.diskcache.DataSource
import com.baimaisu.mlide.engine.DecodeHelper
import com.baimaisu.mlide.engine.generator.iinterface.Generator
import com.baimaisu.mlide.request.RequestOptions
import java.lang.Exception

class ResourceGenerator(var requestOptions: RequestOptions, var decodeHelper: DecodeHelper) : Generator, DataFetcher.DataCallBack<Any>{
    var width = 0
    var height = 0

    var isCanceled = false
    var loadData:ModelLoader.LoadData<Any>?= null
    lateinit var fetchReadyCallBack: Generator.FetchReadyCallBack
    private var indexOfModelLoader = 0
    override fun start(): Boolean {
        val key = requestOptions.toResourceKey()
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
        fetchReadyCallBack.onDataFetchReady( data, DataSource.DISK_RESOURCE_CACHE)
    }
}