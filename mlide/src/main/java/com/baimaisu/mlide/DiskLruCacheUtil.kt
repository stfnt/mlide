package com.baimaisu.mlide

import com.jakewharton.disklrucache.DiskLruCache
import java.io.BufferedOutputStream
import java.io.File
import java.io.InputStream
import java.io.OutputStream

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
class DiskLruCacheUtil {
    companion object{
        fun saveFile(url:String,inputStream: InputStream){
            val key = mlide.keyGenerator.generateKey(url)
            val bw = mlide.diskLruCache.edit(key).newOutputStream(0)
            inputStream.copyTo(bw)
        }

        fun getInputStream(url:String):InputStream?{
            val key = mlide.keyGenerator.generateKey(url)
            val snapshot = mlide.diskLruCache.get(key)
            if(snapshot != null){
                return snapshot.getInputStream(0)
            }
            return null
        }

        fun getFile(url:String):File?{
            val key = mlide.keyGenerator.generateKey(url)
            return mlide.diskLruCache.getFile(key)
        }
    }
}
