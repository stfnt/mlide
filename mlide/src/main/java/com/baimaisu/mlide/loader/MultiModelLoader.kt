package com.baimaisu.mlide.loader

import com.baimaisu.mlide.DataFetcher
import com.baimaisu.mlide.ModelLoader
import com.baimaisu.mlide.fetcher.MultiDataFetcher

class MultiModelLoader<Model,Data>(var loaders: List<ModelLoader<Model, Data>>) :
    ModelLoader<Model, Data> {

    override fun handles(model: Model):Boolean {
        loaders.forEach {
            if(it.handles(model)){
                return true
            }
        }

        return false
    }

    override fun build(model: Model): ModelLoader.LoadData<Data> {
        val fetchers:MutableList<DataFetcher<Data>> = mutableListOf()
        loaders.forEach {
            if(it.handles(model)){
                fetchers.add(it.build(model).fetcher)
            }
        }

        return ModelLoader.LoadData<Data>(MultiDataFetcher<Data>(fetchers))
    }

}
