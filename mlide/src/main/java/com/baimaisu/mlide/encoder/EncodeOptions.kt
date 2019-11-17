package com.baimaisu.mlide.encoder

import android.app.Activity
import android.graphics.Bitmap

/***********************************************************
 ** Copyright (C), 2008-2016, OPPO Mobile Comm Corp., Ltd.
 ** VENDOR_EDIT
 ** File:
 ** Description:
 ** Version: 1.0
 ** Date : 2019/10/16
 ** Author: liangweibin@myoas.com
 **
 ** ---------------------Revision History: ---------------------
 **  <author>	             <data> 	 <version >	    <desc>
 ** liangweibin@myoas.com    2019/10/16    1.0
 ****************************************************************/

open class Options<EncodeOptions : Options<EncodeOptions>> {
    val map:HashMap<Option<*>,Any?> = HashMap()

    fun <T:Any> put(option:Option<T>, value:T){
        map[option] = value
    }

    fun putAll(options:EncodeOptions){
        map.putAll(options.map)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T:Any> get(option:Option<T>):T?{
        return if(map.containsKey(option)) map[option] as T else option.defaultValue
    }
}



class EncodeOptions : Options<EncodeOptions>()



//class EncodeOptions {
//    val map:HashMap<Option<*>,Any?> = HashMap()
//
//    fun <T:Any> put(option:Option<T>, value:T){
//        map[option] = value
//    }
//
//    fun putAll(options:EncodeOptions){
//        map.putAll(options.map)
//    }
//
//    @Suppress("UNCHECKED_CAST")
//    fun <T:Any> get(option:Option<T>):T?{
//        return if(map.containsKey(option)) map[option] as T else option.defaultValue
//    }
//}