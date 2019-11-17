package com.baimaisu.mlide.engine.generator.iinterface

import com.baimaisu.mlide.diskcache.DataSource
import java.lang.Exception


interface Generator {
    fun start():Boolean
    fun cancel()

    interface FetchReadyCallBack{
        fun reschedule()
        fun onDataFetchReady(any:Any,dataSource: DataSource)
        fun onDataFetchFailed(e:Exception)
    }
}