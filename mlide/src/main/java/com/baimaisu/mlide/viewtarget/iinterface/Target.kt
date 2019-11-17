package com.baimaisu.mlide.viewtarget

import android.graphics.drawable.Drawable
import com.baimaisu.mlide.viewtarget.iinterface.SizeReadyCallback

interface Target<R>{

    fun onStart(placeHolder:Drawable)

    fun onSuccess(resource:R)

    fun onFail(errorDrawable:Drawable)
    fun onClear(placeHolder:Drawable)

    fun getSize(callback: SizeReadyCallback)
}