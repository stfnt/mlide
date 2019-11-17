package com.baimaisu.mlide.viewtarget

import android.graphics.drawable.Drawable
import android.widget.ImageView

class DrawableImageViewTarget : ImageViewTarget<Drawable> {
    constructor(imageView: ImageView) : super(imageView)

    override fun setResource(resource: Drawable) {
        imageView.setImageDrawable(resource)
    }


}