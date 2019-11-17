package com.baimaisu.mlide.engine

import android.util.Log
import com.baimaisu.mlide.request.RequestOptions
import com.baimaisu.mlide.decoder.Resource
import com.baimaisu.mlide.diskcache.DataSource
import com.baimaisu.mlide.engine.generator.DataGenerator
import com.baimaisu.mlide.engine.generator.ResourceGenerator
import com.baimaisu.mlide.engine.generator.SourceGenerator
import com.baimaisu.mlide.engine.generator.iinterface.Generator
import com.baimaisu.mlide.exception.MlideException
import com.baimaisu.mlide.mlide
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
class DecodeJob<R> :Runnable,Comparable<DecodeJob<R>> ,Generator.FetchReadyCallBack{

    lateinit var requestOptions: RequestOptions
    lateinit var engineCallBack: ResourceCallBack<R>

    lateinit var currenThread:Thread
    lateinit var decodeHelper: DecodeHelper
    lateinit var mGenerator: Generator

    var width:Int = 0
    var height:Int = 0

    var stage:Stage = Stage.INITIALIZE
    val mlideException = MlideException()
    var runReason:RunReason = RunReason.DEFAULT
    lateinit var dataWaitForDecode:Any
    fun init(requestOptions: RequestOptions, cb: ResourceCallBack<R>){
        this.requestOptions = requestOptions
        this.engineCallBack = cb

    }

    override fun run() {
        runWrapped()
    }

    override fun compareTo(other: DecodeJob<R>): Int {
        return other.requestOptions.requestPriority.compareTo(this.requestOptions.requestPriority)
    }

    fun runWrapped(){
        currenThread = Thread.currentThread()
        when(runReason){
            RunReason.DECODE_DATA-> decodeData(dataWaitForDecode)
            RunReason.SWITCH_TO_MY_THREAD-> runGenerator()
            RunReason.DEFAULT-> initAndRunGenerator()
        }
    }

    fun initAndRunGenerator(){
        stage = getNextStage(stage)
        mGenerator = getGenerator()
        runGenerator()
    }

    fun runGenerator(){
        while (true){
            if(mGenerator.start()){
                break
            }
            stage = getNextStage(stage)
            if(stage == Stage.FINISHED){
                if(mlideException.causes.isEmpty()){
                    engineCallBack.onLoadFail(Exception("not start"))
                }else{
                    engineCallBack.onLoadFail(mlideException)
                }
                break
            }

            mGenerator = getGenerator()
        }
    }

    fun getGenerator():Generator{
        return when(stage){
            Stage.DATA_CACHE ->
                DataGenerator().apply {
                    requestOptions = this@DecodeJob.requestOptions
                    fetchReadyCallBack = this@DecodeJob
                }

            Stage.RESOURCE_CACHE->
                ResourceGenerator(requestOptions,decodeHelper).apply {
                    fetchReadyCallBack = this@DecodeJob
                    width = this@DecodeJob.width
                    height = this@DecodeJob.height

                }

            Stage.SOURCE->
                SourceGenerator(decodeHelper).apply {
                    width = this@DecodeJob.width
                    height = this@DecodeJob.height
                }
            else->
                throw Exception("unknown generator")
        }
    }

    fun getNextStage(stage: Stage):Stage{
        val diskCacheStrategy = requestOptions.diskCacheStrategy
        return when(stage){
            Stage.INITIALIZE ->
                if(diskCacheStrategy.decodeFromResource()) Stage.RESOURCE_CACHE else getNextStage(Stage.RESOURCE_CACHE)

            Stage.RESOURCE_CACHE ->
                if(diskCacheStrategy.decodeFromData()) Stage.DATA_CACHE else getNextStage(Stage.DATA_CACHE)

            Stage.DATA_CACHE ->
                Stage.SOURCE

            else ->
                Stage.FINISHED
        }

    }


    enum class Stage{
        INITIALIZE,
        RESOURCE_CACHE,
        DATA_CACHE,
        SOURCE,
        ENCODE,
        FINISHED
    }


    enum class RunReason{
        SWITCH_TO_MY_THREAD,
        DECODE_DATA,
        DEFAULT
    }

    override fun onDataFetchReady(any: Any, dataSource: DataSource) {
        runReason = RunReason.DECODE_DATA
        if(Thread.currentThread() == currenThread){
            decodeData(any)
        }else{
            runReason = RunReason.DECODE_DATA
            dataWaitForDecode = any
            engineCallBack.onReschedule(this)
        }

    }

    override fun onDataFetchFailed(e: Exception) {
        mlideException.causes.add(e)
        if(Thread.currentThread() == currenThread){
            runGenerator()
        }else{
            runReason = RunReason.SWITCH_TO_MY_THREAD
            engineCallBack.onReschedule(this)
        }
    }

    fun decodeData(data:Any){
        var resource:Resource<R>? = null
        decodeHelper.getAllDecodePaths(dataClass = data.javaClass).forEach {
            if(resource == null && it.handles(data,requestOptions.decodeOptions)){
                try{
                    resource = it.decode(data,width,height,requestOptions.decodeOptions) as Resource<R>?
                }catch (e:Exception){
                    mlideException.causes.add(e)
                }

            }
        }

        if(resource != null){
            notifyResourcetoEngine(resource as Resource<R>,DataSource.DISK_DATA_CACHE)
            notifyEncode(resource as Resource<R>,DataSource.DISK_DATA_CACHE)

        }else{
            runGenerator()
        }
    }

    private  fun notifyResourcetoEngine(resource: Resource<R>,dataSource: DataSource){
        this.engineCallBack.onResourceCallBack(resource,dataSource)
    }

    fun notifyEncode(resource: Resource<R>,dataSource: DataSource){
        if(!requestOptions.diskCacheStrategy.isResourceCacheable(dataSource)){
            return
        }
        val encodePaths = mlide.registry.encoderRegistry.getEncodePath(resource.getResourceClass())
        if(encodePaths.isEmpty()){
            Log.e("axx","no encoder found")
        }else{
            encodePaths[0].apply {
                val file = mlide.diskLruCache.getFile(requestOptions.toResourceKey())
                this.encode(resource.get(),file,requestOptions.encodeOptions)
            }
        }
    }

    override fun reschedule() {
        this.runReason = RunReason.SWITCH_TO_MY_THREAD
        mlide.threadPoolExecutor.execute(this)
    }
}