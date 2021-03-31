package io.github.sunshinewzy.sunnyaurora.listeners

import io.github.sunshinewzy.sunnyaurora.SunnyAurora.Companion.sunnyScope
import io.github.sunshinewzy.sunnyaurora.bot.SABot
import io.github.sunshinewzy.sunnyaurora.bot.SABotSubscriber.serverOnContacts
import kotlinx.coroutines.launch
import org.bukkit.event.Listener
import java.util.logging.ConsoleHandler
import java.util.logging.LogRecord

object SAListener : Listener {
    val bot = SABot.getSunnyBot()
    
//    @EventHandler
//    fun onAsyncPlayerChat(e: AsyncPlayerChatEvent) {
//        sunnyScope.launch {
//            serverOnContacts.forEach {
//                it.sendMessage("[${e.player.name}] ${e.message}")
//            }
//        }
//    }
    
    fun subscribe() {
//        logger.addHandler(SALoggerHandler)
        
    }
    
    fun sendToServerOnContacts(msg: String) {
        if(serverOnContacts.isEmpty()) return
        
        sunnyScope.launch {
            serverOnContacts.forEach {
                it.sendMessage(msg)
            }
        }
    }
    
    
    object SALoggerHandler : ConsoleHandler() {
        override fun publish(record: LogRecord) {
            super.publish(record)
            
            sunnyScope.launch { 
                serverOnContacts.forEach { 
                    it.sendMessage(record.message)
                }
            }
        }
    }
    
}