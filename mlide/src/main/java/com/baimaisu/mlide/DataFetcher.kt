package com.baimaisu.mlide

import java.io.File
import java.io.InputStream
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
interface DataFetcher<T> {

    fun fetchData(callBack: DataCallBack<T>)
    fun cancel()
    fun getDataClass():Class<T>



    interface DataCallBack<T>{
        fun onFail(e:Exception)
        fun onReady(data:T)
    }

}

