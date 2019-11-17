package com.baimaisu.mlide.engine.generator

import android.graphics.Bitmap
import android.util.Log


interface Interceptor{
    fun intercept(chain:Chain):Bitmap?
}
class Chain (var interceptors : List<Interceptor>,val index:Int){
    fun process():Bitmap?{
        if(index >= interceptors.size){
            return null
        }

        val newChain = Chain(interceptors,index+1)
        return interceptors[index].intercept(newChain)
    }
}

class LoginInterceptor : Interceptor{
    override fun intercept(chain: Chain): Bitmap? {
        Log.d("axx","122")
        var result = chain.process()
        Log.d("axx","233")
        return result
    }

}