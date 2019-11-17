package com.baimaisu.mlide.fetcher

import buf
import com.baimaisu.mlide.DataFetcher

import java.io.File
import java.io.FileInputStream
import java.io.InputStream

class FileDataFetcher(var file: File): DataFetcher<InputStream> {
    override fun fetchData(callBack: DataFetcher.DataCallBack<InputStream>) {
        callBack.onReady(FileInputStream(file).buf())
    }

    override fun cancel() {
    }

    override fun getDataClass(): Class<InputStream> {
        return InputStream::class.java
    }

}