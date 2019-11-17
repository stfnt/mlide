package com.baimaisu.mlide.viewtarget

import android.graphics.Bitmap
import android.widget.ImageView

class BitmapImageViewTarget(imageView: ImageView) : ImageViewTarget<Bitmap>(imageView) {
    override fun setResource(resource: Bitmap) {
        imageView.setImageBitmap(resource)
    }
}