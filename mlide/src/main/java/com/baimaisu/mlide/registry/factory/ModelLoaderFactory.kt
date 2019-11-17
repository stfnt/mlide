package com.baimaisu.mlide.registry.factory

import android.view.Display
import com.baimaisu.mlide.ModelLoader

interface ModelLoaderFactory<Model,Data>{
    fun create(multiModelLoaderFactory: MultiModelLoaderFactory):ModelLoader<Model,Data>
}