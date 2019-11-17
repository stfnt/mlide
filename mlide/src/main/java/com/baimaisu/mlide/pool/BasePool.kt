package com.baimaisu.mlide.pool

import java.util.*

abstract class BasePool<T:Poolable> {
    companion object{
        const val MAX_SIZE = 20
    }

    var keyPool:Queue<T> = ArrayDeque<T>(MAX_SIZE)
    abstract fun create():T


    fun offer(key:T){
        if(keyPool.size < MAX_SIZE){
            keyPool.offer(key)
        }
    }

    fun get():T{
        return keyPool.poll()?:create()
    }
}