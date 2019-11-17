package com.baimaisu.mlide.viewtarget

import android.widget.ImageView

abstract class ImageViewTarget<R> : ViewTarget<ImageView,R>{


    var imageView:ImageView
    constructor(imageView:ImageView){
        //region project
        this.imageView = imageView
    }

    abstract fun setResource(resource:R)


}
