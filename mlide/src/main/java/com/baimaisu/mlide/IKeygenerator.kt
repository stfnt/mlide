package com.baimaisu.mlide

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

interface IKeyGenerator<T>{
    fun generateKey(key:T):String
}

interface KeyGenerator : IKeyGenerator<String>

abstract class KeyGeneratorFactory : Factory<KeyGenerator>()

class KeyGeneratorFactoryImpl : KeyGeneratorFactory(){
    override fun create(): KeyGenerator {
        return object : KeyGenerator{
            override fun generateKey(key: String): String {
                return Md5Util.strToMD5(key)
            }

        }
    }

}