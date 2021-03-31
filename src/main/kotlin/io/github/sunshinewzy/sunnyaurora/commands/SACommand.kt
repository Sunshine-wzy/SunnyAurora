package io.github.sunshinewzy.sunnyaurora.commands

import io.github.sunshinewzy.sunnyaurora.bot.SABot
import io.github.sunshinewzy.sunstcore.commands.SCommand
import org.bukkit.command.CommandSender

object SACommand : SCommand("sau") {
    
    init {
        addCommand("login") {
            SABot.login()
            
            true
        }
    }
    
    
    override fun helper(sender: CommandSender, page: Int) {
        sendSeparator(sender)
        sender.sendMessage("§6§lSunnyAurora §b命令指南 §d第 $page 页")

        when(page) {
            1 -> {
                sender.apply {
                    sendMessage("§e/sau login  §a>> 重新登录Bot")
                }
            }

            else -> sender.sendMessage("§cSunnyAurora 命令指南没有此页！")
        }

        sendSeparator(sender)
    }
    
}