package com.baimaisu.mlide.registry.factory

import android.graphics.ColorSpace
import com.baimaisu.mlide.ModelLoader
import com.baimaisu.mlide.loader.MultiModelLoader
import isSelfOrSuperClass

class MultiModelLoaderFactory {

    class Entry<Model,Data>(var factory: ModelLoaderFactory<Model,Data>,
                            var modelClass:Class<Model>,
                            var dataClass: Class<Data>){


    }

    var entrys:MutableList<Entry< Any, Any>> = mutableListOf()
    fun <Model,Data> append(modelClass : Class<Model>,dataClass: Class<Data>,factory: ModelLoaderFactory<Model, Data>){
        entrys.add(Entry(factory,modelClass,dataClass) as Entry<Any,Any>)
    }


    fun <Model> getModelLoadPath(modelClass:Class<Model>):List<ModelLoader<Model,Any>>{
        val list = mutableListOf<ModelLoader<Model,Any>>()
        entrys.forEach {
            val entry = it
            if(entry.modelClass.isSelfOrSuperClass(modelClass) ){
                val loader = entry.factory.create(this)
                list.add(loader as ModelLoader<Model,Any> )
            }
        }

        return list
    }

    fun <Model,Data> build(modelClass: Class<Model>,dataClass: Class<Data>):ModelLoader<Model,Data>{
        val list = mutableListOf<ModelLoader<Model,Data>>()
        entrys.forEach {
             val entry = it
            if(entry.modelClass.isSelfOrSuperClass(modelClass) && dataClass.isSelfOrSuperClass(entry.dataClass)){
                val loader = entry.factory.create(this) as ModelLoader<Model,Data>
                list.add(loader)
            }
        }

        return MultiModelLoader(list)
    }
}