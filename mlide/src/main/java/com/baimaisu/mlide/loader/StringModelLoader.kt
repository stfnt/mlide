package com.baimaisu.mlide.loader

import com.baimaisu.mlide.ModelLoader
import com.baimaisu.mlide.fetcher.HttpDataFetcher
import com.baimaisu.mlide.registry.factory.ModelLoaderFactory
import com.baimaisu.mlide.registry.factory.MultiModelLoaderFactory
import java.io.InputStream

class StringModelLoader : ModelLoader<String,InputStream> {
    override fun handles(model: String): Boolean {
        return model.startsWith("https://") ||
                model.startsWith("http://")
    }

    override fun build(model: String): ModelLoader.LoadData<InputStream> {
        return ModelLoader.LoadData(HttpDataFetcher(model))
    }


    class Factory : ModelLoaderFactory<String,InputStream>{
        override fun create(multiModelLoaderFactory: MultiModelLoaderFactory): ModelLoader<String, InputStream> {
            return StringModelLoader()
        }

    }
}