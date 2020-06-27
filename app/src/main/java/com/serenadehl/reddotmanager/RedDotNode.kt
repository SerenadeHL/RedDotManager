package com.serenadehl.reddotmanager

import androidx.lifecycle.MutableLiveData

/**
 * 文件名：RedDotNode
 * 作者：Serenade
 * 创建时间：2020-06-27 12:38:51
 * 邮箱：SerenadeHL@163.com
 * 描述：
 */
data class RedDotNode(val type: String, val parent: RedDotNode?) {
    val children = hashSetOf<RedDotNode>()
    val isRed = MutableLiveData<Boolean>().apply { value = false }
}