package com.serenadehl.reddotmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewParent
import android.widget.Button
import androidx.core.view.children
import androidx.lifecycle.Observer
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val json = """
            {
              "type": "parent1",
              "isRed": true,
              "children": [
                {
                  "type": "child1",
                  "isRed": true,
                  "children": [
                    {
                      "type": "child11",
                      "isRed": true
                    },
                    {
                      "type": "child12",
                      "isRed": true
                    }
                  ]
                },
                {
                  "type": "child2",
                  "isRed": true,
                  "children": [
                    {
                      "type": "child21",
                      "isRed": true
                    },
                    {
                      "type": "child22",
                      "isRed": true
                    }
                  ]
                }
              ]
            }
        """.trimIndent()
        val redDotNodeModel = Gson().fromJson(json, RedDotNodeModel::class.java)
        register(redDotNodeModel, null)

//        val nodeParent1 = RedDotManager.register("parent1", true, RedDotManager.ROOT)
//        val nodeChild1 = RedDotManager.register("child1", true, nodeParent1)
//        val nodeChild2 = RedDotManager.register("child2", true, nodeParent1)
//        val nodeChild11 = RedDotManager.register("child11", true, nodeChild1)
//        val nodeChild12 = RedDotManager.register("child12", true, nodeChild1)
//        val nodeChild21 = RedDotManager.register("child21", true, nodeChild2)
//        val nodeChild22 = RedDotManager.register("child22", true, nodeChild2)

        RedDotManager.observe("parent1", this, Observer {
            parent1.visible(it)
        })
        RedDotManager.observe("child1", this, Observer {
            child1.visible(it)
        })
        RedDotManager.observe("child2", this, Observer {
            child2.visible(it)
        })
        RedDotManager.observe("child11", this, Observer {
            child11.visible(it)
        })
        RedDotManager.observe("child12", this, Observer {
            child12.visible(it)
        })
        RedDotManager.observe("child21", this, Observer {
            child21.visible(it)
        })
        RedDotManager.observe("child22", this, Observer {
            child22.visible(it)
        })

        var num = 0
        btn.setOnClickListener {
            when (num) {
                0 -> {
                    val find = RedDotManager.findNode("child22")
                    log(find.toString())
                    RedDotManager.syncNode("child22", false)
                }
                1 -> {
                    val find = RedDotManager.findNode("child21")
                    log(find.toString())
                    RedDotManager.syncNode("child21", false)
                }
                2 -> {
                    val find = RedDotManager.findNode("child11")
                    log(find.toString())
                    RedDotManager.syncNode("child11", false)
                }
                3 -> {
                    val find = RedDotManager.findNode("child12")
                    log(find.toString())
                    RedDotManager.syncNode("child12", false)
                }
            }
            num++
        }
    }

    fun register(redDotNodeModel: RedDotNodeModel, parent: RedDotNodeModel?) {
        if (parent == null) {
            RedDotManager.register(redDotNodeModel.type, redDotNodeModel.isRed, RedDotManager.ROOT)
        } else {
            RedDotManager.register(redDotNodeModel.type, redDotNodeModel.isRed, parent.type)
        }
        if (!redDotNodeModel.children.isNullOrEmpty()) {
            redDotNodeModel.children.forEach { register(it, redDotNodeModel) }
        }
    }

    private fun log(text: String) {
        Log.e("===========>", text)
    }
}

fun View.visible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}