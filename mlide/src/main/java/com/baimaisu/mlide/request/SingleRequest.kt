package com.baimaisu.mlide.request

import android.widget.ImageView
import com.baimaisu.mlide.decoder.Resource
import com.baimaisu.mlide.diskcache.DataSource
import com.baimaisu.mlide.engine.DecodeJob
import com.baimaisu.mlide.engine.Engine
import com.baimaisu.mlide.engine.ResourceCallBack
import com.baimaisu.mlide.viewtarget.Target
import com.baimaisu.mlide.viewtarget.ViewTarget
import com.baimaisu.mlide.viewtarget.iinterface.SizeReadyCallback
import isValidWidth
import java.lang.Exception

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

class SingleRequest<R> : ResourceCallBack<R>,Request,SizeReadyCallback{

    override fun onReschedule(decodeJob: DecodeJob<R>) {

    }

    override fun onResourceCallBack(resource: Resource<R>, dataSource: DataSource) {
        target.onSuccess(resource.get())
    }


    lateinit var target: Target<R>
    lateinit var requestOptions: RequestOptions



    override fun onLoadFail(e: Exception) {
    }

    enum class Status{
        PENDING,
        RUNNING,
        COMPLETE,
        FAILED,
        CLEARED

    }

    var status = Status.PENDING
    override fun begin() {
        status = Status.RUNNING
        if(isValidWidth(requestOptions.width) && isValidWidth(requestOptions.height)){
            startEngine()
            return
        }

        target.getSize(this)
    }



    fun startEngine(){
        val engine = Engine<R>()
        engine.load(requestOptions,this)
    }

    override fun clear() {
        if(isCleared()){
            return
        }

        cancel()
        status = Status.CLEARED
    }

    override fun isComplete():Boolean {
        return status == Status.COMPLETE
    }

    override fun isFailed():Boolean {
        return status == Status.FAILED
    }

    override fun isRunning():Boolean {
        return status == Status.RUNNING
    }

    override fun isCleared():Boolean {
        return status == Status.CLEARED
    }

    fun cancel(){

    }

    override fun onSizeReady(width: Int, height: Int) {
        this.requestOptions.width = width
        this.requestOptions.height = height
        startEngine()
    }
}
