package com.baimaisu.mlide.request

interface Request{
    fun begin()
    fun clear()


    fun isComplete():Boolean
    fun isFailed():Boolean
    fun isRunning():Boolean
    fun isCleared():Boolean

}