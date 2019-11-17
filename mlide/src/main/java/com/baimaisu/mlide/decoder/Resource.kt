package com.baimaisu.mlide.decoder

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.core.graphics.drawable.toBitmap

interface Resource<T>{
    fun getSize():Int
    fun getResourceClass():Class<T>

    fun recycle()

    fun get():T
}

class BitmapResouce (var bitmap: Bitmap,var bitmapPool: BitmapPool): Resource<Bitmap>{


    override fun getSize(): Int {
        return bitmap.rowBytes * bitmap.height
    }

    override fun getResourceClass(): Class<Bitmap> {
        return Bitmap::class.java
    }

    override fun recycle() {
        bitmapPool.recycleBitmap(bitmap)
    }

    override fun get(): Bitmap {
        return bitmap;
    }

}

class DrawableResource(var drawable: BitmapDrawable,var bitmapPool: BitmapPool): Resource<BitmapDrawable>{
    override fun getSize(): Int {
        return drawable.bitmap.rowBytes * drawable.bitmap.height
    }

    override fun getResourceClass(): Class<BitmapDrawable> {
        return BitmapDrawable::class.java
    }

    override fun recycle() {
        bitmapPool.recycleBitmap(drawable.bitmap)
    }

    override fun get(): BitmapDrawable {
        return drawable.constantState!!.newDrawable() as BitmapDrawable;
    }

}