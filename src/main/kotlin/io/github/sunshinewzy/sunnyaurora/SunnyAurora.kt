package io.github.sunshinewzy.sunnyaurora

import io.github.sunshinewzy.sunnyaurora.bot.SABot
import io.github.sunshinewzy.sunnyaurora.commands.SACommand
import io.github.sunshinewzy.sunnyaurora.listeners.SAListener
import io.github.sunshinewzy.sunnyaurora.packet.SAPacket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class SunnyAurora : JavaPlugin() {

    companion object {
        private var plugin: SunnyAurora? = null
        val sunnyScope = CoroutineScope(SupervisorJob())

        val logger by lazy {
            getPlugin().logger
        }
        val pluginManager = Bukkit.getServer().pluginManager

        fun getPlugin(): SunnyAurora {
            return plugin!!
        }
    }
    
    override fun onEnable() {
        plugin = this

        regCommands()
        
        if(!File(dataFolder, "config.yml").exists())
            saveDefaultConfig()
        
        SABot.login()
        logger.info("SunnyAurora 加载成功！")
        logger.info("作者: Sunshine_wzy")
        logger.info("Bug反馈/交流Q群: 423179929")
    }

    override fun onDisable() {
        Bukkit.getScheduler().cancelTasks(this)

        logger.info("SunnyAurora 已卸载！")
    }

    private fun regCommands() {
        SACommand.init()
        
    }
    
    
    fun init() {
        regListeners()
        regBotCommands()
        SAPacket.init()
        
    }
    
    private fun regListeners() {
        pluginManager.registerEvents(SAListener, this)
        SAListener.subscribe()
        
    }
    
    private fun regBotCommands() {
        
    }
    
}