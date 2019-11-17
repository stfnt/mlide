package com.baimaisu.mlide.encoder

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
class Option<T> (var key:String,var defaultValue:T?) {

    companion object{

        fun <T> memory(key:String) :Option<T>{
            return memory(key,null)
        }

        fun <T> memory(key:String,defaultValue:T?) :Option<T>{
            return Option(key,defaultValue)
        }

        fun <T> disk(key:String,defaultValue:T) :Option<T>{
            return Option(key,defaultValue)
        }
    }
}