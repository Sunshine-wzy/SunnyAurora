package io.github.sunshinewzy.sunnyaurora.bot

import io.github.sunshinewzy.sunnyaurora.SunnyAurora
import io.github.sunshinewzy.sunnyaurora.SunnyAurora.Companion.getPlugin
import io.github.sunshinewzy.sunnyaurora.SunnyAurora.Companion.logger
import kotlinx.coroutines.launch
import net.mamoe.mirai.Bot
import net.mamoe.mirai.BotFactory
import net.mamoe.mirai.contact.Friend
import net.mamoe.mirai.containsFriend
import net.mamoe.mirai.utils.BotConfiguration
import java.io.File

object SABot {
    private var isLogin = false
    private var sunnyBot: Bot? = null
    private var admin: Friend? = null
    
    fun login() {
        if(isLogin)
            return
        
        SunnyAurora.sunnyScope.launch { 
            if(start()){
                logger.info("SunnyAurora 加载成功！")
                
                getPlugin().init()
                SABotSubscriber.subscribe()
                
                getAdmin().sendMessage("您的 SunnyAurora Bot 上线啦~")
                
                isLogin = true
                getSunnyBot().join()
            }
            else{
                logger.info { 
                    """
                        SunnyBot 加载失败！
                        请输入 /sau login 重新登录 SunnyAurora bot!
                        (加载失败该插件无法使用)
                    """.trimIndent()
                }
                isLogin = false
            }
        }
    }
    
    private suspend fun start(): Boolean {
        getPlugin().reloadConfig()
        
        if(!getPlugin().config.contains("qqId") || !getPlugin().config.contains("passWord")
            || getPlugin().config["qqId"] == null || getPlugin().config["passWord"] == null){
            logger.info("配置文件中缺少QQ号和密码！")
            return false
        }
        if(!getPlugin().config.contains("adminId") || getPlugin().config["adminId"] == null){
            logger.info("配置文件中缺少管理员QQ号！")
            return false
        }

        val qqId = getPlugin().config.getLong("qqId")
        val passWord = getPlugin().config.getString("passWord")
        val adminId = getPlugin().config.getLong("adminId")

        sunnyBot = BotFactory.newBot(qqId, passWord) {
            workingDir = File(getPlugin().dataFolder, "Bot")
            
            protocol = BotConfiguration.MiraiProtocol.ANDROID_PHONE
            fileBasedDeviceInfo()
        }
        getSunnyBot().login()

        if(!getSunnyBot().containsFriend(adminId)){
            logger.info("机器人管理员($adminId)与机器人(${sunnyBot?.id})" +
                    "不是好友，无法发送消息！\n" +
                    "请添加好友。")
            return false
        }

        admin = getSunnyBot().getFriend(adminId)
        if(admin == null){
            logger.info("管理员($adminId)加载失败！")
            return false
        }
        logger.info("管理员($adminId)加载成功！")

        return true
    }
    
    
    fun getSunnyBot(): Bot = sunnyBot!!
    
    fun getAdmin(): Friend = admin!!
    
}