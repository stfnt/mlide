

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import androidx.core.content.ContextCompat
import java.io.*
import java.lang.Exception

/***********************************************************
 ** Copyright (C), 2008-2016, OPPO Mobile Comm Corp., Ltd.
 ** VENDOR_EDIT
 ** File:
 ** Description:
 ** Version: 1.0
 ** Date : 2019/10/15
 ** Author: liangweibin@myoas.com
 **
 ** ---------------------Revision History: ---------------------
 **  <author>	             <data> 	 <version >	    <desc>
 ** liangweibin@myoas.com    2019/10/15    1.0
 ****************************************************************/

object Utils{
    fun close(closeable: Closeable?){
        try{
            closeable?.close()
        }catch (e:Exception){}
    }

    fun flush(flushable: Flushable?){
        try{
            flushable?.flush()
        }catch (e:Exception){}
    }
}

fun InputStream.buf():BufferedInputStream{
    return if(this is BufferedInputStream) this else BufferedInputStream(this)
}

fun OutputStream.buf():BufferedOutputStream{
    return if(this is BufferedOutputStream) this else BufferedOutputStream(this)
}


fun InputStream.copyTo(file:File,closeInput: Boolean = true,closeOutput: Boolean = true){
    copyTo(FileOutputStream(file),closeInput,closeOutput)
}

fun InputStream.copyTo(os:OutputStream,closeInput:Boolean = true,closeOutput :Boolean= true){
    var input:BufferedInputStream? = null
    var output:BufferedOutputStream? = null
    try{
        input = this.buf()
        output = os.buf()

        val buff = ByteArray(8192)
        var len = 0
        do{
            len = input.read(buff)
            if(len == 0){
                break
            }
            output.write(buff,0,len)
        }while (true)
    }finally {
        if(closeInput){
            Utils.close(input)
        }
        if(closeOutput){
            Utils.close(output)
        }
    }

}


val handler = Handler(Looper.getMainLooper())
fun Any.ui(runnable:Runnable){
    handler.post(runnable)
}

typealias FUNC =  ()->Unit
fun Any.ui(runnable:Runnable,delay:Long){
    handler.postDelayed(runnable,delay)
}

fun ImageView.setImageDrawable(id:Int){
    val drawable =  ContextCompat.getDrawable(this.context,id)
    this.setImageDrawable(drawable)
}

fun Any.ui(func:FUNC){
    this.ui(Runnable(func))
}

fun Any.ui(func:FUNC,delay: Long){
    this.ui(Runnable(func))
}

fun Class<*>.isSelfOrSuperClass(c:Class<*>):Boolean{
    return this.isAssignableFrom(c)
}

fun File.openOutStream():OutputStream{
    return FileOutputStream(this).buf()
}

fun isValidWidth(width:Int):Boolean{
    return width>0
}


