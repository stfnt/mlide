package com.baimaisu.mlide.fetcher

import com.baimaisu.mlide.DataFetcher
import java.lang.Exception

class MultiDataFetcher<T>(var fetchers:List<DataFetcher<T>>) : DataFetcher<T>, DataFetcher.DataCallBack<T>{

    override fun onFail(e: Exception) {
        startNextOrFail()
    }

    override fun onReady(data: T) {
        this.callBack.onReady(data)
    }

    var isCanceled = false
    var index = 0

    lateinit var callBack: DataFetcher.DataCallBack<T>
    override fun fetchData(callBack: DataFetcher.DataCallBack<T>) {
        this.callBack = callBack
        startNextOrFail()
    }

    private fun startNextOrFail(){
        if(index >= fetchers.size){
            this.callBack.onFail(Exception("cannot fetch"))
            return
        }
        fetchers[index].fetchData(this)
        index++
    }

    override fun cancel() {
        isCanceled = true
    }

    override fun getDataClass(): Class<T> {
        return fetchers[0].getDataClass()
    }

}
