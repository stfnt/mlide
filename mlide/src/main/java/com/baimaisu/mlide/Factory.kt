package com.baimaisu.mlide

import com.jakewharton.disklrucache.DiskLruCache
import java.io.File
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.math.max

/***********************************************************
 ** Copyright (C), 2008-2016, OPPO Mobile Comm Corp., Ltd.
 ** VENDOR_EDIT
 ** File:
 ** Description:
 ** Version: 1.0
 ** Date : 2019/10/15
 ** Author: liangweibin@myoas.com
 **
 ** ---------------------Revision History: ---------------------
 **  <author>	             <data> 	 <version >	    <desc>
 ** liangweibin@myoas.com    2019/10/15    1.0
 ****************************************************************/
abstract class Factory<T> {
    abstract fun create():T
}

abstract class DiskLruCacheFactory : Factory<DiskLruCache>()


class DiskLruCacheFactoryImpl : DiskLruCacheFactory(){

    var dir:File = File(ApplicationHolder.application.filesDir,"mlideLruCache")
    var maxSize:Long = 512 * 1024 * 1024
    override fun create(): DiskLruCache {
        return DiskLruCache.open(dir,1,1, maxSize)
    }


}

abstract class ExecutorFactory : Factory<ThreadPoolExecutor>()

class ExecutorFactoryImpl : ExecutorFactory(){

    val threadFactory = object : ThreadFactory{
        val index = AtomicInteger(0)
        override fun newThread(r: Runnable): Thread {
            return Thread(r,"mlide-${index.getAndAdd(1)}")
        }

    }

    val corePoolSize = 0
    val maxPoolSize = 5

    override fun create(): ThreadPoolExecutor {
        return ThreadPoolExecutor(
            corePoolSize, maxPoolSize, 60, TimeUnit.SECONDS,
            PriorityBlockingQueue<Runnable>(128), threadFactory)
    }

}


























