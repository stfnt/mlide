package com.baimaisu.mlide.viewtarget

import android.graphics.drawable.Drawable
import com.baimaisu.mlide.request.Request



abstract class BaseTarget<R> : Target<R>{
    open  lateinit var request: Request




    override fun onStart(placeHolder:Drawable) {

    }

    override fun onSuccess(resource: R) {
    }

    override fun onClear(placeHolder: Drawable) {
    }

    override fun onFail(errorDrawable: Drawable) {

    }
}