package com.serenadehl.reddotmanager

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

/**
 * 文件名：RedDotManager
 * 作者：Serenade
 * 创建时间：2020-06-27 12:44:00
 * 邮箱：SerenadeHL@163.com
 * 描述：
 */
object RedDotManager {
    val ROOT = RedDotNode("root_node", null)

    fun register(type: String, red: Boolean, parent: RedDotNode?): RedDotNode {
        val redDotNode = RedDotNode(type, parent)
        redDotNode.isRed.value = red
        parent?.children?.add(redDotNode)
        return redDotNode
    }

    fun register(type: String, red: Boolean, parentType: String): RedDotNode {
        val parent = findNode(parentType)
        return register(type, red, parent)
    }

    fun observe(type: String, lifecycleOwner: LifecycleOwner, observer: Observer<Boolean>) {
        findNode(type)?.isRed?.observe(lifecycleOwner, observer)
    }

    fun syncNode(type: String, red: Boolean) {
        var node = findNode(type)
        if (node?.isRed?.value == red) return
        if (red) {
            while (node != null && node != ROOT) {
                node.isRed.value = true
                node = node.parent
            }
        } else {
            while (node != null && node != ROOT) {
                if (node.type == type) {//如果该节点是目标节点的话，直接取消红点
                    node.isRed.value = false
                } else {//如果当前节点是目标节点的上级节点，则需要上级节点检查自己的子节点是否有显示红点
                    node.isRed.value = node.children.any { it.isRed.value!! }
                }
                node = node.parent
            }
        }
    }

    fun findNode(type: String): RedDotNode? {
        return findNodeInternal(type, ROOT)
    }

    private fun findNodeInternal(type: String, node: RedDotNode): RedDotNode? {
        return if (node.type == type) {
            node
        } else {
            var targetNode: RedDotNode? = null
            for (child in node.children) {
                targetNode = findNodeInternal(type, child)
                if (targetNode != null) break
            }
            targetNode
        }
    }
}