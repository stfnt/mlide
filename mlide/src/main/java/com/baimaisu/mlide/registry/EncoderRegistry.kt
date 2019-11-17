package com.baimaisu.mlide.registry

import com.baimaisu.mlide.decoder.iinterface.Decoder
import com.baimaisu.mlide.encoder.iinterface.Encoder
import com.baimaisu.mlide.isSelfOrSuperClass

class EncoderRegistry {
    class Entry<T>(
        var dataClass:Class<T>,
        var encoder: Encoder<T>
    )

    private val encoders = mutableListOf<Entry<*>>()
    fun <T> append(dataClass:Class<T>,encoder: Encoder<T>){
        encoders.add(Entry(dataClass,encoder))
    }

    fun <T> getEncodePath(dataClass:Class<T>):List<Encoder<T>>{
        val list = mutableListOf<Encoder<T>>()

        return list

    }
}