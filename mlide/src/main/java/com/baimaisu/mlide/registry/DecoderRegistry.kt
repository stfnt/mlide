package com.baimaisu.mlide.registry

import com.baimaisu.mlide.ModelLoader
import com.baimaisu.mlide.decoder.Resource
import com.baimaisu.mlide.decoder.iinterface.Decoder
import isSelfOrSuperClass


class DecoderRegistry {

    class Entry<Data,Resource>(
        var dataClass:Class<Data>,
        var resourceClass: Class<Resource>,
        var decoder:Decoder<Data,Resource>
    )

    private val decoders = mutableListOf<Entry<*,*>>()
    fun <Data,Resource> append(dataClass:Class<Data>,resourceClass: Class<Resource>,decoder:Decoder<Data,Resource>){
        decoders.add(Entry(dataClass,resourceClass,decoder))
    }

    fun <Data,Resource> getDecodePath(dataClass:Class<Data>,resourceClass: Class<Resource>):List<Decoder<Data,Resource>>{
        val list = mutableListOf<Decoder<Data, Resource>>()
        decoders.forEach {
            if(it.dataClass.isSelfOrSuperClass(dataClass) && it.resourceClass.isSelfOrSuperClass(resourceClass)){
                list.add(it.decoder as Decoder<Data,Resource>)
            }
        }

        return list

    }
}