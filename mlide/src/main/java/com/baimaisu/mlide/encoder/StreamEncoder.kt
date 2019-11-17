package com.baimaisu.mlide.encoder

import com.baimaisu.mlide.encoder.iinterface.Encoder
import java.io.File
import java.io.FileOutputStream
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
class StreamEncoder : Encoder<InputStream> {
    override fun encode(data: InputStream, file: File, options: EncodeOptions) {
        val fos = FileOutputStream(file)
        data.copyTo(fos)
        fos.close()
    }
}