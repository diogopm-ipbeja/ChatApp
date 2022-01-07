package pt.ipbeja.chatapp.utils

val Any.TAG
    get() = javaClass.simpleName.let {
        if (it.length > 23) it.substring(0, 23) else it
    }