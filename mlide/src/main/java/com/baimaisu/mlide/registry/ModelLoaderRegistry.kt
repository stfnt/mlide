package com.baimaisu.mlide.registry

import com.baimaisu.mlide.ModelLoader
import com.baimaisu.mlide.registry.factory.ModelLoaderFactory
import com.baimaisu.mlide.registry.factory.MultiModelLoaderFactory

class ModelLoaderRegistry(var multiModelLoaderFactory: MultiModelLoaderFactory){
    fun <Model,Data> append(modelClass: Class<Model>,dataClass:Class<Data>,factory: ModelLoaderFactory<Model, Data>){
        multiModelLoaderFactory.append(modelClass,dataClass,factory)
    }

    fun <Model> getModelLoadPath(modelClass:Class<Model>):List<ModelLoader<Model,Any>>{
        return multiModelLoaderFactory.getModelLoadPath(modelClass)
    }
}