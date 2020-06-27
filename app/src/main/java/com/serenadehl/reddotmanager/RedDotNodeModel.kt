package com.serenadehl.reddotmanager

/**
 * 文件名：RedDotNodeModel
 * 作者：Serenade
 * 创建时间：2020-06-27 14:25:07
 * 邮箱：SerenadeHL@163.com
 * 描述：
 */
data class RedDotNodeModel(
    val type: String,
    val isRed: Boolean,
    val children: MutableList<RedDotNodeModel>?
)