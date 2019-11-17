package com.baimaisu.mlide.request.manager

import android.graphics.drawable.Drawable
import com.baimaisu.mlide.R
import com.baimaisu.mlide.request.RequestBuilder
import com.baimaisu.mlide.lifecycle.iinterface.ILifecycle
import com.baimaisu.mlide.request.Request
import java.lang.ref.WeakReference

class RequestManager  : ILifecycle{

    val requests:MutableList<WeakReference<Request>>  = mutableListOf()



    fun runRequest(request: Request){
        requests.add(WeakReference(request))
    }

    fun clearRequest(request: Request){
        val toBeCleared:MutableList<WeakReference<Request>> = mutableListOf()
        requests.forEach {
            if(it.get() == null){
                toBeCleared.add(it)
            }else{
                if(it.get() == request){
                    toBeCleared.add(it)
                }
            }
        }

        toBeCleared.forEach {
            requests.remove(it)
        }
    }
    override fun onStart() {
        requests.forEach {
            it.get()?.begin()
        }
    }

    override fun onStop() {
        requests.forEach {
            it.get()?.clear()
        }
    }

    fun  load(url:String): RequestBuilder<Drawable> {
        return asDrawable().load(url)
    }

    fun  asDrawable(): RequestBuilder<Drawable> {
        return RequestBuilder(this,Drawable::class.java)
    }
}