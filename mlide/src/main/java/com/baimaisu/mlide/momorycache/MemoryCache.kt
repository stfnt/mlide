package com.baimaisu.mlide.momorycache

import android.graphics.Bitmap
import android.util.LruCache
import java.lang.ref.WeakReference


val maxSize = 1024 * 1024 * 10
object memoryCache : LruCache<String,WeakReference<Any>>(maxSize){

}