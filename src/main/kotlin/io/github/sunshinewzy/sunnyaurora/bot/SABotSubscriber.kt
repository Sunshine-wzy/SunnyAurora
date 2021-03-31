package io.github.sunshinewzy.sunnyaurora.bot

import io.github.sunshinewzy.sunnyaurora.SunnyAurora
import io.github.sunshinewzy.sunnyaurora.SunnyAurora.Companion.getPlugin
import io.github.sunshinewzy.sunnyaurora.SunnyAurora.Companion.sunnyScope
import kotlinx.coroutines.launch
import net.mamoe.mirai.contact.Contact
import net.mamoe.mirai.event.globalEventChannel
import net.mamoe.mirai.event.subscribeMessages
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.findIsInstance
import org.bukkit.Bukkit

object SABotSubscriber {
    val bot = SABot.getSunnyBot()
    val channel = SunnyAurora.sunnyScope.globalEventChannel()
    val serverOnContacts = ArrayList<Contact>()
    
    fun subscribe() {
        channel.subscribeMessages {
            startsWith("#") {
                val msg = message.findIsInstance<PlainText>() ?: return@startsWith
                val text = msg.content.substring(1)
                val cmd = text.toLowerCase()
                
                when {
                    cmd.startsWith("server") -> {
                        val serverCmd = cmd.substringAfter("server")
                        when {
                            serverCmd.contains("t") -> {
                                serverOnContacts += subject
                                subject.sendMessage("服务器消息同步开启！")
                            }
                            
                            serverCmd.contains("f") -> {
                                serverOnContacts -= subject
                                subject.sendMessage("服务器消息同步关闭！")
                            }
                        }
                    }
                }
            }

            startsWith("/") {
                if(sender.id != SABot.getAdmin().id){
                    subject.sendMessage(At(sender) + " 您不是机器人管理员，不能执行指令！")
                    return@startsWith
                }
                
                val msg = message.findIsInstance<PlainText>() ?: return@startsWith
                val text = msg.content.substring(1)
                
                Bukkit.getScheduler().runTask(getPlugin()) {
                    try {
                        val flag = Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), text)
                        
                        sunnyScope.launch {
                            if(flag) {
                                subject.sendMessage(At(sender) + " 指令执行成功~")
                            } else {
                                subject.sendMessage(At(sender) + " 指令执行失败！")
                            }
                        }
                    } catch (ex: Exception) {
                        sunnyScope.launch {
                            subject.sendMessage(At(sender) + " 您输入的指令引发了一个异常：\n" + ex.stackTraceToString())
                        }
                    }
                }
            }
        }
    }
    
}