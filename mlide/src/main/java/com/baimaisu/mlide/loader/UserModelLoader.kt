package com.baimaisu.mlide.loader

import com.baimaisu.mlide.ModelLoader
import com.baimaisu.mlide.registry.factory.ModelLoaderFactory
import com.baimaisu.mlide.registry.factory.MultiModelLoaderFactory
import java.io.InputStream


data class User(val name:String,val url:String)
class UserModelLoader(var stringLoader: ModelLoader<String,InputStream>): ModelLoader<User,InputStream>{
    override fun handles(model: User): Boolean {
        return stringLoader.handles(model.url)
    }

    override fun build(model: User): ModelLoader.LoadData<InputStream> {
        return stringLoader.build(model.url)
    }

    class Factory : ModelLoaderFactory<User,InputStream>{
        override fun create(multiModelLoaderFactory: MultiModelLoaderFactory): ModelLoader<User, InputStream> {
            return UserModelLoader(multiModelLoaderFactory.build(String::class.java,InputStream::class.java))
        }

    }
}