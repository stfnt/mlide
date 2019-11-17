package com.baimaisu.mlide.fetcher

import com.baimaisu.mlide.DataFetcher
import java.io.File
import java.io.FileInputStream
import java.io.InputStream
import java.net.URL

class HttpDataFetcher(var url:String): DataFetcher<InputStream> {


    override fun fetchData(callBack: DataFetcher.DataCallBack<InputStream>) {
        val connection = URL(url).openConnection()
        connection.readTimeout = 1000 * 10
        callBack.onReady(connection.getInputStream())
    }

    override fun cancel() {

    }

    override fun getDataClass(): Class<InputStream> {
        return InputStream::class.java
    }
}

