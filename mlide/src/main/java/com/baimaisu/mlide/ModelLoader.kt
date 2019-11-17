package com.baimaisu.mlide


/**
 *  for transform model to inputstream or byteBuffer
 */

interface ModelLoader<MODEL,DATA> {
    fun handles(model:MODEL):Boolean

    fun build(model:MODEL):LoadData<DATA>

    class LoadData<T>(var fetcher:DataFetcher<T>){

    }
}

class Axx<T>{
    fun test(t:T){

    }
}

fun main(){
    Axx<String>().test("axx")
    Axx<Any?>().test(null)
    Axx<Unit>().test(Unit)
}



