package com.baimaisu.mlide.encoder

import android.graphics.Bitmap
import android.util.Log
import com.baimaisu.mlide.encoder.iinterface.ResourceEncoder

import openOutStream
import java.io.File
import java.io.IOException
import java.io.OutputStream

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
class BitmapResourceEncoder : ResourceEncoder<Bitmap> {

    companion object{
        val COMPRESSQUALITY : Option<Int>  = Option.memory("com.baimaisu.mlide.encoder.BitmapResourceEncoder.COMPRESSQUALITY",90)
        val COMPRESSFORMAT : Option<Bitmap.CompressFormat> = Option.memory("com.baimaisu.mlide.encoder.BitmapResourceEncoder.COMPRESSFORMAT")
    }
    override fun encode(data: Bitmap, file: File, options: EncodeOptions) {

        /**
         *  format
         *  quality
         *
         */
//        public boolean compress(CompressFormat format, int quality, OutputStream stream) {

        val format = getFormat(data,options)
        val quality = getQuality(options)
        val os = file.openOutStream()
        data.compress(format,quality,os)
        os.close()
    }

    fun getQuality(options: EncodeOptions):Int{
        return options.get(COMPRESSQUALITY) as Int
    }

    fun getFormat(data: Bitmap,options:EncodeOptions):Bitmap.CompressFormat{
        if(options.get(COMPRESSFORMAT) != null){
            return options.get(COMPRESSFORMAT) as Bitmap.CompressFormat
        }

        if(data.hasAlpha()){
            return Bitmap.CompressFormat.PNG
        }

        return Bitmap.CompressFormat.JPEG
    }
}