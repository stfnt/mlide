package com.baimaisu.mlide.decoder.impl

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.baimaisu.mlide.Mlide
import com.baimaisu.mlide.decoder.BitmapResouce
import com.baimaisu.mlide.decoder.DecodeOptions
import com.baimaisu.mlide.decoder.Resource
import com.baimaisu.mlide.decoder.getBitmapPool
import com.baimaisu.mlide.decoder.iinterface.Decoder
import com.baimaisu.mlide.mlide
import java.io.InputStream

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
class StreamBitmapDecoder : Decoder<InputStream,Bitmap> {
    override fun handles(data: InputStream, options: DecodeOptions): Boolean {
        return true
    }


    override fun decode(
        data: InputStream,
        width: Int,
        height: Int,
        options: DecodeOptions
    ): Resource<Bitmap>? {

        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeStream(data,null,options);
        val sourceWidth = options.outWidth
        val sourceHeight = options.outHeight
        val scaleX:Float = sourceWidth.toFloat() / width
        val scaleY:Float = sourceHeight.toFloat() / height
        val scale = Math.max(scaleX,scaleY)
        options.apply {
            inDensity = Int.MAX_VALUE
            inTargetDensity = (Int.MAX_VALUE / scale).toInt()
            inJustDecodeBounds = false
            inBitmap = mlide.getBitmapPool().getBitmap(width,height,Bitmap.Config.ARGB_8888)
        }
        val bitmap =  BitmapFactory.decodeStream(data,null,options) as Bitmap
        return BitmapResouce(bitmap, mlide.getBitmapPool())
    }
}