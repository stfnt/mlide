package com.baimaisu.mlide.viewtarget

import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewTreeObserver
import com.baimaisu.mlide.request.Request
import com.baimaisu.mlide.viewtarget.iinterface.SizeReadyCallback
import java.lang.Exception

abstract class ViewTarget<T: View,R> : BaseTarget<R>(){

    override  var request: Request
    get() {
        if(tagId !=-1){
            return view.getTag(tagId) as Request
        }

        if(view.tag !is Request){
            throw Exception("unexcept view tag" + view.tag + ",which will cause to request leak")
        }

        return view.tag as Request
    }

    set(value) {
        if(tagId !=-1){
            view.setTag(tagId,value)
        }else{
            view.tag = value
        }
    }

    private var tagId:Int = -1
    lateinit var view:T
    var hasAddedAttachListener = false
    var onAttachStateChangeListener:View.OnAttachStateChangeListener? = null

    fun clearOnDetach():ViewTarget<T,R>{
        if(!hasAddedAttachListener){
            onAttachStateChangeListener = object : View.OnAttachStateChangeListener{
                override fun onViewDetachedFromWindow(v: View?) {
                    request.clear()
                }

                override fun onViewAttachedToWindow(v: View?) {
                    request.begin()
                }

            }

            hasAddedAttachListener = true
        }
        return this
    }

    override fun onClear(placeHolder: Drawable) {
        super.onClear(placeHolder)

    }


    override fun getSize(callback: SizeReadyCallback) {
        this.view.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener{
            override fun onPreDraw(): Boolean {
                val width = this@ViewTarget.view.width
                val height = this@ViewTarget.view.height
                this@ViewTarget.view.viewTreeObserver.removeOnPreDrawListener(this)
                callback.onSizeReady(width,height)
                return false
            }

        })
    }

}