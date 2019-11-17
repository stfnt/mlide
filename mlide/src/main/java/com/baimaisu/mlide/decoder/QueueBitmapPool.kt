package com.baimaisu.mlide.decoder;

import android.content.ComponentCallbacks2
import android.graphics.Bitmap
import com.baimaisu.mlide.pool.BasePool
import com.baimaisu.mlide.pool.Poolable
import java.util.Map
import java.util.ArrayList
import kotlin.math.max

data class Key(val width:Int,val height: Int,val bitmapConfig:Bitmap.Config) : Poolable{
    lateinit var queueBitmapPool: QueueBitmapPool
    fun init(queueBitmapPool:QueueBitmapPool){
        this.queueBitmapPool = queueBitmapPool
    }
    override fun offer() {
        queueBitmapPool.offer(this)
    }

}


class QueueBitmapPool : BasePool<Key>(),BitmapPool {

    private val bitmapMap :LinkedHashMap<Key,ArrayList<Bitmap>> =  LinkedHashMap()

    val maxSize = 1024 * 1024 * 8
    var currentSize = 0
    override fun create(): Key {
        return Key(0,0,Bitmap.Config.ARGB_8888).apply {
            init(this@QueueBitmapPool)
        }
    }



    override fun recycleBitmap(bitmap: Bitmap) {
        val key = Key(bitmap.width,bitmap.height,bitmap.config)
        var keyNeedRecycle = true
        if(bitmapMap.get(key) == null){
            bitmapMap[key] = ArrayList()
            keyNeedRecycle = false
        }

        bitmapMap[key]!!.add(bitmap)
        if(keyNeedRecycle){
            key.offer()
        }
        currentSize+=bitmap.byteCount
    }

    override fun getBitmap(width: Int, height: Int, bitmapConfig: Bitmap.Config): Bitmap {
        val key = Key(width,height,bitmapConfig)
        var bitmap:Bitmap? = null
        bitmapMap[key]?.apply {
            if(size>0){
                bitmap = this.removeAt(0)
            }
        }

        bitmap = bitmap?:Bitmap.createBitmap(width,height,bitmapConfig)
        key.offer()
        currentSize-=bitmap!!.byteCount
        return bitmap as Bitmap
    }

    fun trimSize(maxSize:Int){
        while (true){
            val last = getLast()
            if(last == null){
                return
            }
            val size = last!!.value.size
            while (size >0 ){
                if(currentSize <= maxSize){
                    return
                }

                val bitmap = getLast()!!.value.removeAt(size-1)
                currentSize-=bitmap.byteCount
            }

            if(size == 0){
                bitmapMap.remove(last!!.key)
            }
        }
    }

    fun getLast():Map.Entry<Key,ArrayList<Bitmap>>?{
        val iterator = bitmapMap.iterator()
        var  entry: Map.Entry<Key,ArrayList<Bitmap>>? = null
        while (iterator.hasNext()){
            entry = iterator.next() as Map.Entry<Key,ArrayList<Bitmap>>
        }

        return entry

    }

    override fun trimMemory(level: Int) {
        if(level > ComponentCallbacks2.TRIM_MEMORY_BACKGROUND){
            trimSize(0)
        }else{
            trimSize(maxSize/2)
        }
    }
}
