package com.baimaisu.mlide.lifecycle

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.baimaisu.mlide.lifecycle.iinterface.ILifecycle
import com.baimaisu.mlide.request.manager.RequestManager
import java.util.*
import kotlin.collections.HashMap

class MLifecycleObserver(var lifecycleOwner: LifecycleOwner): LifecycleObserver {


    companion object{
        val registry:HashMap<LifecycleOwner,RequestManager> = HashMap()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(){
        registry[lifecycleOwner]?.onStart()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop(){
        registry[lifecycleOwner]?.onStop()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun unRegister(){
        registry.remove(lifecycleOwner)
    }
}