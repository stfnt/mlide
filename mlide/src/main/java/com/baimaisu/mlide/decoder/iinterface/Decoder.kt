package com.baimaisu.mlide.decoder.iinterface

import com.baimaisu.mlide.decoder.DecodeOptions
import com.baimaisu.mlide.decoder.Resource

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
interface Decoder<DATA,RESOURCE> {
    fun handles(data: DATA,options:DecodeOptions):Boolean
    fun decode(data: DATA,width:Int,height:Int,options: DecodeOptions):Resource<RESOURCE>?
}