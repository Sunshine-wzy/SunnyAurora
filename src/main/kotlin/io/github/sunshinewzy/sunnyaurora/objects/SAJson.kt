package io.github.sunshinewzy.sunnyaurora.objects

import com.google.gson.Gson

object SAJson {
    val gson = Gson()
    
    fun chat(json: String): String {
        if(!json.startsWith("""
            {"extra":[{
        """.trimIndent())) return ""
        
        val chat = gson.fromJson(json, SAJChat::class.java)
        if(chat != null){
            var text = ""
            chat.extra.forEach { 
                text += it.text
            }
            return text
        }
        
        return ""
    }
    
}


interface SABean

data class SAJChat(
    val extra: Array<SAJChatExtra>,
    val text: String
) : SABean

data class SAJChatExtra(
    val color: String,
    val text: String
) : SABean