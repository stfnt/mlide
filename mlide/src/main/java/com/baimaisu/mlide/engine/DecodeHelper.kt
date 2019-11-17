package com.baimaisu.mlide.engine


import android.view.Display
import com.baimaisu.mlide.ModelLoader
import com.baimaisu.mlide.decoder.Resource
import com.baimaisu.mlide.decoder.iinterface.Decoder
import com.baimaisu.mlide.mlide

class DecodeHelper(private val model:Any,
                   private val resourceClass: Class<Any>) {


    private var decodePaths:MutableList<Decoder<Any, Any>> = mutableListOf()
    private var hasLoadDecodePaths = false

    private var loadDatas:MutableList<ModelLoader.LoadData<Any>> = mutableListOf()
    private var hasLoadModelLoadePaths = false


    fun getAllDecodePaths(dataClass: Class<Any>):List<Decoder<Any, Any>>{
        if(!hasLoadDecodePaths){
            decodePaths.addAll(mlide.registry.decoderRegistry.getDecodePath(dataClass,resourceClass))
            hasLoadDecodePaths = true
        }

        return decodePaths
    }


    fun getAllModelLoaderPaths():List<ModelLoader.LoadData<Any>>{
        if(!hasLoadModelLoadePaths){
            mlide.registry.modelLoaderRegistry.getModelLoadPath(model.javaClass).forEach {
                loadDatas.add((it).build(model))
            }
            hasLoadModelLoadePaths = true
        }

        return loadDatas
    }


}