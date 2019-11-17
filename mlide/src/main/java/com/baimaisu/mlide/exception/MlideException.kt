package com.baimaisu.mlide.exception

import java.lang.Exception

class MlideException : Exception() {
    val causes:MutableList<Throwable> = mutableListOf()
}