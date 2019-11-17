package com.baimaisu.mlide.encoder.iinterface

import com.baimaisu.mlide.encoder.EncodeOptions
import java.io.File

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
interface Encoder<T> {
    fun encode(data:T,file:File,options: EncodeOptions)

    class EncoderHelper<T>(var encoders:Encoder<T>)
}